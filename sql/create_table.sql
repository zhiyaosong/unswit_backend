# 数据库初始化

-- 创建库
create database if not exists user_centre;

-- 切换库
use user_centre;

# 按顺序drop，因为有外键
drop table if exists user_note_likes;
drop table if exists note;
drop table if exists course;
drop table if exists blog_comments;
drop table if exists user_blog_likes;
drop table if exists blog;
drop table if exists user;

# 用户表
create table if not exists `user`
(
    userName     varchar(256)                       null comment '用户昵称',
    id           char(32)          primary key      not null comment 'id,UUID（无中划线32位）' ,
    userAccount  varchar(256)                       null comment '账号',
    avatarUrl    varchar(1024)                      null comment '用户头像',
    gender       tinyint                            null comment '性别',
    userPassword varchar(512)                       not null comment '密码(MD5 Hash)',
    admissionTime varchar(256)                      null comment '入学时间（早于24T1，24T1, 24T2, 24T3, 25T1, 25T2, 25T3, 晚于25T3）',
    email        varchar(512)                       null comment '邮箱',
    phone        varchar(256)                       null comment '手机号',
    signature    TEXT                               null comment '自我描述',

    userStatus   tinyint  default 0             not null comment '状态 0 - 正常， 1 - 封号',
    userRole     tinyint  default 2                 not null comment '用户角色: 0 - 管理员 1 - 会员（已发布笔记） 2 - 普通用户',

    createTime   datetime default CURRENT_TIMESTAMP null comment '创建时间',
    updateTime   datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
    isDelete     tinyint  default 0                 not null comment '是否删除'
)comment '用户';


create table if not exists `course`
(
    id           bigint auto_increment comment 'id' primary key,
    code         varchar(256)                       not null comment '课程码',
    title        varchar(512)                       null comment '课程名',
    category     int      default 0                 not null comment '所属课程体系,0:it,1:ai,2:ds,3:cb,4:ee,5:pm,99:其他',
    toolTip      TEXT                               null comment '标题说明(旁边的!),也可以作为课程描述',

    createTime   datetime default CURRENT_TIMESTAMP null comment '创建时间',
    updateTime   datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
    isDelete     tinyint  default 0                 not null comment '是否删除'
)comment '课程';


create table if not exists `note`
(
      id         bigint auto_increment primary key comment '笔记主键',
      courseId   bigint              not null comment '所属课程的id',
      enrollTime varchar(256)        null comment '一般形式为24T1,25T1,25T2这样，只在逻辑中做约束',
      title      varchar(512)        null comment '笔记名',
      link       varchar(512)        null comment '笔记链接',
      author     varchar(512)        not null comment '笔记作者自定义名',
      userId     char(32)              not null comment '发布者id',
      lecturer   varchar(512)        comment '任课教师',
      isOfficial tinyint   default 0 not null comment '是否是官方笔记：0：非官方，1：官方',
      toolTip    text                comment '笔记描述',
      noteStatus int default 0       not null comment '状态 0-正常,1-封号',
      isChecked  int default 0       not null comment '状态 0-未审核，1-审核通过',
      likeCount  int default 0       not null comment '笔记被点赞数',


      createTime datetime default CURRENT_TIMESTAMP comment '创建时间',
      updateTime datetime default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP comment '更新时间',
      isDelete   tinyint default 0   not null comment '是否删除',

      constraint fk_note_course foreign key (courseId) references course(id),
      constraint fk_note_user foreign key (userId) references user(id)
) comment '笔记';

create table if not exists user_note_likes
(
     userId          char(32)              NOT NULL,
     noteId          BIGINT                NOT NULL,
     likedAt         TIMESTAMP   DEFAULT   CURRENT_TIMESTAMP,
     PRIMARY KEY (userId, noteId),
     FOREIGN KEY (userId) REFERENCES user(id),
     FOREIGN KEY (noteId) REFERENCES note(id)
) comment '用户笔记点赞表';

# 自动更新note.likeCount的触发器
DELIMITER $$
CREATE TRIGGER trg_like_insert
    AFTER INSERT ON user_note_likes
    FOR EACH ROW
