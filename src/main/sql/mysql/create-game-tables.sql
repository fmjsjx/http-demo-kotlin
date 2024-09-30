-- ----------------------------
-- Table structure for tb_account
-- ----------------------------
CREATE TABLE `tb_account` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键 自增 用户ID',
    `group_id` tinyint NOT NULL DEFAULT 1 COMMENT 'GROUP ID 用于数据库分库',
    `type` tinyint NOT NULL COMMENT '分类：1 -> 游客账号，2 -> 第三方平台账号',
    `state` tinyint NOT NULL DEFAULT 1 COMMENT '账号状态：1 正常，2 禁用',
    `remote_address` varchar(64) NOT NULL COMMENT '创建账号时的远端地址',
    `client_version` varchar(32) NOT NULL COMMENT '创建账号时的客户端版本号',
    `udid` varchar(64) NOT NULL COMMENT '创建账号时的客户端设备ID',
    `slot` tinyint NOT NULL DEFAULT 0 COMMENT '用于AB测试的编号，0-15，算法：udid.hash & 0xF',
    `device_info` varchar(255) NOT NULL COMMENT '创建账号时的客户端设备信息',
    `os_info` varchar(255) NOT NULL COMMENT '创建账号时的客户端操作系统信息',
    `created_at` bigint NOT NULL COMMENT '创建时间',
    `updated_at` bigint NOT NULL COMMENT '最后更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `UNIQUE_udid` (`udid`)
) COMMENT = '账号表';
