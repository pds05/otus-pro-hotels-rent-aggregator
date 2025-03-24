create sequence user_profile_pk_seq
    increment 1 minvalue 1 maxvalue 99999999 start 1 cache 1;

create table if not exists user_profile
(
    id            varchar(8) default to_char(nextval('user_profile_pk_seq'), 'FM00000000') primary key,
    login         varchar(50),
    password      varchar(255),
    first_name    varchar(50) NOT NULL,
    last_name     varchar(50) NOT NULL,
    middle_name   varchar(50),
    birthday_date date,
    phone_number  varchar(11) UNIQUE,
    email         varchar(255) UNIQUE,
    is_active     boolean    default true,
    created_at    timestamp  default now(),
    updated_at    timestamp  default now(),
    CONSTRAINT check_email CHECK ( email ~* '^[A-Za-z0-9._+%-]+@[A-Za-z0-9.-]+[.][A-Za-z]+$')
);

create table if not exists user_orders
(
    order_id           bigint not null,
    user_profile_id    varchar(8),
    hotel_room_id      int    not null,
    hotel_room_rate_id int    not null,
    date_in            date,
    date_out           date,
    status             varchar(10),
    order_price        numeric(10, 2),
    is_refund          boolean,
    description        varchar(255),
    created_at         timestamp default now(),
    updated_at         timestamp default now(),
    CONSTRAINT pk_order_id_user_profile_id PRIMARY KEY (order_id, user_profile_id),
    CONSTRAINT fk_hotel_room_user_orders FOREIGN KEY (hotel_room_id) references hotel_rooms (id) ON DELETE RESTRICT ON UPDATE RESTRICT,
    CONSTRAINT fk_hotel_room_rate_user_orders FOREIGN KEY (hotel_room_rate_id) references hotel_room_rate (id) ON DELETE RESTRICT ON UPDATE RESTRICT,
    CONSTRAINT fk_user_profile_user_order FOREIGN KEY (user_profile_id) references user_profile (id) ON DELETE CASCADE ON UPDATE CASCADE
);

create table if not exists order_guests
(
    id              bigserial primary key,
    first_name      varchar(50) NOT NULL,
    last_name       varchar(50) NOT NULL,
    middle_name     varchar(50),
    is_child        boolean default false,
    child_age       smallint,
    user_order_id   bigint      not null,
    user_profile_id varchar(8),
    CONSTRAINT check_child_age CHECK ( child_age > 0 AND child_age < 18),
    CONSTRAINT fk_order_guests_user_orders FOREIGN KEY (user_order_id, user_profile_id) references user_orders (order_id, user_profile_id) ON DELETE CASCADE ON UPDATE CASCADE
);