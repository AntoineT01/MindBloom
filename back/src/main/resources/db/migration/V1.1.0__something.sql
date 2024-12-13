create table if not exists something
(
    id              bigint       auto_increment not null
                                 COMMENT 'Autoincrement identifier',
    label           varchar(255) not null
                                 COMMENT 'Something''s label, must be set and unique',

    -- AUDITING columns.
    created_by      bigint      not null
                                COMMENT 'Account id of the account that has created the data. Set during creation only',
    created_on      timestamp   not null default NOW()
                                COMMENT 'Date of creation of this data. Set during creation only',
    updated_by      bigint      not null
                                COMMENT 'Account id of the account that has created the data. Set during creation, then after each update',
    updated_on      timestamp   not null default NOW()
                                COMMENT 'Date of update of this data. Set during creation, then after each update',

    constraint      UK_something_label unique (label),
    constraint      FK_something_created_by
            foreign key (created_by) references account (id),
    constraint      FK_something_updated_by
            foreign key (updated_by) references account (id),
    constraint      PK_something primary key (id)
) COMMENT = 'Representation of something in the database';