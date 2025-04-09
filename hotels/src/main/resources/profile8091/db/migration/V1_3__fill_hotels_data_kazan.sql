insert into hotels (hotel_type_id, title, city_id, location, phone_number, email, building_year, rooms_value,
                    user_rating, location_desc, hotel_desc, rooms_desc, guest_info, time_check_in, time_check_out,
                    is_active, star_grade)
values ((select ct_hotel_types.id from ct_hotel_types where ct_hotel_types.title = 'hotel'),
        'Отель Castro',
        (select ct_cities.id from ct_cities where ct_cities.title = 'Казань'),
        'ул. Интернациональная, д. 1/1',
        '78431112233',
        'castro@mail.ru', 2017,
        292,
        8.9,
        'Отель «Castro» расположен в Казани, в 10 минутах езды от центра города. Рядом с отелем расположены остановки общественного транспорта, что позволит вам легко добраться до любых достопримечательностей и памятников. Расстояние до международного аэропорта составляет 21 км.',
        'Утро для гостей отеля «Castro» начинается с бесплатного завтрака. В ресторане отеля «Castro» вы можете заказать блюда татарской, русской и международной кухни. Вечером в лобби-баре попробуйте различные напитки и закуски, отдохните и пообщайтесь с друзьями.

Вы можете примкнуть к организованным экскурсиям или самостоятельно изучать окрестности. Если вы не представляете себе отдых без пикника, организуйте барбекю в специально отведенном месте. Любители бильярда могут сыграть несколько партий с друзьями и деловыми партнерами.',
        'Отель «Castro» располагает 31 уютным номером с кондиционером и мини-баром. В ванной комнате вас ждут бесплатные туалетные принадлежности и фен. Вечером после напряженного дня вы можете посмотреть телевизор или послушать радио.',
        'Российским гражданам при заезде обязательно нужно иметь оригинал действующего паспорта РФ.',
        '14:00',
        '12:00',
        true,
        3);

insert into hotels_hotel_amenities (hotel_id, hotel_amenity_id, is_special, is_additional_cost, description)
VALUES ((select hotels.id from hotels where title = 'Отель Castro'),
        (select hotel_amenities.id from hotel_amenities where hotel_amenities.title = 'Банкомат'), false, false, ''),
       ((select hotels.id from hotels where title = 'Отель Castro'),
        (select hotel_amenities.id from hotel_amenities where hotel_amenities.title = 'Компьютер'), false, false, ''),
       ((select hotels.id from hotels where title = 'Отель Castro'),
        (select hotel_amenities.id from hotel_amenities where hotel_amenities.title = 'Кондиционер'), false, false, ''),
       ((select hotels.id from hotels where title = 'Отель Castro'),
        (select hotel_amenities.id
         from hotel_amenities
         where hotel_amenities.title = 'Круглосуточная стойка регистрации'), false, false, ''),
       ((select hotels.id from hotels where title = 'Отель Castro'),
        (select hotel_amenities.id from hotel_amenities where hotel_amenities.title = 'Лифт'), false, false, ''),
       ((select hotels.id from hotels where title = 'Отель Castro'),
        (select hotel_amenities.id from hotel_amenities where hotel_amenities.title = 'Отель для некурящих'), false,
        false, ''),
       ((select hotels.id from hotels where title = 'Отель Castro'),
        (select hotel_amenities.id from hotel_amenities where hotel_amenities.title = 'Телевизор'), false, false, ''),
       ((select hotels.id from hotels where title = 'Отель Castro'),
        (select hotel_amenities.id from hotel_amenities where hotel_amenities.title = 'Обслуживание номеров'), false,
        false, ''),
       ((select hotels.id from hotels where title = 'Отель Castro'),
        (select hotel_amenities.id from hotel_amenities where hotel_amenities.title = 'Гладильные услуги'), false, true,
        ''),
       ((select hotels.id from hotels where title = 'Отель Castro'),
        (select hotel_amenities.id from hotel_amenities where hotel_amenities.title = 'Прачечная'), false, true, ''),
       ((select hotels.id from hotels where title = 'Отель Castro'),
        (select hotel_amenities.id from hotel_amenities where hotel_amenities.title = 'Ресторан'), true, false, ''),
       ((select hotels.id from hotels where title = 'Отель Castro'),
        (select hotel_amenities.id from hotel_amenities where hotel_amenities.title = 'Бесплатный Wi-Fi'), true, false,
        ''),
       ((select hotels.id from hotels where title = 'Отель Castro'),
        (select hotel_amenities.id from hotel_amenities where hotel_amenities.title = 'Частная парковка'), true, true,
        '1200 RUB за каждый автомобиль за ночь');

