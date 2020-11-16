/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50612
Source Host           : localhost:3306
Source Database       : cc

Target Server Type    : MYSQL
Target Server Version : 50612
File Encoding         : 65001

Date: 2020-11-16 18:23:25
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `board`
-- ----------------------------
DROP TABLE IF EXISTS `board`;
CREATE TABLE `board` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `sn` varchar(255) DEFAULT NULL COMMENT 'sn序号',
  `mac` varchar(255) DEFAULT NULL COMMENT 'mac地址',
  `mcu_id` varchar(255) DEFAULT NULL COMMENT '机器ID',
  `board_type` varchar(255) DEFAULT NULL COMMENT '测试版类型',
  `org_id` bigint(20) DEFAULT NULL COMMENT '厂商ID',
  `test_status` varchar(11) DEFAULT NULL COMMENT '濞村鐦悩鑸碘偓?濞村鐦稉?瀹告彃鍙嗘惔?瀹告彃鐣幋?3閸戝搫绨辨稉?瀹告彃鍤惔?',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint(20) DEFAULT NULL COMMENT '创建人',
  `test_time` datetime DEFAULT NULL,
  `test_user` bigint(20) DEFAULT NULL,
  `putin_time` datetime DEFAULT NULL COMMENT '入库时间(入库时间不一定就是创建时间)',
  `putin_user` bigint(20) DEFAULT NULL COMMENT '入库人(入库人不一定就是创建人)',
  `out_time` datetime DEFAULT NULL COMMENT '出库时间',
  `putin_num` varchar(255) DEFAULT NULL COMMENT '入库编号',
  `out_user` bigint(20) DEFAULT NULL COMMENT '出库人',
  `out_num` varchar(255) DEFAULT NULL COMMENT '出库单号',
  `esn` varchar(255) DEFAULT NULL COMMENT '工装序号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=194 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of board
-- ----------------------------
INSERT INTO `board` VALUES ('191', 'A11201000006', '000000000000', null, 'A1', '2', '6', '2020-10-23 16:52:07', '5', '2020-10-23 17:45:49', '5', '2020-11-10 16:46:36', '5', '2020-11-16 10:46:08', 'A112011009', '5', '1201101', null);
INSERT INTO `board` VALUES ('192', 'A11201000007', '000000000001', null, 'A1', '2', '6', '2020-11-10 16:32:55', '5', '2020-11-10 16:33:00', '5', '2020-11-10 16:46:36', '5', '2020-11-16 10:46:08', 'A112011009', '5', '1201101', '');
INSERT INTO `board` VALUES ('193', 'A11201000008', '5556612', null, 'A1', '2', '4', '2020-11-16 11:39:12', '5', '2020-11-16 11:39:12', '5', '2020-11-16 18:09:21', '5', null, 'A112011016', null, null, null);

-- ----------------------------
-- Table structure for `dict`
-- ----------------------------
DROP TABLE IF EXISTS `dict`;
CREATE TABLE `dict` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `val` varchar(64) DEFAULT NULL,
  `tag` varchar(64) DEFAULT NULL,
  `type_id` bigint(64) DEFAULT NULL,
  `explain` varchar(64) DEFAULT NULL COMMENT '说明',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dict
-- ----------------------------
INSERT INTO `dict` VALUES ('1', '1', '板类型1', '1', null, '1');
INSERT INTO `dict` VALUES ('2', '2', '板类型2', '1', null, '2');
INSERT INTO `dict` VALUES ('3', '3', '板类型3', '1', '', null);
INSERT INTO `dict` VALUES ('5', 'A1', '桌面款主板', '1', null, '10');
INSERT INTO `dict` VALUES ('6', 'A2', '灯板', '1', null, '30');

-- ----------------------------
-- Table structure for `dict_type`
-- ----------------------------
DROP TABLE IF EXISTS `dict_type`;
CREATE TABLE `dict_type` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `type_name` varchar(255) NOT NULL COMMENT '分类',
  `type` varchar(255) NOT NULL COMMENT '分类id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dict_type
-- ----------------------------
INSERT INTO `dict_type` VALUES ('1', '电路板类型', 'board_type');

-- ----------------------------
-- Table structure for `menu`
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL COMMENT '菜单名称',
  `pid` bigint(20) DEFAULT NULL COMMENT '父id',
  `pids` varchar(255) DEFAULT NULL COMMENT '所有父级id',
  `url` varchar(255) DEFAULT NULL COMMENT 'url地址',
  `permission` varchar(255) DEFAULT NULL COMMENT '权限',
  `img` varchar(255) DEFAULT NULL COMMENT '图片',
  `seq` int(11) DEFAULT NULL COMMENT '顺序',
  `status` int(11) DEFAULT NULL COMMENT '0禁用1启用',
  `level` int(11) DEFAULT NULL COMMENT '菜单级别',
  `temp1` varchar(200) DEFAULT NULL COMMENT '备用字段',
  `temp2` varchar(200) DEFAULT NULL COMMENT '备用字段',
  `ct` datetime DEFAULT NULL COMMENT '创建时间',
  `mt` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of menu
