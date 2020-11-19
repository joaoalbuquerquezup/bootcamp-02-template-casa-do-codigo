create table author
(
    id            bigserial    not null,
    creation_time timestamp    not null,
    description   varchar(400) not null,
    email         varchar(255) not null,
    name          varchar(255) not null,
    primary key (id)
);

alter table author
    add constraint uk_author_email unique (email)