CREATE TABLE `article` (
                           `id` bigint NOT NULL COMMENT '文章id',
                           `user_id` bigint NOT NULL COMMENT '发布人',
                           `user_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '发帖人姓名',
                           `admission_year` int NOT NULL DEFAULT '0' COMMENT '入学年份',
                           `title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '标题',
                           `content` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '文章内容',
                           `topic_type` tinyint NOT NULL DEFAULT '0',
                           `like_count` int NOT NULL DEFAULT '0' COMMENT '点赞数',
                           `comment_count` int NOT NULL DEFAULT '0' COMMENT '评论数',
                           `update_time` datetime DEFAULT NULL COMMENT '更新时间',
                           `create_time` datetime NOT NULL COMMENT '发布时间',
                           `status` tinyint NOT NULL DEFAULT '0' COMMENT '文章状态',
                           PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='存储文章数据'

