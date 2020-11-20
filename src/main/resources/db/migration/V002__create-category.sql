create table category
(
    id   bigserial    not null,
    name varchar(255) not null,
    primary key (id)
);

alter table if exists category
    add constraint uk_category_name unique (name);
