SET @OLD_UNIQUE_CHECKS = @@UNIQUE_CHECKS, UNIQUE_CHECKS = 0;
SET @OLD_FOREIGN_KEY_CHECKS = @@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS = 0;
SET @OLD_SQL_MODE = @@SQL_MODE, SQL_MODE = 'TRADITIONAL,ALLOW_INVALID_DATES';

CREATE SCHEMA IF NOT EXISTS `vd_mall` DEFAULT CHARACTER SET utf8;
USE `vd_mall`;

-- -----------------------------------------------------
-- Table `v_distribution`.`users`
-- -----------------------------------------------------
CREATE TABLE `user`(
    `id`          int(11)            NOT NULL AUTO_INCREMENT,
    `username`    varchar(45)        DEFAULT NULL COMMENT '登入用户名',
    `password`    varchar(45)        DEFAULT NULL COMMENT '密码',
    `avatar`      varchar(500)       DEFAULT NULL COMMENT '头像地址',
    `nickname`    varchar(45)        DEFAULT NULL COMMENT '昵称',
    `sex`         tinyint(1)         DEFAULT 0 COMMENT '性别 0-男 1-女',
    `enable`      tinyint(1)         DEFAULT 1 COMMENT '账号状态 0-禁用 1-启用',
    `account_non_expired` tinyint(1) DEFAULT 1 COMMENT '账号过期 0-过期 1-正常',
    `account_non_locked` tinyint(1)  DEFAULT 1 COMMENT '账号状态 0-锁定 1-正常',
    `credentials_non_expired` tinyint(1) DEFAULT NULL COMMENT '凭证过期 0-过期 1-正常',
    `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='用户表';

SET SQL_MODE = @OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS = @OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS = @OLD_UNIQUE_CHECKS;