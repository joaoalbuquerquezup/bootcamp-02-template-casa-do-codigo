alter table if exists purchase
    add column total numeric(19, 2) not null;

create table purchase_item
(
    id          bigserial not null,
    amount      int8      not null,
    book_id     int8      not null,
    purchase_id int8      not null,
    primary key (id)
);

alter table if exists purchase_item
    add constraint fk_purchase_item_book
        foreign key (book_id)
            references book;

alter table if exists purchase_item
    add constraint fk_purchase_item_purchase
        foreign key (purchase_id)
            references purchase;
