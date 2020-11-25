create table country
(
    id   bigserial    not null,
    name varchar(255) not null,
    primary key (id)
);

create table state
(
    id         bigserial    not null,
    name       varchar(255) not null,
    country_id int8         not null,
    primary key (id)
);

alter table if exists country
    add constraint uk_country_name unique (name);

alter table if exists state
    add constraint uk_state_name unique (name);

alter table if exists state
    add constraint fk_state_country
        foreign key (country_id)
            references country