package com.fit.model;

import com.common.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @AUTO 用户表
 * @Author AIM
 * @DATE 2020-08-18 11:51:00
 */
@Data
@Builder
@NoArgsConstructor //无参数的构造方法
@AllArgsConstructor //包含所有变量构造方法
public class SysUser extends BaseEntity<SysUser> {
    private static final long serialVersionUID = 1L;

    /** 主键 (主健ID) (无默认值) */
    private Long id;

    /** 创建时间 (无默认值) */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date ctime;

    /** 用户姓名 (无默认值) */
    private String name;

    /** 登陆用户名(登陆号) (无默认值) */
    private String username;

    /** 用户密码 (无默认值) */
    private String password;

    /** 描述 (无默认值) */
    private String desc;

    /** 修改时间 (无默认值) */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date etime;

    /** 是否被禁用 0禁用1正常  (默认值为: 0) */
    private Boolean enabled;

    /** 是否是超级用户 0非1是  (默认值为: 0) */
    private Integer isys;
}