BEGIN
    UPDATE note
    SET likeCount = likeCount + 1
    WHERE id = NEW.noteId;
END$$

CREATE TRIGGER trg_like_delete
    AFTER DELETE ON user_note_likes
    FOR EACH ROW
BEGIN
    UPDATE note
    SET likeCount = likeCount - 1
    WHERE id = OLD.noteId;
END$$
DELIMITER ;

# 帖子表
create table if not exists `blog`
(
    id           bigint auto_increment primary key comment '帖子id',
    userId       char(32)      not null comment 'id,UUID（无中划线32位）' ,
    title        varchar(255)  character set utf8mb4 collate utf8mb4_unicode_ci not null comment '标题',
    images       varchar(2048) character set utf8mb4 collate utf8mb4_general_ci  comment '帖子照片，最多9张，多张以","隔开',
    content      varchar(2048) character set utf8mb4 collate utf8mb4_unicode_ci not null comment '帖子内容',
    likeCount    int(8) unsigned null default 0 comment '点赞数量',
    commentCount int(8) unsigned null default null comment '评论数量',
    status       tinyint(1) UNSIGNED NULL DEFAULT NULL COMMENT '状态，0：正常，1：被举报，2：禁止查看',

    createTime   datetime default CURRENT_TIMESTAMP null comment '创建时间',
    updateTime   datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
    isDelete     tinyint  default 0                 not null comment '是否删除',
    FOREIGN KEY (userId) REFERENCES user(id)
)comment '帖子';


# blog comments
CREATE TABLE if not exists `blog_comments`
(
    id          bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
    userId      char(32)  not null comment 'id,UUID（无中划线32位）' ,
    blogId      bigint  NOT NULL COMMENT 'blog_id',
    parentId    bigint(20) UNSIGNED NULL COMMENT '关联的1级评论id，如果是一级评论，则值为NULL',
    content     varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '回复的内容',
    status      tinyint(1) UNSIGNED NULL DEFAULT NULL COMMENT '状态，0：正常，1：被举报，2：禁止查看',
    createTime  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updateTime  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id) USING BTREE,
    CONSTRAINT fk_comment_user FOREIGN KEY (userId) REFERENCES user(id),
    CONSTRAINT fk_comment_blog FOREIGN KEY (blogId) REFERENCES blog(id),
    CONSTRAINT fk_comment_parent FOREIGN KEY (parentId) REFERENCES blog_comments(id) ON DELETE CASCADE
)comment 'blog comments' ;


create table if not exists user_blog_likes
(
    userId          char(32)              NOT NULL,
    blogId          BIGINT                NOT NULL,
    likedAt         TIMESTAMP   DEFAULT   CURRENT_TIMESTAMP,
    PRIMARY KEY (userId, blogId),
    FOREIGN KEY (userId) REFERENCES user(id),
    FOREIGN KEY (blogId) REFERENCES blog(id)
) comment '用户帖子点赞表';

# blog.likeCount的触发器
DELIMITER $$
CREATE TRIGGER trg_blog_like_insert
    AFTER INSERT ON user_blog_likes
    FOR EACH ROW
BEGIN
    UPDATE blog
    SET likeCount = likeCount + 1
    WHERE id = NEW.blogId;
END$$

CREATE TRIGGER trg_blog_like_delete
    AFTER DELETE ON user_blog_likes
    FOR EACH ROW
BEGIN
    UPDATE blog
    SET likeCount = likeCount - 1
    WHERE id = OLD.blogId;
END$$

# blog.commentCount的触发器
CREATE TRIGGER trg_blog_comment_insert
    AFTER INSERT ON blog_comments
    FOR EACH ROW
BEGIN
    UPDATE blog
    SET commentCount = commentCount + 1
    WHERE id = NEW.blogId;
END$$

CREATE TRIGGER trg_blog_comment_delete
    AFTER DELETE ON blog_comments
    FOR EACH ROW
BEGIN
    UPDATE blog
    SET commentCount = commentCount - 1
    WHERE id = OLD.blogId;
END$$
DELIMITER ;


