SET @yangId = REPLACE(UUID(),'-','');

# 导入示例用户1
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


INSERT INTO `post` (
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

-- 先设置用户ID变量
SET @zhiyaoId = REPLACE(UUID(),'-','');

-- 确保用户存在（如果用户表已有数据，可以跳过这部分）
INSERT INTO user_centre.user (
    username, id, userAccount, gender, userPassword, avatarUrl,
    admissionTime, email, signature, userStatus,
    createTime, updateTime, userRole
) VALUES (
             'zhiyao', @zhiyaoId, 'zhiyao', null,'1bbd886460827015e5d605ed44252251', '/img/a.png',
             '25T1', null, '我是一个热爱学习编程的学生。', 0,
             '2023-08-06 14:14:22', '2023-08-06 14:39:37', 1
         ) ON DUPLICATE KEY UPDATE id = @zhiyaoId;


-- 博客帖子数据插入SQL (评论数修改为0)
INSERT INTO post (userId, title, images, content, likeCount, commentCount, status, createTime, updateTime, isDelete) VALUES
     (@zhiyaoId, '第一次发帖！', 'https://picsum.photos/400/300?random=1', '大家好，这是我在新平台的第一篇帖子，欢迎交流。', 12, 0, 0, '2025-07-01 09:15:23', '2025-07-01 09:15:23', 0),
     (@zhiyaoId, 'SpringBoot学习心得', 'https://picsum.photos/400/300?random=2', '今天学习了SpringBoot的自动配置原理，感觉收获很多，特别是对@EnableAutoConfiguration注解的理解。', 25, 0, 2, '2025-07-01 10:30:45', '2025-07-01 10:30:45', 0),
     (@zhiyaoId, 'MySQL索引优化技巧', 'https://picsum.photos/400/300?random=3', '分享一些MySQL索引优化的实战经验，包括复合索引的使用和查询性能分析方法。', 31, 0, 5, '2025-07-01 14:22:16', '2025-07-01 14:22:16', 0),
     (@zhiyaoId, 'Redis缓存策略探讨', 'https://picsum.photos/400/300?random=4', '在项目中使用Redis作为缓存层，总结了一些缓存穿透、雪崩和击穿的解决方案。', 18, 0, 1, '2025-07-02 08:45:30', '2025-07-02 08:45:30', 0),
     (@zhiyaoId, 'Vue3组件设计思路', 'https://picsum.photos/400/300?random=5', '最近在用Vue3重构前端项目，分享一些组件设计和状态管理的心得体会。', 42, 0, 8, '2025-07-02 11:18:52', '2025-07-02 11:18:52', 0),
     (@zhiyaoId, 'Docker容器化部署实践', 'https://picsum.photos/400/300?random=6', '记录了从开发到生产环境的Docker容器化部署全流程，包括镜像优化和编排配置。', 36, 0, 4, '2025-07-02 16:33:41', '2025-07-02 16:33:41', 0),
     (@zhiyaoId, 'Git版本管理最佳实践', 'https://picsum.photos/400/300?random=7', '整理了团队开发中Git的分支管理策略和代码提交规范，提高协作效率。', 28, 0, 3, '2025-07-03 09:12:18', '2025-07-03 09:12:18', 0),
     (@zhiyaoId, 'ElasticSearch搜索优化', 'https://picsum.photos/400/300?random=8', '在学习平台中集成ES做全文搜索，分享了分词器选择和查询性能优化经验。', 33, 0, 2, '2025-07-03 13:25:37', '2025-07-03 13:25:37', 0),
     (@zhiyaoId, 'JVM性能调优笔记', 'https://picsum.photos/400/300?random=9', '记录了一次生产环境JVM调优的过程，包括堆内存分析和GC参数优化。', 45, 0, 6, '2025-07-03 15:48:29', '2025-07-03 15:48:29', 0),
     (@zhiyaoId, '微服务架构设计心得', 'https://picsum.photos/400/300?random=10', '从单体应用向微服务架构演进的思考，包括服务拆分原则和通信方式选择。', 52, 0, 9, '2025-07-04 08:36:14', '2025-07-04 08:36:14', 0),
     (@zhiyaoId, 'Nginx负载均衡配置', 'https://picsum.photos/400/300?random=11', '分享Nginx作为反向代理和负载均衡器的配置方法，包括健康检查和故障转移。', 19, 0, 1, '2025-07-04 10:55:46', '2025-07-04 10:55:46', 0),
     (@zhiyaoId, 'RabbitMQ消息队列应用', 'https://picsum.photos/400/300?random=12', '在异步处理场景中使用RabbitMQ，总结了消息可靠性保证和死信队列处理。', 27, 0, 3, '2025-07-04 14:12:33', '2025-07-04 14:12:33', 0),
     (@zhiyaoId, 'MyBatis-Plus实用技巧', 'https://picsum.photos/400/300?random=13', '整理了MyBatis-Plus在项目中的高级用法，包括自定义SQL和性能优化。', 22, 0, 2, '2025-07-05 09:28:17', '2025-07-05 09:28:17', 0),
     (@zhiyaoId, '前端性能优化实战', 'https://picsum.photos/400/300?random=14', '从首屏加载时间优化入手，分享了代码分割、懒加载等性能优化技术。', 38, 0, 5, '2025-07-05 11:41:25', '2025-07-05 11:41:25', 0),
     (@zhiyaoId, 'Linux服务器运维经验', 'https://picsum.photos/400/300?random=15', '记录了Linux服务器的日常运维操作，包括监控脚本和自动化部署。', 29, 0, 4, '2025-07-05 16:19:58', '2025-07-05 16:19:58', 0),
     (@zhiyaoId, 'JWT身份认证实现', 'https://picsum.photos/400/300?random=16', '详细介绍了JWT在前后端分离项目中的身份认证实现方案和安全考虑。', 41, 0, 7, '2025-07-06 08:53:42', '2025-07-06 08:53:42', 0),
     (@zhiyaoId, 'React Hooks深度解析', 'https://picsum.photos/400/300?random=17', '深入理解React Hooks的原理和使用场景，包括自定义Hooks的设计模式。', 35, 0, 6, '2025-07-06 12:07:31', '2025-07-06 12:07:31', 0),
     (@zhiyaoId, 'MongoDB文档数据库应用', 'https://picsum.photos/400/300?random=18', '在内容管理系统中使用MongoDB存储文档数据，分享了查询优化和索引设计。', 24, 0, 2, '2025-07-06 15:34:19', '2025-07-06 15:34:19', 0),
     (@zhiyaoId, 'Kubernetes集群部署指南', 'https://picsum.photos/400/300?random=19', '从零开始搭建K8s集群，包括网络配置、存储管理和应用部署实践。', 48, 0, 8, '2025-07-07 09:16:45', '2025-07-07 09:16:45', 0),
     (@zhiyaoId, 'API接口设计规范', 'https://picsum.photos/400/300?random=20', '制定RESTful API的设计规范，包括状态码使用、参数校验和文档生成。', 26, 0, 3, '2025-07-07 13:42:27', '2025-07-07 13:42:27', 0),
     (@zhiyaoId, 'Webpack打包优化策略', 'https://picsum.photos/400/300?random=21', '针对大型前端项目的Webpack配置优化，减少打包体积和构建时间。', 32, 0, 4, '2025-07-07 17:25:13', '2025-07-07 17:25:13', 0),
     (@zhiyaoId, 'TCP/IP网络编程基础', 'https://picsum.photos/400/300?random=22', '回顾网络编程基础知识，包括Socket通信和HTTP协议原理解析。', 21, 0, 1, '2025-07-08 08:38:56', '2025-07-08 08:38:56', 0),
     (@zhiyaoId, 'Python爬虫技术分享', 'https://picsum.photos/400/300?random=23', '使用Scrapy框架开发爬虫程序，包括反爬虫处理和数据存储策略。', 39, 0, 6, '2025-07-08 11:52:34', '2025-07-08 11:52:34', 0),
     (@zhiyaoId, '算法与数据结构复习', 'https://picsum.photos/400/300?random=24', '整理了常见算法题的解题思路，包括动态规划和贪心算法的应用。', 44, 0, 5, '2025-07-08 14:16:48', '2025-07-08 14:16:48', 0),
     (@zhiyaoId, 'CSS Grid布局实践', 'https://picsum.photos/400/300?random=25', '使用CSS Grid实现复杂页面布局，比Flexbox更适合二维布局场景。', 30, 0, 3, '2025-07-08 16:43:22', '2025-07-08 16:43:22', 0),
     (@zhiyaoId, 'GraphQL API开发经验', 'https://picsum.photos/400/300?random=26', '尝试用GraphQL替代REST API，分享了Schema设计和查询优化心得。', 37, 0, 4, '2025-07-09 09:21:37', '2025-07-09 09:21:37', 0),
     (@zhiyaoId, '单元测试最佳实践', 'https://picsum.photos/400/300?random=27', '使用JUnit5编写高质量的单元测试，包括Mock对象和测试覆盖率分析。', 25, 0, 2, '2025-07-09 12:58:14', '2025-07-09 12:58:14', 0),
     (@zhiyaoId, 'TypeScript类型系统详解', 'https://picsum.photos/400/300?random=28', '深入学习TypeScript的类型系统，包括泛型、联合类型和类型守卫。', 33, 0, 5, '2025-07-09 15:35:49', '2025-07-09 15:35:49', 0),
     (@zhiyaoId, 'OAuth2.0授权机制', 'https://picsum.photos/400/300?random=29', '实现第三方登录功能，详细分析OAuth2.0的授权码模式和安全性。', 28, 0, 3, '2025-07-10 08:47:26', '2025-07-10 08:47:26', 0),
     (@zhiyaoId, 'Zookeeper分布式协调', 'https://picsum.photos/400/300?random=30', '在分布式系统中使用Zookeeper实现配置管理和服务发现功能。', 31, 0, 4, '2025-07-10 11:24:53', '2025-07-10 11:24:53', 0),
     (@zhiyaoId, '数据库事务隔离级别', 'https://picsum.photos/400/300?random=31', '深入理解MySQL事务的ACID特性和四种隔离级别的应用场景。', 26, 0, 2, '2025-07-10 14:13:18', '2025-07-10 14:13:18', 0),
     (@zhiyaoId, 'Sass/Less预处理器', 'https://picsum.photos/400/300?random=32', '使用CSS预处理器提高样式开发效率，包括变量、混合和嵌套语法。', 23, 0, 1, '2025-07-10 16:58:41', '2025-07-10 16:58:41', 0),
     (@zhiyaoId, 'Netty网络编程框架', 'https://picsum.photos/400/300?random=33', '使用Netty开发高性能网络应用，包括编解码器和业务处理器设计。', 40, 0, 6, '2025-07-11 09:32:15', '2025-07-11 09:32:15', 0),
     (@zhiyaoId, '设计模式在项目中的应用', 'https://picsum.photos/400/300?random=34', '总结了常用设计模式的实际应用场景，包括单例、工厂和观察者模式。', 34, 0, 5, '2025-07-11 12:46:38', '2025-07-11 12:46:38', 0),
     (@zhiyaoId, 'Jenkins CI/CD流水线', 'https://picsum.photos/400/300?random=35', '搭建自动化构建和部署流水线，实现代码提交到生产环境的全自动化。', 42, 0, 7, '2025-07-11 15:21:52', '2025-07-11 15:21:52', 0),
     (@zhiyaoId, 'Apache Kafka消息系统', 'https://picsum.photos/400/300?random=36', '在大数据场景下使用Kafka处理实时消息流，包括分区策略和消费者组配置。', 36, 0, 4, '2025-07-12 08:55:29', '2025-07-12 08:55:29', 0),
     (@zhiyaoId, 'PWA渐进式Web应用', 'https://picsum.photos/400/300?random=37', '将传统Web应用改造为PWA，实现离线访问和推送通知功能。', 29, 0, 3, '2025-07-12 11:37:44', '2025-07-12 11:37:44', 0),
     (@zhiyaoId, 'Hibernate ORM框架详解', 'https://picsum.photos/400/300?random=38', '深入学习Hibernate的对象关系映射，包括缓存机制和懒加载优化。', 27, 0, 2, '2025-07-12 14:49:17', '2025-07-12 14:49:17', 0),
     (@zhiyaoId, 'WebSocket实时通信', 'https://picsum.photos/400/300?random=39', '实现浏览器和服务器的双向实时通信，包括心跳检测和断线重连。', 35, 0, 5, '2025-07-12 17:14:36', '2025-07-12 17:14:36', 0),
     (@zhiyaoId, '数据结构与算法优化', 'https://picsum.photos/400/300?random=40', '分析了常见数据结构的时间复杂度，并针对具体业务场景进行算法优化。', 41, 0, 6, '2025-07-13 09:08:23', '2025-07-13 09:08:23', 0),
     (@zhiyaoId, 'Dubbo微服务治理', 'https://picsum.photos/400/300?random=41', '使用Dubbo框架实现服务治理，包括负载均衡、容错机制和监控管理。', 33, 0, 4, '2025-07-13 12:26:45', '2025-07-13 12:26:45', 0),
     (@zhiyaoId, 'Quartz定时任务调度', 'https://picsum.photos/400/300?random=42', '集成Quartz实现复杂的定时任务调度，包括Cron表达式和集群部署。', 24, 0, 2, '2025-07-13 15:53:18', '2025-07-13 15:53:18', 0),
     (@zhiyaoId, 'Node.js后端开发实践', 'https://picsum.photos/400/300?random=43', '使用Node.js和Express框架开发RESTful API，包括中间件和错误处理。', 38, 0, 5, '2025-07-13 18:17:42', '2025-07-13 18:17:42', 0),
     (@zhiyaoId, 'Flutter跨平台开发', 'https://picsum.photos/400/300?random=44', '学习Flutter开发移动应用，包括Widget组件和状态管理解决方案。', 30, 0, 3, '2025-07-14 08:42:56', '2025-07-14 08:42:56', 0),
     (@zhiyaoId, 'Prometheus监控系统', 'https://picsum.photos/400/300?random=45', '搭建Prometheus + Grafana监控体系，实现应用性能和业务指标监控。', 32, 0, 4, '2025-07-14 11:29:33', '2025-07-14 11:29:33', 0),
     (@zhiyaoId, 'Maven项目管理工具', 'https://picsum.photos/400/300?random=46', '深入理解Maven的生命周期和依赖管理，包括多模块项目的构建配置。', 22, 0, 2, '2025-07-14 14:56:19', '2025-07-14 14:56:19', 0),
     (@zhiyaoId, 'Apache Shiro安全框架', 'https://picsum.photos/400/300?random=47', '集成Shiro实现用户认证和权限控制，包括会话管理和密码加密。', 26, 0, 3, '2025-07-14 17:33:47', '2025-07-14 17:33:47', 0),
     (@zhiyaoId, 'Elasticsearch聚合查询', 'https://picsum.photos/400/300?random=48', '使用ES的聚合功能实现复杂的数据统计和分析，包括桶聚合和指标聚合。', 39, 0, 5, '2025-07-15 09:18:24', '2025-07-15 09:18:24', 0),
     (@zhiyaoId, 'Vue Router路由管理', 'https://picsum.photos/400/300?random=49', '深入学习Vue Router的路由配置，包括嵌套路由、路由守卫和懒加载。', 28, 0, 3, '2025-07-15 12:45:51', '2025-07-15 12:45:51', 0),
     (@zhiyaoId, 'Apache Tomcat服务器优化', 'https://picsum.photos/400/300?random=50', '针对生产环境的Tomcat性能调优，包括连接池配置和JVM参数优化。', 31, 0, 4, '2025-07-15 15:22:38', '2025-07-15 15:22:38', 0),
     (@zhiyaoId, '机器学习算法入门', 'https://picsum.photos/400/300?random=51', '学习了线性回归和决策树等基础算法，使用Python实现了简单的预测模型。', 45, 0, 7, '2025-07-15 18:07:12', '2025-07-15 18:07:12', 0),
     (@zhiyaoId, 'Lombok代码简化工具', 'https://picsum.photos/400/300?random=52', '使用Lombok注解减少样板代码，包括@Data、@Builder等常用注解的应用。', 20, 0, 1, '2025-07-16 08:34:45', '2025-07-16 08:34:45', 0),
     (@zhiyaoId, 'HTTP/2协议特性分析', 'https://picsum.photos/400/300?random=53', '深入了解HTTP/2的多路复用、服务器推送等新特性，以及对Web性能的提升。', 33, 0, 4, '2025-07-16 11:52:27', '2025-07-16 11:52:27', 0),
     (@zhiyaoId, 'Spring Security安全配置', 'https://picsum.photos/400/300?random=54', '详细配置Spring Security的认证和授权机制，包括自定义登录页面和权限控制。', 37, 0, 6, '2025-07-16 14:18:59', '2025-07-16 14:18:59', 0),
     (@zhiyaoId, 'Logback日志框架配置', 'https://picsum.photos/400/300?random=55', '配置Logback实现灵活的日志管理，包括日志分级、滚动策略和异步输出。', 25, 0, 2, '2025-07-16 16:41:33', '2025-07-16 16:41:33', 0),
     (@zhiyaoId, 'RxJava响应式编程', 'https://picsum.photos/400/300?random=56', '学习RxJava的响应式编程思想，包括Observable、操作符和线程调度。', 34, 0, 5, '2025-07-17 09:15:46', '2025-07-17 09:15:46', 0),
     (@zhiyaoId, 'Thymeleaf模板引擎', 'https://picsum.photos/400/300?random=57', '使用Thymeleaf作为Spring Boot的视图层技术，包括表达式语法和布局模板。', 23, 0, 2, '2025-07-17 12:38:21', '2025-07-17 12:38',0);
-- 博客数据插入SQL (100条记录)
INSERT INTO post (userId, title, images, content, likeCount, commentCount, status, createTime, updateTime, isDelete)
VALUES
    (@zhiyaoId, '第一次发帖！', 'https://picsum.photos/400/300?random=1', '大家好，这是我在新平台的第一篇帖子，欢迎交流。', 12, 3, 0, '2025-07-01 09:15:23', '2025-07-01 09:15:23', 0);

-- 1) 先插入所有 parentId = null 的“一级评论”, parentId 默认为 null
INSERT INTO `post_comments`
(userId, postId, content, status, createTime, updateTime)
VALUES
    (@yangId, 1, '这篇文章写得很详细，受益匪浅！', 0, '2025-06-20 10:15:00', '2025-06-20 10:15:00'),
    (@yangId, 1, '请问作者能否分享一下数据来源？', 0, '2025-06-20 11:05:23', '2025-06-20 11:05:23'),
    (@yangId, 1, '有几点疑问：第 3 部分的示例代码运行会出错。', 0, '2025-06-20 12:30:45', '2025-06-20 12:30:45'),
    (@yangId, 2, '第二篇文章也很精彩，期待更多！', 0, '2025-06-21 09:10:05', '2025-06-21 09:10:05'),
    (@yangId, 2,  '内容有误，第 2 节标题写反了。',    1, '2025-06-21 10:00:00', '2025-06-21 10:00:00');

