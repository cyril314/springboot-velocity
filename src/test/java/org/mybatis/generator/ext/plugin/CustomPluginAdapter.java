package org.mybatis.generator.ext.plugin;

import org.mybatis.generator.api.*;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * @AUTO 自定义代码生成器
 * @Author AIM
 * @DATE 2018/5/2
 */
public class CustomPluginAdapter extends PluginAdapter {

    public static SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");

    public static void main(String[] args) {
        generate();
    }

    /**
     * 生成dao
     */
    @Override
    public boolean clientGenerated(Interface interfaze, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        FullyQualifiedJavaType fqjt = new FullyQualifiedJavaType("BaseCrudDao<" + introspectedTable.getBaseRecordType() + ">");
        FullyQualifiedJavaType imp = new FullyQualifiedJavaType("com.common.base.BaseCrudDao");
        FullyQualifiedJavaType mapimp = new FullyQualifiedJavaType("org.apache.ibatis.annotations.Mapper");
        interfaze.addSuperInterface(fqjt);// 添加 extends BaseDao<User>
        interfaze.addImportedType(imp);// 添加import common.BaseDao;
        interfaze.addImportedType(mapimp);// 添加import common.BaseDao;
        interfaze.addAnnotation("@Mapper");
        interfaze.addJavaDocLine("/**\n" +
                " * @AUTO\n" +
                " * @Author AIM\n" +
                " * @DATE " + df.format(new Date()) +
                "\n */");
        interfaze.getMethods().clear();
        return true;
    }

