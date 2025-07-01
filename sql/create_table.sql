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
    signature TEXT                                  null comment '自我描述',

    userStatus   tinyint      default 0                 not null comment '状态 0 - 正常， 1 - 封号',
    userRole     tinyint  default 2                 not null comment '用户角色: 0 - 管理员 1 - 会员（已发布笔记） 2 - 普通用户',

    createTime   datetime default CURRENT_TIMESTAMP null comment '创建时间',
    updateTime   datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
    isDelete     tinyint  default 0                 not null comment '是否删除'
)comment '用户';

SET @yangId = REPLACE(UUID(),'-','');


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
    commentCount     int(8) unsigned null default null comment '评论数量',
    status       tinyint(1) UNSIGNED NULL DEFAULT NULL COMMENT '状态，0：正常，1：被举报，2：禁止查看',

    createTime   datetime default CURRENT_TIMESTAMP null comment '创建时间',
    updateTime   datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
    isDelete     tinyint  default 0                 not null comment '是否删除',
    FOREIGN KEY (userId) REFERENCES user(id)
)comment '帖子';

# blog comments
CREATE TABLE if not exists `blog_comments`
(
    id           bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
    userId       char(32)  not null comment 'id,UUID（无中划线32位）' ,
    blogId      bigint  NOT NULL COMMENT 'blog_id',
    parentId    bigint(20) UNSIGNED NOT NULL COMMENT '关联的1级评论id，如果是一级评论，则值为0',
    content      varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '回复的内容',
    status       tinyint(1) UNSIGNED NULL DEFAULT NULL COMMENT '状态，0：正常，1：被举报，2：禁止查看',
    createTime  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updateTime  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id) USING BTREE,
    FOREIGN KEY (userId) REFERENCES user(id),
    FOREIGN KEY (blogId) REFERENCES blog(id),
    foreign key (parentId) references blog_comments(id) on delete cascade
)comment 'blog comments' ;

ALTER TABLE blog_comments
ADD CONSTRAINT fk_comment_parent
FOREIGN KEY (parentId) REFERENCES blog_comments(id)
ON DELETE CASCADE;


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


# 导入示例用户
INSERT INTO user_centre.user (
    username, id,userAccount, gender, userPassword,avatarUrl,
    admissionTime, email, signature, userStatus,
    createTime, updateTime, userRole
) VALUES (
             '阳阳阳',  @yangId,'yang', null, '1bbd886460827015e5d605ed44252251','/img/a.png',
             '24T1', null, '我是一个热爱学习编程的学生。', 0,
             '2023-08-06 14:14:22', '2023-08-06 14:39:37', 1);

