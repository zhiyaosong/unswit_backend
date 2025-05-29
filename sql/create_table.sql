# 数据库初始化

-- 创建库
create database if not exists user_centre;

-- 切换库
use user_centre;

# # 如果用户表已存在,则删除
# drop table if exists user;

# 用户表
create table if not exists `user`
(
    username     varchar(256)                       null comment '用户昵称',
    id           bigint auto_increment comment 'id' primary key,
    userAccount  varchar(256)                       null comment '账号',
    avatarUrl    varchar(1024)                      null comment '用户头像',
    gender       tinyint                            null comment '性别',
    userPassword varchar(512)                       not null comment '密码(MD5 Hash)',
    admissionTime varchar(16)                       null comment '入学时间（早于24T1，24T1, 24T2, 24T3, 25T1, 25T2, 25T3, 晚于25T3）',
    email        varchar(512)                       null comment '邮箱',
    selfDescription  TEXT                           null comment '自我描述',

    userStatus   int      default 0                 not null comment '状态 0 - 正常， 1 - 封号',
    createTime   datetime default CURRENT_TIMESTAMP null comment '创建时间',
    updateTime   datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
    isDelete     tinyint  default 0                 not null comment '是否删除',
    userRole     int      default 0                 not null comment '用户角色 0 - 普通用户 1 - 管理员'

)comment '用户';

# 导入示例用户
INSERT INTO user_centre.user (
  username, userAccount, gender, userPassword,avatarUrl,
  admissionTime, email, selfDescription, userStatus,
  createTime, updateTime, isDelete, userRole
) VALUES (
  '阳阳阳', 'yang', null, '1bbd886460827015e5d605ed44252251','/img/a.png',
  '24T1', null, '我是一个热爱学习编程的学生。', 0,
  '2023-08-06 14:14:22', '2023-08-06 14:39:37', 0, 1);

create table if not exists `notes`
(
    id bigint auto_increment comment 'id' primary key,
    code         varchar(256)                       not null comment '课程码',
    title        varchar(512)                       null comment '课程名',
    author       varchar(512)                       not null comment '笔记作者',
    lecturer     varchar(512)                       null comment '任课教师',
    stream       varchar(256)                       null comment '所属课程体系',
    selfDescription  TEXT                           null comment '自我描述',

    noteStatus   int      default 0                 not null comment '状态 0 - 正常， 1 - 封号',
    createTime   datetime default CURRENT_TIMESTAMP null comment '创建时间',
    updateTime   datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
    isDelete     tinyint  default 0                 not null comment '是否删除'
)comment '笔记';

INSERT INTO user_centre.notes (code, title, author, lecturer, stream, selfDescription
) VALUES (
  '6713','自然语言处理','yang','joshi',
  'ai','这是一门好课，我也是一个好笔记');
