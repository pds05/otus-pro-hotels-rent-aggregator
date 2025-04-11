create table if not exists providers
(
    id              serial primary key,
    title           varchar(50)  not null,
    property_name   varchar(25)  not null UNIQUE,
    description     varchar(255),
    api_url         varchar(255) not null,
    api_login       varchar(50),
    api_password    varchar(255),
    is_active       boolean   default true,
    read_timeout    numeric(3, 1),
    connect_timeout numeric(3, 1),
    created_at      timestamp default now(),
    updated_at      timestamp default now()
);

create table if not exists provider_apis
(
    id                serial primary key,
    path              varchar(255) not null,
    rest_method       varchar(6)   not null,
    business_method   varchar(50)  not null,
    description       varchar(255),
    response_template varchar(255),
    provider_id       int          not null,
    CONSTRAINT fk_provider_id_provider_apis FOREIGN KEY (provider_id) references providers (id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT unique_path_rest_method_provider_id UNIQUE (path, rest_method, provider_id)
);

create sequence if not exists user_profiles_pk_seq
    increment 1 minvalue 1 maxvalue 9999999999 start 1 cache 1;

create table if not exists user_profiles
(
    id            varchar(10) default to_char(nextval('user_profiles_pk_seq'), 'FM0000000000') primary key,
    login         varchar(50)  not null,
    password      varchar(255) not null,
    first_name    varchar(50)  not null,
    last_name     varchar(50)  not null,
    middle_name   varchar(50),
    birthday_date date,
    phone_number  varchar(11) UNIQUE,
    email         varchar(255) UNIQUE,
    is_active     boolean    default true,
    created_at    timestamp  default now(),
    updated_at    timestamp  default now(),
    CONSTRAINT check_email CHECK ( email ~* '^[A-Za-z0-9._+%-]+@[A-Za-z0-9.-]+[.][A-Za-z]+$')
);

create table if not exists provider_user_profiles
(
    id                bigserial primary key,
    first_name        varchar(50)  not null,
    last_name         varchar(50)  not null,
    middle_name       varchar(50),
    user_profile_id   varchar(10)   not null,
    provider_id       int          not null,
    providers_user_id varchar(25),
    login             varchar(50)  not null,
    password          varchar(255) not null,
    password_salt     varchar(10)  not null,
    phone_number      varchar(11) UNIQUE,
    email             varchar(255) UNIQUE,
    created_at        timestamp default now(),
    updated_at        timestamp default now(),
    CONSTRAINT unique_user_profile_id_provider_id UNIQUE (user_profile_id, provider_id),
    CONSTRAINT fk_provider_id_provider_user_profiles FOREIGN KEY (provider_id) references providers (id) ON DELETE RESTRICT ON UPDATE RESTRICT,
    CONSTRAINT fk_user_profile_id_provider_user_profiles FOREIGN KEY (user_profile_id) references user_profiles (id) ON DELETE CASCADE ON UPDATE RESTRICT
);

create table if not exists user_orders
(
    order_id                 bigint      not null,
    user_profile_id          varchar(10) not null,
    provider_user_profile_id bigint      not null,
    provider_order           varchar(50),
    hotel                    varchar(255),
    location                 varchar(255),
    room_name                varchar(50),
    rate_name                varchar(50),
    date_in                  date,
    date_out                 date,
    status                   varchar(10),
    order_price              numeric(10, 2) check ( order_price >= 0 ),
    is_refund                boolean,
    description              varchar(255),
    created_at               timestamp default now(),
    updated_at               timestamp default now(),
    CONSTRAINT pk_order_id_user_profile_id PRIMARY KEY (order_id, user_profile_id),
    CONSTRAINT fk_provider_user_profile_user_orders FOREIGN KEY (provider_user_profile_id) references provider_user_profiles (id) ON DELETE CASCADE ON UPDATE RESTRICT,
    CONSTRAINT fk_user_profile_user_orders FOREIGN KEY (user_profile_id) references user_profiles (id) ON DELETE CASCADE ON UPDATE RESTRICT
);