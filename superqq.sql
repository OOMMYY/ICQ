/*
Navicat MySQL Data Transfer

Source Server         : courseregis
Source Server Version : 50022
Source Host           : localhost:3306
Source Database       : superqq

Target Server Type    : MYSQL
Target Server Version : 50022
File Encoding         : 65001

Date: 2016-07-21 21:46:12
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for failmessage
-- ----------------------------
DROP TABLE IF EXISTS `failmessage`;
CREATE TABLE `failmessage` (
  `id1` bigint(10) NOT NULL,
  `id2` bigint(10) default NULL,
  `time` datetime default NULL,
  `content` varchar(140) default NULL,
  `id` bigint(20) NOT NULL auto_increment,
  PRIMARY KEY  (`id`),
  KEY `f3` USING BTREE (`id1`),
  KEY `f4` USING BTREE (`id2`),
  CONSTRAINT `failmessage_ibfk_1` FOREIGN KEY (`id1`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `failmessage_ibfk_2` FOREIGN KEY (`id2`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='InnoDB free: 3072 kB; (`id1`) REFER `superqq/user`(`id`) ON ';

-- ----------------------------
-- Records of failmessage
-- ----------------------------
INSERT INTO `failmessage` VALUES ('119', '150', '2016-08-17 10:52:17', '你好', '4');
INSERT INTO `failmessage` VALUES ('119', '150', '2016-08-17 10:52:17', '你好', '5');
INSERT INTO `failmessage` VALUES ('120', '122', '2016-08-17 14:43:17', '你好', '11');
INSERT INTO `failmessage` VALUES ('120', '122', '2016-08-17 14:43:17', '你好', '15');
INSERT INTO `failmessage` VALUES ('120', '122', '2016-08-17 14:44:17', '你好', '19');
INSERT INTO `failmessage` VALUES ('120', '122', '2016-08-17 14:44:17', '你好', '23');
INSERT INTO `failmessage` VALUES ('120', '122', '2016-08-17 14:44:17', '你好', '27');
INSERT INTO `failmessage` VALUES ('119', '150', '2016-08-17 14:44:17', '你好', '31');
INSERT INTO `failmessage` VALUES ('119', '150', '2016-08-17 14:48:17', '大家好', '32');
INSERT INTO `failmessage` VALUES ('120', '122', '2016-08-17 14:49:17', '大家好', '33');
INSERT INTO `failmessage` VALUES ('119', '150', '2016-08-17 14:49:17', '大家好', '37');
INSERT INTO `failmessage` VALUES ('120', '122', '2016-08-17 14:56:17', '你好', '38');
INSERT INTO `failmessage` VALUES ('121', '122', '2016-08-18 07:55:18', 'NIHAO', '44');
INSERT INTO `failmessage` VALUES ('188', '170', '2016-08-21 21:07:21', '筒子们，大家好，大家辛苦了', '46');
INSERT INTO `failmessage` VALUES ('188', '172', '2016-08-21 21:07:21', '筒子们，大家好，大家辛苦了', '47');
INSERT INTO `failmessage` VALUES ('188', '173', '2016-08-21 21:07:21', '筒子们，大家好，大家辛苦了', '48');
INSERT INTO `failmessage` VALUES ('188', '187', '2016-08-21 21:07:21', '筒子们，大家好，大家辛苦了', '49');

-- ----------------------------
-- Table structure for failrelation
-- ----------------------------
DROP TABLE IF EXISTS `failrelation`;
CREATE TABLE `failrelation` (
  `id1` bigint(10) NOT NULL,
  `id2` bigint(10) NOT NULL,
  PRIMARY KEY  (`id1`,`id2`),
  KEY `f2` USING BTREE (`id2`),
  CONSTRAINT `failrelation_ibfk_1` FOREIGN KEY (`id1`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `failrelation_ibfk_2` FOREIGN KEY (`id2`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='InnoDB free: 3072 kB; (`id1`) REFER `superqq/user`(`id`) ON ';

-- ----------------------------
-- Records of failrelation
-- ----------------------------
INSERT INTO `failrelation` VALUES ('123', '150');
INSERT INTO `failrelation` VALUES ('180', '170');
INSERT INTO `failrelation` VALUES ('187', '185');
INSERT INTO `failrelation` VALUES ('185', '187');

-- ----------------------------
-- Table structure for message
-- ----------------------------
DROP TABLE IF EXISTS `message`;
CREATE TABLE `message` (
  `id1` bigint(10) NOT NULL,
  `id2` bigint(10) NOT NULL,
  `time` datetime NOT NULL,
  `content` varchar(140) NOT NULL,
  `id` bigint(20) NOT NULL auto_increment,
  `d1` bit(1) default '',
  `d2` bit(1) default '',
  PRIMARY KEY  (`id`),
  KEY `f3` (`id1`),
  KEY `f4` (`id2`),
  CONSTRAINT `f3` FOREIGN KEY (`id1`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `f4` FOREIGN KEY (`id2`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of message
-- ----------------------------
INSERT INTO `message` VALUES ('119', '120', '2016-07-07 10:42:13', 'hello 120 I\'m 119', '1', '', '\0');
INSERT INTO `message` VALUES ('121', '120', '2016-07-06 10:42:37', 'hello 120  I\'m 121', '2', '', '\0');
INSERT INTO `message` VALUES ('120', '123', '2016-08-04 10:43:09', 'hello 123 I\'m 120', '3', '', '');
INSERT INTO `message` VALUES ('120', '119', '2017-12-09 00:00:00', 'test insert time.', '4', '\0', '');
INSERT INTO `message` VALUES ('119', '120', '2016-07-27 14:00:48', 'hello ', '5', '', '\0');
INSERT INTO `message` VALUES ('119', '120', '2016-07-31 14:01:10', 'hello', '6', '', '\0');
INSERT INTO `message` VALUES ('120', '119', '2016-07-08 15:39:49', 'hello', '7', '\0', '');
INSERT INTO `message` VALUES ('119', '120', '2016-07-08 15:43:53', 'hello test date', '8', '', '\0');
INSERT INTO `message` VALUES ('119', '120', '2016-07-08 15:45:59', 'hello test date', '9', '', '\0');
INSERT INTO `message` VALUES ('119', '120', '2016-07-08 15:46:08', 'hello test date', '10', '', '\0');
INSERT INTO `message` VALUES ('119', '120', '2016-07-08 15:46:43', 'hello test date', '11', '', '\0');
INSERT INTO `message` VALUES ('120', '124', '2016-07-22 17:47:53', '', '12', '', '');
INSERT INTO `message` VALUES ('120', '119', '2015-03-03 12:13:14', 'nihao', '13', '\0', '');
INSERT INTO `message` VALUES ('120', '119', '2016-10-08 10:12:11', '120群发信息。', '14', '\0', '');
INSERT INTO `message` VALUES ('120', '119', '2016-08-10 14:05:10', '213213', '15', '', '');
INSERT INTO `message` VALUES ('119', '120', '2016-08-10 17:34:10', '你好--', '18', '', '');
INSERT INTO `message` VALUES ('119', '120', '2016-08-10 17:37:10', 'hHh', '19', '', '');
INSERT INTO `message` VALUES ('119', '120', '2016-08-10 17:39:10', 'HH', '20', '', '');
INSERT INTO `message` VALUES ('119', '120', '2016-08-10 17:42:10', '而且为二次', '21', '', '');
INSERT INTO `message` VALUES ('119', '120', '2016-08-10 17:43:10', '年后', '22', '', '');
INSERT INTO `message` VALUES ('119', '120', '2016-08-10 17:46:10', '哈哈', '23', '', '');
INSERT INTO `message` VALUES ('119', '120', '2016-08-10 17:49:10', '哈哈', '24', '', '');
INSERT INTO `message` VALUES ('120', '119', '2016-08-10 10:11:11', 'nihao', '25', '', '');
INSERT INTO `message` VALUES ('119', '120', '2016-08-10 18:12:10', '你好', '26', '', '');
INSERT INTO `message` VALUES ('120', '119', '2016-08-10 10:11:10', 'nihao', '27', '', '');
INSERT INTO `message` VALUES ('120', '119', '2016-08-10 10:11:10', 'nihao', '28', '', '');
INSERT INTO `message` VALUES ('120', '119', '2016-08-10 10:11:10', 'nihao', '29', '', '');
INSERT INTO `message` VALUES ('120', '119', '2016-08-10 10:11:10', 'nihao', '30', '', '');
INSERT INTO `message` VALUES ('119', '120', '2016-08-10 18:20:10', '你好', '31', '', '');
INSERT INTO `message` VALUES ('120', '119', '2016-08-10 18:21:10', '年号', '32', '', '');
INSERT INTO `message` VALUES ('119', '120', '2016-08-10 18:22:10', 'niaoj', '33', '', '');
INSERT INTO `message` VALUES ('120', '119', '2016-08-10 18:22:10', '090', '34', '', '');
INSERT INTO `message` VALUES ('119', '120', '2016-08-10 18:25:10', '你好', '35', '', '');
INSERT INTO `message` VALUES ('119', '120', '2016-08-10 19:16:10', 'NIHAO', '36', '', '');
INSERT INTO `message` VALUES ('120', '119', '2016-08-10 19:16:10', 'NIHAO', '37', '', '');
INSERT INTO `message` VALUES ('121', '122', '2016-08-10 19:43:10', 'njkfds', '38', '', '');
INSERT INTO `message` VALUES ('121', '122', '2016-08-10 19:43:10', 'fjskadljf dsa', '39', '', '');
INSERT INTO `message` VALUES ('119', '120', '2016-08-11 10:44:11', '你哈', '40', '', '');
INSERT INTO `message` VALUES ('120', '119', '2016-08-11 10:45:11', '哈哈', '41', '', '');
INSERT INTO `message` VALUES ('119', '120', '2016-08-11 10:45:11', '你好哈', '42', '', '');
INSERT INTO `message` VALUES ('119', '120', '2016-08-11 11:12:11', '你好', '43', '', '');
INSERT INTO `message` VALUES ('120', '119', '2016-08-11 11:13:11', '我很好', '44', '', '');
INSERT INTO `message` VALUES ('119', '120', '2016-08-11 11:13:11', '年号', '45', '', '');
INSERT INTO `message` VALUES ('120', '119', '2016-08-11 11:14:11', '大家好', '46', '', '');
INSERT INTO `message` VALUES ('119', '120', '2016-08-11 11:14:11', '你好O', '47', '', '');
INSERT INTO `message` VALUES ('119', '120', '2016-08-11 11:29:11', '你好', '48', '', '');
INSERT INTO `message` VALUES ('119', '120', '2016-08-11 11:29:11', '你好', '49', '', '');
INSERT INTO `message` VALUES ('119', '120', '2016-08-11 11:30:11', '你好', '50', '', '');
INSERT INTO `message` VALUES ('119', '120', '2016-08-11 11:30:11', '你好', '51', '', '');
INSERT INTO `message` VALUES ('119', '120', '2016-08-11 11:30:11', '你哈', '52', '', '');
INSERT INTO `message` VALUES ('120', '119', '2016-08-11 11:30:11', '好', '53', '', '');
INSERT INTO `message` VALUES ('120', '119', '2016-08-11 11:30:11', '好', '54', '', '');
INSERT INTO `message` VALUES ('120', '119', '2016-08-11 11:30:11', '恩', '55', '', '');
INSERT INTO `message` VALUES ('119', '120', '2016-08-11 11:33:11', '哈哈', '56', '', '');
INSERT INTO `message` VALUES ('119', '120', '2016-08-11 11:33:11', '你好', '57', '', '');
INSERT INTO `message` VALUES ('119', '120', '2016-08-11 11:33:11', '地方', '58', '', '');
INSERT INTO `message` VALUES ('119', '120', '2016-08-11 11:33:11', '发', '59', '', '');
INSERT INTO `message` VALUES ('119', '120', '2016-08-11 11:38:11', '年后', '60', '', '');
INSERT INTO `message` VALUES ('119', '120', '2016-08-11 11:40:11', '年后', '61', '', '');
INSERT INTO `message` VALUES ('119', '120', '2016-08-11 11:42:11', '你好', '62', '', '');
INSERT INTO `message` VALUES ('119', '120', '2016-08-11 11:47:11', '你好', '63', '', '');
INSERT INTO `message` VALUES ('119', '120', '2016-08-11 11:49:11', '你好', '64', '', '');
INSERT INTO `message` VALUES ('120', '119', '2016-08-11 11:50:11', '你哈', '65', '', '');
INSERT INTO `message` VALUES ('120', '119', '2016-08-11 11:50:11', '你好', '66', '', '');
INSERT INTO `message` VALUES ('120', '119', '2016-08-11 11:50:11', '你好', '67', '', '');
INSERT INTO `message` VALUES ('120', '119', '2016-08-11 11:50:11', ' 你好', '68', '', '');
INSERT INTO `message` VALUES ('120', '119', '2016-08-11 11:50:11', '你好', '69', '', '');
INSERT INTO `message` VALUES ('120', '119', '2016-08-11 11:51:11', '你好', '70', '', '');
INSERT INTO `message` VALUES ('120', '119', '2016-08-11 11:51:11', '你好', '71', '', '');
INSERT INTO `message` VALUES ('119', '120', '2016-08-11 11:51:11', '你好', '72', '', '');
INSERT INTO `message` VALUES ('119', '120', '2016-08-11 11:51:11', '你好', '73', '', '');
INSERT INTO `message` VALUES ('119', '120', '2016-08-11 11:51:11', '你好', '74', '', '');
INSERT INTO `message` VALUES ('119', '120', '2016-08-11 11:51:11', '你好', '75', '', '');
INSERT INTO `message` VALUES ('119', '120', '2016-08-11 11:51:11', '你好', '76', '', '');
INSERT INTO `message` VALUES ('120', '120', '2016-08-11 14:56:11', '大家好我是Yuan', '77', '', '');
INSERT INTO `message` VALUES ('122', '122', '2016-08-11 14:59:11', '大家好', '78', '', '');
INSERT INTO `message` VALUES ('122', '121', '2016-08-11 15:01:11', '你好', '79', '', '');
INSERT INTO `message` VALUES ('122', '121', '2016-08-11 15:01:11', '你好', '80', '', '');
INSERT INTO `message` VALUES ('122', '121', '2016-08-11 15:01:11', '你哈', '81', '', '');
INSERT INTO `message` VALUES ('121', '122', '2016-08-11 15:01:11', '表', '82', '', '');
INSERT INTO `message` VALUES ('119', '120', '2016-08-11 15:26:11', '你哈', '83', '', '');
INSERT INTO `message` VALUES ('120', '120', '2016-08-11 15:28:11', '大家好', '84', '', '');
INSERT INTO `message` VALUES ('120', '119', '2016-01-12 10:10:12', 'hello', '85', '', '');
INSERT INTO `message` VALUES ('119', '120', '2016-08-08 10:02:08', 'hello', '86', '', '');
INSERT INTO `message` VALUES ('119', '120', '2017-01-12 10:12:12', 'hello', '87', '', '');
INSERT INTO `message` VALUES ('119', '120', '2017-01-12 10:02:12', 'hello', '88', '', '');
INSERT INTO `message` VALUES ('119', '120', '2017-01-12 10:02:12', 'hello', '89', '', '');
INSERT INTO `message` VALUES ('119', '120', '2017-01-12 10:02:12', 'hello', '90', '', '');
INSERT INTO `message` VALUES ('119', '120', '2017-01-12 10:02:12', 'hello', '91', '', '');
INSERT INTO `message` VALUES ('153', '120', '2017-01-04 05:05:04', 'hello', '92', '', '');
INSERT INTO `message` VALUES ('120', '153', '2016-08-17 10:02:17', 'hello', '93', '', '');
INSERT INTO `message` VALUES ('153', '120', '2017-01-04 05:05:04', 'hello', '94', '', '');
INSERT INTO `message` VALUES ('153', '120', '2017-01-04 05:05:04', 'hello', '95', '', '');
INSERT INTO `message` VALUES ('153', '120', '2017-01-04 05:05:04', 'hello', '96', '', '');
INSERT INTO `message` VALUES ('153', '120', '2017-01-04 05:05:04', 'hello', '97', '', '');
INSERT INTO `message` VALUES ('120', '153', '2016-08-17 10:03:17', 'hello', '98', '', '');
INSERT INTO `message` VALUES ('119', '120', '2017-01-12 10:02:12', 'hello', '99', '', '');
INSERT INTO `message` VALUES ('120', '120', '2016-08-17 10:05:17', '大家好我是120', '100', '', '');
INSERT INTO `message` VALUES ('120', '120', '2016-08-17 10:06:17', '大家好', '101', '', '');
INSERT INTO `message` VALUES ('153', '120', '2017-01-01 05:04:01', '大家好', '102', '', '');
INSERT INTO `message` VALUES ('120', '119', '2016-08-17 10:09:17', '大家好', '103', '', '');
INSERT INTO `message` VALUES ('120', '153', '2016-08-17 10:09:17', '大家好', '104', '', '');
INSERT INTO `message` VALUES ('120', '153', '2016-08-17 10:41:17', '你好', '105', '', '');
INSERT INTO `message` VALUES ('119', '120', '2016-08-17 11:29:17', '你好', '106', '', '');
INSERT INTO `message` VALUES ('120', '119', '2016-08-17 11:29:17', '你好', '107', '', '');
INSERT INTO `message` VALUES ('120', '119', '2016-08-17 11:29:17', '你好', '108', '', '');
INSERT INTO `message` VALUES ('120', '119', '2016-08-17 11:30:17', '你好', '109', '', '');
INSERT INTO `message` VALUES ('120', '119', '2016-08-17 11:30:17', '你好', '110', '', '');
INSERT INTO `message` VALUES ('119', '120', '2016-08-17 11:30:17', '你好', '111', '', '');
INSERT INTO `message` VALUES ('120', '119', '2016-08-17 11:30:17', '大家好', '112', '', '');
INSERT INTO `message` VALUES ('120', '153', '2016-08-17 11:30:17', '大家好', '113', '', '');
INSERT INTO `message` VALUES ('119', '120', '2016-08-17 11:31:17', '你好', '114', '', '');
INSERT INTO `message` VALUES ('119', '120', '2016-08-17 11:40:17', '你好', '115', '', '');
INSERT INTO `message` VALUES ('120', '119', '2016-08-17 11:40:17', '你好', '116', '', '');
INSERT INTO `message` VALUES ('120', '119', '2016-08-17 11:41:17', '你好', '117', '', '');
INSERT INTO `message` VALUES ('120', '119', '2016-08-17 11:41:17', '你好', '118', '', '');
INSERT INTO `message` VALUES ('119', '120', '2016-08-17 11:41:17', '你好', '119', '', '');
INSERT INTO `message` VALUES ('120', '119', '2016-08-17 11:41:17', '你好', '120', '', '');
INSERT INTO `message` VALUES ('119', '120', '2016-08-17 11:42:17', '不好', '121', '', '');
INSERT INTO `message` VALUES ('119', '120', '2016-08-17 11:42:17', '你好', '122', '', '');
INSERT INTO `message` VALUES ('119', '120', '2016-08-17 11:43:17', '你好', '123', '', '');
INSERT INTO `message` VALUES ('119', '120', '2016-08-17 11:43:17', '我是119', '124', '', '');
INSERT INTO `message` VALUES ('119', '120', '2016-08-17 11:51:17', '你好', '125', '', '');
INSERT INTO `message` VALUES ('119', '120', '2016-08-17 12:07:17', '你好', '126', '', '');
INSERT INTO `message` VALUES ('120', '119', '2016-08-17 12:07:17', '你好', '127', '', '');
INSERT INTO `message` VALUES ('119', '120', '2016-08-17 12:07:17', '你好', '128', '', '');
INSERT INTO `message` VALUES ('120', '119', '2016-08-17 12:07:17', '你好', '129', '', '');
INSERT INTO `message` VALUES ('119', '120', '2016-08-17 14:42:17', '你好', '130', '', '');
INSERT INTO `message` VALUES ('120', '119', '2016-08-17 14:42:17', '你好', '131', '', '');
INSERT INTO `message` VALUES ('120', '119', '2016-08-17 14:43:17', '你好', '132', '', '');
INSERT INTO `message` VALUES ('120', '153', '2016-08-17 14:43:17', '你好', '133', '', '');
INSERT INTO `message` VALUES ('120', '119', '2016-08-17 14:43:17', '你好', '134', '', '');
INSERT INTO `message` VALUES ('120', '119', '2016-08-17 14:43:17', '你好', '135', '', '');
INSERT INTO `message` VALUES ('120', '119', '2016-08-17 14:43:17', '你好', '136', '', '');
INSERT INTO `message` VALUES ('120', '153', '2016-08-17 14:43:17', '你好', '137', '', '');
INSERT INTO `message` VALUES ('120', '119', '2016-08-17 14:44:17', '你好', '138', '', '');
INSERT INTO `message` VALUES ('120', '153', '2016-08-17 14:44:17', '你好', '139', '', '');
INSERT INTO `message` VALUES ('120', '119', '2016-08-17 14:44:17', '你好', '140', '', '');
INSERT INTO `message` VALUES ('120', '153', '2016-08-17 14:44:17', '你好', '141', '', '');
INSERT INTO `message` VALUES ('120', '119', '2016-08-17 14:44:17', '你好', '142', '', '');
INSERT INTO `message` VALUES ('120', '153', '2016-08-17 14:44:17', '你好', '143', '', '');
INSERT INTO `message` VALUES ('119', '120', '2016-08-17 14:44:17', '你好', '144', '', '');
INSERT INTO `message` VALUES ('153', '120', '2017-01-01 05:04:01', '大家好', '145', '', '');
INSERT INTO `message` VALUES ('119', '120', '2016-08-17 14:48:17', '大家好', '146', '', '');
INSERT INTO `message` VALUES ('120', '119', '2016-08-17 14:49:17', '大家好', '147', '', '');
INSERT INTO `message` VALUES ('120', '153', '2016-08-17 14:49:17', '大家好', '148', '', '');
INSERT INTO `message` VALUES ('119', '120', '2016-08-17 14:49:17', '大家好', '149', '', '');
INSERT INTO `message` VALUES ('119', '120', '2016-08-17 14:49:17', '大家好', '150', '', '');
INSERT INTO `message` VALUES ('119', '120', '2016-08-17 14:55:17', '你好', '151', '', '');
INSERT INTO `message` VALUES ('120', '119', '2016-08-17 14:56:17', '你好', '152', '', '');
INSERT INTO `message` VALUES ('120', '153', '2016-08-17 14:56:17', '你好', '153', '', '');
INSERT INTO `message` VALUES ('119', '121', '2016-08-18 07:53:18', 'NIHAO', '154', '', '');
INSERT INTO `message` VALUES ('121', '119', '2016-08-18 07:55:18', 'NIHAO', '155', '', '');
INSERT INTO `message` VALUES ('119', '121', '2016-08-18 07:55:18', 'NIAHO', '156', '', '');
INSERT INTO `message` VALUES ('119', '188', '2016-08-18 07:56:18', '你好', '157', '', '');
INSERT INTO `message` VALUES ('166', '120', '2016-08-21 11:24:21', '你好', '158', '', '');
INSERT INTO `message` VALUES ('119', '120', '2016-08-21 11:28:21', '你好', '159', '', '');
INSERT INTO `message` VALUES ('190', '120', '2016-08-21 12:08:21', '你好', '160', '', '');
INSERT INTO `message` VALUES ('188', '173', '2016-08-21 12:56:21', '你好', '161', '', '');
INSERT INTO `message` VALUES ('120', '119', '2016-08-21 15:10:21', '你好', '162', '', '');
INSERT INTO `message` VALUES ('119', '120', '2016-08-21 15:11:21', '你好', '163', '', '');
INSERT INTO `message` VALUES ('188', '120', '2016-08-21 20:51:21', '你好，我是Mary', '164', '', '');
INSERT INTO `message` VALUES ('120', '188', '2016-08-21 20:55:21', '收到，我是Yuan', '165', '', '');
INSERT INTO `message` VALUES ('188', '119', '2016-08-21 21:07:21', '筒子们，大家好，大家辛苦了', '166', '', '');
INSERT INTO `message` VALUES ('188', '120', '2016-08-21 21:07:21', '筒子们，大家好，大家辛苦了', '167', '', '');
INSERT INTO `message` VALUES ('188', '123', '2016-08-21 21:07:21', '筒子们，大家好，大家辛苦了', '168', '', '');
INSERT INTO `message` VALUES ('188', '190', '2016-08-21 21:07:21', '筒子们，大家好，大家辛苦了', '169', '', '');

-- ----------------------------
-- Table structure for relation
-- ----------------------------
DROP TABLE IF EXISTS `relation`;
CREATE TABLE `relation` (
  `id1` bigint(10) NOT NULL,
  `id2` bigint(10) NOT NULL,
  PRIMARY KEY  (`id1`,`id2`),
  KEY `f2` (`id2`),
  CONSTRAINT `f1` FOREIGN KEY (`id1`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `f2` FOREIGN KEY (`id2`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of relation
-- ----------------------------
INSERT INTO `relation` VALUES ('119', '120');
INSERT INTO `relation` VALUES ('120', '120');
INSERT INTO `relation` VALUES ('119', '121');
INSERT INTO `relation` VALUES ('120', '122');
INSERT INTO `relation` VALUES ('121', '122');
INSERT INTO `relation` VALUES ('119', '123');
INSERT INTO `relation` VALUES ('120', '123');
INSERT INTO `relation` VALUES ('120', '124');
INSERT INTO `relation` VALUES ('123', '124');
INSERT INTO `relation` VALUES ('120', '125');
INSERT INTO `relation` VALUES ('121', '125');
INSERT INTO `relation` VALUES ('119', '130');
INSERT INTO `relation` VALUES ('120', '130');
INSERT INTO `relation` VALUES ('120', '140');
INSERT INTO `relation` VALUES ('119', '150');
INSERT INTO `relation` VALUES ('120', '153');
INSERT INTO `relation` VALUES ('120', '160');
INSERT INTO `relation` VALUES ('123', '160');
INSERT INTO `relation` VALUES ('120', '165');
INSERT INTO `relation` VALUES ('120', '166');
INSERT INTO `relation` VALUES ('120', '169');
INSERT INTO `relation` VALUES ('155', '169');
INSERT INTO `relation` VALUES ('120', '170');
INSERT INTO `relation` VALUES ('169', '172');
INSERT INTO `relation` VALUES ('172', '173');
INSERT INTO `relation` VALUES ('120', '177');
INSERT INTO `relation` VALUES ('120', '181');
INSERT INTO `relation` VALUES ('180', '181');
INSERT INTO `relation` VALUES ('120', '187');
INSERT INTO `relation` VALUES ('119', '188');
INSERT INTO `relation` VALUES ('120', '188');
INSERT INTO `relation` VALUES ('123', '188');
INSERT INTO `relation` VALUES ('170', '188');
INSERT INTO `relation` VALUES ('172', '188');
INSERT INTO `relation` VALUES ('173', '188');
INSERT INTO `relation` VALUES ('187', '188');
INSERT INTO `relation` VALUES ('119', '189');
INSERT INTO `relation` VALUES ('120', '189');
INSERT INTO `relation` VALUES ('120', '190');
INSERT INTO `relation` VALUES ('180', '190');
INSERT INTO `relation` VALUES ('120', '191');
INSERT INTO `relation` VALUES ('191', '192');
INSERT INTO `relation` VALUES ('188', '194');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint(10) NOT NULL auto_increment,
  `name` varchar(20) default NULL,
  `pwd` varchar(32) NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('119', 'Jiaxin', '7e1cd7dca89a1678042477183b7ac3f');
INSERT INTO `user` VALUES ('120', 'Yuan', 'c6f057b86584942e415435ffb1fa93d4');
INSERT INTO `user` VALUES ('121', 'Xuewei', '4c56ff4ce4aaf9573aa5dff913df997a');
INSERT INTO `user` VALUES ('122', 'Yaze', 'a0a080f42e6f13b3a2df133f073095dd');
INSERT INTO `user` VALUES ('123', 'Tom', '202cb962ac59075b964b07152d234b70');
INSERT INTO `user` VALUES ('124', 'John', 'c8ffe9a587b126f152ed3d89a146b445');
INSERT INTO `user` VALUES ('125', 'Yunchen', '3def184ad8f4755ff269862ea77393dd');
INSERT INTO `user` VALUES ('130', 'M', '9b8619251a19057cff70779273e95aa6');
INSERT INTO `user` VALUES ('132', 'Silly', '65ded5353c5ee48d0b7d48c591b8f430');
INSERT INTO `user` VALUES ('133', 'Tikky', '9fc3d7152ba9336a670e36d0ed79bc43');
INSERT INTO `user` VALUES ('140', '140', '1385974ed5904a438616ff7bdb3f7439');
INSERT INTO `user` VALUES ('150', 'Mary', '7ef605fc8dba5425d6965fbd4c8fbe1f');
INSERT INTO `user` VALUES ('151', 'Yane', 'b73ce398c39f506af761d2277d853a92');
INSERT INTO `user` VALUES ('152', 'jiaGu', '37a749d808e46495a8da1e5352d03cae');
INSERT INTO `user` VALUES ('153', 'YY', 'da4fb5c6e93e74d3df8527599fa62642');
INSERT INTO `user` VALUES ('154', 'Yane', '202cb962ac59075b964b07152d234b70');
INSERT INTO `user` VALUES ('155', 'Yanem', '202cb962ac59075b964b07152d234b70');
INSERT INTO `user` VALUES ('156', 'Y', '202cb962ac59075b964b07152d234b70');
INSERT INTO `user` VALUES ('157', 'YY', '202cb962ac59075b964b07152d234b70');
INSERT INTO `user` VALUES ('158', 'ZZ', '202cb962ac59075b964b07152d234b70');
INSERT INTO `user` VALUES ('159', 'X', '202cb962ac59075b964b07152d234b70');
INSERT INTO `user` VALUES ('160', 'X', '202cb962ac59075b964b07152d234b70');
INSERT INTO `user` VALUES ('161', 'te', '202cb962ac59075b964b07152d234b70');
INSERT INTO `user` VALUES ('162', 'Marry', '202cb962ac59075b964b07152d234b70');
INSERT INTO `user` VALUES ('163', 'Marry', '202cb962ac59075b964b07152d234b70');
INSERT INTO `user` VALUES ('164', 'Gray', '202cb962ac59075b964b07152d234b70');
INSERT INTO `user` VALUES ('165', 'Y', '202cb962ac59075b964b07152d234b70');
INSERT INTO `user` VALUES ('166', 'Velly', '202cb962ac59075b964b07152d234b70');
INSERT INTO `user` VALUES ('167', 'Harry', '202cb962ac59075b964b07152d234b70');
INSERT INTO `user` VALUES ('168', 'Harry', '202cb962ac59075b964b07152d234b70');
INSERT INTO `user` VALUES ('169', 'Harry', '202cb962ac59075b964b07152d234b70');
INSERT INTO `user` VALUES ('170', 'Harry', '202cb962ac59075b964b07152d234b70');
INSERT INTO `user` VALUES ('171', 'Harry', 'c20ad4d76fe97759aa27a0c99bff6710');
INSERT INTO `user` VALUES ('172', 'Harray', '202cb962ac59075b964b07152d234b70');
INSERT INTO `user` VALUES ('173', 'Hyne', '202cb962ac59075b964b07152d234b70');
INSERT INTO `user` VALUES ('174', 'Hyne', '202cb962ac59075b964b07152d234b70');
INSERT INTO `user` VALUES ('175', 'Ted', '202cb962ac59075b964b07152d234b70');
INSERT INTO `user` VALUES ('176', 'Ted', '202cb962ac59075b964b07152d234b70');
INSERT INTO `user` VALUES ('177', 'Ted', '202cb962ac59075b964b07152d234b70');
INSERT INTO `user` VALUES ('178', 'Ted', '202cb962ac59075b964b07152d234b70');
INSERT INTO `user` VALUES ('179', 'Ted', '202cb962ac59075b964b07152d234b70');
INSERT INTO `user` VALUES ('180', 'Tedr', '202cb962ac59075b964b07152d234b70');
INSERT INTO `user` VALUES ('181', 'Tedr', '202cb962ac59075b964b07152d234b70');
INSERT INTO `user` VALUES ('182', 'Ted', '202cb962ac59075b964b07152d234b70');
INSERT INTO `user` VALUES ('183', 'YaneZane', '202cb962ac59075b964b07152d234b70');
INSERT INTO `user` VALUES ('184', 'YaneZanen', '202cb962ac59075b964b07152d234b70');
INSERT INTO `user` VALUES ('185', 'Trne', '202cb962ac59075b964b07152d234b70');
INSERT INTO `user` VALUES ('187', 'Tone', '202cb962ac59075b964b07152d234b70');
INSERT INTO `user` VALUES ('188', 'Mary', '202cb962ac59075b964b07152d234b70');
INSERT INTO `user` VALUES ('189', 'Hery', '202cb962ac59075b964b07152d234b70');
INSERT INTO `user` VALUES ('190', 'Tommas', '202cb962ac59075b964b07152d234b70');
INSERT INTO `user` VALUES ('191', 'Johnson', '202cb962ac59075b964b07152d234b70');
INSERT INTO `user` VALUES ('192', '刘远圳', '202cb962ac59075b964b07152d234b70');
INSERT INTO `user` VALUES ('193', 'Torrorrent', '202cb962ac59075b964b07152d234b70');
INSERT INTO `user` VALUES ('194', '基友小花', '202cb962ac59075b964b07152d234b70');