-- ----------------------------
INSERT INTO `menu` VALUES ('3', '厂商管理', '-1', '-1', 'vendorManagement', '', '', '2', '1', '1', null, null, '2020-08-26 14:56:23', null);
INSERT INTO `menu` VALUES ('4', '用户管理', '-1', '-1', 'userManagement', '', '', '2', '1', '1', null, null, '2020-08-26 14:56:38', null);
INSERT INTO `menu` VALUES ('5', '电路板检测', '-1', '-1', 'circuitBoardInspection', null, null, '6', '1', '1', null, null, null, null);
INSERT INTO `menu` VALUES ('6', '出库管理', '-1', '-1', 'outboundManagement', null, null, '5', '1', '1', null, null, null, null);
INSERT INTO `menu` VALUES ('7', '电路板台帐', '-1', '-1', 'accountBook', null, null, '4', '1', '1', null, null, null, null);
INSERT INTO `menu` VALUES ('8', '系统管理', '-1', '-1', 'systemManagement', null, null, '3', '1', '1', null, null, null, null);
INSERT INTO `menu` VALUES ('9', '角色管理', '8', '-1,8', 'role', null, null, '2', '1', '2', null, null, null, null);
INSERT INTO `menu` VALUES ('10', '字典表管理', '8', '-1,8', 'dictionaryTable', null, null, '1', '1', '2', null, null, null, null);
INSERT INTO `menu` VALUES ('11', '字典表类型管理', '8', '-1,8', 'dictionaryType', null, null, '3', '1', '2', null, null, null, null);

-- ----------------------------
-- Table structure for `org`
-- ----------------------------
DROP TABLE IF EXISTS `org`;
CREATE TABLE `org` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL COMMENT '组织名称',
  `num` varchar(255) DEFAULT NULL COMMENT '厂商编号',
  `address` varchar(255) DEFAULT NULL COMMENT '厂商地址',
  `contacts` varchar(20) DEFAULT NULL COMMENT '联系人',
  `phone` varchar(255) DEFAULT NULL COMMENT '联系电话',
  `ip_address` varchar(255) DEFAULT NULL COMMENT '外网IP地址，用于与通信端通信',
  `temp1` varchar(255) DEFAULT NULL,
  `temp2` varchar(255) DEFAULT NULL,
  `ct` datetime DEFAULT NULL COMMENT '创建时间',
  `mt` datetime DEFAULT NULL COMMENT '修改时间',
  `is_delete` int(1) NOT NULL DEFAULT '1' COMMENT '0删除1正常',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='厂商表';

-- ----------------------------
-- Records of org
-- ----------------------------
INSERT INTO `org` VALUES ('2', '策创3', '1', '中海国际中心', 'hy', '135897897979', 'http://127.0.0.1:10001', null, null, '2020-08-26 17:25:46', null, '1');
INSERT INTO `org` VALUES ('3', '测试', '2', '1', '1', '1', '127.0.0.1', null, null, '2020-09-22 14:26:16', null, '1');

-- ----------------------------
-- Table structure for `role`
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL COMMENT '角色名称',
  `status` int(11) DEFAULT NULL COMMENT '0不可用1可用',
  `remark` varchar(255) DEFAULT NULL COMMENT '描述',
  `ct` datetime DEFAULT NULL COMMENT '创建时间',
  `mt` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('1', '超级管理员', '1', '超管，不可编辑', null, null);
INSERT INTO `role` VALUES ('2', '测试', '1', '测试角色', '2020-08-27 15:22:39', null);
INSERT INTO `role` VALUES ('3', '测试2', '1', null, null, null);

