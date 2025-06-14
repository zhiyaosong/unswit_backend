# 数据库初始化

-- 创建库
create database if not exists user_centre;

-- 切换库
use user_centre;

# 按顺序drop，因为有外键
drop table if exists user_note_likes;
drop table if exists note;
drop table if exists course;
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

    userStatus   int      default 0                 not null comment '状态 0 - 正常， 1 - 封号',
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

CREATE TABLE user_note_likes
(
     userId          char(32)              NOT NULL,
     noteId          BIGINT                NOT NULL,
     likedAt         TIMESTAMP   DEFAULT   CURRENT_TIMESTAMP,
     PRIMARY KEY (userId, noteId),
     FOREIGN KEY (userId) REFERENCES user(id),
     FOREIGN KEY (noteId) REFERENCES note(id)
) comment '用户笔记点赞表';

# 帖子表
create table if not exists `blog`
(
    id         bigint auto_increment primary key comment '帖子id',
    userId     char(32)  not null comment 'id,UUID（无中划线32位）' ,
    title varchar(255) character set utf8mb4 collate utf8mb4_unicode_ci not null comment '标题',
    images varchar(2048) character set utf8mb4 collate utf8mb4_general_ci  comment '帖子照片，最多9张，多张以","隔开',
    content varchar(2048) character set utf8mb4 collate utf8mb4_unicode_ci not null comment '帖子内容',
    liked int(8) unsigned null default 0 comment '点赞数量',
    comments int(8) unsigned null default null comment '评论数量',
    createTime   datetime default CURRENT_TIMESTAMP null comment '创建时间',
    updateTime   datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
    isDelete     tinyint  default 0                 not null comment '是否删除',
    FOREIGN KEY (userId) REFERENCES user(id)
)comment '帖子';


# blog comments
CREATE TABLE `blog_comments`
(
     id bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
     userId     char(32)  not null comment 'id,UUID（无中划线32位）' ,
     blog_id bigint  NOT NULL COMMENT 'blog_id',
     parent_id bigint(20) UNSIGNED NOT NULL COMMENT '关联的1级评论id，如果是一级评论，则值为0',
     content varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '回复的内容',
     status tinyint(1) UNSIGNED NULL DEFAULT NULL COMMENT '状态，0：正常，1：被举报，2：禁止查看',
     create_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
     update_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
     PRIMARY KEY (id) USING BTREE,
     FOREIGN KEY (userId) REFERENCES user(id),
     FOREIGN KEY (blog_id) REFERENCES blog(id),
     foreign key(parent_id) references blog_comments(id)
         on delete cascade
)comment 'blog comments' ;









# mysql的触发器，自动更新note.likeCount
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

