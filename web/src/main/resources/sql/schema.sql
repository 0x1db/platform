#### 用户基本信息表
DROP TABLE IF EXISTS base_user_info;
CREATE TABLE base_user_info
(
    id       BIGINT(20)   NOT NULL AUTO_INCREMENT COMMENT '主键',
    username VARCHAR(64)  NOT NULL COMMENT '用户名',
    password VARCHAR(64)  NOT NULL COMMENT '密码',
    status   CHAR(1)      NOT NULL DEFAULT 1 COMMENT '状态(0:禁用 1：正常)',
    type     CHAR(1)      NOT NULL DEFAULT '' COMMENT '用户类型(1.后台用户 2.前端用户)',
    remark   VARCHAR(100) NOT NULL DEFAULT '' COMMENT '备注',
    primary key (id)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1000
  DEFAULT CHARSET = utf8 COMMENT ='用户基本信息表';

#### 后台用户表
DROP TABLE IF EXISTS sys_user;
CREATE TABLE sys_user
(
    id           BIGINT(20)  NOT NULL AUTO_INCREMENT COMMENT '主键',
    base_user_id BIGINT(20)  NOT NULL COMMENT '用户基本信息ID',
    head_img     VARCHAR(80) NOT NULL DEFAULT '' COMMENT '用户头像',
    gender       CHAR(1)     NOT NULL DEFAULT '2' COMMENT '性别(0:女 1:男 2:未知)',
    email        VARCHAR(30) NOT NULL DEFAULT '' COMMENT '邮箱',
    create_date  BIGINT(10)  NOT NULL DEFAULT 0 COMMENT '创建时间',
    modify_date  BIGINT(10)  NOT NULL DEFAULT 0 COMMENT '修改时间',
    del_flag     TINYINT(1)  NOT NULL DEFAULT 0 COMMENT '删除标识（1：已删除 0：正常）',
    primary key (id)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1000
  DEFAULT CHARSET = utf8 COMMENT ='后台用户表';

#### 角色信息表
DROP TABLE IF EXISTS sys_role;
CREATE TABLE sys_role
(
    id          BIGINT(20)   NOT NULL AUTO_INCREMENT COMMENT '主键',
    name        VARCHAR(32)  NOT NULL COMMENT '名称',
    flag        VARCHAR(32)  NOT NULL COMMENT '标识',
    description VARCHAR(100) NOT NULL DEFAULT '' COMMENT '描述',
    primary key (id)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1000
  DEFAULT CHARSET = utf8 COMMENT ='角色信息表';

#### 系统资源信息表
DROP TABLE IF EXISTS `sys_resource`;
CREATE TABLE `sys_resource`
(
    id          BIGINT(20)   NOT NULL AUTO_INCREMENT COMMENT '主键',
    name        VARCHAR(32)  NOT NULL DEFAULT '' COMMENT '名称',
    flag        VARCHAR(20)  NOT NULL DEFAULT '' COMMENT '标识',
    url         VARCHAR(64)  NOT NULL DEFAULT '' COMMENT 'url地址',
    type        CHAR(1)      NOT NULL DEFAULT '' COMMENT '类别(1.菜单 2.按钮)',
    method      VARCHAR(20)  NOT NULL DEFAULT '' COMMENT '',
    parent_id   BIGINT(20)   NOT NULL DEFAULT 0 COMMENT '父级ID',
    description VARCHAR(100) NOT NULL DEFAULT '' COMMENT '描述',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1000
  DEFAULT CHARSET = utf8 COMMENT ='资源表';

#### 角色用户信息表
DROP TABLE IF EXISTS `sys_user_roles`;
CREATE TABLE `sys_user_roles`
(
    id      BIGINT(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
    role_id BIGINT(11) NOT NULL COMMENT '角色ID',
    user_id BIGINT(11) NOT NULL COMMENT '用户id',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1000
  DEFAULT CHARSET = utf8 COMMENT ='用户-角色信息表';

#### 角色资源信息表
DROP TABLE IF EXISTS `sys_role_resource`;
CREATE TABLE `sys_role_resource`
(
    id          BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
    role_id     BIGINT(20) NOT NULL COMMENT '角色ID',
    resource_id BIGINT(20) NOT NULL COMMENT '资源ID',
    create_date BIGINT(10) NOT NULL COMMENT '创建时间',
    creator     BIGINT(20) NOT NULL DEFAULT 0 COMMENT '创建人',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1000
  DEFAULT CHARSET = utf8 COMMENT ='角色-资源信息表';