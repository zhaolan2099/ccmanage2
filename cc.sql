/*
Navicat MySQL Data Transfer

Source Server         : CC线上
Source Server Version : 80021
Source Host           : 123.56.104.52:3306
Source Database       : cc

Target Server Type    : MYSQL
Target Server Version : 80021
File Encoding         : 65001

Date: 2020-09-28 14:42:56
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `board`
-- ----------------------------
DROP TABLE IF EXISTS `board`;
CREATE TABLE `board` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `sn` varchar(255) DEFAULT NULL COMMENT 'sn序号',
  `mac` varchar(255) DEFAULT NULL COMMENT 'mac地址',
  `board_type` varchar(255) DEFAULT NULL COMMENT '测试版类型',
  `org_id` bigint DEFAULT NULL COMMENT '厂商ID',
  `test_status` varchar(11) DEFAULT NULL COMMENT '测试状态1测试中2已入库(已完成)3出库中4已出库',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint DEFAULT NULL COMMENT '创建人',
  `putin_time` datetime DEFAULT NULL COMMENT '入库时间(入库时间不一定就是创建时间)',
  `putin_user` bigint DEFAULT NULL COMMENT '入库人(入库人不一定就是创建人)',
  `out_time` datetime DEFAULT NULL COMMENT '出库时间',
  `out_user` bigint DEFAULT NULL COMMENT '出库人',
  `out_num` varchar(255) DEFAULT NULL COMMENT '出库单号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=48 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of board
-- ----------------------------
INSERT INTO `board` VALUES ('9', '11200900010', '286dcd3c8994', 'A1', '2', '4', '2020-09-23 19:45:25', '5', '2020-09-23 19:55:47', '5', '2020-09-23 20:15:47', '5', '出库单号1600863347296');
INSERT INTO `board` VALUES ('10', '11200900011', '286dcd3c8405', 'A1', '2', '4', '2020-09-23 19:59:59', '5', '2020-09-23 20:01:58', '5', '2020-09-23 20:15:47', '5', '出库单号1600863347296');
INSERT INTO `board` VALUES ('16', 'A11200900002', 'test111', 'A1', '2', '3', '2020-09-24 18:10:48', '5', '2020-09-24 18:22:03', '5', null, null, null);
INSERT INTO `board` VALUES ('46', null, '77', 'A1', '2', '1', '2020-09-27 17:29:36', '5', '2020-09-27 17:29:36', '5', null, null, null);
INSERT INTO `board` VALUES ('47', null, '88', 'A1', '2', '1', '2020-09-27 17:29:51', '5', '2020-09-27 17:29:51', '5', null, null, null);

-- ----------------------------
-- Table structure for `dict`
-- ----------------------------
DROP TABLE IF EXISTS `dict`;
CREATE TABLE `dict` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `val` varchar(64) DEFAULT NULL,
  `tag` varchar(64) DEFAULT NULL,
  `type_id` bigint DEFAULT NULL,
  `explain` varchar(64) DEFAULT NULL COMMENT '说明',
  `sort` int DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dict
-- ----------------------------
INSERT INTO `dict` VALUES ('5', 'A1', '桌面款主板', '1', null, '10');
INSERT INTO `dict` VALUES ('6', 'A2', '灯板', '1', null, '30');
INSERT INTO `dict` VALUES ('7', 'A3', 'wifi通信板', '1', null, '3');

-- ----------------------------
-- Table structure for `dict_type`
-- ----------------------------
DROP TABLE IF EXISTS `dict_type`;
CREATE TABLE `dict_type` (
  `id` bigint NOT NULL AUTO_INCREMENT,
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
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL COMMENT '菜单名称',
  `pid` bigint DEFAULT NULL COMMENT '父id',
  `pids` varchar(255) DEFAULT NULL COMMENT '所有父级id',
  `url` varchar(255) DEFAULT NULL COMMENT 'url地址',
  `permission` varchar(255) DEFAULT NULL COMMENT '权限',
  `img` varchar(255) DEFAULT NULL COMMENT '图片',
  `seq` int DEFAULT NULL COMMENT '顺序',
  `status` int DEFAULT NULL COMMENT '0禁用1启用',
  `level` int DEFAULT NULL COMMENT '菜单级别',
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
  `id` bigint NOT NULL AUTO_INCREMENT,
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
  `is_delete` int NOT NULL DEFAULT '1' COMMENT '0删除1正常',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='厂商表';

-- ----------------------------
-- Records of org
-- ----------------------------
INSERT INTO `org` VALUES ('2', '策创3', '1', '中海国际中心', 'hy', '135897897979', 'http://3427r92c90.wicp.vip:34344', null, null, '2020-08-26 17:25:46', null, '1');
INSERT INTO `org` VALUES ('3', '测试', '2', '1', '1', '1', '127.0.0.1', null, null, '2020-09-22 14:26:16', null, '1');
INSERT INTO `org` VALUES ('4', '成都地满科技有限', 'A02', '国腾科技园11号楼', '罗勇', '13996220567', null, null, null, '2020-09-25 21:43:16', '2020-09-25 21:46:17', '1');
INSERT INTO `org` VALUES ('5', '加多利电子', 'A01', '磨具工业园C1-3层', '刘平', '123456', null, null, null, '2020-09-25 21:46:03', null, '1');
INSERT INTO `org` VALUES ('6', '成都策创科技', 'C01', '天府二街雄川金融中心', '范亚非', '13558602947', null, null, null, '2020-09-25 21:47:08', null, '1');

-- ----------------------------
-- Table structure for `role`
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL COMMENT '角色名称',
  `status` int DEFAULT NULL COMMENT '0不可用1可用',
  `remark` varchar(255) DEFAULT NULL COMMENT '描述',
  `ct` datetime DEFAULT NULL COMMENT '创建时间',
  `mt` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('1', '超级管理员', '1', '超管，不可编辑', null, null);
INSERT INTO `role` VALUES ('2', '测试', '1', '测试角色', '2020-08-27 15:22:39', null);
INSERT INTO `role` VALUES ('3', '测试2', '1', null, null, null);
INSERT INTO `role` VALUES ('4', '入库', '1', '入库操作员', '2020-09-25 21:39:08', null);
INSERT INTO `role` VALUES ('5', '管理员', '1', '策创公司管理人员', '2020-09-25 21:40:00', null);
INSERT INTO `role` VALUES ('6', '厂商管理员', '1', '生产厂家的管理员，可以删除错误的测试记录', '2020-09-25 21:40:50', null);
INSERT INTO `role` VALUES ('7', '厂商测试人员', '1', '厂商测试人员', '2020-09-26 13:47:34', null);

-- ----------------------------
-- Table structure for `role_menu`
-- ----------------------------
DROP TABLE IF EXISTS `role_menu`;
CREATE TABLE `role_menu` (
  `role_id` bigint NOT NULL,
  `menu_id` bigint NOT NULL,
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
INSERT INTO `role_menu` VALUES ('4', '5');
INSERT INTO `role_menu` VALUES ('4', '7');
INSERT INTO `role_menu` VALUES ('5', '3');
INSERT INTO `role_menu` VALUES ('5', '4');
INSERT INTO `role_menu` VALUES ('5', '5');
INSERT INTO `role_menu` VALUES ('5', '6');
INSERT INTO `role_menu` VALUES ('5', '7');
INSERT INTO `role_menu` VALUES ('5', '8');
INSERT INTO `role_menu` VALUES ('5', '9');
INSERT INTO `role_menu` VALUES ('5', '10');
INSERT INTO `role_menu` VALUES ('5', '11');
INSERT INTO `role_menu` VALUES ('6', '3');
INSERT INTO `role_menu` VALUES ('6', '5');
INSERT INTO `role_menu` VALUES ('6', '6');
INSERT INTO `role_menu` VALUES ('6', '7');
INSERT INTO `role_menu` VALUES ('7', '5');
INSERT INTO `role_menu` VALUES ('7', '6');

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `login_name` varchar(32) DEFAULT NULL COMMENT '登陆名',
  `name` varchar(32) DEFAULT NULL COMMENT '姓名',
  `pwd` varchar(128) DEFAULT NULL,
  `phone` varchar(32) DEFAULT NULL COMMENT '电话',
  `org_id` bigint DEFAULT NULL COMMENT '所属组织',
  `status` int DEFAULT NULL COMMENT '0禁用1正常',
  `last_login_time` datetime DEFAULT NULL COMMENT '最近一次登陆时间',
  `temp2` varchar(255) DEFAULT NULL,
  `temp1` varchar(255) DEFAULT NULL,
  `ct` datetime DEFAULT NULL COMMENT '修改时间',
  `mt` datetime DEFAULT NULL COMMENT '创建时间',
  `is_delete` int NOT NULL DEFAULT '1' COMMENT '0正常1删除',
  `is_admin` int NOT NULL DEFAULT '0' COMMENT '0不是超管1是',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'admin', '超级管理员', 'a66abb5684c45962d887564f08346e8d', null, null, '1', null, null, null, null, null, '1', '1');
INSERT INTO `user` VALUES ('4', 'huy', 'huy', '123456', '18980444290', '2', '1', null, null, null, '2020-08-27 15:30:33', null, '1', '0');
INSERT INTO `user` VALUES ('5', 'zhaol', '赵兰12', '67d190e48e55bfdb653a5e496493deea', '18980444290', '2', '1', null, null, null, '2020-08-27 15:33:05', '2020-09-26 13:31:38', '1', '0');
INSERT INTO `user` VALUES ('6', 'changs1(已删除)', '厂商1', null, '13566778987', '6', null, null, null, null, '2020-09-26 13:00:41', '2020-09-27 15:57:29', '1', '0');
INSERT INTO `user` VALUES ('7', '1111', 'test111', null, '134898', null, null, null, null, null, '2020-09-26 13:27:58', null, '1', '0');
INSERT INTO `user` VALUES ('8', 'te1111', '111', null, '111', null, null, null, null, null, '2020-09-26 13:28:28', null, '1', '0');
INSERT INTO `user` VALUES ('9', 'demo', '111', null, '13500000000', '2', null, null, null, null, '2020-09-26 13:55:59', null, '1', '0');
INSERT INTO `user` VALUES ('10', '111', '222', null, '333', null, null, null, null, null, '2020-09-26 14:10:29', null, '1', '0');
INSERT INTO `user` VALUES ('11', 'test', '测试', '47ec2dd791e31e2ef2076caf64ed9b3d', '112121', '2', '1', null, null, null, '2020-09-27 13:35:32', null, '1', '0');
INSERT INTO `user` VALUES ('12', 'changs2', '厂商2', '0b8cb5e327e44933aa42e2e926939e71', '14599898765', '5', null, null, null, null, '2020-09-27 14:15:00', null, '1', '0');
INSERT INTO `user` VALUES ('13', 'test1', '111', '4a3252a5edf8fcaa8bde0bfcce79560d', '111', '2', null, null, null, null, '2020-09-27 14:23:20', null, '1', '0');
INSERT INTO `user` VALUES ('14', 'changs3', '厂商3', '18db85a09417f267a513448b341a22e5', '16789878236', '4', '1', null, null, null, '2020-09-27 14:43:00', null, '1', '0');
INSERT INTO `user` VALUES ('15', 'changs4', '厂商4', 'ee59b02b20ad8608b83552b35b5674be', '13566987458', '4', '1', null, null, null, '2020-09-27 15:57:15', null, '1', '0');
INSERT INTO `user` VALUES ('16', 'changs5', '厂商5', 'd97222e665b84a05750d2eac9881be8a', '13599887876', '4', '1', null, null, null, '2020-09-27 21:07:37', null, '1', '0');
INSERT INTO `user` VALUES ('17', 'changs6', '厂商6', 'cef30b859722d12a7d3e8ca4550b15e5', '13658456987', '4', '1', null, null, null, '2020-09-28 10:28:39', null, '1', '0');

-- ----------------------------
-- Table structure for `user_role`
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `user_id` bigint NOT NULL,
  `role_id` bigint NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES ('5', '2');
INSERT INTO `user_role` VALUES ('5', '3');
INSERT INTO `user_role` VALUES ('6', '6');
INSERT INTO `user_role` VALUES ('6', '7');
INSERT INTO `user_role` VALUES ('7', '6');
INSERT INTO `user_role` VALUES ('8', '2');
INSERT INTO `user_role` VALUES ('9', '2');
INSERT INTO `user_role` VALUES ('10', '3');
INSERT INTO `user_role` VALUES ('11', '2');
INSERT INTO `user_role` VALUES ('12', '2');
INSERT INTO `user_role` VALUES ('12', '4');
INSERT INTO `user_role` VALUES ('12', '6');
INSERT INTO `user_role` VALUES ('12', '7');
INSERT INTO `user_role` VALUES ('13', '2');
INSERT INTO `user_role` VALUES ('14', '4');
INSERT INTO `user_role` VALUES ('14', '7');
INSERT INTO `user_role` VALUES ('15', '7');
INSERT INTO `user_role` VALUES ('16', '7');
INSERT INTO `user_role` VALUES ('17', '6');
INSERT INTO `user_role` VALUES ('17', '7');
