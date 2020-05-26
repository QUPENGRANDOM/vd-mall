CREATE SCHEMA IF NOT EXISTS `vd_mall_user_center` DEFAULT CHARACTER SET utf8;
USE `vd_mall_user_center`;

CREATE TABLE IF NOT EXISTS `ucs_user`
(
    `id`              INT(11)      NOT NULL AUTO_INCREMENT,
    `username`        VARCHAR(45)  NOT NULL COMMENT '用户名',
    `password`        VARCHAR(200) NULL COMMENT '密码',
    `nickname`        VARCHAR(45)  NULL COMMENT '昵称',
    `age`             INT          NULL     DEFAULT 0 COMMENT '年龄',
    `sex`             TINYINT(4)   NULL COMMENT '性别 0-男 1-女',
    `avatar`          VARCHAR(200) NULL COMMENT '头像',
    `mail`            VARCHAR(200) NULL COMMENT '邮箱',
    `telephone`       VARCHAR(45)  NULL COMMENT '电话',
    `address`         VARCHAR(45)  NULL COMMENT '所属地',
    `status`          TINYINT(4)   NULL     DEFAULT 0 COMMENT '状态 0-禁用 1-启用',
    `last_login_time` TIMESTAMP    NULL     DEFAULT NULL COMMENT '最后登录时间',
    `create_time`     TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
)
    ENGINE = InnoDB COMMENT '系统用户表';


CREATE TABLE IF NOT EXISTS `ucs_role`
(
    `id`          INT(11)      NOT NULL AUTO_INCREMENT,
    `role_name`   VARCHAR(45)  NOT NULL COMMENT '角色名',
    `description` VARCHAR(200) NULL     DEFAULT NULL COMMENT '描述',
    `status`      TINYINT(4)   NULL     DEFAULT 0 COMMENT '状态 0-禁用 1-启用',
    `create_time` TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`)
)
    ENGINE = InnoDB COMMENT '系统角色表';


CREATE TABLE IF NOT EXISTS `ucs_user_role_relation`
(
    `id`      INT(11) NOT NULL AUTO_INCREMENT,
    `user_id` INT(11) NOT NULL COMMENT '用户id',
    `role_id` INT(11) NOT NULL COMMENT '角色id',
    PRIMARY KEY (id)
) ENGINE = InnoDB COMMENT '用户角色关系表';


CREATE TABLE IF NOT EXISTS `ucs_menu`
(
    `id`          INT(11)      NOT NULL AUTO_INCREMENT,
    `title`       VARCHAR(200) NULL COMMENT '菜单名称',
    `sort`        INT(11)      NULL COMMENT '菜单顺序',
    `parent_id`   INT(11)      NOT NULL DEFAULT 0 COMMENT '父级菜单，0 时为 根节点',
    `icon`        VARCHAR(200) COMMENT '图标',
    `icon_type`   TINYINT(4)   NOT NULL DEFAULT 0 COMMENT '图标类型 0-CSS 1-IMG',
    `status`      TINYINT(4)   NULL     DEFAULT 0 COMMENT '状态 0-禁用 1-启用',
    `create_time` TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (id)
) ENGINE = InnoDB COMMENT '系统菜单表';


CREATE TABLE IF NOT EXISTS `ucs_role_menu_relation`
(
    `id`      INT(11) NOT NULL AUTO_INCREMENT,
    `menu_id` INT(11) NOT NULL COMMENT '菜单id',
    `role_id` INT(11) NOT NULL COMMENT '角色id',
    PRIMARY KEY (id)
) ENGINE = InnoDB COMMENT '角色菜单关系表';


CREATE TABLE IF NOT EXISTS `ucs_permission`
(
    `id`          INT(11)      NOT NULL AUTO_INCREMENT,
    `name`        VARCHAR(200) NULL COMMENT '权限名称',
    `pattern`     VARCHAR(200) NULL COMMENT 'ANT 规则',
    `description` VARCHAR(200) NULL     DEFAULT NULL COMMENT '描述',
    `create_time` TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (id)
) ENGINE = InnoDB COMMENT '系统权限表';


CREATE TABLE IF NOT EXISTS `ucs_role_permission_relation`
(
    `id`            INT(11) NOT NULL AUTO_INCREMENT,
    `permission_id` INT(11) NOT NULL COMMENT '权限id',
    `role_id`       INT(11) NOT NULL COMMENT '角色id',
    PRIMARY KEY (id)
) ENGINE = InnoDB COMMENT '角色权限关系表';