    /**
     * 生成实体中每个属性
     */
    @Override
    public boolean modelGetterMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, ModelClassType modelClassType) {
        return true;
    }

    /**
     * 制定序列化
     */
    protected void makeSerializable(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        Field field = new Field();
        field.setFinal(true);
        field.setInitializationString("1L");
        field.setName("serialVersionUID");
        field.setStatic(true);
        field.setType(new FullyQualifiedJavaType("long"));
        field.setVisibility(JavaVisibility.PRIVATE);

        List<Field> fields = topLevelClass.getFields();
        fields.add(0, field);
    }

    /**
     * 生成实体Entity
     */
    @Override
    public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        FullyQualifiedJavaType fqjt = new FullyQualifiedJavaType("BaseEntity<" + introspectedTable.getBaseRecordType() + ">");
        topLevelClass.setSuperClass(fqjt);
        FullyQualifiedJavaType imp = new FullyQualifiedJavaType("com.common.base.BaseEntity");
        topLevelClass.addImportedType(imp);
        FullyQualifiedJavaType impDate = new FullyQualifiedJavaType("java.util.Date");
        topLevelClass.addImportedType(impDate);
        makeSerializable(topLevelClass, introspectedTable);
        topLevelClass.addJavaDocLine("/**\n" +
                " * @AUTO\n" +
                " * @Author AIM\n" +
                " * @DATE " + df.format(new Date()) +
                "\n */");
        return true;
    }

    /**
     * 生成mapping
     */
    @Override
    public boolean sqlMapGenerated(GeneratedXmlFile sqlMap, IntrospectedTable introspectedTable) {
        try {
            //使用反射在运行时把'isMergeable'强制改成false
            java.lang.reflect.Field field = sqlMap.getClass().getDeclaredField("isMergeable");
            field.setAccessible(true);
            field.setBoolean(sqlMap, false);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.sqlMapGenerated(sqlMap, introspectedTable);
    }

    /**
     * 生成mapping 添加自定义sql
     */
    @Override
    public boolean sqlMapDocumentGenerated(Document document, IntrospectedTable introspectedTable) {
        //添加自定义sql
        sqlXMLGenerated(document, introspectedTable);
        //添加自定义方法
        methodXMLGenerated_one(document, introspectedTable);
        methodXMLGenerated_two(document, introspectedTable);
        methodXMLGenerated_three(document, introspectedTable);
        methodXMLGenerated_four(document, introspectedTable);

        return true;
    }

    //生成mapping 添加自定义sql Base_Where_List
    public boolean sqlXMLGenerated(Document document, IntrospectedTable introspectedTable) {
        XmlElement parentElement = document.getRootElement();
        XmlElement sql = new XmlElement("sql");
        sql.addAttribute(new Attribute("id", "Base_Where_List"));
        XmlElement where = new XmlElement("where");
        // 加入 逻辑删除 del_flag标识 根据选择是否添加
        // where.addElement(new TextElement(" DEL_FLAG != 1 "));
        StringBuilder sb = new StringBuilder();
        for (IntrospectedColumn introspectedColumn : introspectedTable.getNonPrimaryKeyColumns()) {
            XmlElement isNotNullElement = new XmlElement("if");
            sb.setLength(0);
            sb.append(introspectedColumn.getJavaProperty() + " != null");
            if (!introspectedColumn.getJavaProperty().equals("creatdate")) {//mybatis保存日期报错
                sb.append(" and ");
                sb.append(introspectedColumn.getJavaProperty() + " != ''");
            }
            isNotNullElement.addAttribute(new Attribute("test", sb.toString()));
            where.addElement(isNotNullElement);

            sb.setLength(0);
            sb.append(" and ");
            sb.append(MyBatis3FormattingUtilities.getEscapedColumnName(introspectedColumn));
            sb.append(" = "); //$NON-NLS-1$
            sb.append(MyBatis3FormattingUtilities.getParameterClause(introspectedColumn));
            isNotNullElement.addElement(new TextElement(sb.toString()));
        }
        sql.addElement(where);
        parentElement.addElement(sql);
        return super.sqlMapDocumentGenerated(document, introspectedTable);
    }

    //生成mapping 添加自定义方法(一) 条件查询列表记录   findList
    public boolean methodXMLGenerated_one(Document document, IntrospectedTable introspectedTable) {
        XmlElement parentElement = document.getRootElement();
        XmlElement select = new XmlElement("select");
        select.addAttribute(new Attribute("id", "findList"));
        select.addAttribute(new Attribute("resultMap", "BaseResultMap"));
        select.addAttribute(new Attribute("parameterType", introspectedTable.getBaseRecordType()));
        select.addElement(new TextElement(" select "));
        XmlElement include1 = new XmlElement("include");
        include1.addAttribute(new Attribute("refid", "Base_Column_List"));
        select.addElement(include1);
        select.addElement(new TextElement(" from " + introspectedTable.getFullyQualifiedTableNameAtRuntime()));

        XmlElement include2 = new XmlElement("include");
        include2.addAttribute(new Attribute("refid", "Base_Where_List"));
        select.addElement(include2);
        select.addElement(new TextElement(
                "order by id desc \n\t<if test=\"offset != null and limit != null\">\n\tlimit ${offset}, ${limit}\n\t</if>"));
        parentElement.addElement(select);
        return super.sqlMapDocumentGenerated(document, introspectedTable);
    }

    //生成mapping 添加自定义方法(二)条件查询列表记录总数  findCount
    public boolean methodXMLGenerated_two(Document document, IntrospectedTable introspectedTable) {
        XmlElement parentElement = document.getRootElement();
        XmlElement selectCount = new XmlElement("select");
        selectCount.addAttribute(new Attribute("id", "findCount"));
        selectCount.addAttribute(new Attribute("resultType", "java.lang.Long"));
        selectCount.addElement(new TextElement("select count(1) from " + introspectedTable.getFullyQualifiedTableNameAtRuntime()));

        XmlElement include = new XmlElement("include");
        include.addAttribute(new Attribute("refid", "Base_Where_List"));
        selectCount.addElement(include);
        parentElement.addElement(selectCount);
        return super.sqlMapDocumentGenerated(document, introspectedTable);
    }

    //生成mapping 添加自定义方法(三) 条件查询列表记录   findList
    public boolean methodXMLGenerated_three(Document document, IntrospectedTable introspectedTable) {
        XmlElement parentElement = document.getRootElement();
        XmlElement select = new XmlElement("select");
        select.addAttribute(new Attribute("id", "get"));
        select.addAttribute(new Attribute("resultMap", "BaseResultMap"));
//        select.addAttribute(new Attribute("parameterType", introspectedTable.getBaseRecordType()));
        select.addAttribute(new Attribute("parameterType", "java.util.Map"));
        select.addElement(new TextElement(" select "));
        XmlElement include1 = new XmlElement("include");
        include1.addAttribute(new Attribute("refid", "Base_Column_List"));
        select.addElement(include1);
        select.addElement(new TextElement(" from " + introspectedTable.getFullyQualifiedTableNameAtRuntime()));
        select.addElement(new TextElement(" <include refid=\"Base_Where_List\" />"));

        parentElement.addElement(select);
        return super.sqlMapDocumentGenerated(document, introspectedTable);
    }

    //生成mapping 添加自定义方法(四) 条件批量伤处
    public boolean methodXMLGenerated_four(Document document, IntrospectedTable introspectedTable) {
        XmlElement parentElement = document.getRootElement();
        XmlElement select = new XmlElement("delete");
        select.addAttribute(new Attribute("id", "batchDelete"));
        select.addElement(new TextElement("delete from " + introspectedTable.getFullyQualifiedTableNameAtRuntime()));
        select.addElement(new TextElement(" where id in ("));
        select.addElement(new TextElement(" <foreach collection=\"array\" item=\"id\" separator=\",\">"));
        select.addElement(new TextElement(" #{id}"));
        select.addElement(new TextElement(" </foreach>"));
        select.addElement(new TextElement(")"));

        parentElement.addElement(select);
        return super.sqlMapDocumentGenerated(document, introspectedTable);
    }

    @Override
    public boolean sqlMapInsertElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        return false;
    }

    /**
     * 自定义插入方法
     */
    @Override
    public boolean sqlMapInsertSelectiveElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        List<Attribute> attributes = element.getAttributes();
        for (Attribute a : attributes) {
            if (a.getName().toString().trim().equals("id")) {
                attributes.remove(a);
            }
        }
        element.addAttribute(new Attribute("id", "save"));
        Collections.reverse(attributes);
        return true;
    }

    /**
     * 自定义更新方法
     */
    @Override
    public boolean sqlMapUpdateByPrimaryKeySelectiveElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        List<Attribute> attributes = element.getAttributes();
        for (Attribute a : attributes) {
            if (a.getName().toString().trim().equals("id")) {
                attributes.remove(a);
            }
        }
        element.addAttribute(new Attribute("id", "update"));
        Collections.reverse(attributes);
        return true;
    }

    @Override
    public boolean sqlMapUpdateByPrimaryKeyWithoutBLOBsElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        return false;
    }

    /**
     * 自定义删除方法
     */
    @Override
    public boolean sqlMapDeleteByPrimaryKeyElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        List<Attribute> attributes = element.getAttributes();
        for (Attribute a : attributes) {
            if (a.getName().toString().trim().equals("id")) {
                attributes.remove(a);
            }
        }
        element.addAttribute(new Attribute("id", "delete"));
        Collections.reverse(attributes);

        return true;
    }

    /**
     * 自定义查询方法
     */
    @Override
    public boolean sqlMapSelectByPrimaryKeyElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        List<Attribute> attributes = element.getAttributes();
        for (Iterator<Attribute> it = attributes.iterator(); it.hasNext(); ) {
            Attribute a = it.next();
            if (a.getName().toString().trim().equals("id")) {
                it.remove();  // ok
            }
        }
        element.addAttribute(new Attribute("id", "getById"));
        Collections.reverse(attributes);
        return true;
    }

    @Override
    public boolean sqlMapSelectByExampleWithoutBLOBsElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        // LIMIT5,10; // 检索记录行 6-15
        //      XmlElement isNotNullElement = new XmlElement("if");//$NON-NLS-1$
        //      isNotNullElement.addAttribute(new Attribute("test", "limitStart != null and limitStart >=0"));//$NON-NLS-1$ //$NON-NLS-2$
        // isNotNullElement.addElement(new
        // TextElement("limit ${limitStart} , ${limitEnd}"));
        // element.addElement(isNotNullElement);
        // LIMIT 5;//检索前 5个记录行
        return super.sqlMapSelectByExampleWithoutBLOBsElementGenerated(element, introspectedTable);
    }

    public boolean validate(List<String> arg0) {
        return true;
    }

    public static void generate() {
        String config = CustomPluginAdapter.class.getClassLoader().getResource("generatorConfig.xml").getFile();
        String[] arg = {"-configfile", config, "-overwrite"};
        ShellRunner.main(arg);
    }
}
