CREATE TABLE `user` (
                        `id` bigint NOT NULL COMMENT '用户id',
                        `name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户名',
                        `password` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
                        `admission_year` int NOT NULL DEFAULT '0' COMMENT '入学年份',
                        `profile_desc` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '用户自我描述',
                        `question` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '自定义安全问题',
                        `answer` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
                        `create_time` datetime NOT NULL COMMENT '创建用户时间',
                        `update_time` datetime NOT NULL COMMENT '修改用户时间',
                        `status` tinyint NOT NULL DEFAULT '0' COMMENT '用户账户状态',
                        PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci

