SET @yangId = REPLACE(UUID(),'-','');

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


INSERT INTO `blog` (
    `userId`, `title`, `images`, `content`,
    `likeCount`, `commentCount`, `status`,
    `createTime`, `updateTime`, `isDelete`
) VALUES
-- 示例 1：单张图片
(@yangId,
 '第一次发帖！',
 'https://ss0.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=2972567671,623337291&fm=253&gp=0.jpg',
 '大家好，这是我在新平台的第一篇帖子，欢迎交流。',
 12, 3, 0,
 '2025-07-01 09:15:23', '2025-07-01 09:15:23', 0),

-- 示例 2：多张图片
(@yangId,
 '周末郊游',
 CONCAT_WS(' ',
           'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT0H4EGTZVYkQYmwRim2AzOSTrrNLBHq4JsVQ&s',
           'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSw-_aKvSjl1qQBkFLNDteekjcIPWC0UgjnHA&s'
 ),
 '这周末去了郊外踏青，风景很美，分享几张照片。',
 45, 10, 0,
 '2025-06-28 18:42:10', '2025-06-28 18:50:05', 0),

-- 示例 3：无图片
(@yangId,
 '读书笔记：<算法导论>',
 NULL,
 '最近在读《算法导论》，做了些笔记，整理如下：…',
 30, 5, 0,
 '2025-06-30 21:07:45', '2025-06-30 21:07:45', 0),

-- 示例 4：9 张图片上限测试
(@yangId,
 '美食分享',
 CONCAT_WS(' ',
           'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSlw3ae9xZJfONtGrG3oZ_n3gW0Ye3TO8Ecg&s',
           'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS_OSf_8Koet-8U45jV_KUU7fmT_fuUA-G_Vg&s',
           'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSlnEAdxkP5kLegY3xl2naYNbzcZQ91R1HiOg&s',
           'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQh2enDQfvL_wJUEbjMPwSDouUHyfFxGlwaUw&s',
           'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTzBB899PODFTkry-A9768KylDDofNVk64G9w&s',
           'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTTsOloJ4xI4XGRQ0yQmwNYXcDmhwj-U8EWfQ&s',
           'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQarAtotkbKIdSCPYPkTY1tsXprHVd67pTz2g&s',
           'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS-AHJmFiZJopCgHt_g5zOIrjBQub1hiwh0QQ&s',
           'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQcllweQxEbELhkJseGM9NHSrI4H7yDFYlUug&s'
 ),
 '今天在家做了九种小吃，味道不错，大家可以参考我的做法。',
 88, 22, 0,
 '2025-06-25 12:30:00', '2025-06-25 12:45:10', 0),

-- 示例 5：已删除和被举报示例
(@yangId,
 '测试帖（已删除）',
 NULL,
 '这是一条测试帖，目前已被软删除。',
 0, 0, 2,
 '2025-06-20 08:00:00', '2025-06-20 08:00:00', 1);


-- 1) 先插入所有 parentId = 0 的“一级评论”
INSERT INTO `blog_comments`
(userId, blogId, parentId, content, status, createTime, updateTime)
VALUES
    (@yangId, 1, null, '这篇文章写得很详细，受益匪浅！', 0, '2025-06-20 10:15:00', '2025-06-20 10:15:00'),
    (@yangId, 1, null, '请问作者能否分享一下数据来源？', 0, '2025-06-20 11:05:23', '2025-06-20 11:05:23'),
    (@yangId, 1, null, '有几点疑问：第 3 部分的示例代码运行会出错。', 0, '2025-06-20 12:30:45', '2025-06-20 12:30:45'),
    (@yangId, 2, null, '第二篇文章也很精彩，期待更多！', 0, '2025-06-21 09:10:05', '2025-06-21 09:10:05'),
    (@yangId, 2, null, '内容有误，第 2 节标题写反了。',    1, '2025-06-21 10:00:00', '2025-06-21 10:00:00');

-- 2) 然后再插入那些 parentId ≠ 0 的“二级回复”
INSERT INTO `blog_comments`
(userId, blogId, parentId, content, status, createTime, updateTime)
VALUES
    (@yangId, 1, 1, '@a1b2c3… 我也是遇到同样的问题，可以看一下官方文档提供的示例。', 0, '2025-06-20 13:00:00', '2025-06-20 13:00:00'),
    (@yangId, 1, 1, '作者回复：感谢支持，数据来源已在文末补充。',                   0, '2025-06-20 14:22:10', '2025-06-20 14:22:10'),
    (@yangId, 1, 3, '遇到什么错误？可以贴一下日志方便定位。',                    0, '2025-06-20 15:45:30', '2025-06-20 15:45:30');



INSERT INTO user_centre.note (
    courseId, enrollTime, title, link, author, userId, lecturer, toolTip,isOfficial,isChecked)
VALUES ('17','24T1','6713官方笔记','https://sudden-comic-c00.notion.site/1f1f45253452809daeaad72fceb2146f?v=1f1f45253452806fb07b000ce212e8c5&pvs=4',
        'yang', @yangId,'joshi','这是yang的6713笔记', '1','1');

INSERT INTO user_centre.note (
    courseId, enrollTime,title, link, author, userId, lecturer, toolTip, isOfficial, isChecked)
VALUES ('17','25T1','6713非官方笔记','https://sudden-comic-c00.notion.site/1f1f45253452809daeaad72fceb2146f?v=1f1f45253452806fb07b000ce212e8c5&pvs=4',
        'yang', @yangId,'XXX','这是yang的6713笔记2号', '0', '1');


