create table if not exists article
(
    id
    bigint
    not
    null
    comment
    '文章id'
    primary
    key,
    user_id
    bigint
    not
    null
    comment
    '发布人',
    user_name
    varchar
(
    30
) not null comment '发帖人姓名',
    title varchar
(
    50
) not null comment '标题',
    content varchar
(
    1000
) not null comment '文章内容',
    like_count int default 0 not null comment '点赞数',
    comment_count int default 0 not null comment '评论数',
    update_time datetime null comment '更新时间',
    create_time datetime not null comment '发布时间',
    status tinyint default 0 not null comment '文章状态'
    )
    comment '存储文章数据' engine = InnoDB;

