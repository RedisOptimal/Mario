-- import tables data

INSERT INTO `xweb_menu` (`id`,`parent_id`,`parent_ids`,`name`,`href`,`sort`,`is_show`,`permission`) VALUES 
 (1,0,'0,','顶级菜单',NULL,0,'1',NULL),
 (2,1,'0,1,','测试菜单','',0,'1',''),
 (3,2,'0,1,2,','测试','/test',0,'1','');

 INSERT INTO `xweb_role` (`id`,`name`,`detail`) VALUES 
 (1,'admin','超级管理员'),
 (2,'user','普通用户');
 
INSERT INTO `xweb_role_menu` (`role_id`,`menu_id`) VALUES 
 (1,1),
 (1,2),
 (1,3),
 (2,1),
 (2,2),
 (2,3);

INSERT INTO `xweb_user` (`id`,`login_name`,`name`,`password`,`salt`,`email`,`status`,`staff_no`,`phone`,`mobile`) VALUES 
 (1,'admin','Admin','691b14d79bf0fa2215f155235df5e670b64394cc','7efbd59d9741d34f','yong.cao@renren-inc.com','1','CIAC010988','',''),
 (2,'user','User','21955a5c3d51126b77e6fb219dd89ed03c000962','68562c9f3268d72d','test@renren-inc.com','1','','','');

INSERT INTO `xweb_user_role` (`user_id`,`role_id`) VALUES 
 (1,1),
 (2,2);
 
INSERT INTO `xweb_test` (`id`,`msg`) VALUES (1,'test');