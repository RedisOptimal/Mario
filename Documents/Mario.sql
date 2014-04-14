-- MySQL dump 10.13  Distrib 5.6.16, for osx10.9 (x86_64)
--
-- Host: localhost    Database: xweb
-- ------------------------------------------------------
-- Server version	5.6.16

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `mario_node_state`
--

DROP TABLE IF EXISTS `mario_node_state`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mario_node_state` (
  `zk_id` int(10) unsigned NOT NULL,
  `path` varchar(255) NOT NULL,
  `data` mediumblob,
  `data_length` int(10) unsigned DEFAULT '0',
  `num_children` int(10) unsigned DEFAULT '0',
  `version` int(10) unsigned DEFAULT NULL,
  `aversion` int(10) unsigned DEFAULT NULL,
  `cversion` int(10) unsigned DEFAULT NULL,
  `ctime` bigint(20) unsigned DEFAULT NULL,
  `mtime` bigint(20) unsigned DEFAULT NULL,
  `czxid` bigint(20) unsigned DEFAULT NULL,
  `mzxid` bigint(20) unsigned NOT NULL DEFAULT '0',
  `pzxid` bigint(20) unsigned DEFAULT NULL,
  `ephemeral_owner` bigint(20) unsigned DEFAULT NULL,
  `state_version` int(10) unsigned zerofill NOT NULL,
  `state_time` datetime NOT NULL,
  PRIMARY KEY (`zk_id`,`path`,`mzxid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='节点数据表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mario_node_state`
--

