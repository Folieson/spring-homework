create table writer
(
    id          serial not null
        constraint writer_pk
            primary key,
    first_name  text   not null,
    second_name text   not null,
    birth_date  date   not null
);

alter table writer
    owner to postgres;

create unique index writer_id_uindex
    on writer (id);


