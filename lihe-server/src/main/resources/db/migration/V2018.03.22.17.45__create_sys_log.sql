SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `token` varchar(64) DEFAULT NULL,
  `uid` varchar(64) DEFAULT NULL,
  `ip` varchar(32) DEFAULT NULL,
  `action` varchar(255) DEFAULT NULL,
  `action_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `action_cost_time` bigint(20) DEFAULT NULL,
  `action_group` varchar(255) DEFAULT NULL,
  `action_status` varchar(32) DEFAULT NULL,
  `action_status_desc` varchar(2048) DEFAULT NULL,
  `business_status` varchar(32) DEFAULT NULL,
  `business_status_desc` longtext,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;