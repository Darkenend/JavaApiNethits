create schema nethitsdb;

create table if not exists nethitsdb.customer
(
    id    int auto_increment
    constraint `PRIMARY`
    primary key,
    name  varchar(64) not null,
    phone varchar(20) not null,
    constraint customer_id_uindex
    unique (id)
    );

create table if not exists nethitsdb.user
(
    id               int auto_increment
    constraint `PRIMARY`
    primary key,
    username         varchar(16)                                 not null,
    hashed_password  varchar(256)                                not null,
    token            varchar(256)                                null,
    token_expiration datetime default ((now() + interval 1 day)) null,
    constraint table_name_id_uindex
    unique (id),
    constraint table_name_token_uindex
    unique (token)
    );