-- ----------------------------
-- Table structure for `role_menu`
-- ----------------------------
DROP TABLE IF EXISTS `role_menu`;
CREATE TABLE `role_menu` (
  `role_id` bigint(20) NOT NULL,
  `menu_id` bigint(20) NOT NULL,
  PRIMARY KEY (`role_id`,`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role_menu
-- ----------------------------
INSERT INTO `role_menu` VALUES ('2', '3');
INSERT INTO `role_menu` VALUES ('2', '4');
INSERT INTO `role_menu` VALUES ('2', '5');
INSERT INTO `role_menu` VALUES ('2', '6');
INSERT INTO `role_menu` VALUES ('2', '7');
INSERT INTO `role_menu` VALUES ('2', '8');
INSERT INTO `role_menu` VALUES ('2', '9');
INSERT INTO `role_menu` VALUES ('2', '10');
INSERT INTO `role_menu` VALUES ('2', '11');

-- ----------------------------
-- Table structure for `sn_log`
-- ----------------------------
DROP TABLE IF EXISTS `sn_log`;
CREATE TABLE `sn_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `mac` varchar(255) DEFAULT NULL,
  `mcu` varchar(255) DEFAULT NULL,
  `sn` varchar(255) DEFAULT NULL,
  `status` int(11) DEFAULT NULL COMMENT '1成功0失败',
  `ct` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sn_log
-- ----------------------------
INSERT INTO `sn_log` VALUES ('6', '000000000000', null, 'A11201000006', '1', '2020-10-23 17:31:57');

-- ----------------------------
-- Table structure for `t_yz_vehicle`
-- ----------------------------
DROP TABLE IF EXISTS `t_yz_vehicle`;
CREATE TABLE `t_yz_vehicle` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `vehicle_no` varchar(32) DEFAULT NULL COMMENT '车辆号牌',
  `color` varchar(16) DEFAULT NULL COMMENT '车牌颜色编码',
  `vehicle_type` varchar(16) DEFAULT NULL COMMENT '车辆类型编码',
  `business_state` varchar(16) DEFAULT NULL COMMENT '车辆营运状态编码',
  `tenant_id` varchar(64) DEFAULT NULL COMMENT '业户编号',
  `operLine_Id` varchar(64) DEFAULT NULL COMMENT '客运路线编号，无客运路线为-1',
  `license_code` varchar(128) DEFAULT NULL COMMENT '道路经营许可证号',
  `trans_certificate_code` varchar(128) DEFAULT NULL COMMENT '道路运输证号',
  `platform` varchar(64) DEFAULT NULL COMMENT '卫星定位平台运营商',
  `device_type` varchar(32) DEFAULT NULL COMMENT '卫星定位设备型号',
  `technical_class` varchar(16) DEFAULT NULL COMMENT '车辆技术等级',
  `curr_examined_time` varchar(255) DEFAULT NULL COMMENT '本次年审日期',
  `next_examined_time` varchar(255) DEFAULT NULL COMMENT '下次年审日期',
  `create_time` datetime NOT NULL,
  `update_time` datetime DEFAULT NULL,
  `type` varchar(16) DEFAULT NULL COMMENT '联网联控车辆类型编码',
  PRIMARY KEY (`id`),
  KEY `index_trans_code` (`trans_certificate_code`),
  KEY `index_tenant_id` (`tenant_id`)
) ENGINE=InnoDB AUTO_INCREMENT=131671 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_yz_vehicle
-- ----------------------------
INSERT INTO `t_yz_vehicle` VALUES ('1599', '藏AL3095', '1', 'K31', '10', 'XZS54000000000005401000000010100', null, '540100010022', '540100033341', '西藏金采科技股份有限公司', 'DS-M3506HM-K/GE', '1', '202003   ', '202109   ', '2020-08-01 12:00:00', '2020-08-01 12:00:00', null);
INSERT INTO `t_yz_vehicle` VALUES ('1601', '藏AL3738', '2', 'K11', '10', 'XZS54000000000005401000000011010', null, '540100010917', '540100037611', '西藏金采科技股份有限公司', 'DS-M3506HM-K/GE', '1', '202003   ', '202103   ', '2020-08-01 12:00:00', '2020-08-01 12:00:00', null);
INSERT INTO `t_yz_vehicle` VALUES ('1700', '藏AL3321', '1', 'K31', '10', 'XZS54000000000005401000000010100', null, '540100010022', '540100033995', '西藏金采科技股份有限公司', 'DS-M3506HM-K/GE', '1', '202004   ', '202101   ', '2020-08-01 12:00:00', '2020-08-01 12:00:00', null);
INSERT INTO `t_yz_vehicle` VALUES ('1743', '藏AL5690', '2', 'K21', '10', 'XZS54000000000005401000000011010', null, '540100010917', '540100039601', '西藏金采科技股份有限公司', 'DS-M3506HM-K/GE', '1', '202003   ', '202103   ', '2020-08-01 12:00:00', '2020-08-01 12:00:00', null);
INSERT INTO `t_yz_vehicle` VALUES ('1837', '藏AL3582', '2', 'K11', '10', 'XZS54000000000005401000000011216', null, '540100011125', '540100037216', '西藏金采科技股份有限公司', 'DS-M3506HM-K/GE', '1', '202003   ', '202103   ', '2020-08-01 12:00:00', '2020-08-01 12:00:00', null);
INSERT INTO `t_yz_vehicle` VALUES ('1847', '藏AL3507', '2', 'K11', '10', 'XZS54000000000005401000000011010', null, '540100010917', '540100037153', '西藏金采科技股份有限公司', 'DS-M3506HM-K/GE', '1', '202003   ', '202103   ', '2020-08-01 12:00:00', '2020-08-01 12:00:00', null);
INSERT INTO `t_yz_vehicle` VALUES ('1867', '藏AL5225', '2', 'K11', '10', 'XZS54000000000005401000000011216', null, '540100011125', '540100038601', '西藏金采科技股份有限公司', 'DS-M3506HM-K/GE', '1', '202002   ', '202102   ', '2020-08-01 12:00:00', '2020-08-01 12:00:00', null);
INSERT INTO `t_yz_vehicle` VALUES ('7335', '藏AL5113', '2', 'K11', '10', 'XZS54000000000005401000000011216', null, '540100011125', '540100038320', '西藏金采科技股份有限公司', 'DS-M3506HM-K/GE', '1', '202002   ', '202102   ', '2020-08-01 12:00:00', '2020-08-01 12:00:00', null);
INSERT INTO `t_yz_vehicle` VALUES ('7438', '藏AL3507', '1', 'K31', '10', 'XZS54000000000005401000000010100', '', '540100010022', '540100034163', '西藏金采科技股份有限公司', 'DS-M3506HM-K/GE', '1', '202011', '202105', '2020-08-01 12:00:00', '2020-11-02 20:00:31', null);
INSERT INTO `t_yz_vehicle` VALUES ('7440', '藏AL3228', '1', 'K31', '10', 'XZS54000000000005401000000010100', null, '540100010022', '540100033818', '北京中交慧联信息科技有限公司', 'DS-M3506HM-K/GE', '1', '202006   ', '202012   ', '2020-08-01 12:00:00', '2020-08-01 12:00:00', null);
INSERT INTO `t_yz_vehicle` VALUES ('7455', '藏AL3582', '1', 'K31', '10', 'XZS54000000000005401000000010100', '', '540100010022', '540100034483', '西藏金采科技股份有限公司', 'DS-M3506HM-K/GE', '1', '202004', '202010', '2020-08-01 12:00:00', '2020-10-12 18:59:48', null);
INSERT INTO `t_yz_vehicle` VALUES ('7693', '藏AL5172', '2', 'K11', '10', 'XZS54000000000005401000000011010', '', '540100010917', '540100038404', '西藏金采科技股份有限公司', 'DS-M3506HM-K/GE', '1', '202003', '202103', '2020-08-01 12:00:00', '2020-09-24 17:59:12', null);
INSERT INTO `t_yz_vehicle` VALUES ('7712', '藏AL5292', '2', 'K11', '10', 'XZS54000000000005401000000011010', null, '540100010917', '540100038789', '西藏金采科技股份有限公司', 'DS-M3506HM-K/GE', '1', '202002   ', '202102   ', '2020-08-01 12:00:00', '2020-08-01 12:00:00', null);
INSERT INTO `t_yz_vehicle` VALUES ('13155', '藏AL5071', '2', 'K11', '10', 'XZS54000000000005401000000011216', null, '540100011125', '540100038283', '北京中交慧联信息科技有限公司', 'DS-M3506HM-K/GE', '1', '202002   ', '202102   ', '2020-08-01 12:00:00', '2020-08-01 12:00:00', null);
INSERT INTO `t_yz_vehicle` VALUES ('13209', '藏AL3136', '2', 'K11', '10', 'XZS54000000000005401000000010100', '', '540100010022', '540100034237', '西藏金采科技股份有限公司', 'DS-M3506HM-K/GE', '1', '202009', '202103', '2020-08-01 12:00:00', '2020-09-24 17:59:12', null);
INSERT INTO `t_yz_vehicle` VALUES ('13322', '藏AL3612', '2', 'K11', '10', 'XZS54000000000005401000000011216', null, '540100011125', '540100037384', '西藏金采科技股份有限公司', 'DS-M3506HM-K/GE', '1', '202003   ', '202103   ', '2020-08-01 12:00:00', '2020-08-01 12:00:00', null);
INSERT INTO `t_yz_vehicle` VALUES ('13333', '藏AL3562', '2', 'K11', '21', 'XZS54000000000005401000000011216', null, '540100011125', '540100037171', '西藏金采科技股份有限公司', 'DS-M3506HM-K/GE', '1', '202002   ', '202102   ', '2020-08-01 12:00:00', '2020-08-01 12:00:00', null);
INSERT INTO `t_yz_vehicle` VALUES ('13436', '藏AL3701', '1', 'K31', '10', 'XZS54000000000005401000000011216', null, '540100011125', '540100038859', '西藏金采科技股份有限公司', 'DS-M3506HM-K/GE', '1', '202003   ', '202103   ', '2020-08-01 12:00:00', '2020-08-01 12:00:00', null);
INSERT INTO `t_yz_vehicle` VALUES ('13830', '藏AL3821', '1', 'K31', '21', 'XZS54000000000005401000000010100', null, '540100010022', '540100045237', '西藏金采科技股份有限公司', 'DS-M3506HM-K/GE', '1', '201903   ', '202003   ', '2020-08-01 12:00:00', '2020-08-01 12:00:00', null);
INSERT INTO `t_yz_vehicle` VALUES ('18908', '藏AL3222', '1', 'K31', '10', 'XZS54000000000005401000000010100', '', '540100010022', '540100033845', '西藏金采科技股份有限公司', 'DS-M3506HM-K/GE', '1', '202009', '202103', '2020-08-01 12:00:00', '2020-09-09 23:22:18', null);
INSERT INTO `t_yz_vehicle` VALUES ('19006', '藏AL5595', '2', 'K21', '10', 'XZS54000000000005401000000010100', null, '540100010022', '540100039713', '西藏金采科技股份有限公司', 'DS-M3506HM-K/GE', '1', '202003   ', '202103   ', '2020-08-01 12:00:00', '2020-08-01 12:00:00', null);
INSERT INTO `t_yz_vehicle` VALUES ('19052', '藏AL3731', '1', 'K31', '10', 'XZS54000000000005401000000011010', '', '540100010917', '540100039675', '西藏金采科技股份有限公司', 'DS-M3506HM-K/GE', '1', '202003', '202103', '2020-08-01 12:00:00', '2020-09-03 23:22:06', null);
INSERT INTO `t_yz_vehicle` VALUES ('19195', '藏AL5308', '2', 'K11', '10', 'XZS54000000000005401000000010100', null, '540100010022', '540100038761', '西藏金采科技股份有限公司', 'DS-M3506HM-K/GE', '1', '202002   ', '202102   ', '2020-08-01 12:00:00', '2020-08-01 12:00:00', null);
INSERT INTO `t_yz_vehicle` VALUES ('19202', '藏AL5331', '2', 'K11', '21', 'XZS54000000000005401000000011216', '', '540100011125', '540100039148', '西藏金采科技股份有限公司', 'DS-M3506HM-K/GE', '1', '202003', '202103', '2020-08-01 12:00:00', '2020-11-04 18:00:35', null);
INSERT INTO `t_yz_vehicle` VALUES ('24312', '藏AL6037', '2', 'K21', '21', 'XZS54000000000005401000000010129', '', '540100010049', '540100033395', '西藏金采科技股份有限公司', 'HB-DV06', '1', '202005', '202011', '2020-08-01 12:00:00', '2020-10-26 18:00:17', null);
INSERT INTO `t_yz_vehicle` VALUES ('24478', '藏AL5115', '2', 'K11', '10', 'XZS54000000000005401000000010100', null, '540100010022', '540100038321', '西藏金采科技股份有限公司', 'DS-M3506HM-K/GE', '1', '202003   ', '202103   ', '2020-08-01 12:00:00', '2020-08-01 12:00:00', null);
INSERT INTO `t_yz_vehicle` VALUES ('24633', '藏AL3766', '1', 'K31', '10', 'XZS54000000000005401000000011216', '', '540100011125', '540100039762', '西藏金采科技股份有限公司', 'DS-M3506HM-K/GE', '1', '202004', '202104', '2020-08-01 12:00:00', '2020-09-03 23:22:06', null);
INSERT INTO `t_yz_vehicle` VALUES ('24648', '藏AL5673', '2', 'K11', '10', 'XZS54000000000005401000000011010', null, '540100010917', '540100039541', '西藏金采科技股份有限公司', 'DS-M3506HM-K/GE', '1', '202003   ', '202103   ', '2020-08-01 12:00:00', '2020-08-01 12:00:00', null);
INSERT INTO `t_yz_vehicle` VALUES ('24723', '藏AL3321', '2', 'K11', '10', 'XZS54000000000005401000000011010', null, '540100010917', '540100037046', '西藏金采科技股份有限公司', 'DS-M3506HM-K/GE', '1', '202002   ', '202102   ', '2020-08-01 12:00:00', '2020-08-01 12:00:00', null);
INSERT INTO `t_yz_vehicle` VALUES ('24878', '藏AL3875', '2', 'K11', '10', 'XZS54000000000005401000000010100', null, '540100010022', '540100038592', '西藏金采科技股份有限公司', 'DS-M3506HM-K/GE', '1', '202004   ', '202104   ', '2020-08-01 12:00:00', '2020-08-01 12:00:00', null);
INSERT INTO `t_yz_vehicle` VALUES ('29810', '藏AL3222', '2', 'K21', '10', 'XZS54000000000005401000000011010', null, '540100010917', '540100025836', '西藏金采科技股份有限公司', 'DS-M3506HM-K/GE', '1', '202008   ', '202102   ', '2020-08-01 12:00:00', '2020-08-01 12:00:00', null);
INSERT INTO `t_yz_vehicle` VALUES ('29939', '藏AL3136', '1', 'K31', '10', 'XZS54000000000005401000000010100', '', '540100010022', '540100033466', '西藏金采科技股份有限公司', 'DS-M3506HM-K/GE', '1', '202011', '202105', '2020-08-01 12:00:00', '2020-11-05 18:30:37', null);
INSERT INTO `t_yz_vehicle` VALUES ('30142', '藏AL3979', '2', 'K11', '10', 'XZS54000000000005401000000010100', null, '540100010022', '540100038109', '西藏金采科技股份有限公司', 'DS-M3506HM-K/GE', '1', '202002   ', '202102   ', '2020-08-01 12:00:00', '2020-08-01 12:00:00', null);
INSERT INTO `t_yz_vehicle` VALUES ('30147', '藏AL3707', '2', 'K11', '21', 'XZS54000000000005401000000011010', null, '540100010917', '540100037645', '西藏金采科技股份有限公司', 'DS-M3506HM-K/GE', '1', '201903   ', '202003   ', '2020-08-01 12:00:00', '2020-08-01 12:00:00', null);
INSERT INTO `t_yz_vehicle` VALUES ('30203', '藏AL3237', '1', 'K31', '10', 'XZS54000000000005401000000010100', null, '540100010022', '540100033827', '西藏金采科技股份有限公司', 'DS-M3506HM-K/GE', '1', '202005   ', '202011   ', '2020-08-01 12:00:00', '2020-08-01 12:00:00', null);
INSERT INTO `t_yz_vehicle` VALUES ('30317', '藏AL5711', '2', 'K21', '10', 'XZS54000000000005401000000011216', null, '540100011125', '540100039570', '西藏金采科技股份有限公司', 'DS-M3506HM-K/GE', '1', '202005   ', '202105   ', '2020-08-01 12:00:00', '2020-08-01 12:00:00', null);
INSERT INTO `t_yz_vehicle` VALUES ('31276', '藏BC1581', '1', 'H31', '10', 'XZS54000000000005421000000002103', null, '542100002157', '542100003001', null, null, '2', '201602   ', '201702   ', '2020-08-01 12:00:00', '2020-08-01 12:00:00', null);
INSERT INTO `t_yz_vehicle` VALUES ('35574', '藏AL3017', '2', 'K21', '10', 'XZS54000000000005401000000010100', '', '540100010022', '540100033267', '西藏金采科技股份有限公司', 'DS-M3506HM-K/GE', '1', '202009', '202103', '2020-08-01 12:00:00', '2020-09-25 17:59:14', null);
INSERT INTO `t_yz_vehicle` VALUES ('35845', '藏AL3766', '2', 'K11', '10', 'XZS54000000000005401000000010100', '', '540100010022', '540100037515', '西藏金采科技股份有限公司', 'DS-M3506HM-K/GE', '1', '202003', '202103', '2020-08-01 12:00:00', '2020-09-24 18:59:12', null);
INSERT INTO `t_yz_vehicle` VALUES ('35928', '藏AL5689', '2', 'K21', '10', 'XZS54000000000005401000000010100', null, '540100010022', '540100039600', '西藏金采科技股份有限公司', 'DS-M3506HM-K/GE', '1', '202004   ', '202104   ', '2020-08-01 12:00:00', '2020-08-01 12:00:00', null);
INSERT INTO `t_yz_vehicle` VALUES ('36536', '藏AL5878', '2', 'K21', '10', 'XZS54000000000005401000000011216', null, '540100011125', '540100047309', '西藏金采科技股份有限公司', 'DS-M3506HM-K/GE', '1', '202002   ', '202102   ', '2020-08-01 12:00:00', '2020-08-01 12:00:00', null);
INSERT INTO `t_yz_vehicle` VALUES ('39921', '藏AL2055', '2', 'K21', '10', 'XZS54000000000005401000000010100', null, '540100010022', '540100024690', '西藏金采科技股份有限公司', 'DS-M3506HM-K/GE', '1', '202008   ', '202102   ', '2020-08-01 12:00:00', '2020-08-01 12:00:00', null);
INSERT INTO `t_yz_vehicle` VALUES ('41307', '藏AL3913', '2', 'K11', '10', 'XZS54000000000005401000000011010', null, '540100010917', '540100037851', '西藏金采科技股份有限公司', 'DS-M3506HM-K/GE', '1', '202003   ', '202103   ', '2020-08-01 12:00:00', '2020-08-01 12:00:00', null);
INSERT INTO `t_yz_vehicle` VALUES ('41410', '藏AL3890', '2', 'K11', '10', 'XZS54000000000005401000000010100', null, '540100010022', '540100037974', '西藏金采科技股份有限公司', 'DS-M3506HM-K/GE', '1', '202002   ', '202102   ', '2020-08-01 12:00:00', '2020-08-01 12:00:00', null);
INSERT INTO `t_yz_vehicle` VALUES ('41681', '藏AL5326', '2', 'K11', '10', 'XZS54000000000005401000000011216', null, '540100011125', '540100039147', '西藏金采科技股份有限公司', 'DS-M3506HM-K/GE', '1', '202003   ', '202103   ', '2020-08-01 12:00:00', '2020-08-01 12:00:00', null);
INSERT INTO `t_yz_vehicle` VALUES ('41691', '藏AL5616', '2', 'K11', '10', 'XZS54000000000005401000000011010', null, '540100010917', '540100039315', '西藏金采科技股份有限公司', 'DS-M3506HM-K/GE', '1', '202003   ', '202103   ', '2020-08-01 12:00:00', '2020-08-01 12:00:00', null);
INSERT INTO `t_yz_vehicle` VALUES ('41708', '藏AL5631', '2', 'K11', '10', 'XZS54000000000005401000000011010', null, '540100010917', '540100039289', '西藏金采科技股份有限公司', 'DS-M3506HM-K/GE', '1', '202003   ', '202103   ', '2020-08-01 12:00:00', '2020-08-01 12:00:00', null);
INSERT INTO `t_yz_vehicle` VALUES ('47125', '藏AL5023', '2', 'K11', '10', 'XZS54000000000005401000000011010', null, '540100010917', '540100038075', '西藏金采科技股份有限公司', 'DS-M3506HM-K/GE', '1', '202003   ', '202103   ', '2020-08-01 12:00:00', '2020-08-01 12:00:00', null);
INSERT INTO `t_yz_vehicle` VALUES ('47152', '藏AL3630', '2', 'K11', '10', 'XZS54000000000005401000000011216', null, '540100011125', '540100037367', '西藏金采科技股份有限公司', 'DS-M3506HM-K/GE', '1', '202003   ', '202103   ', '2020-08-01 12:00:00', '2020-08-01 12:00:00', null);
INSERT INTO `t_yz_vehicle` VALUES ('47196', '藏AL3851', '2', 'K11', '10', 'XZS54000000000005401000000010100', null, '540100010022', '540100037954', '西藏金采科技股份有限公司', 'DS-M3506HM-K/GE', '1', '202003   ', '202103   ', '2020-08-01 12:00:00', '2020-08-01 12:00:00', null);
INSERT INTO `t_yz_vehicle` VALUES ('47260', '藏AL6216', '1', 'K31', '10', 'XZS54000000000005401000000010129', null, '540100010049', '540100033960', '显创北斗物联网联控平台', 'HB-DV06', '1', '202005   ', '202011   ', '2020-08-01 12:00:00', '2020-08-01 12:00:00', null);
INSERT INTO `t_yz_vehicle` VALUES ('47289', '藏AL3315', '1', 'K31', '21', 'XZS54000000000005401000000010100', '', '540100010022', '540100034009', '西藏金采科技股份有限公司', 'DS-M3506HM-K/GE', '1', '202004', '202012', '2020-08-01 12:00:00', '2020-11-12 19:00:51', null);
INSERT INTO `t_yz_vehicle` VALUES ('47311', '藏AL5718', '2', 'K21', '10', 'XZS54000000000005401000000011010', null, '540100010917', '540100039576', '西藏金采科技股份有限公司', 'DS-M3506HM-K/GE', '1', '202005   ', '202105   ', '2020-08-01 12:00:00', '2020-08-01 12:00:00', null);
INSERT INTO `t_yz_vehicle` VALUES ('47532', '藏AL3707', '1', 'K31', '10', 'XZS54000000000005401000000011010', null, '540100010917', '540100038862', '西藏金采科技股份有限公司', 'DS-M3506HM-K/GE', '1', '202003   ', '202103   ', '2020-08-01 12:00:00', '2020-08-01 12:00:00', null);
INSERT INTO `t_yz_vehicle` VALUES ('52798', '藏AL3910', '2', 'K11', '10', 'XZS54000000000005401000000011010', '', '540100010917', '540100037848', '西藏金采科技股份有限公司', 'DS-M3506HM-K/GE', '1', '202010', '202103', '2020-08-01 12:00:00', '2020-10-16 17:59:56', null);
INSERT INTO `t_yz_vehicle` VALUES ('52894', '藏AL3780', '1', 'K31', '10', 'XZS54000000000005401000000011010', '', '540100010917', '540100039769', '西藏金采科技股份有限公司', 'DS-M3506HM-K/GE', '1', '202003', '202103', '2020-08-01 12:00:00', '2020-09-03 23:22:06', null);
INSERT INTO `t_yz_vehicle` VALUES ('52935', '藏AL5527', '2', 'K21', '10', 'XZS54000000000005401000000011010', null, '540100010917', '540100039628', '西藏金采科技股份有限公司', 'DS-M3506HM-K/GE', '1', '202004   ', '202104   ', '2020-08-01 12:00:00', '2020-08-01 12:00:00', null);
INSERT INTO `t_yz_vehicle` VALUES ('53221', '藏AL5798', '2', 'K21', '10', 'XZS54000000000005401000000010100', null, '540100010022', '540100044261', '西藏金采科技股份有限公司', 'DS-M3506HM-K/GE', '1', '202003   ', '202103   ', '2020-08-01 12:00:00', '2020-08-01 12:00:00', null);
INSERT INTO `t_yz_vehicle` VALUES ('58492', '藏AL5126', '2', 'K11', '10', 'XZS54000000000005401000000011216', null, '540100011125', '540100038318', '西藏金采科技股份有限公司', 'DS-M3506HM-K/GE', '1', '202003   ', '202103   ', '2020-08-01 12:00:00', '2020-08-01 12:00:00', null);
INSERT INTO `t_yz_vehicle` VALUES ('58576', '藏AL3825', '2', 'K11', '10', 'XZS54000000000005401000000011216', null, '540100011125', '540100037528', '西藏金采科技股份有限公司', 'DS-M3506HM-K/GE', '1', '202003   ', '202103   ', '2020-08-01 12:00:00', '2020-08-01 12:00:00', null);
INSERT INTO `t_yz_vehicle` VALUES ('58697', '藏AL5730', '2', 'K21', '10', 'XZS54000000000005401000000011010', null, '540100010917', '540100039689', '西藏金采科技股份有限公司', 'DS-M3506HM-K/GE', '1', '202003   ', '202103   ', '2020-08-01 12:00:00', '2020-08-01 12:00:00', null);
INSERT INTO `t_yz_vehicle` VALUES ('64357', '藏AL3772', '1', 'K31', '10', 'XZS54000000000005401000000011216', '', '540100011125', '540100039698', '西藏金采科技股份有限公司', 'DS-M3506HM-K/GE', '1', '202009', '202104', '2020-08-01 12:00:00', '2020-09-11 23:22:22', null);
INSERT INTO `t_yz_vehicle` VALUES ('64397', '藏AL3228', '2', 'K11', '10', 'XZS54000000000005401000000010100', null, '540100010022', '540100037083', '西藏金采科技股份有限公司', 'DS-M3506HM-K/GE', '1', '202002   ', '202102   ', '2020-08-01 12:00:00', '2020-08-01 12:00:00', null);
INSERT INTO `t_yz_vehicle` VALUES ('69910', '藏AL3772', '2', 'K11', '10', 'XZS54000000000005401000000010100', null, '540100010022', '540100037517', '西藏金采科技股份有限公司', 'DS-M3506HM-K/GE', '1', '202003   ', '202103   ', '2020-08-01 12:00:00', '2020-08-01 12:00:00', null);
INSERT INTO `t_yz_vehicle` VALUES ('70047', '藏AL3630', '1', 'K31', '10', 'XZS54000000000005401000000011216', null, '540100011125', '540100037255', '西藏金采科技股份有限公司', 'DS-M3506HM-K/GE', '1', '202003   ', '202103   ', '2020-08-01 12:00:00', '2020-08-01 12:00:00', null);
INSERT INTO `t_yz_vehicle` VALUES ('70084', '藏AL3731', '2', 'K11', '10', 'XZS54000000000005401000000011010', null, '540100010917', '540100037642', '西藏金采科技股份有限公司', 'DS-M3506HM-K/GE', '1', '202003   ', '202103   ', '2020-08-01 12:00:00', '2020-08-01 12:00:00', null);
INSERT INTO `t_yz_vehicle` VALUES ('70204', '藏AL5182', '2', 'K11', '10', 'XZS54000000000005401000000011216', null, '540100011125', '540100038439', '西藏金采科技股份有限公司', 'DS-M3506HM-K/GE', '1', '202003   ', '202103   ', '2020-08-01 12:00:00', '2020-08-01 12:00:00', null);
INSERT INTO `t_yz_vehicle` VALUES ('70230', '藏AL5378', '2', 'K11', '10', 'XZS54000000000005401000000011216', null, '540100011125', '540100039237', '西藏金采科技股份有限公司', 'DS-M3506HM-K/GE', '1', '202003   ', '202103   ', '2020-08-01 12:00:00', '2020-08-01 12:00:00', null);
INSERT INTO `t_yz_vehicle` VALUES ('71048', '藏BC1581', '2', 'H14', '10', 'XZS54000000000005421000000000005', null, '542100000004', '542100004923', '西藏金采科技股份有限公司', 'GK-110R6-GC', '1', '202006   ', '202105   ', '2020-08-01 12:00:00', '2020-08-01 12:00:00', null);
INSERT INTO `t_yz_vehicle` VALUES ('75635', '藏AL3701', '2', 'K11', '21', 'XZS54000000000005401000000010100', null, '540100010022', '540100037593', '西藏金采科技股份有限公司', 'DS-M3506HM-K/GE', '1', '201903   ', '202003   ', '2020-08-01 12:00:00', '2020-08-01 12:00:00', null);
INSERT INTO `t_yz_vehicle` VALUES ('75640', '藏AL5117', '2', 'K11', '10', 'XZS54000000000005401000000011010', null, '540100010917', '540100038369', '西藏金采科技股份有限公司', 'DS-M3506HM-K/GE', '1', '202003   ', '202103   ', '2020-08-01 12:00:00', '2020-08-01 12:00:00', null);
INSERT INTO `t_yz_vehicle` VALUES ('75666', '藏AL6037', '1', 'K31', '10', 'XZS54000000000005401000000010129', null, '540100010049', '540100033556', '西藏金采科技股份有限公司', 'HB-DV06', '1', '202004   ', '202012   ', '2020-08-01 12:00:00', '2020-08-01 12:00:00', null);
INSERT INTO `t_yz_vehicle` VALUES ('75678', '藏AL3562', '1', 'K31', '10', 'XZS54000000000005401000000010100', null, '540100010022', '540100034358', '西藏金采科技股份有限公司', 'DS-M3506HM-K/GE', '1', '202006   ', '202012   ', '2020-08-01 12:00:00', '2020-08-01 12:00:00', null);
INSERT INTO `t_yz_vehicle` VALUES ('75679', '藏AL3821', '2', 'K11', '10', 'XZS54000000000005401000000010100', null, '540100010022', '540100037534', '西藏金采科技股份有限公司', 'DS-M3506HM-K/GE', '1', '202002   ', '202102   ', '2020-08-01 12:00:00', '2020-08-01 12:00:00', null);
INSERT INTO `t_yz_vehicle` VALUES ('75843', '藏AL3237', '2', 'K11', '10', 'XZS54000000000005401000000010100', null, '540100010022', '540100037167', '西藏金采科技股份有限公司', 'DS-M3506HM-K/GE', '1', '202003   ', '202103   ', '2020-08-01 12:00:00', '2020-08-01 12:00:00', null);
INSERT INTO `t_yz_vehicle` VALUES ('81413', '藏AL6158', '1', 'K31', '21', 'XZS54000000000005401000000010129', '', '540100010049', '540100033762', '显创北斗物联网联控平台', 'HB-DV06', '1', '202006', '202012', '2020-08-01 12:00:00', '2020-10-19 17:30:03', null);
INSERT INTO `t_yz_vehicle` VALUES ('81480', '藏AL3095', '2', 'K11', '10', 'XZS54000000000005401000000010100', null, '540100010022', '540100034211', '西藏金采科技股份有限公司', 'DS-M3506HM-K/GE', '1', '202006   ', '202012   ', '2020-08-01 12:00:00', '2020-08-01 12:00:00', null);
INSERT INTO `t_yz_vehicle` VALUES ('81546', '藏AL5705', '2', 'K21', '10', 'XZS54000000000005401000000011010', null, '540100010917', '540100039565', '西藏金采科技股份有限公司', 'DS-M3506HM-K/GE', '1', '202005   ', '202105   ', '2020-08-01 12:00:00', '2020-08-01 12:00:00', null);
INSERT INTO `t_yz_vehicle` VALUES ('81598', '藏AL3612', '1', 'K31', '10', 'XZS54000000000005401000000011010', null, '540100010917', '540100037263', '西藏金采科技股份有限公司', 'DS-M3506HM-K/GE', '1', '202002   ', '202102   ', '2020-08-01 12:00:00', '2020-08-01 12:00:00', null);
INSERT INTO `t_yz_vehicle` VALUES ('81638', '藏AL5190', '2', 'K11', '10', 'XZS54000000000005401000000010100', null, '540100010022', '540100038446', '西藏金采科技股份有限公司', 'DS-M3506HM-K/GE', '1', '202002   ', '202102   ', '2020-08-01 12:00:00', '2020-08-01 12:00:00', null);
INSERT INTO `t_yz_vehicle` VALUES ('86902', '藏AL3017', '1', 'K31', '10', 'XZS54000000000005401000000010100', null, '540100010022', '540100033247', '西藏金采科技股份有限公司', 'DS-M3506HM-K/GE', '1', '202006   ', '202012   ', '2020-08-01 12:00:00', '2020-08-01 12:00:00', null);
INSERT INTO `t_yz_vehicle` VALUES ('87126', '藏AL5007', '2', 'K11', '10', 'XZS54000000000005401000000011216', null, '540100011125', '540100038095', '西藏金采科技股份有限公司', 'DS-M3506HM-K/GE', '1', '202003   ', '202103   ', '2020-08-01 12:00:00', '2020-08-01 12:00:00', null);
INSERT INTO `t_yz_vehicle` VALUES ('87133', '藏AL3780', '2', 'K11', '10', 'XZS54000000000005401000000010100', null, '540100010022', '540100037568', '西藏金采科技股份有限公司', 'DS-M3506HM-K/GE', '1', '202002   ', '202102   ', '2020-08-01 12:00:00', '2020-08-01 12:00:00', null);
INSERT INTO `t_yz_vehicle` VALUES ('87165', '藏AL3987', '2', 'K11', '10', 'XZS54000000000005401000000011216', null, '540100011125', '540100038082', '西藏金采科技股份有限公司', 'DS-M3506HM-K/GE', '1', '202003   ', '202103   ', '2020-08-01 12:00:00', '2020-08-01 12:00:00', null);
INSERT INTO `t_yz_vehicle` VALUES ('87242', '藏AL3738', '1', 'K31', '10', 'XZS54000000000005401000000010100', null, '540100010022', '540100039741', '西藏金采科技股份有限公司', 'DS-M3506HM-K/GE', '1', '202002   ', '202102   ', '2020-08-01 12:00:00', '2020-08-01 12:00:00', null);
INSERT INTO `t_yz_vehicle` VALUES ('87374', '藏AL3315', '2', 'K11', '10', 'XZS54000000000005401000000011216', null, '540100011125', '540100037150', '西藏金采科技股份有限公司', 'DS-M3506HM-K/GE', '1', '202002   ', '202102   ', '2020-08-01 12:00:00', '2020-08-01 12:00:00', null);

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `login_name` varchar(32) DEFAULT NULL COMMENT '登陆名',
  `name` varchar(32) DEFAULT NULL COMMENT '姓名',
  `pwd` varchar(128) DEFAULT NULL,
  `phone` varchar(32) DEFAULT NULL COMMENT '电话',
  `org_id` bigint(20) DEFAULT NULL COMMENT '所属组织',
  `status` int(1) DEFAULT NULL COMMENT '0禁用1正常',
  `last_login_time` datetime DEFAULT NULL COMMENT '最近一次登陆时间',
  `temp2` varchar(255) DEFAULT NULL,
  `temp1` varchar(255) DEFAULT NULL,
  `ct` datetime DEFAULT NULL COMMENT '修改时间',
  `mt` datetime DEFAULT NULL COMMENT '创建时间',
  `is_delete` int(1) NOT NULL DEFAULT '1' COMMENT '0正常1删除',
  `is_admin` int(1) NOT NULL DEFAULT '0' COMMENT '0不是超管1是',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'admin', '超级管理员', 'a66abb5684c45962d887564f08346e8d', null, null, '1', null, null, null, null, null, '1', '1');
INSERT INTO `user` VALUES ('4', 'huy', 'huy', '123456', '18980444290', '2', '1', null, null, null, '2020-08-27 15:30:33', null, '1', '0');
INSERT INTO `user` VALUES ('5', 'zhaol', '赵兰', '67d190e48e55bfdb653a5e496493deea', '18980444290', '2', '1', null, null, null, '2020-08-27 15:33:05', null, '1', '0');

-- ----------------------------
-- Table structure for `user_role`
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `user_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES ('5', '2');
INSERT INTO `user_role` VALUES ('5', '3');
