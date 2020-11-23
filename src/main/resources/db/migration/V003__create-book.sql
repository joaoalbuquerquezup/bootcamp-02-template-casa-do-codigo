create table book
(
    id           bigserial      not null,
    index        varchar(255),
    isbn         varchar(255)   not null,
    price        numeric(19, 2) not null check (price >= 20),
    publish_date date,
    summary      varchar(500)   not null,
    title        varchar(255)   not null,
    total_pages  int4           not null check (total_pages >= 100),
    author_id    int8           not null,
    category_id  int8           not null,
    primary key (id)
);

alter table if exists book
    add constraint uk_book_isbn unique (isbn);

alter table if exists book
    add constraint uk_book_title unique (title);

alter table if exists book
    add constraint fk_book_author
        foreign key (author_id)
            references author;

alter table if exists book
    add constraint fk_book_category
        foreign key (category_id)
            references category;
