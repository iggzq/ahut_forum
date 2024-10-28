create table if not exists user
(
    id
    bigint
    not
    null
    comment
    '用户id'
    primary
    key,
    name
    varchar
(
    30
) not null comment '用户名',
    password varchar
(
    15
) default '' not null,
    profile_desc varchar
(
    100
) default '' not null comment '用户自我描述',
    question varchar
(
    50
) null comment '自定义安全问题',
    answer varchar
(
    50
) null,
    create_time datetime not null comment '创建用户时间',
    update_time datetime not null comment '修改用户时间',
    status tinyint default 0 not null comment '用户账户状态'
    )
    engine = InnoDB;