LOCK TABLES `mario_node_state` WRITE;
/*!40000 ALTER TABLE `mario_node_state` DISABLE KEYS */;
/*!40000 ALTER TABLE `mario_node_state` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mario_plugin_info`
--

DROP TABLE IF EXISTS `mario_plugin_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mario_plugin_info` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `plugin_name` varchar(45) NOT NULL,
  `zk_id` int(10) unsigned NOT NULL,
  `msg_sender` varchar(45) NOT NULL,
  `mail_sender` varchar(45) NOT NULL,
  `phone_number` varchar(45) DEFAULT NULL,
  `email_address` varchar(45) DEFAULT NULL,
  `args` varchar(45) DEFAULT NULL,
  `commit` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `zk_id_index` (`zk_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mario_plugin_info`
--

LOCK TABLES `mario_plugin_info` WRITE;
/*!40000 ALTER TABLE `mario_plugin_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `mario_plugin_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mario_server_info`
--

DROP TABLE IF EXISTS `mario_server_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mario_server_info` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'server_id',
  `zk_id` int(10) unsigned NOT NULL,
  `host` varchar(45) NOT NULL,
  `port` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UniqueHostAndPort` (`host`,`port`),
  KEY `zk_id` (`zk_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='服务器信息';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mario_server_info`
--

LOCK TABLES `mario_server_info` WRITE;
/*!40000 ALTER TABLE `mario_server_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `mario_server_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mario_server_state`
--

DROP TABLE IF EXISTS `mario_server_state`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mario_server_state` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `server_id` int(10) unsigned NOT NULL,
  `min_latency` int(11) DEFAULT NULL,
  `ave_latency` int(11) DEFAULT NULL,
  `max_latency` int(11) DEFAULT NULL,
  `received` bigint(20) DEFAULT NULL,
  `sent` bigint(20) DEFAULT NULL,
  `outstanding` int(11) DEFAULT NULL,
  `zxid` bigint(45) DEFAULT NULL,
  `mode` varchar(45) DEFAULT NULL,
  `node_count` int(11) DEFAULT NULL,
  `total_watches` int(11) DEFAULT NULL,
  `client_number` int(11) DEFAULT NULL,
  `time_stamp` bigint(20) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `Server` (`server_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='机器状态信息';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mario_server_state`
--

LOCK TABLES `mario_server_state` WRITE;
/*!40000 ALTER TABLE `mario_server_state` DISABLE KEYS */;
/*!40000 ALTER TABLE `mario_server_state` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mario_zk_info`
--

DROP TABLE IF EXISTS `mario_zk_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mario_zk_info` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'zk_id',
  `zk_name` varchar(200) NOT NULL COMMENT '全局唯一名字',
  `session_timeout` int(10) NOT NULL,
  `observer` varchar(45) DEFAULT NULL,
  `observer_auth` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UniqueName` (`zk_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='ZK集群信息';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mario_zk_info`
--

LOCK TABLES `mario_zk_info` WRITE;
/*!40000 ALTER TABLE `mario_zk_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `mario_zk_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `xweb_menu`
--

DROP TABLE IF EXISTS `xweb_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `xweb_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `parent_id` bigint(20) NOT NULL COMMENT '父类ID',
  `parent_ids` varchar(255) DEFAULT NULL COMMENT '所有父类编号',
  `name` varchar(100) DEFAULT NULL COMMENT '菜单名称',
  `href` varchar(255) DEFAULT NULL COMMENT 'href链接名称',
  `sort` int(11) NOT NULL COMMENT '排序',
  `is_show` char(1) NOT NULL COMMENT '是否展示',
  `permission` varchar(200) DEFAULT NULL COMMENT '权限',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UQ_xweb_menu_id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 COMMENT='菜单表;';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `xweb_menu`
--

LOCK TABLES `xweb_menu` WRITE;
/*!40000 ALTER TABLE `xweb_menu` DISABLE KEYS */;
INSERT INTO `xweb_menu` VALUES (1,0,'0,','顶级菜单',NULL,0,'1',NULL),(6,1,'0,1,','服务器','',0,'1',''),(7,6,'0,1,6,','集群管理','/mario_zk_info/',0,'1',''),(8,6,'0,1,6,','服务器管理','/mario_server_info',0,'1',''),(9,6,'0,1,6,','服务器状态信息','/mario_server_state',0,'1',''),(10,1,'0,1,','节点','',2,'1',''),(11,10,'0,1,10,','节点状态','/mario_node_state',0,'1',''),(12,1,'0,1,','插件','',1,'1',''),(13,12,'0,1,12,','插件管理','/mario_plugin_info',0,'1','');
/*!40000 ALTER TABLE `xweb_menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `xweb_role`
--

DROP TABLE IF EXISTS `xweb_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `xweb_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(255) NOT NULL COMMENT '角色名称',
  `detail` varchar(255) NOT NULL COMMENT '明细',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UQ_xweb_role_id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='角色表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `xweb_role`
--

LOCK TABLES `xweb_role` WRITE;
/*!40000 ALTER TABLE `xweb_role` DISABLE KEYS */;
INSERT INTO `xweb_role` VALUES (1,'admin','超级管理员'),(2,'user','普通用户');
/*!40000 ALTER TABLE `xweb_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `xweb_role_menu`
--

DROP TABLE IF EXISTS `xweb_role_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `xweb_role_menu` (
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  `menu_id` bigint(20) NOT NULL COMMENT '菜单ID',
  PRIMARY KEY (`role_id`,`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色菜单';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `xweb_role_menu`
--

LOCK TABLES `xweb_role_menu` WRITE;
/*!40000 ALTER TABLE `xweb_role_menu` DISABLE KEYS */;
INSERT INTO `xweb_role_menu` VALUES (1,1),(1,6),(1,7),(1,8),(1,9),(1,10),(1,11),(1,12),(1,13),(2,1),(2,6),(2,9),(2,10),(2,11);
/*!40000 ALTER TABLE `xweb_role_menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `xweb_test`
--

DROP TABLE IF EXISTS `xweb_test`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `xweb_test` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `msg` varchar(200) NOT NULL,
  `detail` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 COMMENT='测试表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `xweb_test`
--

LOCK TABLES `xweb_test` WRITE;
/*!40000 ALTER TABLE `xweb_test` DISABLE KEYS */;
INSERT INTO `xweb_test` VALUES (1,'test',NULL);
/*!40000 ALTER TABLE `xweb_test` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `xweb_user`
--

DROP TABLE IF EXISTS `xweb_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `xweb_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `login_name` varchar(255) NOT NULL COMMENT '登录名',
  `name` varchar(50) DEFAULT NULL COMMENT '用户名',
  `password` varchar(255) DEFAULT NULL COMMENT '密码',
  `salt` varchar(64) DEFAULT NULL COMMENT '加密密文',
  `email` varchar(128) DEFAULT NULL COMMENT '邮箱',
  `status` varchar(32) DEFAULT NULL COMMENT '状态',
  `staff_no` varchar(50) DEFAULT NULL COMMENT '工号',
  `phone` varchar(128) DEFAULT NULL COMMENT '电话',
  `mobile` varchar(128) DEFAULT NULL COMMENT '手机号',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UQ_xweb_user_id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='用户表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `xweb_user`
--

LOCK TABLES `xweb_user` WRITE;
/*!40000 ALTER TABLE `xweb_user` DISABLE KEYS */;
INSERT INTO `xweb_user` VALUES (1,'admin','Admin','691b14d79bf0fa2215f155235df5e670b64394cc','7efbd59d9741d34f','','1','','',''),(2,'user','User','21955a5c3d51126b77e6fb219dd89ed03c000962','68562c9f3268d72d','','1','','','');
/*!40000 ALTER TABLE `xweb_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `xweb_user_role`
--

DROP TABLE IF EXISTS `xweb_user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `xweb_user_role` (
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户角色表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `xweb_user_role`
--

LOCK TABLES `xweb_user_role` WRITE;
/*!40000 ALTER TABLE `xweb_user_role` DISABLE KEYS */;
INSERT INTO `xweb_user_role` VALUES (1,1),(2,2);
/*!40000 ALTER TABLE `xweb_user_role` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-04-10 17:17:50
