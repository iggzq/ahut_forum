create table if not exists comment_article
(
    id          bigint            not null comment '文章评论id'
        primary key,
    u_id        bigint            not null comment '评论人id',
    user_name   varchar(40)       not null comment '评论人姓名',
    parentId    bigint            null comment '被评论人评论id',
    to_user_id  bigint            null comment '被评论人的id',
    article_id  bigint            not null comment '文章id',
    content     varchar(100)      not null comment '文章评论内容',
    likes       int     default 0 null comment '赞数',
    create_time datetime          not null comment '评论创建时间',
    update_time datetime          not null comment '修改时间',
    status      tinyint default 0 not null comment '文章状态(0代表未读，1代表已读)'
)
    comment '文章评论' engine = InnoDB;

