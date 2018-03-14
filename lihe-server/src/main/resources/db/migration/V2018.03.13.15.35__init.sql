SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for auth_account
-- ----------------------------
DROP TABLE IF EXISTS `auth_account`;
CREATE TABLE `auth_account` (
  `uuid` varchar(32) NOT NULL DEFAULT '',
  `version` int(11) NOT NULL,
  `enable` tinyint(1) NOT NULL,
  `email` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `registerTime` datetime NOT NULL,
  `organizationId` varchar(255) DEFAULT NULL COMMENT 'organization表的id组合列',
  `nickName` varchar(36) DEFAULT NULL,
  `realName` varchar(36) DEFAULT NULL,
  `sexId` int(11) DEFAULT NULL,
  `userType` int(11) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `loginCount` int(11) DEFAULT NULL,
  `user_pic` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`uuid`),
  UNIQUE KEY `UK_username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统用户管理表';

-- ----------------------------
-- Table structure for auth_authority
-- ----------------------------
DROP TABLE IF EXISTS `auth_authority`;
CREATE TABLE `auth_authority` (
  `id` varchar(32) NOT NULL COMMENT '主键，关联role_authority表的authority列',
  `version` int(11) NOT NULL,
  `enable` tinyint(1) NOT NULL,
  `name` varchar(255) NOT NULL,
  `levelCode` varchar(255) NOT NULL,
  `position` int(11) NOT NULL,
  `theValue` varchar(255) DEFAULT NULL,
  `url` varchar(255) NOT NULL,
  `matchUrl` varchar(255) NOT NULL,
  `itemIcon` varchar(255) DEFAULT NULL,
  `parentId` varchar(32) DEFAULT NULL COMMENT '与id自关联',
  `m_id` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_authority_parentId_authority` (`parentId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='权限表';

-- ----------------------------
-- Table structure for auth_authority_api
-- ----------------------------
DROP TABLE IF EXISTS `auth_authority_api`;
CREATE TABLE `auth_authority_api` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `authority_id` varchar(32) DEFAULT NULL,
  `api` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for auth_organization
-- ----------------------------
DROP TABLE IF EXISTS `auth_organization`;
CREATE TABLE `auth_organization` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `version` int(11) NOT NULL,
  `enable` tinyint(1) NOT NULL,
  `name` varchar(255) NOT NULL,
  `levelCode` varchar(255) NOT NULL,
  `position` int(11) NOT NULL,
  `theValue` varchar(255) DEFAULT NULL,
  `parentId` int(11) DEFAULT NULL COMMENT '与id自关联',
  PRIMARY KEY (`id`),
  KEY `FK_organization_parentId_organization` (`parentId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='组织机构表';

-- ----------------------------
-- Table structure for auth_role
-- ----------------------------
DROP TABLE IF EXISTS `auth_role`;
CREATE TABLE `auth_role` (
  `id` varchar(32) NOT NULL COMMENT '主键关联account表roleId',
  `version` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `enable` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统角色表';

-- ----------------------------
-- Table structure for auth_role_account
-- ----------------------------
DROP TABLE IF EXISTS `auth_role_account`;
CREATE TABLE `auth_role_account` (
  `uuid` varchar(32) NOT NULL,
  `account_uuid` varchar(32) DEFAULT NULL,
  `role_id` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for auth_role_authority
-- ----------------------------
DROP TABLE IF EXISTS `auth_role_authority`;
CREATE TABLE `auth_role_authority` (
  `roleId` varchar(32) NOT NULL COMMENT '外键关联role表的id列',
  `authorityId` varchar(32) NOT NULL COMMENT '外键关联authority表的id列',
  KEY `FK_sccf4fx8omb6jlsy2ra75xxer` (`authorityId`),
  KEY `FK_fftr98ew5vtbdpcfetn7xd715` (`roleId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='权限角色关系表';

INSERT INTO `auth_account` (`uuid`, `version`, `enable`, `email`, `username`, `password`, `registerTime`,
`organizationId`, `nickName`, `realName`, `sexId`, `userType`, `phone`, `loginCount`, `user_pic`)
VALUES ('8d1f809e5c5a4ba18f92976146b88e87', '0', '1', '', 'admin', '303a43df294400074f4ffa16907f6eec',
'2017-03-14 14:59:11', '', '', '', NULL, NULL, '', NULL, '');

INSERT INTO `auth_role` (`id`, `version`, `name`, `enable`)
VALUES ('70ff5fe5609211e78810fa163ef7fc74', '0', 'admin', '1');

INSERT INTO `auth_role_account` (`uuid`, `account_uuid`, `role_id`)
VALUES ('bdb8ed84612c11e78810fa163ef7fc74', '8d1f809e5c5a4ba18f92976146b88e87', '70ff5fe5609211e78810fa163ef7fc74');

INSERT INTO `auth_authority` (`id`, `version`, `enable`, `name`, `levelCode`, `position`, `theValue`, `url`,
`matchUrl`, `itemIcon`, `parentId`, `m_id`) VALUES ('786b0563609811e78810fa163ef7fc74', '0', '1', '用户权限', '1',
'10', 'true', '0', 'user/', 'fa-users', '0', 'user_unuse');
INSERT INTO `auth_authority` (`id`, `version`, `enable`, `name`, `levelCode`, `position`, `theValue`,
`url`, `matchUrl`, `itemIcon`, `parentId`, `m_id`) VALUES ('a40f27d3609811e78810fa163ef7fc74', '0', '1', '权限管理',
'2', '13', 'false', '0', 'user/authority', 'fa-keyboard-o', '786b0563609811e78810fa163ef7fc74', 'auth_setting');
INSERT INTO `auth_authority` (`id`, `version`, `enable`, `name`, `levelCode`, `position`, `theValue`,
`url`, `matchUrl`, `itemIcon`, `parentId`, `m_id`) VALUES ('ec0bf17a609811e78810fa163ef7fc74', '0', '1', '用户管理',
'2', '11', 'false', '0', 'user/user', 'fa-user', '786b0563609811e78810fa163ef7fc74', 'user_setting');
INSERT INTO `auth_authority` (`id`, `version`, `enable`, `name`, `levelCode`, `position`, `theValue`, `url`,
`matchUrl`, `itemIcon`, `parentId`, `m_id`) VALUES ('f6653cdb609811e78810fa163ef7fc74', '0', '1', '角色管理', '2',
'12', 'false', '0', 'user/role', 'fa-building-o', '786b0563609811e78810fa163ef7fc74', 'role_setting');

INSERT INTO `auth_authority_api` (`id`, `authority_id`, `api`)
VALUES ('1', 'a40f27d3609811e78810fa163ef7fc74', '/authority');
INSERT INTO `auth_authority_api` (`id`, `authority_id`, `api`)
VALUES ('2', 'ec0bf17a609811e78810fa163ef7fc74', '/account');
INSERT INTO `auth_authority_api` (`id`, `authority_id`, `api`)
VALUES ('3', 'ec0bf17a609811e78810fa163ef7fc74', '/org');
INSERT INTO `auth_authority_api` (`id`, `authority_id`, `api`)
VALUES ('4', 'f6653cdb609811e78810fa163ef7fc74', '/role');
INSERT INTO `auth_authority_api` (`id`, `authority_id`, `api`)
VALUES ('5', 'f6653cdb609811e78810fa163ef7fc74', '/authority');

INSERT INTO `smarthr`.`auth_role_authority` (`roleId`, `authorityId`)
VALUES ('70ff5fe5609211e78810fa163ef7fc74', '786b0563609811e78810fa163ef7fc74');
INSERT INTO `auth_role_authority` (`roleId`, `authorityId`)
VALUES ('70ff5fe5609211e78810fa163ef7fc74', 'a40f27d3609811e78810fa163ef7fc74');
INSERT INTO `auth_role_authority` (`roleId`, `authorityId`)
VALUES ('70ff5fe5609211e78810fa163ef7fc74', 'ec0bf17a609811e78810fa163ef7fc74');
INSERT INTO `auth_role_authority` (`roleId`, `authorityId`)
VALUES ('70ff5fe5609211e78810fa163ef7fc74', 'f6653cdb609811e78810fa163ef7fc74');