-- 2) 然后再插入那些 parentId ≠ null 的“二级回复”
INSERT INTO `post_comments`
(userId, postId, parentId, content, status, createTime, updateTime)
VALUES
    (@yangId, 1, 1, '@a1b2c3… 我也是遇到同样的问题，可以看一下官方文档提供的示例。', 0, '2025-06-20 13:00:00', '2025-06-20 13:00:00'),
    (@yangId, 1, 1, '作者回复：感谢支持，数据来源已在文末补充。',                   0, '2025-06-20 14:22:10', '2025-06-20 14:22:10'),
    (@yangId, 1, 3, '遇到什么错误？可以贴一下日志方便定位。',                    0, '2025-06-20 15:45:30', '2025-06-20 15:45:30');


-- 博客评论数据插入SQL
-- 注意：post的id是从6开始自增的

-- 1) 先插入所有 parentId = null 的"一级评论"
INSERT INTO `post_comments`
(userId, postId, content, status, createTime, updateTime)
VALUES
    -- 第1篇帖子的评论
    (@zhiyaoId, 6, '第一次在这里发帖，感谢大家的关注！', 0, '2025-07-01 09:30:15', '2025-07-01 09:30:15'),
    (@zhiyaoId, 6, '新平台界面很简洁，使用体验不错。', 0, '2025-07-01 10:45:22', '2025-07-01 10:45:22'),
    (@zhiyaoId, 6, '期待能在这里学到更多技术知识。', 0, '2025-07-01 11:20:38', '2025-07-01 11:20:38'),

    -- 第2篇帖子的评论 (SpringBoot学习心得)
    (@zhiyaoId, 7, '自动配置确实是SpringBoot的核心特性之一。', 0, '2025-07-01 11:15:45', '2025-07-01 11:15:45'),
    (@zhiyaoId, 7, '能详细讲讲@Conditional注解的使用吗？', 0, '2025-07-01 12:30:18', '2025-07-01 12:30:18'),
    (@zhiyaoId, 7, '建议补充一些实际项目中的配置示例。', 0, '2025-07-01 13:45:33', '2025-07-01 13:45:33'),
    (@zhiyaoId, 7, 'starter机制真的很方便，减少了很多配置工作。', 0, '2025-07-01 14:22:55', '2025-07-01 14:22:55'),
    (@zhiyaoId, 7, '有没有性能优化方面的建议？', 0, '2025-07-01 15:18:42', '2025-07-01 15:18:42'),

    -- 第3篇帖子的评论 (MySQL索引优化技巧)
    (@zhiyaoId, 8, '索引优化确实是数据库性能调优的重点。', 0, '2025-07-01 15:10:25', '2025-07-01 15:10:25'),
    (@zhiyaoId, 8, '复合索引的最左前缀原则很重要！', 0, '2025-07-01 16:25:14', '2025-07-01 16:25:14'),
    (@zhiyaoId, 8, 'EXPLAIN命令在查询分析中非常有用。', 0, '2025-07-01 17:40:33', '2025-07-01 17:40:33'),
    (@zhiyaoId, 8, '能分享一下慢查询日志的分析方法吗？', 0, '2025-07-01 18:15:48', '2025-07-01 18:15:48'),
    (@zhiyaoId, 8, '索引失效的几种情况总结得很全面。', 0, '2025-07-01 19:30:22', '2025-07-01 19:30:22'),

    -- 第4篇帖子的评论 (Redis缓存策略探讨)
    (@zhiyaoId, 9, '缓存雪崩确实是生产环境的常见问题。', 0, '2025-07-02 09:20:16', '2025-07-02 09:20:16'),
    (@zhiyaoId, 9, '布隆过滤器在缓存穿透防护中效果很好。', 0, '2025-07-02 10:35:42', '2025-07-02 10:35:42'),
    (@zhiyaoId, 9, '热点数据的缓存策略还需要考虑数据一致性。', 0, '2025-07-02 11:50:29', '2025-07-02 11:50:29'),
    (@zhiyaoId, 9, 'Redis集群模式下的缓存设计有什么注意点？', 0, '2025-07-02 12:45:15', '2025-07-02 12:45:15'),
    (@zhiyaoId, 9, '分布式锁也是Redis的重要应用场景。', 0, '2025-07-02 13:22:38', '2025-07-02 13:22:38'),
    (@zhiyaoId, 9, '缓存更新策略的选择很关键。', 0, '2025-07-02 14:18:52', '2025-07-02 14:18:52'),

    -- 第5篇帖子的评论 (Vue3组件设计思路)
    (@zhiyaoId, 10, 'Composition API相比Options API确实更灵活。', 0, '2025-07-02 12:25:33', '2025-07-02 12:25:33'),
    (@zhiyaoId, 10, '组件的复用性设计是前端开发的核心。', 0, '2025-07-02 13:40:17', '2025-07-02 13:40:17'),
    (@zhiyaoId, 10, 'Pinia状态管理比Vuex使用起来更简单。', 0, '2025-07-02 14:55:44', '2025-07-02 14:55:44'),
    (@zhiyaoId, 10, 'TypeScript的类型检查在大型项目中很有必要。', 0, '2025-07-02 15:32:28', '2025-07-02 15:32:28'),
    (@zhiyaoId, 10, '响应式原理的理解对组件设计很重要。', 0, '2025-07-02 16:18:56', '2025-07-02 16:18:56'),
    (@zhiyaoId, 10, '能分享一下组件通信的最佳实践吗？', 0, '2025-07-02 17:45:12', '2025-07-02 17:45:12'),
    (@zhiyaoId, 10, 'Vue3的性能提升确实很明显。', 0, '2025-07-02 18:20:35', '2025-07-02 18:20:35'),
    (@zhiyaoId, 10, '单文件组件的开发体验很好。', 0, '2025-07-02 19:15:48', '2025-07-02 19:15:48'),

    -- 第6篇帖子的评论 (Docker容器化部署实践)
    (@zhiyaoId, 11, 'Docker确实是现代应用部署的标准。', 0, '2025-07-02 17:45:28', '2025-07-02 17:45:28'),
    (@zhiyaoId, 11, '镜像分层设计对构建速度影响很大。', 0, '2025-07-02 18:30:15', '2025-07-02 18:30:15'),
    (@zhiyaoId, 11, '多阶段构建可以有效减小镜像体积。', 0, '2025-07-02 19:15:42', '2025-07-02 19:15:42'),
    (@zhiyaoId, 11, 'Docker Compose在开发环境很实用。', 0, '2025-07-02 20:22:33', '2025-07-02 20:22:33'),

    -- 第7篇帖子的评论 (Git版本管理最佳实践)
    (@zhiyaoId, 12, 'Git Flow工作流在团队开发中很有效。', 0, '2025-07-03 10:25:18', '2025-07-03 10:25:18'),
    (@zhiyaoId, 12, '提交信息的规范化确实很重要。', 0, '2025-07-03 11:40:32', '2025-07-03 11:40:32'),
    (@zhiyaoId, 12, '分支合并时的冲突解决需要小心处理。', 0, '2025-07-03 12:55:45', '2025-07-03 12:55:45'),

    -- 第8篇帖子的评论 (ElasticSearch搜索优化)
    (@zhiyaoId, 13, '分词器的选择对搜索效果影响很大。', 0, '2025-07-03 14:35:22', '2025-07-03 14:35:22'),
    (@zhiyaoId, 13, 'IK分词器在中文搜索中表现很好。', 0, '2025-07-03 15:20:18', '2025-07-03 15:20:18'),
    (@zhiyaoId, 13, '搜索结果的相关性评分如何优化？', 0, '2025-07-03 16:45:33', '2025-07-03 16:45:33'),
    (@zhiyaoId, 13, 'Kibana的可视化功能很强大。', 0, '2025-07-03 17:30:47', '2025-07-03 17:30:47'),

    -- 第9篇帖子的评论 (JVM性能调优笔记)
    (@zhiyaoId, 14, 'GC日志分析是性能调优的基础。', 0, '2025-07-03 16:55:29', '2025-07-03 16:55:29'),
    (@zhiyaoId, 14, '堆内存分配策略对性能影响很大。', 0, '2025-07-03 17:40:15', '2025-07-03 17:40:15'),
    (@zhiyaoId, 14, 'G1垃圾回收器在大堆内存下表现更好。', 0, '2025-07-03 18:25:42', '2025-07-03 18:25:42'),
    (@zhiyaoId, 14, '内存泄漏的排查方法很实用。', 0, '2025-07-03 19:10:33', '2025-07-03 19:10:33'),
    (@zhiyaoId, 14, 'JVM参数调优需要结合具体业务场景。', 0, '2025-07-03 20:15:28', '2025-07-03 20:15:28'),
    (@zhiyaoId, 14, 'MAT工具在内存分析中很有用。', 0, '2025-07-03 21:30:45', '2025-07-03 21:30:45'),

    -- 第10篇帖子的评论 (微服务架构设计心得)
    (@zhiyaoId, 15, '微服务的拆分粒度需要仔细考虑。', 0, '2025-07-04 09:45:22', '2025-07-04 09:45:22'),
    (@zhiyaoId, 15, '服务间通信的方式选择很关键。', 0, '2025-07-04 10:30:18', '2025-07-04 10:30:18'),
    (@zhiyaoId, 15, '分布式事务是微服务的难点之一。', 0, '2025-07-04 11:15:35', '2025-07-04 11:15:35'),
    (@zhiyaoId, 15, 'API网关在微服务架构中很重要。', 0, '2025-07-04 12:40:47', '2025-07-04 12:40:47'),
    (@zhiyaoId, 15, '服务治理和监控不能忽视。', 0, '2025-07-04 13:25:29', '2025-07-04 13:25:29'),
    (@zhiyaoId, 15, '容器化部署让微服务管理更简单。', 0, '2025-07-04 14:50:16', '2025-07-04 14:50:16'),
    (@zhiyaoId, 15, '数据一致性的保证是技术难点。', 0, '2025-07-04 15:35:42', '2025-07-04 15:35:42'),
    (@zhiyaoId, 15, '服务发现机制的选择也很重要。', 0, '2025-07-04 16:20:33', '2025-07-04 16:20:33'),
    (@zhiyaoId, 15, '配置中心能提高运维效率。', 0, '2025-07-04 17:45:28', '2025-07-04 17:45:28');

