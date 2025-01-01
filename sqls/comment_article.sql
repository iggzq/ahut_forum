CREATE TABLE `comment_article` (
                                   `id` bigint NOT NULL COMMENT '文章评论id',
                                   `u_id` bigint NOT NULL COMMENT '评论人id',
                                   `user_name` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '评论人姓名',
                                   `parentId` bigint DEFAULT NULL COMMENT '被评论人评论id',
                                   `to_user_id` bigint DEFAULT NULL COMMENT '被评论人的id',
                                   `article_id` bigint NOT NULL COMMENT '文章id',
                                   `content` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '文章评论内容',
                                   `likes` int DEFAULT '0' COMMENT '赞数',
                                   `create_time` datetime NOT NULL COMMENT '评论创建时间',
                                   `update_time` datetime NOT NULL COMMENT '修改时间',
                                   `status` tinyint NOT NULL DEFAULT '0' COMMENT '文章状态(0代表未读，1代表已读)',
                                   PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='文章评论'

