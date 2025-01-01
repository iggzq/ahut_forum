CREATE TABLE `like_article` (
                                `id` bigint NOT NULL,
                                `article_id` bigint NOT NULL COMMENT '文章id',
                                `user_id` bigint NOT NULL COMMENT '发布人id',
                                `like_user_id` bigint NOT NULL COMMENT '点赞人的id',
                                `status` tinyint DEFAULT '0',
                                PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci

