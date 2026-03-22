CREATE TABLE users
(
    id         varchar(36) primary key not null,
    name       varchar(50)             not null,
    nick_names json,
    created_by varchar(36),
    created_time datetime(3),
    last_modified_by varchar(36),
    last_modified_time datetime(3),
    is_deleted bit
);

CREATE TABLE roles
(
    id   varchar(36) primary key not null,
    name varchar(50)             not null,
    created_by varchar(36),
    created_time datetime(3),
    last_modified_by varchar(36),
    last_modified_time datetime(3),
    is_deleted bit
);


CREATE TABLE user_roles
(
    id      varchar(36) primary key not null,
    user_id varchar(36)             not null,
    role_id varchar(36)             not null,
    created_by varchar(36),
    created_time datetime(3),
    last_modified_by varchar(36),
    last_modified_time datetime(3)
);