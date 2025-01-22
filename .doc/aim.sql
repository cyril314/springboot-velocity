/*
SQLyog v10.2 
MySQL - 5.7.9 : Database - aim
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
/*Table structure for table `sys_authorities` */

DROP TABLE IF EXISTS `sys_authorities`;

CREATE TABLE `sys_authorities` (
  `id` bigint(30) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `creatDate` datetime DEFAULT NULL COMMENT '创建时间',
  `name` varchar(40) DEFAULT NULL COMMENT '权限名称',
  `desc` varchar(100) DEFAULT NULL COMMENT '权限描述',
  `enabled` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否被禁用 0禁用1正常',
  `isys` int(1) NOT NULL DEFAULT '0' COMMENT '是否是超级权限 0非1是',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='权限表';

/*Data for the table `sys_authorities` */

insert  into `sys_authorities`(`id`,`creatDate`,`name`,`desc`,`enabled`,`isys`) values (1,'2017-11-11 00:00:00','ROOT','超级管理员',1,1),(2,'2017-11-11 00:00:00','SYSTEM','系统管理员',1,0),(3,'2017-11-11 00:00:00','ADMIN','管理员',1,0),(4,'2017-11-11 00:00:00','USER','用户',1,0);

/*Table structure for table `sys_authorities_res` */

DROP TABLE IF EXISTS `sys_authorities_res`;

CREATE TABLE `sys_authorities_res` (
  `id` bigint(30) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `auth_id` bigint(30) DEFAULT NULL COMMENT '权限id',
  `res_id` bigint(30) DEFAULT NULL COMMENT '资源id',
  PRIMARY KEY (`id`),
  KEY `auth_id` (`auth_id`),
  KEY `res_id` (`res_id`),
  CONSTRAINT `sys_authorities_res_ibfk_1` FOREIGN KEY (`auth_id`) REFERENCES `sys_authorities` (`id`),
  CONSTRAINT `sys_authorities_res_ibfk_2` FOREIGN KEY (`res_id`) REFERENCES `sys_resources` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8 COMMENT='角色权限关联表';

/*Data for the table `sys_authorities_res` */

insert  into `sys_authorities_res`(`id`,`auth_id`,`res_id`) values (1,1,0),(2,1,1),(3,1,2),(4,1,3),(5,1,4),(6,1,5),(7,1,6),(8,1,7),(9,1,8),(10,4,1),(11,4,2);

/*Table structure for table `sys_resources` */

DROP TABLE IF EXISTS `sys_resources`;

CREATE TABLE `sys_resources` (
  `id` bigint(30) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `creatDate` datetime DEFAULT NULL COMMENT '创建时间',
  `name` varchar(100) DEFAULT NULL COMMENT '资源名称',
  `type` varchar(40) DEFAULT NULL COMMENT '资源类型(M:目录 C:菜单 A:按钮)',
  `icon` varchar(60) DEFAULT NULL COMMENT '资源图标',
  `priority` int(10) DEFAULT NULL COMMENT '资源优先权',
  `url` varchar(200) DEFAULT NULL COMMENT '资源链接',
  `description` varchar(200) DEFAULT NULL COMMENT '资源描述',
  `enabled` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否被禁用 0禁用1正常',
  `isys` int(1) NOT NULL DEFAULT '0' COMMENT '是否是超级权限 0非1是',
  `pid` int(30) NOT NULL DEFAULT '0' COMMENT '父级ID',
  `pname` varchar(100) DEFAULT NULL COMMENT '父级名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COMMENT='资源表';

/*Data for the table `sys_resources` */

insert  into `sys_resources`(`id`,`creatDate`,`name`,`type`,`icon`,`priority`,`url`,`description`,`enabled`,`isys`,`pid`,`pname`) values (0,'2017-11-11 00:00:00','主目录','M',NULL,1,'/admin','后台框架首页',1,0,-1,NULL),(1,'2017-11-11 00:00:00','用户管理','M','layui-icon-user',1,'#','后台框架首页',1,0,0,'主目录'),(2,'2017-11-11 00:00:00','用户列表','C','x-icon icon-user',1,'/sysuser/list','用户管理列表页',1,0,1,'用户管理'),(3,'2017-11-11 00:00:00','用户保存更新','A',NULL,1,'/sysuser/save','用户管理保存更新',1,0,1,'用户管理'),(4,'2017-11-11 00:00:00','角色列表','C',NULL,1,'/sysrole/list','角色管理的列表',1,0,1,'用户管理'),(5,'2017-11-11 00:00:00','角色保存更新','A',NULL,1,'/sysrole/save','角色保存与更新',1,0,1,'用户管理'),(6,'2017-11-11 00:00:00','权限列表','C',NULL,1,'/sysauth/list','权限管理列表',1,0,1,'用户管理'),(7,'2017-11-11 00:00:00','权限保存更新','A',NULL,1,'/sysauth/save','权限管理的保存',1,0,1,'用户管理'),(8,'2017-11-11 00:00:00','资源列表','C',NULL,1,'/sysres/list','资源管理列表',1,0,1,'用户管理'),(9,'2017-11-11 00:00:00','资源保存更新','M','',1,'/sysres/save','资源管理的保存',1,0,1,'用户管理');

/*Table structure for table `sys_role` */

DROP TABLE IF EXISTS `sys_role`;

CREATE TABLE `sys_role` (
  `id` bigint(30) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `creatDate` datetime DEFAULT NULL COMMENT '创建时间',
  `role_name` varchar(50) DEFAULT NULL COMMENT '角色名字',
  `role_desc` varchar(100) DEFAULT NULL COMMENT '角色说明',
  `enabled` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否被禁用 0禁用1正常',
  `isys` int(1) NOT NULL DEFAULT '0' COMMENT '是否是超级权限 0非1是',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='角色表';

/*Data for the table `sys_role` */

insert  into `sys_role`(`id`,`creatDate`,`role_name`,`role_desc`,`enabled`,`isys`) values (1,'2017-11-11 00:00:00','ROLE_ROOT','拥有管理后台最高权限',1,1),(2,'2017-11-11 00:00:00','ROLE_SYSTEM','拥有管理后台系统权限',1,1),(3,'2017-11-11 00:00:00','ROLE_ADMIN','拥有管理后台操作权限',1,1),(4,'2017-11-11 00:00:00','ROLE_USER_TEST','用来测试的用户角色',0,0),(5,'2017-11-11 00:00:00','ROLE_USER','普通用户角色',1,0);

/*Table structure for table `sys_role_auth` */

DROP TABLE IF EXISTS `sys_role_auth`;

CREATE TABLE `sys_role_auth` (
  `id` bigint(30) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role_id` bigint(30) DEFAULT NULL COMMENT '角色id',
  `auth_id` bigint(30) DEFAULT NULL COMMENT '权限id',
  PRIMARY KEY (`id`),
  KEY `role_id` (`role_id`),
  KEY `auth_id` (`auth_id`),
  CONSTRAINT `sys_role_auth_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`),
  CONSTRAINT `sys_role_auth_ibfk_2` FOREIGN KEY (`auth_id`) REFERENCES `sys_resources` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8 COMMENT='角色权限关联表';

/*Data for the table `sys_role_auth` */

insert  into `sys_role_auth`(`id`,`role_id`,`auth_id`) values (1,1,0),(2,1,1),(3,1,2),(4,1,3),(5,1,4),(6,1,5),(7,1,6),(8,1,7),(9,1,8),(10,1,9),(17,2,0),(18,2,1),(19,2,2),(20,2,4),(21,2,6),(22,2,8),(23,3,0),(24,3,1),(25,3,2),(26,3,4),(27,3,6),(28,3,8),(33,5,2);

/*Table structure for table `sys_user` */

DROP TABLE IF EXISTS `sys_user`;

CREATE TABLE `sys_user` (
  `id` bigint(30) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `creatDate` datetime DEFAULT NULL COMMENT '创建时间',
  `name` varchar(30) DEFAULT NULL COMMENT '用户姓名',
  `username` varchar(30) DEFAULT NULL COMMENT '登陆用户名(登陆号)',
  `password` varchar(50) DEFAULT NULL COMMENT '用户密码',
  `desc` varchar(50) DEFAULT NULL COMMENT '描述',
  `enabled` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否被禁用 0禁用1正常',
  `isys` int(1) NOT NULL DEFAULT '0' COMMENT '是否是超级用户 0非1是',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='用户表';

/*Data for the table `sys_user` */

insert  into `sys_user`(`id`,`creatDate`,`name`,`username`,`password`,`desc`,`enabled`,`isys`) values (1,'2017-11-11 00:00:00','管理员','admin','21232f297a57a5a743894a0e4a801fc3','系统超级管理员',1,1),(2,'2019-03-15 14:19:53','测试用户','user','21232f297a57a5a743894a0e4a801fc3','测试用户',0,0);

/*Table structure for table `sys_user_role` */

DROP TABLE IF EXISTS `sys_user_role`;

CREATE TABLE `sys_user_role` (
  `id` bigint(30) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role_id` bigint(30) DEFAULT NULL COMMENT '角色ID',
  `user_id` bigint(30) DEFAULT NULL COMMENT '用户ID',
  PRIMARY KEY (`id`),
  KEY `role_id` (`role_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `sys_user_role_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`),
  CONSTRAINT `sys_user_role_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='角色用户关系表';

/*Data for the table `sys_user_role` */

insert  into `sys_user_role`(`id`,`role_id`,`user_id`) values (1,1,1),(2,4,2);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
