create table if not exists profile
(
    id              bigint       auto_increment not null
                                 COMMENT 'Autoincrement identifier',
    label           varchar(255) not null
                                 COMMENT 'The profile''s label, must be set and unique',

    constraint      UK_profile_label unique (label),
    constraint      PK_profile primary key (id)
) COMMENT = 'Representation of a profile in the database';

create table if not exists account
(
    id              bigint       auto_increment not null
    COMMENT 'Autoincrement identifier',
    profile_id      bigint       not null
    COMMENT 'The profile account, must be set',
    -- nullable if account is deleted
    firstname       varchar(50)  null
    COMMENT 'The account''s firstname. This is nullable in case we "delete" the account.',
    -- nullable if account is deleted
    lastname        varchar(50)  null
    COMMENT 'The account''s lastname. This is nullable in case we "delete" the account.',
    -- nullable if account is deleted
    password        varchar(255) null
    COMMENT 'The account''s password. This is nullable in case we "delete" the account.',
    -- nullable if account is deleted, non unique for testing
    mail            varchar(50)  not null
    COMMENT 'The account''s mail. This is the unique identifier for login.',
    -- nullable if account is deleted
    oauth_provider  varchar(50)  null
    COMMENT 'The provider of the OAuth service (e.g., google, microsoft, facebook)',
    -- nullable if account is deleted
    oauth_id        varchar(255) null
    COMMENT 'The unique identifier for the account from the OAuth provider.',
    -- nullable if account is deleted
    locale          varchar(10)  null
    COMMENT 'IETF Tag of the selected locale for mails (fr, en)',
    active          boolean      default false not null
    COMMENT 'Active status for this account. Should be false if mail has not been checked. Should be false if account was deleted',
    created_at      timestamp    default current_timestamp not null
    COMMENT 'Date and time when the account was created.',
    updated_at      timestamp    default current_timestamp on update current_timestamp not null
    COMMENT 'Date and time when the account was last updated.',
    last_login_at   timestamp    null
    COMMENT 'Date and time when the account was last used.',
    deleted_at      timestamp    null
    COMMENT 'Date and time when the account was logically deleted.',

    constraint      UK_account_mail unique (mail),
    constraint      UK_account_oauth unique (oauth_provider, oauth_id),
    constraint      FK_account_profile
    foreign key (profile_id) references profile (id),
    constraint      PK_account primary key (id)
    ) COMMENT = 'Representation of an account in the database';

create table if not exists account_request
(
    id              bigint       auto_increment not null
                                 COMMENT 'Autoincrement identifier',
    token           varchar(50)  not null
                                 COMMENT 'An account request token, used to find the account again. Must be set.',
    account_id      bigint       not null
                                 COMMENT 'The account to handle. Must be set',
    expiration_date timestamp    not null
                                 COMMENT 'Expiration date for the request (will last somewhere up to 5-10 minutes). Must be set',
    request_type    enum(
                        'SIGNUP', 'CHANGE', 'RESET', 'VERIFYMAIL'
                    )            not NULL
                                 COMMENT 'The type of request (reset password, etc). Must be set',

    constraint      FK_account_request_account_id
    foreign key (account_id) references account (id),
    constraint      PK_account_request primary key (id)
) COMMENT = 'Representation of an account request in the database';

insert into profile (id, label)
values (1, 'Administrator'),
       (2, 'Normal');