insert into hotel_rooms(hotel_id, title, description, size, inside_rooms_number, total_in_hotel_number, max_guests)
values ((select hotels.id from hotels where title = 'Отель Castro'),
        'Двухместный номер Эконом', 'две отдельные кровати', 20, 1, 30, 2),
       ((select hotels.id from hotels where title = 'Отель Castro'),
        'Двухместный номер Стандарт', 'двуспальная кровать', 25, 1, 60, 2),
       ((select hotels.id from hotels where title = 'Отель Castro'),
        'Двухместный номер Улучшенный', 'двуспальная кровать', 30, 1, 80, 2),
       ((select hotels.id from hotels where title = 'Отель Castro'),
        'Двухместный номер Семейный', 'двуспальная кровать и дополнительное спальное место', 25, 1, 65, 3),
       ((select hotels.id from hotels where title = 'Отель Castro'),
        'Четырехместный номер Семейный', 'двуспальная кровать и две отдельные кровати', 35, 2, 57, 4);

insert into hotel_room_beds (hotel_room_id, bed_type_id, amount)
values ((select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Двухместный номер Эконом'
           and h.title = 'Отель Castro'),
        (select ct_hotel_bed_types.id from ct_hotel_bed_types where title = 'separate'), 1),
       ((select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Двухместный номер Стандарт'
           and h.title = 'Отель Castro'),
        (select ct_hotel_bed_types.id from ct_hotel_bed_types where title = 'queen'), 1),
       ((select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Двухместный номер Улучшенный'
           and h.title = 'Отель Castro'),
        (select ct_hotel_bed_types.id from ct_hotel_bed_types where title = 'king'), 1),
       ((select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Двухместный номер Семейный'
           and h.title = 'Отель Castro'),
        (select ct_hotel_bed_types.id from ct_hotel_bed_types where title = 'queen'), 1),
       ((select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Двухместный номер Семейный'
           and h.title = 'Отель Castro'),
        (select ct_hotel_bed_types.id from ct_hotel_bed_types where title = 'sofa'), 1),
       ((select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Четырехместный номер Семейный'
           and h.title = 'Отель Castro'),
        (select ct_hotel_bed_types.id from ct_hotel_bed_types where title = 'queen'), 1),
       ((select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Четырехместный номер Семейный'
           and h.title = 'Отель Castro'),
        (select ct_hotel_bed_types.id from ct_hotel_bed_types where title = 'separate'), 1);

