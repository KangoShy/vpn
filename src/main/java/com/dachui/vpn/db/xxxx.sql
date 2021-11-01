CREATE TABLE `order_records` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(255) DEFAULT NULL,
  `order_id` varchar(255) DEFAULT NULL,
  `combo_id` varchar(255) DEFAULT NULL,
  `combo_name` varchar(255) DEFAULT NULL,
  `combo_type` varchar(255) DEFAULT NULL,
  `order_price` varchar(255) DEFAULT NULL,
  `order_status` varchar(255) DEFAULT NULL,
  `pay` varchar(255) DEFAULT NULL,
  `pay_time` varchar(255) DEFAULT NULL,
  `price` varchar(255) DEFAULT NULL,
  `failure_time` datetime DEFAULT NULL,
  `deleted` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `id` (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

/*********************************************************/
CREATE TABLE `user_center` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(255) DEFAULT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  `user_account` varchar(255) DEFAULT NULL,
  `user_password` varchar(255) DEFAULT NULL,
  `user_birthday` varchar(255) DEFAULT NULL,
  `user_age` varchar(255) DEFAULT NULL,
  `user_type` varchar(255) DEFAULT NULL,
  `deleted` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `id` (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*********************************************************/
CREATE TABLE `user_token` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL,
  `build_time` varchar(255) DEFAULT NULL,
  `token` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

/*************************模拟数据********************************/
CREATE TABLE `vpn_combo` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `combo_name` varchar(255) DEFAULT NULL,
  `combo_describe` varchar(255) DEFAULT NULL,
  `combo_unit` varchar(255) DEFAULT NULL,
  `combo_price` varchar(255) DEFAULT NULL,
  `combo_price_quarter` varchar(255) DEFAULT NULL,
  `combo_price_half_year` varchar(255) DEFAULT NULL,
  `combo_price_year` varchar(255) DEFAULT NULL,
  `vpn_ip` varchar(255) DEFAULT NULL,
  `vpn_password` varchar(255) DEFAULT NULL,
  `vpn_config` varchar(255) DEFAULT NULL,
  `deleted` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `id` (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

INSERT INTO `vpn`.`vpn_combo`(`id`, `combo_name`, `combo_describe`, `combo_unit`, `combo_price`, `combo_price_quarter`, `combo_price_half_year`, `combo_price_year`, `vpn_ip`, `vpn_password`, `vpn_config`, `deleted`, `create_time`, `update_time`) VALUES (1, '匀速套餐', '高速流畅-支持全部设备', '元', '20', '55', '100', '220', NULL, NULL, NULL, '0', NULL, NULL);
INSERT INTO `vpn`.`vpn_combo`(`id`, `combo_name`, `combo_describe`, `combo_unit`, `combo_price`, `combo_price_quarter`, `combo_price_half_year`, `combo_price_year`, `vpn_ip`, `vpn_password`, `vpn_config`, `deleted`, `create_time`, `update_time`) VALUES (2, '高速套餐', '高速流畅-支持全部设备', '元', '40', '109', '200', '420', NULL, NULL, NULL, '0', NULL, NULL);
INSERT INTO `vpn`.`vpn_combo`(`id`, `combo_name`, `combo_describe`, `combo_unit`, `combo_price`, `combo_price_quarter`, `combo_price_half_year`, `combo_price_year`, `vpn_ip`, `vpn_password`, `vpn_config`, `deleted`, `create_time`, `update_time`) VALUES (3, '极速套餐', '高速流畅-支持全部设备', '元', '60', '169', '300', '620', NULL, NULL, NULL, '0', NULL, NULL);
INSERT INTO `vpn`.`vpn_combo`(`id`, `combo_name`, `combo_describe`, `combo_unit`, `combo_price`, `combo_price_quarter`, `combo_price_half_year`, `combo_price_year`, `vpn_ip`, `vpn_password`, `vpn_config`, `deleted`, `create_time`, `update_time`) VALUES (4, '工作室套餐', '高速流畅-支持全部设备', '元', '90', '250', '480', '980', NULL, NULL, NULL, '0', NULL, NULL);
INSERT INTO `vpn`.`vpn_combo`(`id`, `combo_name`, `combo_describe`, `combo_unit`, `combo_price`, `combo_price_quarter`, `combo_price_half_year`, `combo_price_year`, `vpn_ip`, `vpn_password`, `vpn_config`, `deleted`, `create_time`, `update_time`) VALUES (5, '优惠套餐', '高速流畅-支持全部设备', '元', '28', '60', '119', '249', NULL, NULL, NULL, '0', NULL, NULL);

/*************************模拟数据********************************/
CREATE TABLE `user_know` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `text` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `id` (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

INSERT INTO `vpn`.`user_know`(`id`, `text`) VALUES (1, 'VPN帐号是指在校外通过学校VPN服务进入校内网络的帐号。所有学校正式教职员工和学生（可以登录“今日校园”、“一站式事务中心”人员），均自动获得VPN帐号（即个人的学号和工号），无须申请。');
INSERT INTO `vpn`.`user_know`(`id`, `text`) VALUES (2, '学校二级单位（学院或部门）具有申请资格。二级单位下属机构或挂靠单位需要使用域名的，应由其主管单位出面申请。个人不得申请。');
INSERT INTO `vpn`.`user_know`(`id`, `text`) VALUES (3, '因为行政管理、教学和科研等工作，确需从校外访问校内网络的本校二级单位及其下属机构（含挂靠单位）固定组成人员，可申请VPN帐号。');
INSERT INTO `vpn`.`user_know`(`id`, `text`) VALUES (4, '校外人员原则上不得申请VPN帐号，确有特殊需要的，应事先取得校人事处（国内人员）或外事主管单位（外籍人士）的批复意见，并设定严格的帐号有效期。');
INSERT INTO `vpn`.`user_know`(`id`, `text`) VALUES (5, '所有持有VPN帐号的人员，均须严格遵守国家关于互联网使用的法律、法规和学校的有关规定，不得转借、转让VPN帐号，不得利用VPN帐号侵犯知识产权，或从事以获取商业利益为最终目的的其他活动。');
INSERT INTO `vpn`.`user_know`(`id`, `text`) VALUES (6, 'VPN帐号到期后将自动予以注销，用户无须单独办理注销手续。');
INSERT INTO `vpn`.`user_know`(`id`, `text`) VALUES (7, '帐号注销后，如果仍需继续使用VPN校外接入，请重新提出申请。');
INSERT INTO `vpn`.`user_know`(`id`, `text`) VALUES (8, '长期无人使用的VPN帐号，将予以注销。');

