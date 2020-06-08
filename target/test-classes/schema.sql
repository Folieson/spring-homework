drop table if exists book;
drop table if exists writer;

create table writer
(
    id          serial not null
        constraint writer_pk
            primary key,
    first_name  text   not null,
    second_name text   not null,
    birth_date  date   not null
);

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
