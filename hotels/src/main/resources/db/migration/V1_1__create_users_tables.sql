create table if not exists user_profile
(
    id                   bigserial primary key,
    login                varchar(50),
    password             varchar(255),
    first_name           varchar(50) NOT NULL,
    last_name            varchar(50) NOT NULL,
    middle_name          varchar(50) default '',
    gender               varchar(20) not null,
    birthday_date        date,
    phone_number         varchar(11),
    email                varchar(255),
    is_active            boolean,
    is_send_notification boolean,
    bonus_value          int,
    created_at           timestamp   default now(),
    updated_at           timestamp   default now(),
    CONSTRAINT check_email CHECK ( email ~* '^[A-Za-z0-9._+%-]+@[A-Za-z0-9.-]+[.][A-Za-z]+$')
);

create table if not exists user_orders
(
    id              bigserial primary key,
    order_date      timestamp default now(),
    user_profile_id bigint,
    date_from       date,
    date_to         date,
    is_complete     boolean   default false,
    is_canceled     boolean   default false,
    order_price     numeric(10, 2),
    description     varchar(255),
    CONSTRAINT fk_user_profile_user_order FOREIGN KEY (user_profile_id) references user_profile (id) ON DELETE CASCADE ON UPDATE CASCADE
);

create table if not exists user_order_hotel_room_rel
(
    user_order_id bigint not null,
    hotel_room_id bigint not null,
    CONSTRAINT pk_user_order_id_hotel_room_id primary key (user_order_id, hotel_room_id),
    CONSTRAINT fk_user_order_hotel_room foreign key (user_order_id) REFERENCES user_orders (id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_hotel_room_user_order foreign key (hotel_room_id) REFERENCES hotel_rooms (id) ON DELETE CASCADE ON UPDATE CASCADE
);

create table if not exists order_guests
(
    id            bigserial primary key,
    first_name    varchar(50) NOT NULL,
    last_name     varchar(50) NOT NULL,
    middle_name   varchar(50) default '',
    is_child      boolean     default false,
    child_age     smallint,
    user_order_id bigint      not null,
    CONSTRAINT check_child_age CHECK ( child_age > 0 AND child_age < 18),
    CONSTRAINT fk_order_guests_user_orders FOREIGN KEY (user_order_id) references user_orders(id) ON DELETE CASCADE ON UPDATE CASCADE
);