create table if not exists like_article
(
    id
        bigint
        not
            null
        primary
            key,
    article_id
        bigint
        not
            null
        comment
            '文章id',
    user_id
        bigint
        not
            null
        comment
            '发布人id',
    like_user_id
        bigint
        not
            null
        comment
            '点赞人的id',
    status
        tinyint
        default
            0
        null
)
    engine = InnoDB;

