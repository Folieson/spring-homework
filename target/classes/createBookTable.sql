create table book
(
    id           serial not null
        constraint book_pk
            primary key,
    name         text   not null,
    release_date date,
    author       integer
        constraint book_writer_id_fk
            references writer
            on update restrict on delete restrict
);

alter table book
    owner to postgres;

create unique index book_id_uindex
    on book (id);


