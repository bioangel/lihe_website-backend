SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `uid` varchar(64) DEFAULT NULL,
  `ip` varchar(32) DEFAULT NULL,
  `action` varchar(255) DEFAULT NULL,
  `action_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `action_cost_time` int(11) DEFAULT NULL,
  `action_group` varchar(255) DEFAULT NULL,
  `action_status` varchar(32) DEFAULT NULL,
  `action_status_desc` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;