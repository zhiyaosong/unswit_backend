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

create table if not exists `course`
(
    id bigint auto_increment comment 'id' primary key,
    code         varchar(256)                       not null comment '课程码',
    title        varchar(512)                       null comment '课程名',
    category     int      default 0                 not null comment '所属课程体系,0:it,1:ai,2:ds,3:cb,4:ee',
    toolTip      TEXT                               null comment '标题说明(旁边的!),也可以作为课程描述',
    runTime      int                                null comment '1,2,3,12,13,23,123,分别代表T1,T2,T3开学',

    createTime   datetime default CURRENT_TIMESTAMP null comment '创建时间',
    updateTime   datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
    isDelete     tinyint  default 0                 not null comment '是否删除'
)comment '课程';

INSERT INTO user_centre.course (code, title, category, toolTip, runTime
) VALUES (
  '6713','自然语言处理','1','这是一门好课6713','123');

drop table if exists note;

create table if not exists `note`
(
      id         bigint auto_increment primary key comment '笔记主键',
      courseId  bigint              not null comment '所属课程的id',
      title      varchar(512)        null comment '笔记名',
      link       varchar(512)        null comment '笔记链接',
      author     varchar(512)        not null comment '笔记作者',
      lecturer   varchar(512)        comment '任课教师',
      toolTip    text                comment '笔记描述',

      noteStatus int default 0       not null comment '状态 0-正常,1-封号',
      createTime datetime default CURRENT_TIMESTAMP comment '创建时间',
      updateTime datetime default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP comment '更新时间',
      isDelete   tinyint default 0   not null comment '是否删除',

      constraint fk_note_course foreign key (courseId) references course(id)
) comment '笔记';

INSERT INTO user_centre.note (
        courseId, title, link, author, lecturer, toolTip)
VALUES ('1','6713笔记','https://sudden-comic-c00.notion.site/1f1f45253452809daeaad72fceb2146f?v=1f1f45253452806fb07b000ce212e8c5&pvs=4',
        'yang','joshi','这是yang的6713笔记');