-- 2) 然后插入二级回复评论
INSERT INTO `post_comments`
(userId, postId, parentId, content, status, createTime, updateTime)
VALUES
    -- 对第1篇帖子评论的回复
    (@zhiyaoId, 6, 1, '谢谢鼓励！会继续分享更多技术内容的。', 0, '2025-07-01 10:00:25', '2025-07-01 10:00:25'),
    (@zhiyaoId, 6, 2, '确实，简洁的界面能提高学习效率。', 0, '2025-07-01 11:15:33', '2025-07-01 11:15:33'),
    (@zhiyaoId, 6, 3, '一起加油！技术学习需要持续积累。', 0, '2025-07-01 12:30:18', '2025-07-01 12:30:18'),

    -- 对第2篇帖子评论的回复
    (@zhiyaoId, 7, 4, '是的，理解自动配置原理对使用SpringBoot很有帮助。', 0, '2025-07-01 12:45:22', '2025-07-01 12:45:22'),
    (@zhiyaoId, 7, 5, '@Conditional系列注解确实值得深入研究，后续会专门写一篇。', 0, '2025-07-01 14:20:35', '2025-07-01 14:20:35'),
    (@zhiyaoId, 7, 6, '好建议！实际项目配置示例更有参考价值。', 0, '2025-07-01 15:35:48', '2025-07-01 15:35:48'),
    (@zhiyaoId, 7, 7, 'starter的设计思想值得学习和借鉴。', 0, '2025-07-01 16:22:15', '2025-07-01 16:22:15'),
    (@zhiyaoId, 7, 8, '性能优化是个大话题，可以从JVM调优开始。', 0, '2025-07-01 17:45:33', '2025-07-01 17:45:33'),

    -- 对第3篇帖子评论的回复
    (@zhiyaoId, 8, 9, '索引设计确实是数据库优化的核心技能。', 0, '2025-07-01 16:25:42', '2025-07-01 16:25:42'),
    (@zhiyaoId, 8, 10, '最左前缀原则在复合索引设计中经常用到。', 0, '2025-07-01 17:40:28', '2025-07-01 17:40:28'),
    (@zhiyaoId, 8, 11, 'EXPLAIN结合慢查询日志分析效果更好。', 0, '2025-07-01 18:55:16', '2025-07-01 18:55:16'),
    (@zhiyaoId, 8, 12, '慢查询日志分析可以用pt-query-digest工具。', 0, '2025-07-01 19:30:35', '2025-07-01 19:30:35'),
    (@zhiyaoId, 8, 13, '避免索引失效是查询优化的重点。', 0, '2025-07-01 20:15:48', '2025-07-01 20:15:48'),

    -- 对第4篇帖子评论的回复
    (@zhiyaoId, 9, 14, '缓存雪崩的预防比事后处理更重要。', 0, '2025-07-02 10:35:25', '2025-07-02 10:35:25'),
    (@zhiyaoId, 9, 15, '布隆过滤器确实是解决缓存穿透的好方案。', 0, '2025-07-02 11:50:18', '2025-07-02 11:50:18'),
    (@zhiyaoId, 9, 16, '最终一致性在分布式缓存中是常见选择。', 0, '2025-07-02 13:05:42', '2025-07-02 13:05:42'),
    (@zhiyaoId, 9, 17, 'Redis Cluster的slot分片需要注意数据分布。', 0, '2025-07-02 14:20:33', '2025-07-02 14:20:33'),
    (@zhiyaoId, 9, 18, '分布式锁的实现有多种方案，各有优缺点。', 0, '2025-07-02 15:35:47', '2025-07-02 15:35:47'),
    (@zhiyaoId, 9, 19, 'Cache-Aside模式在实际项目中用得比较多。', 0, '2025-07-02 16:45:29', '2025-07-02 16:45:29'),

    -- 对第5篇帖子评论的回复
    (@zhiyaoId, 10, 20, 'Composition API的逻辑复用能力确实更强。', 0, '2025-07-02 14:40:16', '2025-07-02 14:40:16'),
    (@zhiyaoId, 10, 21, '组件设计要考虑通用性和业务特殊性的平衡。', 0, '2025-07-02 15:55:33', '2025-07-02 15:55:33'),
    (@zhiyaoId, 10, 22, 'Pinia的API设计确实比Vuex更直观。', 0, '2025-07-02 17:10:48', '2025-07-02 17:10:48'),
    (@zhiyaoId, 10, 23, 'TypeScript能提前发现很多潜在问题。', 0, '2025-07-02 18:25:22', '2025-07-02 18:25:22'),
    (@zhiyaoId, 10, 24, '理解响应式原理对性能优化很有帮助。', 0, '2025-07-02 19:40:35', '2025-07-02 19:40:35'),
    (@zhiyaoId, 10, 25, 'props/emit/slot是基础，provide/inject适合跨层级通信。', 0, '2025-07-02 20:55:18', '2025-07-02 20:55:18'),
    (@zhiyaoId, 10, 26, 'Proxy的响应式实现比defineProperty更高效。', 0, '2025-07-02 21:30:42', '2025-07-02 21:30:42'),
    (@zhiyaoId, 10, 27, 'Vite的开发服务器启动速度确实很快。', 0, '2025-07-02 22:15:28', '2025-07-02 22:15:28'),

    -- 对第6篇帖子评论的回复
    (@zhiyaoId, 11, 28, 'Docker让环境一致性问题得到了很好解决。', 0, '2025-07-02 19:00:35', '2025-07-02 19:00:35'),
    (@zhiyaoId, 11, 29, '合理利用镜像缓存能大大提升构建效率。', 0, '2025-07-02 20:15:18', '2025-07-02 20:15:18'),
    (@zhiyaoId, 11, 30, '多阶段构建在生产镜像优化中很实用。', 0, '2025-07-02 21:30:42', '2025-07-02 21:30:42'),
    (@zhiyaoId, 11, 31, 'Kubernetes在生产环境的容器编排更强大。', 0, '2025-07-02 22:45:25', '2025-07-02 22:45:25'),

    -- 对其他帖子的部分回复
    (@zhiyaoId, 13, 32, 'Git Flow适合大团队，GitHub Flow更适合小团队。', 0, '2025-07-03 12:40:28', '2025-07-03 12:40:28'),
    (@zhiyaoId, 14, 35, '搜索相关性可以通过boost和function_score调整。', 0, '2025-07-03 18:50:33', '2025-07-03 18:50:33'),
    (@zhiyaoId, 15, 38, 'JProfiler结合Arthas能更全面地分析性能问题。', 0, '2025-07-03 21:45:22', '2025-07-03 21:45:22'),
    (@zhiyaoId, 16, 42, 'Spring Cloud提供了完整的微服务解决方案。', 0, '2025-07-04 13:50:35', '2025-07-04 13:50:35');
INSERT INTO user_centre.note (
    courseId, enrollTime, title, link, author, userId, lecturer, toolTip,isOfficial,isChecked)
VALUES ('17','24T1','6713官方笔记','https://sudden-comic-c00.notion.site/1f1f45253452809daeaad72fceb2146f?v=1f1f45253452806fb07b000ce212e8c5&pvs=4',
        'yang', @yangId,'joshi','这是yang的6713笔记', '1','1');

INSERT INTO user_centre.note (
    courseId, enrollTime,title, link, author, userId, lecturer, toolTip, isOfficial, isChecked)
VALUES ('17','25T1','6713非官方笔记','https://sudden-comic-c00.notion.site/1f1f45253452809daeaad72fceb2146f?v=1f1f45253452806fb07b000ce212e8c5&pvs=4',
        'yang', @yangId,'XXX','这是yang的6713笔记2号', '0', '1');



