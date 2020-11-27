create table coupon
(
    id                  bigserial     not null,
    code                varchar(255)  not null,
    percentage_discount numeric(19, 2) not null,
    valid_until         date          not null,
    primary key (id)
);

alter table if exists purchase
    add column coupon_id int8;

alter table if exists purchase
    add constraint fk_purchase_coupon
        foreign key (coupon_id)
            references coupon;

alter table if exists coupon
    add constraint uk_coupon_code unique (code);