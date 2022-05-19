/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50553
Source Host           : localhost:3306
Source Database       : test3

Target Server Type    : MYSQL
Target Server Version : 50553
File Encoding         : 65001

Date: 2022-05-19 17:45:04
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `roleName` varchar(50) DEFAULT NULL,
  `roleDesc` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('1', '院长', '负责全面工作');
INSERT INTO `sys_role` VALUES ('2', '研究员', '课程研发工作');
INSERT INTO `sys_role` VALUES ('3', '讲师', '授课工作');
INSERT INTO `sys_role` VALUES ('4', '助教', '协助解决学生的问题');
INSERT INTO `sys_role` VALUES ('7', '助管', '帮助老师处理日常事务');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `password` varchar(80) DEFAULT NULL,
  `phoneNum` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', 'zhangsan', 'zhangsan@itcast.cn', '$2a$10$Ndnetl8gtNSEKtAGHVCZo.Xl8YvIp9VlJU.E5lAMpk0CvU0gzYr5G', '13888888888');
INSERT INTO `sys_user` VALUES ('26', 'test', '1849937402@qq.com', '$2a$10$.OWEI92dQ/jzOuokbXMOheHa7onmYUTB0INK6qQici97tSKSkR64a', '18546523141');
INSERT INTO `sys_user` VALUES ('27', 'admin', '25611@emai', '$2a$10$ayCQHuvjIzk4nw.cEOmj1OGXH5JRE2ThhlLzA.4s4.P36riUbTxqe', '15236544487');
INSERT INTO `sys_user` VALUES ('28', 'huweiv', 'huweiv@com.email', '$2a$10$prbNlqIPY0BHBb5iw56vkeF2mr2foVwpRVk1sGRc3K/m22gftZfWu', '15468645214');

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `userId` bigint(20) NOT NULL,
  `roleId` bigint(20) NOT NULL,
  PRIMARY KEY (`userId`,`roleId`),
  KEY `roleId` (`roleId`),
  CONSTRAINT `sys_user_role_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `sys_user` (`id`),
  CONSTRAINT `sys_user_role_ibfk_2` FOREIGN KEY (`roleId`) REFERENCES `sys_role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES ('27', '1');
INSERT INTO `sys_user_role` VALUES ('1', '2');
INSERT INTO `sys_user_role` VALUES ('26', '2');
INSERT INTO `sys_user_role` VALUES ('28', '2');
INSERT INTO `sys_user_role` VALUES ('26', '3');
INSERT INTO `sys_user_role` VALUES ('26', '4');
