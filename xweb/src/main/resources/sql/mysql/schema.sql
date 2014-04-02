SET FOREIGN_KEY_CHECKS=0;


--  Drop Tables, Stored Procedures and Views 

DROP TABLE IF EXISTS xweb_menu
;
DROP TABLE IF EXISTS xweb_role
;
DROP TABLE IF EXISTS xweb_role_menu
;
DROP TABLE IF EXISTS xweb_user
;
DROP TABLE IF EXISTS xweb_user_role
;
DROP TABLE IF EXISTS xweb_test
;


--  Create Tables 
CREATE TABLE xweb_menu
(
	id BIGINT NOT NULL AUTO_INCREMENT COMMENT 'id',
	parent_id BIGINT NOT NULL COMMENT '父类ID',
	parent_ids VARCHAR(255) COMMENT '所有父类编号',
	name VARCHAR(100) COMMENT '菜单名称',
	href VARCHAR(255) COMMENT 'href链接名称',
	sort INTEGER NOT NULL COMMENT '排序',
	is_show CHAR(1) NOT NULL COMMENT '是否展示',
	permission VARCHAR(200) COMMENT '权限',
	PRIMARY KEY (id),
	UNIQUE UQ_xweb_menu_id(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='菜单表'
;

CREATE TABLE xweb_role
(
	id BIGINT NOT NULL AUTO_INCREMENT COMMENT 'id',
	name VARCHAR(255) NOT NULL COMMENT '角色名称',
	detail VARCHAR(255) NOT NULL COMMENT '明细',
	PRIMARY KEY (id),
	UNIQUE UQ_xweb_role_id(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色表'
;

CREATE TABLE xweb_role_menu
(
	role_id BIGINT NOT NULL COMMENT '角色ID',
	menu_id BIGINT NOT NULL COMMENT '菜单ID',
	PRIMARY KEY (role_id, menu_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色菜单'
;

CREATE TABLE xweb_user
(
	id BIGINT NOT NULL AUTO_INCREMENT COMMENT 'id',
	login_name VARCHAR(255) NOT NULL COMMENT '登录名',
	name VARCHAR(50) COMMENT '用户名',
	password VARCHAR(255) COMMENT '密码',
	salt VARCHAR(64) COMMENT '加密密文',
	email VARCHAR(128) COMMENT '邮箱',
	status VARCHAR(32) COMMENT '状态',
	staff_no VARCHAR(50) COMMENT '工号',
	phone VARCHAR(128) COMMENT '电话',
	mobile VARCHAR(128) COMMENT '手机号',
	PRIMARY KEY (id),
	UNIQUE UQ_xweb_user_id(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表'
;

CREATE TABLE xweb_user_role
(
	user_id BIGINT NOT NULL COMMENT '用户ID',
	role_id BIGINT NOT NULL COMMENT '角色ID',
	PRIMARY KEY (user_id, role_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户角色表'
;


CREATE TABLE xweb_test (
  id int(10) unsigned NOT NULL auto_increment,
  msg varchar(200) NOT NULL,
  detail varchar(200),
  PRIMARY KEY  (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='测试表' 
;

SET FOREIGN_KEY_CHECKS=1;
