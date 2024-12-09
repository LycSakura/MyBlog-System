drop database blog;
create database if not exists blog;
use blog;

-- 删除表
DROP TABLE IF EXISTS comments;
DROP TABLE IF EXISTS article_tag;
DROP TABLE IF EXISTS article_category;
DROP TABLE IF EXISTS articles;
DROP TABLE IF EXISTS tags;
DROP TABLE IF EXISTS categories;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS roles;

-- 角色表
CREATE TABLE roles
(
    id        INT AUTO_INCREMENT PRIMARY KEY,
    role_name VARCHAR(50) NOT NULL UNIQUE
);

-- 用户表
CREATE TABLE users
(
    id       INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50)  NOT NULL UNIQUE ,
    password VARCHAR(255) NOT NULL,
    email    VARCHAR(100) NOT NULL UNIQUE,
    photo    VARCHAR(255) NOT NULL DEFAULT 'default.jpg',
    role_id  INT          NOT NULL,
    FOREIGN KEY (role_id) REFERENCES roles (id)
);

-- 文章表
CREATE TABLE articles
(
    id          INT AUTO_INCREMENT PRIMARY KEY,
    user_id     INT          NOT NULL,
    title       VARCHAR(255) NOT NULL UNIQUE,
    content     TEXT         NOT NULL,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users (id)
);

-- 分类表
CREATE TABLE categories
(
    id            INT AUTO_INCREMENT PRIMARY KEY,
    category_name VARCHAR(50) NOT NULL UNIQUE
);

-- 标签表
CREATE TABLE tags
(
    id       INT AUTO_INCREMENT PRIMARY KEY,
    tag_name VARCHAR(50) NOT NULL UNIQUE
);

-- 文章-分类关联表
CREATE TABLE article_category
(
    article_id  INT NOT NULL,
    category_id INT NOT NULL,
    PRIMARY KEY (article_id, category_id),
    FOREIGN KEY (article_id) REFERENCES articles (id),
    FOREIGN KEY (category_id) REFERENCES categories (id)
);

-- 文章-标签关联表
CREATE TABLE article_tag
(
    article_id INT NOT NULL,
    tag_id     INT NOT NULL,
    PRIMARY KEY (article_id, tag_id),
    FOREIGN KEY (article_id) REFERENCES articles (id),
    FOREIGN KEY (tag_id) REFERENCES tags (id)
);

-- 评论表
CREATE TABLE comments
(
    id          INT AUTO_INCREMENT PRIMARY KEY,
    article_id  INT  NOT NULL,
    user_id     INT  NOT NULL,
    content     TEXT NOT NULL,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (article_id) REFERENCES articles (id),
    FOREIGN KEY (user_id) REFERENCES users (id)
);

-- 添加初始数据

-- 插入角色数据
INSERT INTO roles (role_name) VALUES ('admin');
INSERT INTO roles (role_name) VALUES ('user');

-- 插入用户数据
INSERT INTO users (username, password, email, photo, role_id)
VALUES ('admin', '$2a$10$VnS.ZgwQl1jGKvuUcL9T4u8Hw.8gf7Pv48xLOc61mXHXKxnbWg.Zu', 'admin@example.com', 'admin.jpg', 1),
       ('success', '$2a$10$I8n/LCgDKY/Ty77CL9LwfOBZWvLY2f9cyzaioa.4KLR9fkZvX5xee', 'success@example.com', 'default.jpg', 2),
       ('failure', '$2a$10$mo1fGD4//29hAv8VMMQAVuzbyn5zN87APJ6TR7K8Us.SGzaM9xVtK', 'failure@example.com', 'default.jpg', 2);

-- 插入 categories 数据
INSERT INTO categories (category_name)
VALUES
    ('前端开发'), ('后端开发'), ('移动开发'), ('云计算'), ('数据库'), ('人工智能'),
    ('大数据'), ('物联网'), ('区块链'), ('机器学习'), ('Web 开发'), ('游戏开发'), ('嵌入式系统'), ('网络安全'), ('操作系统');

-- 插入 tags 数据
INSERT INTO tags (tag_name)
VALUES
    ('Vue'), ('JavaScript'), ('CSS'), ('HTML'), ('Java'), ('C'), ('C++'), ('Python'), ('Ruby'), ('Go'),
    ('Swift'), ('Kotlin'), ('Rust'), ('TypeScript'), ('PHP');

