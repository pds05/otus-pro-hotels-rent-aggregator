create table if not exists ct_cities
(
    id           serial primary key,
    phone_prefix varchar(6),
    title        varchar(50) UNIQUE
);

create table if not exists ct_hotel_bed_types
(
    id          smallserial primary key,
    title       varchar(50) not null UNIQUE,
    description varchar(255)

);

create table if not exists ct_hotel_feed_types
(
    id          smallserial primary key,
    title       varchar(50) not null UNIQUE,
    description varchar(255)
);

create table if not exists ct_hotel_types
(
    id          smallserial primary key,
    title       varchar(50) not null UNIQUE,
    description varchar(255)
);

create table if not exists hotels
(
    id             serial primary key,
    hotel_type_id  smallint,
    title          varchar(50) not null,
    city_id        int         not null,
    location       varchar(255),
    phone_number   varchar(11),
    email          varchar(255),
    building_year  varchar(4),
    rooms_value    smallint,
    user_rating    numeric(3, 1),
    location_desc  text,
    hotel_desc     text,
    rooms_desc     text,
    guest_info     text,
    time_check_in  time,
    time_check_out time,
    is_active      boolean,
    CONSTRAINT check_email CHECK ( email ~* '^[A-Za-z0-9._+%-]+@[A-Za-z0-9.-]+[.][A-Za-z]+$'),
    CONSTRAINT fk_hotels_hotel_types FOREIGN KEY (hotel_type_id) references ct_hotel_types (id) ON DELETE RESTRICT ON UPDATE RESTRICT,
    CONSTRAINT fk_hotels_ct_cities FOREIGN KEY (city_id) references ct_cities (id) ON DELETE RESTRICT ON UPDATE RESTRICT,
    CONSTRAINT unique_hotels_title_location UNIQUE (title, city_id, location)
);

create or replace function f_hotel_type_title(smallint) returns ct_hotel_types.title%Type AS
'select title
 from ct_hotel_types
 where id = $1;'
    LANGUAGE SQL
    IMMUTABLE
    RETURNS NULL ON NULL INPUT;

alter table hotels
    add column star_grade smallint CHECK ((f_hotel_type_title(hotels.hotel_type_id) = 'Отель' AND
                                           star_grade BETWEEN 0 AND 5) OR
                                          f_hotel_type_title(hotels.hotel_type_id) != 'Отель') default 0;

create table if not exists hotel_amenity_groups
(
    id        serial primary key,
    title     varchar(50) not null UNIQUE,
    order_num int
);

create table if not exists hotel_amenities
(
    id                     serial primary key,
    hotel_amenity_group_id int,
    title                  varchar(50) not null UNIQUE,
    CONSTRAINT fk_hotel_amenities_group FOREIGN KEY (hotel_amenity_group_id) references hotel_amenity_groups (id) ON DELETE RESTRICT ON UPDATE CASCADE
);

create table if not exists hotels_hotel_amenities
(
    hotel_id           int,
    hotel_amenity_id   int,
    is_special         boolean default false,
    is_additional_cost boolean default false,
    description        varchar(255),
    CONSTRAINT pk_hotel_id_hotel_amenity_id PRIMARY KEY (hotel_id, hotel_amenity_id),
    CONSTRAINT fk_hotels_hotel_amenities_hotel_id FOREIGN KEY (hotel_id) REFERENCES hotels (id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_hotels_hotel_amenities_hotel_amenity_id FOREIGN KEY (hotel_amenity_id) REFERENCES hotel_amenities (id) ON DELETE CASCADE ON UPDATE CASCADE
);

create table if not exists hotel_rooms
(
    id                    serial primary key,
    hotel_id              int         not null,
    title                 varchar(50) not null,
    description           varchar(255),
    size                  smallint check ( size > 0 ),
    inside_rooms_number   smallint default 1,
    total_in_hotel_number smallint default 1,
    available_count       smallint,
    max_guests            smallint check ( max_guests > 0 ),
    CONSTRAINT fk_hotels_hotel_rooms foreign key (hotel_id) REFERENCES hotels (id) ON DELETE CASCADE ON UPDATE CASCADE
);

create or replace function f_update_available_rooms_count() RETURNS trigger AS
$$
BEGIN
    NEW.available_count := NEW.total_in_hotel_number;
    RETURN NEW;
END;
$$
    LANGUAGE plpgsql;

create or replace trigger t_update_available_rooms_count
    BEFORE INSERT OR UPDATE
    ON hotel_rooms
    FOR EACH ROW
EXECUTE FUNCTION f_update_available_rooms_count();

create table if not exists hotel_room_beds
(
    hotel_room_id int not null,
    bed_type_id   int not null,
    amount        smallint check (amount > 0),
    CONSTRAINT pk_hotel_room_id_bed_type_id PRIMARY KEY (hotel_room_id, bed_type_id),
    CONSTRAINT fk_hotel_rooms_bed_type_hotel_room_id foreign key (hotel_room_id) references hotel_rooms (id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_hotel_rooms_ct_hotel_bed_types FOREIGN KEY (bed_type_id) REFERENCES ct_hotel_bed_types (id) ON DELETE CASCADE ON UPDATE CASCADE
);

create table if not exists hotel_room_amenities
(
    id    serial primary key,
    title varchar(50) not null UNIQUE
);

create table if not exists hotel_rooms_hotel_room_amenities_rel
(
    hotel_room_id         int,
    hotel_room_amenity_id int,
    CONSTRAINT pk_hotel_room_id_hotel_room_amenity_id PRIMARY KEY (hotel_room_id, hotel_room_amenity_id),
    CONSTRAINT fk_hotel_rooms_rel FOREIGN KEY (hotel_room_id) REFERENCES hotel_rooms (id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_hotel_amenity_groups_rel FOREIGN KEY (hotel_room_amenity_id) REFERENCES hotel_room_amenities (id) ON DELETE CASCADE ON UPDATE CASCADE
);

create table if not exists hotel_room_rate
(
    id serial primary key,
    hotel_rooms_id int,
    feed_type_id   int,
    title          varchar(50)    not null,
    price          numeric(10, 2) not null,
    payment_type   varchar(50),
    is_refund      boolean,
    CONSTRAINT fk_hotel_rooms_hotel_room_rate FOREIGN KEY (hotel_rooms_id) references hotel_rooms (id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_hotel_room_rate_ct_hotel_feed_types FOREIGN KEY (feed_type_id) references ct_hotel_feed_types (id) ON DELETE CASCADE ON UPDATE CASCADE
)