# 这个在点赞功能不需要
# CREATE TRIGGER trg_like_update
#     AFTER UPDATE ON user_note_likes
#     FOR EACH ROW
# BEGIN
#     IF NEW.noteId <> OLD.noteId THEN
#         UPDATE note SET likeCount = likeCount - 1 WHERE id = OLD.noteId;
#         UPDATE note SET likeCount = likeCount + 1 WHERE id = NEW.noteId;
#     END IF;
# END;


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
    ('COMP4121', '4121 Advanced Algorithms',        0, '高级算法'),
    ('COMP4128', '4128 Programming Challenges',     0, '编程挑战'),
    ('COMP4141', '4141 Theory of Computation',      0, '计算理论'),
    ('COMP4161', '4161 Adv Topics Software Verification', 0, '软件验证高级专题'),
    ('COMP4418', '4418 KR & Reasoning',             0, '知识表示与推理'),
    ('COMP4511', '4511 UI Design & Construction',   0, '用户界面设计与构建'),
    ('COMP6080', '6080 Web Front-End Prog',         0, 'Web 前端编程'),
    ('COMP6131', '6131 Software Sec Analysis',      0, '软件安全分析'),
    ('COMP6441', '6441 Security Eng & Cyber Sec',   3, '安全工程与网络安全'),
    ('COMP6443', '6443 Web App Sec & Testing',       3, 'Web 应用安全与测试'),
    ('COMP6445', '6445 Digital Forensics',           0, '数字取证基础'),
    ('COMP6447', '6447 Sys & Soft Sec Assess',      0, '系统与软件安全评估'),
    ('COMP6448', '6448 Sec Eng Masterclass',         3, '安全工程大师课程'),
    ('COMP6451', '6451 Crypto & DLT',                0, '加密货币与分布式账本技术'),
    ('COMP6452', '6452 SW Arch for Blockchain',      0, '区块链应用软件架构'),
    ('COMP6453', '6453 Applied Cryptography',        3, '应用密码学'),
    ('COMP6713', '6713 NLP',                        1, '自然语言处理'),
    ('COMP6714', '6714 Info Retrieval & Search',    3, '信息检索与网络搜索'),
    ('COMP6721', '6721 (In-)Formal Methods',0, '形式化方法导论'),
    ('COMP6733', '6733 IoT Exp Design',     0, '物联网实验设计工作室'),
    ('COMP6741', '6741 Algo for Intract',   0, '不可解问题算法'),
    ('COMP6752', '6752 Modelling Concur Sys',3,'并发系统建模'),
    ('COMP6771', '6771 Advanced C++ Prog',  0, '高级 C++ 编程'),
    ('COMP6841', '6841 Extended Sec Eng & Cyber Sec', 3, '扩展安全工程与网络安全'),
    ('COMP6843', '6843 Extended Web App Sec',        3, '扩展 Web 应用安全与测试'),
    ('COMP6845', '6845 Extended Digital Forensics',  0, '扩展数字取证与事件响应'),
    ('COMP6991', '6991 Modern Prog w/ Rust',          0, 'Rust 现代编程实战'),
    ('COMP9020', '9020 Foundations CS',              0, '计算机科学基础课程'),
    ('COMP9021', '9021 Principles Prog',             0, '编程原理与实践'),
    ('COMP9024', '9024 Data Struct & Alg',           0, '数据结构与算法核心内容'),
    ('COMP9032', '9032 Microproc & IF',     4, '微处理器及接口技术'),
    ('COMP9044', '9044 Soft Constr Tools',  0, '软件构建技术与工具'),
    ('COMP9101', '9101 Algo Design & Ana',  0, '算法设计与分析'),
    ('COMP9102', '9102 Prog Lang & Comp',   0, '编程语言与编译器原理'),
    ('COMP9153', '9153 Algo Verification',  0, '算法验证方法'),
    ('COMP9154', '9154 Foundations Concur', 3, '并发性基础'),
    ('COMP9164', '9164 Concepts Prog Lang', 0, '编程语言概念'),
    ('COMP9201', '9201 Operating Systems',  0, '操作系统原理'),
    ('GSOE9210','9210 Eng Decision Struct',          5, '工程决策结构分析'),
    ('COMP9211', '9211 Computer Arch',      0, '计算机体系结构'),
    ('GSOE9220','9220 Launching Startup',            5, '创业启动与管理'),
    ('COMP9222', '9222 Digital Circuits & Sys',      4, '数字电路与系统'),
    ('COMP9242', '9242 Adv Operating Systems',       0, '高级操作系统'),
    ('COMP9243', '9243 Distributed Systems',         0, '分布式系统原理'),
    ('COMP9283', '9283 Extended OS',                 0, '扩展操作系统专题'),
    ('COMP9311', '9311 Database Sys',                0, '数据库系统原理课程'),
    ('COMP9312', '9312 Data Analytics for Graphs',   2, '图分析数据挖掘'),
    ('COMP9313', '9313 Big Data Management',         2, '大数据管理技术'),
    ('COMP9315', '9315 DB Sys Implementation',       0, '数据库系统实现'),
    ('COMP9319', '9319 Web Data Comp & Search',      3, '网页数据压缩与检索'),
    ('COMP9321', '9321 Data Services Eng',           2, '数据服务工程'),
    ('COMP9331', '9331 Comp Networks & Apps',        3, '计算机网络及其应用'),
    ('COMP9332', '9332 Network Routing & Switching', 3, '网络路由与交换'),
    ('COMP9333', '9333 Adv Comp Networks',           3, '高级计算机网络专题'),
    ('COMP9334', '9334 Capacity Plan Sys & Nets',    3, '计算机系统与网络容量规划'),
    ('COMP9336', '9336 Mobile Data Networking',      3, '移动数据网络技术'),
    ('COMP9337', '9337 Sec Fixed & Wireless Nets',   3, '有线与无线网络安全'),
    ('COMP9414', '9414 Artificial Intelligence',     1, '人工智能导论'),
    ('COMP9415', '9415 Comp Graphics',               0, '计算机图形学基础'),
    ('COMP9417', '9417 ML & Data Mining',            1, '机器学习与数据挖掘'),
    ('COMP9418', '9418 Adv Stat ML',                 1, '统计机器学习高级专题'),
    ('COMP9434', '9434 Robotic SW Arch',             4, '机器人软件架构'),
    ('COMP9444', '9444 Neural Nets & DL',            1, '神经网络与深度学习概论'),
    ('COMP9447', '9447 Sec Eng Workshop',            3, '安全工程实践研讨'),
    ('COMP9491', '9491 Applied AI',                  1, '应用人工智能技术'),
    ('COMP9511', '9511 HCI',                         0, '人机交互基础课程'),
    ('COMP9517', '9517 Comp Vision',                 1, '计算机视觉技术'),
    ('COMP9727', '9727 Recommender Sys',             1, '推荐系统原理与实现'),
    ('COMP9814', '9814 Extended AI',                 1, '拓展人工智能专题'),
    ('GSOE9820','9820 Eng Project Mgmt',             5, '工程项目管理方法论')
;


INSERT INTO user_centre.note (
    courseId, enrollTime, title, link, author, userId, lecturer, toolTip,isOfficial,isChecked)
VALUES ('17','24T1','6713官方笔记','https://sudden-comic-c00.notion.site/1f1f45253452809daeaad72fceb2146f?v=1f1f45253452806fb07b000ce212e8c5&pvs=4',
        'yang', @yangId,'joshi','这是yang的6713笔记', '1','1');

INSERT INTO user_centre.note (
    courseId, enrollTime,title, link, author, userId, lecturer, toolTip, isOfficial, isChecked)
VALUES ('17','25T1','6713非官方笔记','https://sudden-comic-c00.notion.site/1f1f45253452809daeaad72fceb2146f?v=1f1f45253452806fb07b000ce212e8c5&pvs=4',
        'yang', @yangId,'XXX','这是yang的6713笔记2号', '0', '1');

