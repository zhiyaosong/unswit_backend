# 数据库初始化

-- 创建库
create database if not exists user_centre;

-- 切换库
use user_centre;

# 用户表
create table user
(
    username     varchar(256)                       null comment '用户昵称',
    id           bigint auto_increment comment 'id'
        primary key,
    userAccount  varchar(256)                       null comment '账号',
    avatarUrl    varchar(1024)                      null comment '用户头像',
    gender       tinyint                            null comment '性别',
    userPassword varchar(512)                       not null comment '密码',
    phone        varchar(128)                       null comment '电话',
    email        varchar(512)                       null comment '邮箱',
    userStatus   int      default 0                 not null comment '状态 0 - 正常',
    createTime   datetime default CURRENT_TIMESTAMP null comment '创建时间',
    updateTime   datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
    isDelete     tinyint  default 0                 not null comment '是否删除',
    userRole     int      default 0                 not null comment '用户角色 0 - 普通用户 1 - 管理员',

)
    comment '用户';

# 导入示例用户
INSERT INTO user_centre.user (username, userAccount, gender, userPassword, phone, email, userStatus, createTime, updateTime, isDelete, userRole) VALUES ('阳阳阳', 'yang', null, 'b0dd3697a192885d7c055db46155b26a', null, null, 0, '2023-08-06 14:14:22', '2023-08-06 14:39:37', 0, 1, '1');