INSERT INTO user_centre.course (code, title, category, toolTip)
VALUES
    ('COMP4121', '4121', 0, '高级算法 Advanced Algorithms'),
    ('COMP4128', '4128', 0, '编程挑战 Programming Challenges'),
    ('COMP4141', '4141', 0, '计算理论 Theory of Computation'),
    ('COMP4161', '4161', 0, '软件验证高级专题 Adv Topics Software Verification'),
    ('COMP4418', '4418', 0, '知识表示与推理 KR & Reasoning'),
    ('COMP4511', '4511', 0, '用户界面设计与构建 UI Design & Construction'),
    ('COMP6080', '6080', 0, 'Web 前端编程 Web Front-End Prog'),
    ('COMP6131', '6131', 0, '软件安全分析 Software Sec Analysis'),
    ('COMP6441', '6441', 3, '安全工程与网络安全 Security Eng & Cyber Sec'),
    ('COMP6443', '6443', 3, 'Web 应用安全与测试 Web App Sec & Testing'),
    ('COMP6445', '6445', 0, '数字取证基础 Digital Forensics'),
    ('COMP6447', '6447', 0, '系统与软件安全评估 Sys & Soft Sec Assess'),
    ('COMP6448', '6448', 3, '安全工程大师课程 Sec Eng Masterclass'),
    ('COMP6451', '6451', 0, '加密货币与分布式账本技术 Crypto & DLT'),
    ('COMP6452', '6452', 0, '区块链应用软件架构 SW Arch for Blockchain'),
    ('COMP6453', '6453', 3, '应用密码学 Applied Cryptography'),
    ('COMP6713', '6713', 1, '自然语言处理 NLP'),
    ('COMP6714', '6714', 3, '信息检索与网络搜索 Info Retrieval & Search'),
    ('COMP6721', '6721', 0, '形式化方法导论 (In-)Formal Methods'),
    ('COMP6733', '6733', 0, '物联网实验设计工作室 IoT Exp Design'),
    ('COMP6741', '6741', 0, '不可解问题算法 Algo for Intract'),
    ('COMP6752', '6752', 3, '并发系统建模 Modelling Concur Sys'),
    ('COMP6771', '6771', 0, '高级 C++ 编程 Advanced C++ Prog'),
    ('COMP6841', '6841', 3, '扩展安全工程与网络安全 Extended Sec Eng & Cyber Sec'),
    ('COMP6843', '6843', 3, '扩展 Web 应用安全与测试 Extended Web App Sec'),
    ('COMP6845', '6845', 0, '扩展数字取证与事件响应 Extended Digital Forensics'),
    ('COMP6991', '6991', 0, 'Rust 现代编程实战 Modern Prog w/ Rust'),
    ('COMP9020', '9020', 0, '计算机科学基础课程 Foundations CS'),
    ('COMP9021', '9021', 0, '编程原理与实践 Principles Prog'),
    ('COMP9024', '9024', 0, '数据结构与算法核心内容 Data Struct & Alg'),
    ('COMP9032', '9032', 4, '微处理器及接口技术 Microproc & IF'),
    ('COMP9044', '9044', 0, '软件构建技术与工具 Soft Constr Tools'),
    ('COMP9101', '9101', 0, '算法设计与分析 Algo Design & Ana'),
    ('COMP9102', '9102', 0, '编程语言与编译器原理 Prog Lang & Comp'),
    ('COMP9153', '9153', 0, '算法验证方法 Algo Verification'),
    ('COMP9154', '9154', 3, '并发性基础 Foundations Concur'),
    ('COMP9164', '9164', 0, '编程语言概念 Concepts Prog Lang'),
    ('COMP9201', '9201', 0, '操作系统原理 Operating Systems'),
    ('GSOE9210', '9210', 5, '工程决策结构分析 Eng Decision Struct'),
    ('COMP9211', '9211', 0, '计算机体系结构 Computer Arch'),
    ('GSOE9220', '9220', 5, '创业启动与管理 Launching Startup'),
    ('COMP9222', '9222', 4, '数字电路与系统 Digital Circuits & Sys'),
    ('COMP9242', '9242', 0, '高级操作系统 Adv Operating Systems'),
    ('COMP9243', '9243', 0, '分布式系统原理 Distributed Systems'),
    ('COMP9283', '9283', 0, '扩展操作系统专题 Extended OS'),
    ('COMP9311', '9311', 0, '数据库系统原理课程 Database Sys'),
    ('COMP9312', '9312', 2, '图分析数据挖掘 Data Analytics for Graphs'),
    ('COMP9313', '9313', 2, '大数据管理技术 Big Data Management'),
    ('COMP9315', '9315', 0, '数据库系统实现 DB Sys Implementation'),
    ('COMP9319', '9319', 3, '网页数据压缩与检索 Web Data Comp & Search'),
    ('COMP9321', '9321', 2, '数据服务工程 Data Services Eng'),
    ('COMP9331', '9331', 3, '计算机网络及其应用 Comp Networks & Apps'),
    ('COMP9332', '9332', 3, '网络路由与交换 Network Routing & Switching'),
    ('COMP9333', '9333', 3, '高级计算机网络专题 Adv Comp Networks'),
    ('COMP9334', '9334', 3, '计算机系统与网络容量规划 Capacity Plan Sys & Nets'),
    ('COMP9336', '9336', 3, '移动数据网络技术 Mobile Data Networking'),
    ('COMP9337', '9337', 3, '有线与无线网络安全 Sec Fixed & Wireless Nets'),
    ('COMP9414', '9414', 1, '人工智能导论 Artificial Intelligence'),
    ('COMP9415', '9415', 0, '计算机图形学基础 Comp Graphics'),
    ('COMP9417', '9417', 1, '机器学习与数据挖掘 ML & Data Mining'),
    ('COMP9418', '9418', 1, '统计机器学习高级专题 Adv Stat ML'),
    ('COMP9434', '9434', 4, '机器人软件架构 Robotic SW Arch'),
    ('COMP9444', '9444', 1, '神经网络与深度学习概论 Neural Nets & DL'),
    ('COMP9447', '9447', 3, '安全工程实践研讨 Sec Eng Workshop'),
    ('COMP9491', '9491', 1, '应用人工智能技术 Applied AI'),
    ('COMP9511', '9511', 0, '人机交互基础课程 HCI'),
    ('COMP9517', '9517', 1, '计算机视觉技术 Comp Vision'),
    ('COMP9727', '9727', 1, '推荐系统原理与实现 Recommender Sys'),
    ('COMP9814', '9814', 1, '拓展人工智能专题 Extended AI'),
    ('GSOE9820', '9820', 5, '工程项目管理方法论 Eng Project Mgmt')
;


INSERT INTO user_centre.note (
    courseId, enrollTime, title, link, author, userId, lecturer, toolTip,isOfficial,isChecked)
VALUES ('17','24T1','6713官方笔记','https://sudden-comic-c00.notion.site/1f1f45253452809daeaad72fceb2146f?v=1f1f45253452806fb07b000ce212e8c5&pvs=4',
        'yang', @yangId,'joshi','这是yang的6713笔记', '1','1');

INSERT INTO user_centre.note (
    courseId, enrollTime,title, link, author, userId, lecturer, toolTip, isOfficial, isChecked)
VALUES ('17','25T1','6713非官方笔记','https://sudden-comic-c00.notion.site/1f1f45253452809daeaad72fceb2146f?v=1f1f45253452806fb07b000ce212e8c5&pvs=4',
        'yang', @yangId,'XXX','这是yang的6713笔记2号', '0', '1');