insert into hotel_rooms_hotel_room_amenities_rel
values ((select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Двухместный номер Эконом'
           and h.title = 'Отель Castro'),
        (select id from hotel_room_amenities where title = 'Телевизор')),
       ((select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Двухместный номер Эконом'
           and h.title = 'Отель Castro'),
        (select id from hotel_room_amenities where title = 'Чайник')),
       ((select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Двухместный номер Эконом'
           and h.title = 'Отель Castro'),
        (select id from hotel_room_amenities where title = 'Постельное бельё')),
       ((select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Двухместный номер Стандарт'
           and h.title = 'Отель Castro'),
        (select id from hotel_room_amenities where title = 'Телевизор')),
       ((select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Двухместный номер Стандарт'
           and h.title = 'Отель Castro'),
        (select id from hotel_room_amenities where title = 'Чайник')),
       ((select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Двухместный номер Стандарт'
           and h.title = 'Отель Castro'),
        (select id from hotel_room_amenities where title = 'Постельное бельё')),
       ((select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Двухместный номер Стандарт'
           and h.title = 'Отель Castro'),
        (select id from hotel_room_amenities where title = 'Собственная ванная комната')),
       ((select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Двухместный номер Стандарт'
           and h.title = 'Отель Castro'),
        (select id from hotel_room_amenities where title = 'Туалетные принадлежности')),
       ((select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Двухместный номер Стандарт'
           and h.title = 'Отель Castro'),
        (select id from hotel_room_amenities where title = 'Холодильник')),
       ((select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Двухместный номер Улучшенный'
           and h.title = 'Отель Castro'),
        (select id from hotel_room_amenities where title = 'Телевизор')),
       ((select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Двухместный номер Улучшенный'
           and h.title = 'Отель Castro'),
        (select id from hotel_room_amenities where title = 'Чайник')),
       ((select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Двухместный номер Улучшенный'
           and h.title = 'Отель Castro'),
        (select id from hotel_room_amenities where title = 'Постельное бельё')),
       ((select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Двухместный номер Улучшенный'
           and h.title = 'Отель Castro'),
        (select id from hotel_room_amenities where title = 'Собственная ванная комната')),
       ((select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Двухместный номер Улучшенный'
           and h.title = 'Отель Castro'),
        (select id from hotel_room_amenities where title = 'Туалетные принадлежности')),
       ((select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Двухместный номер Улучшенный'
           and h.title = 'Отель Castro'),
        (select id from hotel_room_amenities where title = 'Холодильник')),
       ((select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Двухместный номер Улучшенный'
           and h.title = 'Отель Castro'),
        (select id from hotel_room_amenities where title = 'Красивый вид')),
       ((select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Двухместный номер Улучшенный'
           and h.title = 'Отель Castro'),
        (select id from hotel_room_amenities where title = 'Кондиционер')),
       ((select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Двухместный номер Улучшенный'
           and h.title = 'Отель Castro'),
        (select id from hotel_room_amenities where title = 'Халат')),
       ((select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Двухместный номер Семейный'
           and h.title = 'Отель Castro'),
        (select id from hotel_room_amenities where title = 'Телевизор')),
       ((select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Двухместный номер Семейный'
           and h.title = 'Отель Castro'),
        (select id from hotel_room_amenities where title = 'Чайник')),
       ((select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Двухместный номер Семейный'
           and h.title = 'Отель Castro'),
        (select id from hotel_room_amenities where title = 'Постельное бельё')),
       ((select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Двухместный номер Семейный'
           and h.title = 'Отель Castro'),
        (select id from hotel_room_amenities where title = 'Собственная ванная комната')),
       ((select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Двухместный номер Семейный'
           and h.title = 'Отель Castro'),
        (select id from hotel_room_amenities where title = 'Туалетные принадлежности')),
       ((select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Двухместный номер Семейный'
           and h.title = 'Отель Castro'),
        (select id from hotel_room_amenities where title = 'Холодильник')),
       ((select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Двухместный номер Семейный'
           and h.title = 'Отель Castro'),
        (select id from hotel_room_amenities where title = 'Кондиционер')),
       ((select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Четырехместный номер Семейный'
           and h.title = 'Отель Castro'),
        (select id from hotel_room_amenities where title = 'Телевизор')),
       ((select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Четырехместный номер Семейный'
           and h.title = 'Отель Castro'),
        (select id from hotel_room_amenities where title = 'Чайник')),
       ((select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Четырехместный номер Семейный'
           and h.title = 'Отель Castro'),
        (select id from hotel_room_amenities where title = 'Постельное бельё')),
       ((select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Четырехместный номер Семейный'
           and h.title = 'Отель Castro'),
        (select id from hotel_room_amenities where title = 'Собственная ванная комната')),
       ((select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Четырехместный номер Семейный'
           and h.title = 'Отель Castro'),
        (select id from hotel_room_amenities where title = 'Туалетные принадлежности')),
       ((select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Четырехместный номер Семейный'
           and h.title = 'Отель Castro'),
        (select id from hotel_room_amenities where title = 'Холодильник')),
       ((select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Четырехместный номер Семейный'
           and h.title = 'Отель Castro'),
        (select id from hotel_room_amenities where title = 'Кондиционер'));

