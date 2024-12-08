/*
 Navicat Premium Data Transfer

 Source Server         : shine
 Source Server Type    : MySQL
 Source Server Version : 50744
 Source Host           : localhost:3306
 Source Schema         : hh_auth

 Target Server Type    : MySQL
 Target Server Version : 50744
 File Encoding         : 65001

 Date: 08/12/2024 14:27:09
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for oauth_access_token
-- ----------------------------
DROP TABLE IF EXISTS `oauth_access_token`;
CREATE TABLE `oauth_access_token`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `client_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '客户端ID',
  `access_token` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '访问令牌',
  `refresh_token` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '刷新令牌',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `grant_type` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '授权类型',
  `scope` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '授权范围',
  `token_type` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'Bearer' COMMENT 'token类型',
  `redirect_uri` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '重定向地址',
  `access_token_expire_time` datetime NOT NULL COMMENT '访问令牌过期时间',
  `refresh_token_expire_time` datetime NOT NULL COMMENT '刷新令牌过期时间',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user` bigint(20) NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `update_user` bigint(20) NULL DEFAULT NULL COMMENT '修改人',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '逻辑删除',
  `remark` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_user_id`(`user_id`) USING BTREE,
  INDEX `idx_client_id`(`client_id`) USING BTREE,
  INDEX `idx_access_token`(`access_token`) USING BTREE,
  INDEX `idx_refresh_token`(`refresh_token`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '访问令牌记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oauth_access_token
-- ----------------------------

-- ----------------------------
-- Table structure for oauth_authorization_code
-- ----------------------------
DROP TABLE IF EXISTS `oauth_authorization_code`;
CREATE TABLE `oauth_authorization_code`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `client_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '客户端ID',
  `authorization_code` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '授权码',
  `redirect_uri` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '重定向地址',
  `scope` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '授权范围',
  `expire_at` datetime NOT NULL COMMENT '过期时间',
  `status` tinyint(4) NOT NULL DEFAULT 1 COMMENT '枚举：授权码状态：1：未使用 2：已使用 3：已过期',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user` bigint(20) NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `update_user` bigint(20) NULL DEFAULT NULL COMMENT '修改人',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '逻辑删除',
  `remark` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `client_id`(`client_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 58 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '授权码' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oauth_authorization_code
-- ----------------------------
INSERT INTO `oauth_authorization_code` VALUES (1, 'auth', 'e38076ab7d0a494c8f9fee00bde8e518', 'sad', 'all', '2024-12-02 18:21:51', 3, '2024-12-02 09:21:50', 1, '2024-12-06 06:10:38', 1, b'0', '使用授权码生成的令牌');
INSERT INTO `oauth_authorization_code` VALUES (2, 'auth', '8da019ad52f34256b100cc5a4029b9ec', 'sad', 'all', '2024-12-02 18:26:49', 3, '2024-12-02 09:26:49', 1, '2024-12-06 06:10:38', 1, b'0', '使用授权码生成的令牌');
INSERT INTO `oauth_authorization_code` VALUES (3, 'auth', '6d0d7f82a3294db0b202d04ab06fe2be', 'sad', 'all', '2024-12-02 18:27:22', 3, '2024-12-02 09:27:21', 1, '2024-12-06 06:10:38', 1, b'0', '使用授权码生成的令牌');
INSERT INTO `oauth_authorization_code` VALUES (4, 'auth', '7faa8f5e6ed94f46b732c2ba51f4a25d', 'sad', 'all', '2024-12-02 18:33:32', 3, '2024-12-02 09:33:32', 1, '2024-12-06 06:10:38', 1, b'0', '使用授权码生成的令牌');
INSERT INTO `oauth_authorization_code` VALUES (5, 'auth', '245aa4b346914a2db22177d3d12bb86a', 'sad', 'all', '2024-12-02 18:38:48', 3, '2024-12-02 09:38:47', 1, '2024-12-06 06:10:38', 1, b'0', '使用授权码生成的令牌');
INSERT INTO `oauth_authorization_code` VALUES (6, 'auth', 'e2e4e31b76d240f19f513e96475293de', 'sad', 'all', '2024-12-02 18:45:47', 3, '2024-12-02 09:45:46', 1, '2024-12-06 06:10:38', 1, b'0', '使用授权码生成的令牌');
INSERT INTO `oauth_authorization_code` VALUES (7, 'auth', 'b3cb9459cf92470a829d164102c0a3a4', 'sad', 'all', '2024-12-02 18:45:50', 3, '2024-12-02 09:45:49', 1, '2024-12-06 06:10:38', 1, b'0', '使用授权码生成的令牌');
INSERT INTO `oauth_authorization_code` VALUES (8, 'auth', '18e2cb3ee0f24f8685d0a5c70a65288a', 'sad', 'all', '2024-12-02 18:45:50', 3, '2024-12-02 09:45:50', 1, '2024-12-06 06:10:38', 1, b'0', '使用授权码生成的令牌');
INSERT INTO `oauth_authorization_code` VALUES (9, 'auth', '4ea321f2c3eb489fa83056345e4199b4', 'sad', 'all', '2024-12-02 18:45:51', 3, '2024-12-02 09:45:50', 1, '2024-12-06 06:10:38', 1, b'0', '使用授权码生成的令牌');
INSERT INTO `oauth_authorization_code` VALUES (10, 'auth', '571491b8a4734ebeb4c783663928d554', 'sad', 'all', '2024-12-02 18:45:51', 3, '2024-12-02 09:45:51', 1, '2024-12-06 06:10:38', 1, b'0', '使用授权码生成的令牌');
INSERT INTO `oauth_authorization_code` VALUES (11, 'auth', '21ffc0b82d944623adff53e93affcb30', 'sad', 'all', '2024-12-02 18:46:28', 3, '2024-12-02 09:46:28', 1, '2024-12-06 06:10:38', 1, b'0', '使用授权码生成的令牌');
INSERT INTO `oauth_authorization_code` VALUES (12, 'auth', '4a38791f8d764b309005587e215985df', 'sad', 'all', '2024-12-02 18:46:28', 3, '2024-12-02 09:46:28', 1, '2024-12-06 06:10:38', 1, b'0', '使用授权码生成的令牌');
INSERT INTO `oauth_authorization_code` VALUES (13, 'auth', '91f0aa1f205841e3811126ea563475bb', 'sad', 'all', '2024-12-02 18:48:28', 3, '2024-12-02 09:48:28', 1, '2024-12-06 06:10:38', 1, b'0', '使用授权码生成的令牌');
INSERT INTO `oauth_authorization_code` VALUES (14, 'auth', '555afb4d8ed0444bb3d38d63c1b94ece', 'sad', 'all', '2024-12-02 18:48:34', 2, '2024-12-02 09:48:34', 1, '2024-12-06 06:10:38', 1, b'0', '使用授权码生成的令牌');
INSERT INTO `oauth_authorization_code` VALUES (15, 'auth', '415b626fbf934b48aaeea8964bff55ff', 'sad', 'all', '2024-12-02 18:48:35', 3, '2024-12-02 09:48:34', 1, '2024-12-06 06:10:38', 1, b'0', '使用授权码生成的令牌');
INSERT INTO `oauth_authorization_code` VALUES (16, 'auth', '0945a2f731c24b28b7e54aabad407a4a', 'sad', 'all', '2024-12-02 19:21:59', 3, '2024-12-02 10:21:59', 1, '2024-12-06 06:10:38', 1, b'0', '使用授权码生成的令牌');
INSERT INTO `oauth_authorization_code` VALUES (17, 'auth', 'fcfd9db3a8ae4c489da1a0042180afe8', 'sad', 'all', '2024-12-02 19:22:01', 3, '2024-12-02 10:22:01', 1, '2024-12-06 06:10:38', 1, b'0', '使用授权码生成的令牌');
INSERT INTO `oauth_authorization_code` VALUES (18, 'auth', 'cce1d4e1467844a785f9d24525cae76c', 'sad', 'all', '2024-12-02 19:22:02', 2, '2024-12-02 10:22:02', 1, '2024-12-06 06:10:38', 1, b'0', '使用授权码生成的令牌');
INSERT INTO `oauth_authorization_code` VALUES (19, 'auth', '5d63d590e8cf44a1b04d566995f4925a', 'sad', 'all', '2024-12-02 19:29:44', 3, '2024-12-02 10:29:44', 1, '2024-12-06 06:10:38', 1, b'0', '使用授权码生成的令牌');
INSERT INTO `oauth_authorization_code` VALUES (20, 'auth', '9bb3fa0654d947c3b16077d1bcc58525', 'sad', 'all', '2024-12-02 19:29:46', 3, '2024-12-02 10:29:45', 1, '2024-12-06 06:10:38', 1, b'0', '使用授权码生成的令牌');
INSERT INTO `oauth_authorization_code` VALUES (21, 'auth', '0944bc3526034070883cceda6bb2135a', 'sad', 'all', '2024-12-02 19:29:48', 3, '2024-12-02 10:29:48', 1, '2024-12-06 06:10:38', 1, b'0', '使用授权码生成的令牌');
INSERT INTO `oauth_authorization_code` VALUES (22, 'auth', 'ff741e85771648098fad7bd42f47727d', 'sad', 'all', '2024-12-02 19:29:50', 3, '2024-12-02 10:29:50', 1, '2024-12-06 06:10:38', 1, b'0', '使用授权码生成的令牌');
INSERT INTO `oauth_authorization_code` VALUES (23, 'auth', '6a44ad13735c412e92c2e96f00fb68d5', 'sad', 'all', '2024-12-02 19:34:18', 3, '2024-12-02 10:34:17', 1, '2024-12-06 06:10:38', 1, b'0', '使用授权码生成的令牌');
INSERT INTO `oauth_authorization_code` VALUES (24, 'auth', 'f4172f076f874f1d856a6fb8af3eb242', 'sad', 'all', '2024-12-02 19:35:11', 3, '2024-12-02 10:35:11', 1, '2024-12-06 06:10:38', 1, b'0', '使用授权码生成的令牌');
INSERT INTO `oauth_authorization_code` VALUES (25, 'auth', '2756778f391c493cb10c34764fbbbe1c', 'sad', 'all', '2024-12-02 19:35:13', 3, '2024-12-02 10:35:12', 1, '2024-12-06 06:10:38', 1, b'0', '使用授权码生成的令牌');
INSERT INTO `oauth_authorization_code` VALUES (26, 'auth', '1fedd39528da4d28bc67eda22bb09e6c', 'sad', 'all', '2024-12-03 16:24:40', 3, '2024-12-03 07:24:40', 1, '2024-12-06 06:10:38', 1, b'0', '使用授权码生成的令牌');
INSERT INTO `oauth_authorization_code` VALUES (27, 'auth', 'f1c3442bb88a4a17a715e51c41415afe', 'https://www.baidu.com', 'all', '2024-12-03 16:24:55', 3, '2024-12-03 07:24:54', 1, '2024-12-06 06:10:38', 1, b'0', '使用授权码生成的令牌');
INSERT INTO `oauth_authorization_code` VALUES (28, 'auth', '7c734bd441934d52a582a33f4ba706e0', 'https://www.baidu.com', 'all', '2024-12-03 16:25:11', 3, '2024-12-03 07:25:10', 1, '2024-12-06 06:10:38', 1, b'0', '使用授权码生成的令牌');
INSERT INTO `oauth_authorization_code` VALUES (29, 'auth', 'b7f39ccdc0d34956a60b35602dbce33c', 'https://www.baidu.com', 'all', '2024-12-03 16:25:22', 3, '2024-12-03 07:25:22', 1, '2024-12-06 06:10:38', 1, b'0', '使用授权码生成的令牌');
INSERT INTO `oauth_authorization_code` VALUES (30, 'auth', '863a9732cffc430d8cd9208c85e9dd08', 'https://www.baidu.com', 'all', '2024-12-03 16:25:26', 3, '2024-12-03 07:25:26', 1, '2024-12-06 06:10:38', 1, b'0', '使用授权码生成的令牌');
INSERT INTO `oauth_authorization_code` VALUES (31, 'auth', '7ea4aecbf2834dbfb0b9c2377629e92e', 'https://www.baidu.com', 'all', '2024-12-03 16:25:27', 3, '2024-12-03 07:25:27', 1, '2024-12-06 06:10:38', 1, b'0', '使用授权码生成的令牌');
INSERT INTO `oauth_authorization_code` VALUES (32, 'auth', '39e12886d0a84efba78e2609315f35b5', 'https://www.baidu.com', 'all', '2024-12-03 16:25:28', 3, '2024-12-03 07:25:27', 1, '2024-12-06 06:10:38', 1, b'0', '使用授权码生成的令牌');
INSERT INTO `oauth_authorization_code` VALUES (33, 'auth', 'a944c515fb6c4320a66f6461844c03ad', 'https://www.baidu.com', 'all', '2024-12-03 16:25:28', 3, '2024-12-03 07:25:28', 1, '2024-12-06 06:10:38', 1, b'0', '使用授权码生成的令牌');
INSERT INTO `oauth_authorization_code` VALUES (34, 'auth', 'c41b0e7ffac3456294a93416733a3b84', 'https://www.baidu.com', 'all', '2024-12-03 16:25:29', 3, '2024-12-03 07:25:28', 1, '2024-12-06 06:10:38', 1, b'0', '使用授权码生成的令牌');
INSERT INTO `oauth_authorization_code` VALUES (35, 'auth', 'f7d6b9464d0a45e79866c37d6febd644', 'https://www.baidu.com', 'all', '2024-12-03 16:25:29', 3, '2024-12-03 07:25:29', 1, '2024-12-06 06:10:38', 1, b'0', '使用授权码生成的令牌');
INSERT INTO `oauth_authorization_code` VALUES (36, 'auth', '0100e5bdd78344a0b61339e61e4320ac', 'https://www.baidu.com', 'all', '2024-12-03 16:25:30', 3, '2024-12-03 07:25:29', 1, '2024-12-06 06:10:38', 1, b'0', '使用授权码生成的令牌');
INSERT INTO `oauth_authorization_code` VALUES (37, 'auth', '039d0df3396f476b805caee53a0f5da0', 'https://www.baidu.com', 'all', '2024-12-03 16:25:30', 3, '2024-12-03 07:25:30', 1, '2024-12-06 06:10:38', 1, b'0', '使用授权码生成的令牌');
INSERT INTO `oauth_authorization_code` VALUES (38, 'auth', 'a329b1e95b114d29a836bbd20bd1a8b9', 'https://www.baidu.com', 'all', '2024-12-03 16:25:30', 3, '2024-12-03 07:25:30', 1, '2024-12-06 06:10:38', 1, b'0', '使用授权码生成的令牌');
INSERT INTO `oauth_authorization_code` VALUES (39, 'auth', '8b0319403b7047fb9ad434135d22e756', 'https://www.baidu.com', 'all', '2024-12-03 16:25:31', 3, '2024-12-03 07:25:30', 1, '2024-12-06 06:10:38', 1, b'0', '使用授权码生成的令牌');
INSERT INTO `oauth_authorization_code` VALUES (40, 'auth', '0ce997e3a6184096819805f709e31360', 'https://www.baidu.com', 'all', '2024-12-03 16:25:31', 3, '2024-12-03 07:25:31', 1, '2024-12-06 06:10:38', 1, b'0', '使用授权码生成的令牌');
INSERT INTO `oauth_authorization_code` VALUES (41, 'auth', '11e11a0800f6436c9a976f39caa39484', 'https://www.baidu.com', 'all', '2024-12-03 16:25:32', 3, '2024-12-03 07:25:32', 1, '2024-12-06 06:10:38', 1, b'0', '使用授权码生成的令牌');
INSERT INTO `oauth_authorization_code` VALUES (42, 'auth', '0d4a9c65fb8749cda1b3436c09c2ebec', 'https://www.baidu.com', 'all', '2024-12-03 16:25:33', 3, '2024-12-03 07:25:32', 1, '2024-12-06 06:10:38', 1, b'0', '使用授权码生成的令牌');
INSERT INTO `oauth_authorization_code` VALUES (43, 'auth', '2b73749f9cb6424ea2924afd55c00f97', 'https://www.baidu.com', 'all', '2024-12-03 16:25:35', 3, '2024-12-03 07:25:35', 1, '2024-12-06 06:10:38', 1, b'0', '使用授权码生成的令牌');
INSERT INTO `oauth_authorization_code` VALUES (44, 'auth', 'dcd21a7632104492aa3285a616b4a343', 'https://www.baidu.com', 'all', '2024-12-03 16:25:36', 3, '2024-12-03 07:25:35', 1, '2024-12-06 06:10:38', 1, b'0', '使用授权码生成的令牌');
INSERT INTO `oauth_authorization_code` VALUES (45, 'auth', 'ac57d5f34e934620877110bef764a2b4', 'https://www.baidu.com', 'all', '2024-12-03 16:25:36', 3, '2024-12-03 07:25:36', 1, '2024-12-06 06:10:38', 1, b'0', '使用授权码生成的令牌');
INSERT INTO `oauth_authorization_code` VALUES (46, 'auth', 'e926cf32916f40aebe6f590e5557b5df', 'sad', 'all', '2024-12-04 17:38:36', 3, '2024-12-04 08:38:35', 1, '2024-12-06 06:10:38', 1, b'0', '使用授权码生成的令牌');
INSERT INTO `oauth_authorization_code` VALUES (47, 'auth', '10ef6485b69b4208b354a9b28417f95b', 'sad', 'all', '2024-12-04 17:38:52', 3, '2024-12-04 08:38:52', 1, '2024-12-06 06:10:38', 1, b'0', '使用授权码生成的令牌');
INSERT INTO `oauth_authorization_code` VALUES (48, 'auth', '4b2b7b02ea1745ec9a1899fd4cb5f3e2', 'www.baidu.com', 'all', '2024-12-04 17:39:07', 3, '2024-12-04 08:39:06', 1, '2024-12-06 06:10:38', 1, b'0', '使用授权码生成的令牌');
INSERT INTO `oauth_authorization_code` VALUES (49, 'auth', '9843b7c77f1840aebc06ca3c5a50896a', 'www.baidu.com', 'all', '2024-12-04 17:39:20', 3, '2024-12-04 08:39:20', 1, '2024-12-06 06:10:38', 1, b'0', '使用授权码生成的令牌');
INSERT INTO `oauth_authorization_code` VALUES (50, 'auth', 'cdf7b84f558d4ff9b0b66980834882c0', 'www.baidu.com', 'all', '2024-12-04 20:08:07', 3, '2024-12-04 11:08:07', 1, '2024-12-06 06:10:38', 1, b'0', '使用授权码生成的令牌');
INSERT INTO `oauth_authorization_code` VALUES (51, 'auth', 'b4d56c88892d4c708c4677701946d474', 'www.baidu.com', 'all', '2024-12-04 20:09:12', 3, '2024-12-04 11:09:11', 1, '2024-12-06 06:10:38', 1, b'0', '使用授权码生成的令牌');
INSERT INTO `oauth_authorization_code` VALUES (52, 'auth', '894526a410e64ef396ecd9687f9ef4df', 'www.baidu.com', 'all', '2024-12-05 10:26:41', 3, '2024-12-05 01:26:41', 1, '2024-12-06 06:10:38', 1, b'0', '使用授权码生成的令牌');
INSERT INTO `oauth_authorization_code` VALUES (53, 'auth', 'a053e4f9b7ed4c8b8628e731651cad50', 'www.baidu.com', 'all', '2024-12-05 10:45:05', 2, '2024-12-05 01:45:05', 1, '2024-12-06 06:10:38', 1, b'0', '使用授权码生成的令牌');
INSERT INTO `oauth_authorization_code` VALUES (54, 'auth', '3b3a7e6275ad4253be0cf40e276da40e', 'www.baidu.com', 'all', '2024-12-05 11:05:05', 2, '2024-12-05 02:05:04', 1, '2024-12-06 06:10:38', 1, b'0', '使用授权码生成的令牌');
INSERT INTO `oauth_authorization_code` VALUES (55, 'auth', '6e8c33e5c1e14d389c4af33d9b37f503', 'www.baidu.com', 'all', '2024-12-05 11:30:01', 3, '2024-12-05 02:30:01', 1, '2024-12-06 06:10:38', 1, b'0', '使用授权码生成的令牌');
INSERT INTO `oauth_authorization_code` VALUES (56, 'auth', '339878a254684d0d868c880d9c76d182', 'www.baidu.com', 'all', '2024-12-05 11:31:56', 2, '2024-12-05 02:31:56', 1, '2024-12-06 06:10:38', 1, b'0', '使用授权码生成的令牌');
INSERT INTO `oauth_authorization_code` VALUES (57, 'auth', '646bc1b2246847a896b37c5222d4a7e0', 'www.baidu.com', 'all', '2024-12-05 11:32:19', 2, '2024-12-05 02:32:18', 1, '2024-12-06 06:10:38', 1, b'0', '使用授权码生成的令牌');

-- ----------------------------
-- Table structure for oauth_client
-- ----------------------------
DROP TABLE IF EXISTS `oauth_client`;
CREATE TABLE `oauth_client`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `client_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '客户点ID',
  `client_secret` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '客户端密钥',
  `client_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '客户端名称',
  `access_token_lefetime` int(10) NOT NULL DEFAULT 3600 COMMENT '访问令牌默认失效时间',
  `refresh_token_lefetime` int(10) NULL DEFAULT NULL COMMENT '刷新令牌默认失效时间',
  `status` tinyint(4) NOT NULL DEFAULT 1 COMMENT '枚举：客户端状态：0：禁用，1：启用',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user` bigint(20) NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `update_user` bigint(20) NULL DEFAULT NULL COMMENT '修改人',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '逻辑删除',
  `remark` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `client_id`(`client_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '客户端' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oauth_client
-- ----------------------------
INSERT INTO `oauth_client` VALUES (1, 'auth', '$2a$10$4ecJOdX/dqLAefuSGzjsOOrziBuca0cjRPpCp08jWcBjyMKu6bPki', '测试客户端', 3600, 7200, 1, '2024-12-01 07:44:48', NULL, '2024-12-01 07:44:48', NULL, b'0', NULL);

-- ----------------------------
-- Table structure for oauth_client_grant_type
-- ----------------------------
DROP TABLE IF EXISTS `oauth_client_grant_type`;
CREATE TABLE `oauth_client_grant_type`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `client_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '客户点ID',
  `grant_type` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '支持的授权模式',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user` bigint(20) NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `update_user` bigint(20) NULL DEFAULT NULL COMMENT '修改人',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '逻辑删除',
  `remark` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `client_id`(`client_id`, `grant_type`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '客户端授权模式' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oauth_client_grant_type
-- ----------------------------

-- ----------------------------
-- Table structure for oauth_client_redirect_uri
-- ----------------------------
DROP TABLE IF EXISTS `oauth_client_redirect_uri`;
CREATE TABLE `oauth_client_redirect_uri`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `client_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '客户点ID',
  `redirect_uri` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '允许的重定向地址',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user` bigint(20) NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `update_user` bigint(20) NULL DEFAULT NULL COMMENT '修改人',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '逻辑删除',
  `remark` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `client_id`(`client_id`, `redirect_uri`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '客户端重定向地址' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oauth_client_redirect_uri
-- ----------------------------

-- ----------------------------
-- Table structure for oauth_client_scope
-- ----------------------------
DROP TABLE IF EXISTS `oauth_client_scope`;
CREATE TABLE `oauth_client_scope`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `client_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '客户点ID',
  `scope` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '授权范围',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user` bigint(20) NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `update_user` bigint(20) NULL DEFAULT NULL COMMENT '修改人',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '逻辑删除',
  `remark` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `client_id`(`client_id`, `scope`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '客户端授权范围' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oauth_client_scope
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
