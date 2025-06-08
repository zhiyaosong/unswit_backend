# 数据库初始化

-- 创建库
create database if not exists user_centre;

-- 切换库
use user_centre;

# 按顺序drop，因为有外键
drop table if exists note;
drop table if exists course;
drop table if exists user;

# 用户表
create table if not exists `user`
(
    userName     varchar(256)                       null comment '用户昵称',
    id           bigint auto_increment comment 'id' primary key,
    userAccount  varchar(256)                       null comment '账号',
    avatarUrl    varchar(1024)                      null comment '用户头像',
    gender       tinyint                            null comment '性别',
    userPassword varchar(512)                       not null comment '密码(MD5 Hash)',
    admissionTime varchar(256)                      null comment '入学时间（早于24T1，24T1, 24T2, 24T3, 25T1, 25T2, 25T3, 晚于25T3）',
    email        varchar(512)                       null comment '邮箱',
    phone        varchar(256)                       null comment '手机号',
    signature TEXT                                  null comment '自我描述',

    userStatus   int      default 0                 not null comment '状态 0 - 正常， 1 - 封号',
    userRole     tinyint  default 2                 not null comment '用户角色: 0 - 管理员 1 - 会员（已发布笔记） 2 - 普通用户',
    createTime   datetime default CURRENT_TIMESTAMP null comment '创建时间',
    updateTime   datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
    isDelete     tinyint  default 0                 not null comment '是否删除'
)comment '用户';

# 导入示例用户
INSERT INTO user_centre.user (
  username, userAccount, gender, userPassword,avatarUrl,
  admissionTime, email, signature, userStatus,
  createTime, updateTime, userRole
) VALUES (
  '阳阳阳', 'yang', null, '1bbd886460827015e5d605ed44252251','/img/a.png',
  '24T1', null, '我是一个热爱学习编程的学生。', 0,
  '2023-08-06 14:14:22', '2023-08-06 14:39:37', 1);


create table if not exists `course`
(
    id bigint auto_increment comment 'id' primary key,
    code         varchar(256)                       not null comment '课程码',
    title        varchar(512)                       null comment '课程名',
    category     int      default 0                 not null comment '所属课程体系,0:it,1:ai,2:ds,3:cb,4:ee,99:其他',
    toolTip      TEXT                               null comment '标题说明(旁边的!),也可以作为课程描述',

    createTime   datetime default CURRENT_TIMESTAMP null comment '创建时间',
    updateTime   datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
    isDelete     tinyint  default 0                 not null comment '是否删除'
)comment '课程';

INSERT INTO user_centre.course (code, title, category, toolTip
) VALUES (
  'COMP6713','6713 NLP','1','这是一门好课6713');

INSERT INTO user_centre.course (code, title, category, toolTip
) VALUES (
 'COMP9417','9417 ML&DM','1','这是9417');

INSERT INTO user_centre.course (code, title, category, toolTip
) VALUES (
 'COMP9101','9101 Algo. Des.&Ana.','0','这是9101');

INSERT INTO user_centre.course (code, title, category, toolTip
) VALUES (
             '0000','0000 其他','99','其他');



create table if not exists `note`
(
      id         bigint auto_increment primary key comment '笔记主键',
      courseId   bigint              not null comment '所属课程的id',
      enrollTime varchar(256)        null comment '一般形式为24T1,25T1,25T2这样，只在逻辑中做约束',
      title      varchar(512)        null comment '笔记名',
      link       varchar(512)        null comment '笔记链接',
      author     varchar(512)        not null comment '笔记作者自定义名',
      userId     bigint              not null comment '发布者id',
      lecturer   varchar(512)        comment '任课教师',
      isOfficial tinyint   default 1 not null comment '是否是官方笔记：0：官方，1：非官方',
      toolTip    text                comment '笔记描述',
      noteStatus int default 0       not null comment '状态 0-正常,1-封号',
      isChecked  int default 0       not null comment '状态 0-未审核，1-审核通过',

      createTime datetime default CURRENT_TIMESTAMP comment '创建时间',
      updateTime datetime default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP comment '更新时间',
      isDelete   tinyint default 0   not null comment '是否删除',

      constraint fk_note_course foreign key (courseId) references course(id),
      constraint fk_note_user foreign key (userId) references user(id)
) comment '笔记';

INSERT INTO user_centre.note (
        courseId, enrollTime, title, link, author, userId, lecturer, toolTip,isOfficial)
VALUES ('1','24T1','6713官方笔记','https://sudden-comic-c00.notion.site/1f1f45253452809daeaad72fceb2146f?v=1f1f45253452806fb07b000ce212e8c5&pvs=4',
        'yang','1','joshi','这是yang的6713笔记', '1');

INSERT INTO user_centre.note (
    courseId, enrollTime,title, link, author, userId, lecturer, toolTip, isOfficial)
VALUES ('1','25T1','6713非官方笔记','https://sudden-comic-c00.notion.site/1f1f45253452809daeaad72fceb2146f?v=1f1f45253452806fb07b000ce212e8c5&pvs=4',
        'yang','1','XXX','这是yang的6713笔记2号', '0');

INSERT INTO user_centre.note (
    courseId, title, link, author, userId, lecturer, toolTip, isOfficial)
VALUES ('2','9417笔记','https://sudden-comic-c00.notion.site/1f1f45253452809daeaad72fceb2146f?v=1f1f45253452806fb07b000ce212e8c5&pvs=4',
        'yang','1','9417-lecturer','这是yang的9417笔记', '1');

INSERT INTO user_centre.note (
    courseId, title, link, author, userId, lecturer, toolTip, isOfficial)
VALUES ('3','9101笔记','https://sudden-comic-c00.notion.site/1f1f45253452809daeaad72fceb2146f?v=1f1f45253452806fb07b000ce212e8c5&pvs=4',
        'yang','1','9101-lecturer','这是yang的9101笔记', '1');