insert into hotel_room_rate (title, hotel_rooms_id, feed_type_id, price, payment_type, is_refund)
values ('Экономный, невозвратный, без питания',
        (select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Двухместный номер Эконом'
           and h.title = 'Отель Castro'),
        (select feed.id from ct_hotel_feed_types feed where title = 'none'),
        3000, 'card', false),
       ('Экономный без питания',
        (select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Двухместный номер Эконом'
           and h.title = 'Отель Castro'),
        (select feed.id from ct_hotel_feed_types feed where title = 'none'),
        3200, 'card', true),
       ('Экономный с питанием',
        (select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Двухместный номер Эконом'
           and h.title = 'Отель Castro'),
        (select feed.id from ct_hotel_feed_types feed where title = 'breakfast'),
        4000, 'card', true),
       ('Стандартный, невозвратный, без питания',
        (select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Двухместный номер Стандарт'
           and h.title = 'Отель Castro'),
        (select feed.id from ct_hotel_feed_types feed where title = 'none'),
        3500, 'card', false),
       ('Стандартный без питания',
        (select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Двухместный номер Стандарт'
           and h.title = 'Отель Castro'),
        (select feed.id from ct_hotel_feed_types feed where title = 'none'),
        3700, 'card', true),
       ('Стандартный с питанием',
        (select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Двухместный номер Стандарт'
           and h.title = 'Отель Castro'),
        (select feed.id from ct_hotel_feed_types feed where title = 'breakfast'),
        5500, 'card', true),
       ('Улучшенный, невозвратный, без питания',
        (select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Двухместный номер Улучшенный'
           and h.title = 'Отель Castro'),
        (select feed.id from ct_hotel_feed_types feed where title = 'none'),
        4000, 'card', false),
       ('Улучшенный без питания',
        (select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Двухместный номер Улучшенный'
           and h.title = 'Отель Castro'),
        (select feed.id from ct_hotel_feed_types feed where title = 'none'),
        4200, 'card', true),
       ('Улучшенный с питанием',
        (select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Двухместный номер Улучшенный'
           and h.title = 'Отель Castro'),
        (select feed.id from ct_hotel_feed_types feed where title = 'breakfast'),
        5700, 'card', true),
       ('Семейный, невозвратный, без питания',
        (select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Двухместный номер Семейный'
           and h.title = 'Отель Castro'),
        (select feed.id from ct_hotel_feed_types feed where title = 'none'),
        4000, 'card', false),
       ('Семейный без питания',
        (select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Двухместный номер Семейный'
           and h.title = 'Отель Castro'),
        (select feed.id from ct_hotel_feed_types feed where title = 'none'),
        4200, 'card', true),
       ('Семейный с питанием',
        (select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Двухместный номер Семейный'
           and h.title = 'Отель Castro'),
        (select feed.id from ct_hotel_feed_types feed where title = 'breakfast'),
        5800, 'card', true),
       ('Семейный, невозвратный, без питания',
        (select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Четырехместный номер Семейный'
           and h.title = 'Отель Castro'),
        (select feed.id from ct_hotel_feed_types feed where title = 'none'),
        6000, 'card', false),
       ('Семейный без питания',
        (select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Четырехместный номер Семейный'
           and h.title = 'Отель Castro'),
        (select feed.id from ct_hotel_feed_types feed where title = 'none'),
        6400, 'card', true),
       ('Семейный с питанием',
        (select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Четырехместный номер Семейный'
           and h.title = 'Отель Castro'),
        (select feed.id from ct_hotel_feed_types feed where title = 'breakfast'),
        8500, 'card', true);