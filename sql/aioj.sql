# 用户表
CREATE TABLE `aioj`.`user`
(
    `id`                 int          NOT NULL AUTO_INCREMENT COMMENT '用户id',
    `phone`              varchar(11)  NOT NULL COMMENT '手机号',
    `password`           varchar(255) NULL COMMENT '密码',
    `nick_name`          varchar(50)  NOT NULL DEFAULT 'mayday' COMMENT '昵称',
    `id_num`             varchar(13)  NULL COMMENT '身份证号码',
    `first_name`         varchar(50)  NULL COMMENT '姓',
    `last_name`          varchar(50)  NULL COMMENT '名',
    `identity`           varchar(20)  NULL COMMENT '身份',
    `email`              varchar(50)  NULL COMMENT '邮箱',
    `sex`                tinyint      NULL COMMENT '性别（0-女，1-男）',
    `avatar_image_url`   varchar(255) NULL COMMENT '头像图片地址',
    `personal_signature` varchar(255) NULL COMMENT '个性签名',
    `person_profile`     text         NULL COMMENT '个人简介',
    `status`             tinyint      NOT NULL DEFAULT '0' COMMENT '账号状态（0-正常，1-禁用）',
    `id_delete`          tinyint      NOT NULL DEFAULT '0' COMMENT '删除键（0-正常，1-删除）',
    `gmt_created`        datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `gmt_modified`       datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `PHONE_UNIQUE` (`phone`),
    UNIQUE KEY `ID_UNIQUE` (`id_num`),
    UNIQUE KEY `EMAIL_UNIQUE` (`email`)
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT = '用户表';


CREATE TABLE `role`
(
    `id`           bigint(20) unsigned zerofill NOT NULL,
    `role`         varchar(50)                  NOT NULL COMMENT '角色',
    `description`  varchar(100)                          DEFAULT NULL COMMENT '描述',
    `status`       tinyint                      NOT NULL DEFAULT '0' COMMENT '默认0可用，1不可用',
    `gmt_create`   datetime                              DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `gmt_modified` datetime                              DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT = '角色表';

CREATE TABLE `user_role`
(
    `id`           bigint unsigned NOT NULL AUTO_INCREMENT,
    `uid`          int             NOT NULL,
    `role_id`      bigint unsigned NOT NULL,
    `gmt_create`   datetime DEFAULT CURRENT_TIMESTAMP,
    `gmt_modified` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    KEY `uid` (`uid`) USING BTREE,
    KEY `role_id` (`role_id`) USING BTREE,
    CONSTRAINT `user_role_ibfk_1` FOREIGN KEY (`uid`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT `user_role_ibfk_2` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT = '用户角色表';

CREATE TABLE `auth`
(
    `id`           bigint unsigned NOT NULL AUTO_INCREMENT,
    `name`         varchar(100)             DEFAULT NULL COMMENT '权限名称',
    `permission`   varchar(100)             DEFAULT NULL COMMENT '权限字符串',
    `status`       tinyint         NOT NULL DEFAULT '0' COMMENT '0可用，1不可用',
    `gmt_create`   datetime                 DEFAULT CURRENT_TIMESTAMP,
    `gmt_modified` datetime                 DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT = '权限表';

CREATE TABLE `role_auth`
(
    `id`           bigint unsigned NOT NULL AUTO_INCREMENT,
    `auth_id`      bigint unsigned NOT NULL,
    `role_id`      bigint unsigned NOT NULL,
    `gmt_create`   datetime DEFAULT CURRENT_TIMESTAMP,
    `gmt_modified` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    KEY `auth_id` (`auth_id`) USING BTREE,
    KEY `role_id` (`role_id`) USING BTREE,
    CONSTRAINT `role_auth_ibfk_1` FOREIGN KEY (`auth_id`) REFERENCES `auth` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT `role_auth_ibfk_2` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON UPDATE CASCADE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT = '角色权限表';