-- 插入文章数据
INSERT INTO articles (user_id, title, content)
VALUES
    (1, 'Vue 全家桶详解', '深入解析 Vue 的核心特性和生态系统，包括 Vuex 和 Vue Router。'),
    (2, '使用 JavaScript 构建动态网页', '探索如何用 JavaScript 构建高效的动态网页。'),
    (3, '响应式设计的 CSS 实践', '讲解如何通过 CSS 媒体查询实现响应式设计。'),
    (1, 'HTML 表单优化指南', '提高用户体验的表单优化技巧及其实现。'),
    (3, 'Spring Boot 实践入门', '学习如何用 Spring Boot 快速构建后端服务。'),
    (2, 'Node.js 与 Express 快速构建 Web 应用', '介绍使用 Node.js 和 Express 的开发流程。'),
    (3, 'Python 数据分析入门', '如何用 Pandas 和 Matplotlib 进行数据分析的教程。'),
    (2, 'Redis 在高并发中的应用', '讲解 Redis 在高并发场景中的使用方法和优化技巧。'),
    (1, '机器学习模型的基础理论', '探讨机器学习的基本理论与算法。'),
    (3, 'Kubernetes 部署指南', 'Kubernetes 的基础知识及实战部署。'),
    (2, '从零开始搭建云计算架构', '构建和优化云计算架构的经验分享。'),
    (3, '使用 Django 开发企业级 Web 应用', '基于 Django 的企业 Web 应用开发实践。'),
    (1, '物联网设备如何高效联网', '探讨物联网设备的联网协议及其实现。'),
    (3, '区块链技术的应用场景分析', '分析区块链在不同领域的应用场景。'),
    (2, 'Rust 编程语言入门', 'Rust 的基础语法及实用特性介绍。'),
    (1, 'TypeScript 与前端工程化', '通过 TypeScript 提升前端代码质量与可维护性。'),
    (2, 'PHP 在 Web 开发中的最佳实践', '使用 PHP 构建高效 Web 应用的方法。'),
    (3, 'C++ 高性能计算实践', '探讨如何用 C++ 实现高性能计算。'),
    (1, 'SwiftUI 开发入门', '学习如何用 SwiftUI 快速构建 iOS 应用。'),
    (3, 'Kotlin 在 Android 开发中的应用', 'Kotlin 的实用开发技巧和案例分析。');

-- 插入文章与分类的关联
INSERT INTO article_category (article_id, category_id)
VALUES
    (1, 1),  -- Vue 全家桶详解 -> 前端开发
    (2, 1),  -- 使用 JavaScript 构建动态网页 -> 前端开发
    (3, 1),  -- 响应式设计的 CSS 实践 -> 前端开发
    (4, 1),  -- HTML 表单优化指南 -> 前端开发
    (5, 2),  -- Spring Boot 实践入门 -> 后端开发
    (6, 2),  -- Node.js 与 Express -> 后端开发
    (7, 5),  -- Python 数据分析入门 -> 数据库
    (8, 5),  -- Redis 在高并发中的应用 -> 数据库
    (9, 6),  -- 机器学习模型的基础理论 -> 人工智能
    (10, 4), -- Kubernetes 部署指南 -> 云计算
    (11, 4), -- 搭建云计算架构 -> 云计算
    (12, 11), -- Django 开发企业级应用 -> Web开发
    (13, 8), -- 物联网设备如何联网 -> 物联网
    (14, 9), -- 区块链技术的应用场景分析 -> 区块链
    (15, 15), -- Rust 编程语言入门 -> 操作系统
    (16, 1), -- TypeScript 与前端工程化 -> 前端开发
    (17, 11), -- PHP 的 Web 开发 -> Web开发
    (18, 13), -- C++ 高性能计算 -> 嵌入式系统
    (19, 3), -- SwiftUI 开发入门 -> 移动开发
    (20, 3); -- Kotlin 在 Android 开发中的应用 -> 移动开发



-- 插入文章与标签的关联
INSERT INTO article_tag (article_id, tag_id)
VALUES
    (1, 1),  -- Vue
    (2, 2),  -- JavaScript
    (3, 3),  -- CSS
    (4, 4),  -- HTML
    (5, 5),  -- Java
    (6, 15), -- PHP
    (7, 8),  -- Python
    (8, 8),  -- Python
    (9, 10), -- Go
    (10, 13), -- Rust
    (11, 14), -- TypeScript
    (12, 15), -- PHP
    (13, 6),  -- C
    (14, 6),  -- C
    (15, 7),  -- C++
    (16, 14), -- TypeScript
    (17, 15), -- PHP
    (18, 7),  -- C++
    (19, 11), -- Swift
    (20, 12); -- Kotlin


-- 插入评论数据
INSERT INTO comments (article_id, user_id, content)
VALUES
    (1, 2, 'Vue 全家桶真是太强大了，非常喜欢！'),
    (2, 3, '动态网页的实现方式很详细。'),
    (3, 1, '响应式设计的 CSS 技巧非常有用！'),
    (4, 3, 'HTML 表单优化后用户体验大幅提升。'),
    (5, 1, 'Spring Boot 很适合快速开发。'),
    (6, 2, 'Node.js 和 Express 搭配使用很高效。'),
    (7, 3, 'Python 数据分析工具用起来很方便！'),
    (8, 2, 'Redis 在缓存和高并发中无可替代。'),
    (9, 1, '机器学习入门的内容对初学者很友好。'),
    (10, 3, 'Kubernetes 的部署步骤很清晰。'),
    (11, 2, '云计算架构的搭建方案很实用。'),
    (12, 3, 'Django 的企业应用开发简直神器！'),
    (13, 1, '物联网设备如何高效联网值得深究。'),
    (14, 2, '区块链的技术前景非常广阔！'),
    (15, 3, 'Rust 的性能和安全性都让人惊叹。'),
    (16, 1, 'TypeScript 很适合工程化开发。'),
    (17, 3, 'PHP 的简单高效一直很吸引我。'),
    (18, 2, 'C++ 在高性能计算领域不可或缺。'),
    (19, 1, 'SwiftUI 是 iOS 开发的绝佳选择。'),
    (20, 2, 'Kotlin 让 Android 开发更高效了。');
