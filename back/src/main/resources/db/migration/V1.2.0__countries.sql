create table if not exists countries
(
    id    bigint auto_increment not null
        COMMENT 'Autoincrement identifier',
    label varchar(255)
        COMMENT 'Country''s label, must be set and unique',
    constraint PK_something primary key (id)
) COMMENT = 'Representation of countries in the database';