use guimi;
/************增加两个用户*********************/
INSERT INTO `tb_cms_user` VALUES (1, 'superadmin', 'zhanglin@ourslook.com', '13312345678', '96e79218965eb72c92a549dd5a330112', 'superadmin', 1, 'superadmin');

/************增加两个角色*********************/
INSERT INTO `tb_cms_role` VALUES (1, '超级管理员', '超级管理员', 1);
INSERT INTO `tb_cms_role` VALUES (2, '普通管理员', '普通管理员', 1);

/************增加基础资源*********************/
INSERT INTO `tb_cms_resource` VALUES (1, 100, null, '首页', '/index.html', 0, 1);
INSERT INTO `tb_cms_resource` VALUES (2, 100, null, '后台管理', '', 2, 1);
INSERT INTO `tb_cms_resource` VALUES (3, 100, 2, '用户管理', 'userList.html', 2, 1);
INSERT INTO `tb_cms_resource` VALUES (4, 100, 2, '角色管理', 'role.html', 2, 1);
INSERT INTO `tb_cms_resource` VALUES (5, 100, 2, '资源管理', 'resources.html', 2, 1);
INSERT INTO `tb_cms_resource` VALUES (6, 100, null, '业务办理', '', 2, 1);
INSERT INTO `tb_cms_resource` VALUES (7, 100, 6, '业务管理', 'projects.html', 2, 1);

/************增加用户角色关系*********************/
INSERT INTO `tb_cms_user_role` VALUES (1, 1, 1);
INSERT INTO `tb_cms_user_role` VALUES (2, 2, 1);

/************增加角色资源*********************/
INSERT INTO `tb_cms_role_resource` VALUES (1, 1, 1);
INSERT INTO `tb_cms_role_resource` VALUES (2, 2, 1);
INSERT INTO `tb_cms_role_resource` VALUES (3, 3, 1);
INSERT INTO `tb_cms_role_resource` VALUES (4, 4, 1);
INSERT INTO `tb_cms_role_resource` VALUES (5, 5, 1);
INSERT INTO `tb_cms_role_resource` VALUES (6, 6, 1);
INSERT INTO `tb_cms_role_resource` VALUES (7, 7, 1);