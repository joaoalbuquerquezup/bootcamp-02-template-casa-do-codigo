create table purchase
(
    id         bigserial    not null,
    address    varchar(255) not null,
    cep        varchar(255) not null,
    city       varchar(255) not null,
    complement varchar(255) not null,
    document   varchar(255) not null,
    email      varchar(255) not null,
    first_name varchar(255) not null,
    last_name  varchar(255) not null,
    phone      varchar(255) not null,
    country_id int8         not null,
    state_id   int8,
    primary key (id)
);

alter table if exists purchase
    add constraint fk_purchase_country
        foreign key (country_id)
            references country;

alter table if exists purchase
    add constraint fk_purchase_state
        foreign key (state_id)
            references state;
