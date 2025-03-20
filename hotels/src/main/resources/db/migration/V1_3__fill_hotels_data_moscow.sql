insert into hotels (hotel_type_id, title, city_id, location, phone_number, email, building_year, rooms_value,
                    user_rating, location_desc, hotel_desc, rooms_desc, guest_info, time_check_in, time_check_out,
                    is_active, star_grade)
values ((select ct_hotel_types.id from ct_hotel_types where ct_hotel_types.title = 'Отель'),
        'Отель Москва Красносельская',
        (select ct_cities.id from ct_cities where ct_cities.title = 'Москва'),
        'Верхняя Красносельская улица, д.11Ас4',
        '74951112233',
        'krasnaya@mail.ru', 2017,
        292,
        8.9,
        'Отель "Москва Красносельская (ex. Hilton Garden Inn Красносельская) находится в Москве в пешей доступности от станции метро «Красносельская» и вокзалов: Ленинградского, Ярославского и Казанского. Напротив отеля находится крупный торговый центр с большим выбором магазинов и кафе. Неподалёку от отеля расположено историческое здание концерна Бабаевский, не территории которого работает музей с возможностью экскурсионного обслуживания и организации мастер классов, а также фирменный магазин шоколадной продукции.',
        'К услугам гостей круглосуточно открыт тренажёрный зал и гостевая прачечная, киоск с товарами первой необходимости, снеками и сувенирной продукцией, самостоятельная подземная парковка за отдельную плату. На втором этаже отеля находится конференц-центр, включающий 5 залов от 30 до 220 кв.м с дневным освещением и современным встроенным оборудованием.',
        'В отеле 292 номера с кондиционером и другими удобствами, в числе которых сейф, мини-холодильник, чайная станция, телефон, ЖК-телевизор, гладильная доска, утюг и бесплатная бутилированная вода при заезде. На всей территории отеля бесплатно предоставляется беспроводной доступ в интернет. В ванной комнате — фен, тапочки, банные полотенца и душевая косметика. По запросу бесплатно предоставляются халат, зубные и швейные принадлежности, шапочка для душа, одноразовый бритвенный станок, косметический набор.',
        'Российским гражданам при заезде обязательно нужно иметь оригинал действующего паспорта РФ.',
        '15:00',
        '12:00',
        true,
        4);

insert into hotels_hotel_amenities (hotel_id, hotel_amenity_id, is_special, is_additional_cost, description)
VALUES ((select hotels.id from hotels where title = 'Отель Москва Красносельская'),
        (select hotel_amenities.id from hotel_amenities where hotel_amenities.title = 'Банкомат'), false, false, ''),
       ((select hotels.id from hotels where title = 'Отель Москва Красносельская'),
        (select hotel_amenities.id from hotel_amenities where hotel_amenities.title = 'Компьютер'), false, false, ''),
       ((select hotels.id from hotels where title = 'Отель Москва Красносельская'),
        (select hotel_amenities.id from hotel_amenities where hotel_amenities.title = 'Кондиционер'), false, false, ''),
       ((select hotels.id from hotels where title = 'Отель Москва Красносельская'),
        (select hotel_amenities.id
         from hotel_amenities
         where hotel_amenities.title = 'Круглосуточная стойка регистрации'), false, false, ''),
       ((select hotels.id from hotels where title = 'Отель Москва Красносельская'),
        (select hotel_amenities.id from hotel_amenities where hotel_amenities.title = 'Лифт'), false, false, ''),
       ((select hotels.id from hotels where title = 'Отель Москва Красносельская'),
        (select hotel_amenities.id from hotel_amenities where hotel_amenities.title = 'Отель для некурящих'), false,
        false, ''),
       ((select hotels.id from hotels where title = 'Отель Москва Красносельская'),
        (select hotel_amenities.id from hotel_amenities where hotel_amenities.title = 'Телевизор'), false, false, ''),
       ((select hotels.id from hotels where title = 'Отель Москва Красносельская'),
        (select hotel_amenities.id from hotel_amenities where hotel_amenities.title = 'Обслуживание номеров'), false,
        false, ''),
       ((select hotels.id from hotels where title = 'Отель Москва Красносельская'),
        (select hotel_amenities.id from hotel_amenities where hotel_amenities.title = 'Гладильные услуги'), false, true,
        ''),
       ((select hotels.id from hotels where title = 'Отель Москва Красносельская'),
        (select hotel_amenities.id from hotel_amenities where hotel_amenities.title = 'Прачечная'), false, true, ''),
       ((select hotels.id from hotels where title = 'Отель Москва Красносельская'),
        (select hotel_amenities.id from hotel_amenities where hotel_amenities.title = 'Ресторан'), true, false, ''),
       ((select hotels.id from hotels where title = 'Отель Москва Красносельская'),
        (select hotel_amenities.id from hotel_amenities where hotel_amenities.title = 'Бесплатный Wi-Fi'), true, false,
        ''),
       ((select hotels.id from hotels where title = 'Отель Москва Красносельская'),
        (select hotel_amenities.id from hotel_amenities where hotel_amenities.title = 'Частная парковка'), true, true,
        '1200 RUB за каждый автомобиль за ночь');

insert into hotel_rooms(hotel_id, title, description, size, inside_rooms_number, total_in_hotel_number, max_guests)
values ((select hotels.id from hotels where title = 'Отель Москва Красносельская'),
        'Двухместный номер Эконом', 'две отдельные кровати', 20, 1, 30, 2),
       ((select hotels.id from hotels where title = 'Отель Москва Красносельская'),
        'Двухместный номер Стандарт', 'двуспальная кровать', 25, 1, 60, 2),
       ((select hotels.id from hotels where title = 'Отель Москва Красносельская'),
        'Двухместный номер Улучшенный', 'двуспальная кровать', 30, 1, 80, 2),
       ((select hotels.id from hotels where title = 'Отель Москва Красносельская'),
        'Двухместный номер Семейный', 'двуспальная кровать и дополнительное спальное место', 25, 1, 65, 3),
       ((select hotels.id from hotels where title = 'Отель Москва Красносельская'),
        'Четырехместный номер Семейный', 'двуспальная кровать и две отдельные кровати', 35, 2, 57, 4);

insert into hotel_room_beds (hotel_room_id, bed_type_id, amount)
values ((select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Двухместный номер Эконом'
           and h.title = 'Отель Москва Красносельская'),
        (select ct_hotel_bed_types.id from ct_hotel_bed_types where title = 'separate'), 1),
       ((select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Двухместный номер Стандарт'
           and h.title = 'Отель Москва Красносельская'),
        (select ct_hotel_bed_types.id from ct_hotel_bed_types where title = 'queen'), 1),
       ((select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Двухместный номер Улучшенный'
           and h.title = 'Отель Москва Красносельская'),
        (select ct_hotel_bed_types.id from ct_hotel_bed_types where title = 'king'), 1),
       ((select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Двухместный номер Семейный'
           and h.title = 'Отель Москва Красносельская'),
        (select ct_hotel_bed_types.id from ct_hotel_bed_types where title = 'queen'), 1),
       ((select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Двухместный номер Семейный'
           and h.title = 'Отель Москва Красносельская'),
        (select ct_hotel_bed_types.id from ct_hotel_bed_types where title = 'sofa'), 1),
       ((select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Четырехместный номер Семейный'
           and h.title = 'Отель Москва Красносельская'),
        (select ct_hotel_bed_types.id from ct_hotel_bed_types where title = 'queen'), 1),
       ((select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Четырехместный номер Семейный'
           and h.title = 'Отель Москва Красносельская'),
        (select ct_hotel_bed_types.id from ct_hotel_bed_types where title = 'separate'), 1);

insert into hotel_rooms_hotel_room_amenities_rel
values ((select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Двухместный номер Эконом'
           and h.title = 'Отель Москва Красносельская'),
        (select id from hotel_room_amenities where title = 'Телевизор')),
       ((select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Двухместный номер Эконом'
           and h.title = 'Отель Москва Красносельская'),
        (select id from hotel_room_amenities where title = 'Чайник')),
       ((select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Двухместный номер Эконом'
           and h.title = 'Отель Москва Красносельская'),
        (select id from hotel_room_amenities where title = 'Постельное бельё')),
       ((select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Двухместный номер Стандарт'
           and h.title = 'Отель Москва Красносельская'),
        (select id from hotel_room_amenities where title = 'Телевизор')),
       ((select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Двухместный номер Стандарт'
           and h.title = 'Отель Москва Красносельская'),
        (select id from hotel_room_amenities where title = 'Чайник')),
       ((select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Двухместный номер Стандарт'
           and h.title = 'Отель Москва Красносельская'),
        (select id from hotel_room_amenities where title = 'Постельное бельё')),
       ((select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Двухместный номер Стандарт'
           and h.title = 'Отель Москва Красносельская'),
        (select id from hotel_room_amenities where title = 'Собственная ванная комната')),
       ((select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Двухместный номер Стандарт'
           and h.title = 'Отель Москва Красносельская'),
        (select id from hotel_room_amenities where title = 'Туалетные принадлежности')),
       ((select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Двухместный номер Стандарт'
           and h.title = 'Отель Москва Красносельская'),
        (select id from hotel_room_amenities where title = 'Холодильник')),
       ((select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Двухместный номер Улучшенный'
           and h.title = 'Отель Москва Красносельская'),
        (select id from hotel_room_amenities where title = 'Телевизор')),
       ((select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Двухместный номер Улучшенный'
           and h.title = 'Отель Москва Красносельская'),
        (select id from hotel_room_amenities where title = 'Чайник')),
       ((select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Двухместный номер Улучшенный'
           and h.title = 'Отель Москва Красносельская'),
        (select id from hotel_room_amenities where title = 'Постельное бельё')),
       ((select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Двухместный номер Улучшенный'
           and h.title = 'Отель Москва Красносельская'),
        (select id from hotel_room_amenities where title = 'Собственная ванная комната')),
       ((select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Двухместный номер Улучшенный'
           and h.title = 'Отель Москва Красносельская'),
        (select id from hotel_room_amenities where title = 'Туалетные принадлежности')),
       ((select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Двухместный номер Улучшенный'
           and h.title = 'Отель Москва Красносельская'),
        (select id from hotel_room_amenities where title = 'Холодильник')),
       ((select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Двухместный номер Улучшенный'
           and h.title = 'Отель Москва Красносельская'),
        (select id from hotel_room_amenities where title = 'Красивый вид')),
       ((select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Двухместный номер Улучшенный'
           and h.title = 'Отель Москва Красносельская'),
        (select id from hotel_room_amenities where title = 'Кондиционер')),
       ((select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Двухместный номер Улучшенный'
           and h.title = 'Отель Москва Красносельская'),
        (select id from hotel_room_amenities where title = 'Халат')),
       ((select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Двухместный номер Семейный'
           and h.title = 'Отель Москва Красносельская'),
        (select id from hotel_room_amenities where title = 'Телевизор')),
       ((select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Двухместный номер Семейный'
           and h.title = 'Отель Москва Красносельская'),
        (select id from hotel_room_amenities where title = 'Чайник')),
       ((select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Двухместный номер Семейный'
           and h.title = 'Отель Москва Красносельская'),
        (select id from hotel_room_amenities where title = 'Постельное бельё')),
       ((select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Двухместный номер Семейный'
           and h.title = 'Отель Москва Красносельская'),
        (select id from hotel_room_amenities where title = 'Собственная ванная комната')),
       ((select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Двухместный номер Семейный'
           and h.title = 'Отель Москва Красносельская'),
        (select id from hotel_room_amenities where title = 'Туалетные принадлежности')),
       ((select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Двухместный номер Семейный'
           and h.title = 'Отель Москва Красносельская'),
        (select id from hotel_room_amenities where title = 'Холодильник')),
       ((select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Двухместный номер Семейный'
           and h.title = 'Отель Москва Красносельская'),
        (select id from hotel_room_amenities where title = 'Кондиционер')),
       ((select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Четырехместный номер Семейный'
           and h.title = 'Отель Москва Красносельская'),
        (select id from hotel_room_amenities where title = 'Телевизор')),
       ((select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Четырехместный номер Семейный'
           and h.title = 'Отель Москва Красносельская'),
        (select id from hotel_room_amenities where title = 'Чайник')),
       ((select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Четырехместный номер Семейный'
           and h.title = 'Отель Москва Красносельская'),
        (select id from hotel_room_amenities where title = 'Постельное бельё')),
       ((select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Четырехместный номер Семейный'
           and h.title = 'Отель Москва Красносельская'),
        (select id from hotel_room_amenities where title = 'Собственная ванная комната')),
       ((select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Четырехместный номер Семейный'
           and h.title = 'Отель Москва Красносельская'),
        (select id from hotel_room_amenities where title = 'Туалетные принадлежности')),
       ((select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Четырехместный номер Семейный'
           and h.title = 'Отель Москва Красносельская'),
        (select id from hotel_room_amenities where title = 'Холодильник')),
       ((select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Четырехместный номер Семейный'
           and h.title = 'Отель Москва Красносельская'),
        (select id from hotel_room_amenities where title = 'Кондиционер'));

insert into hotel_room_rate (title, hotel_rooms_id, feed_type_id, price, payment_type, is_refund)
values ('Экономный, невозвратный, без питания',
        (select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Двухместный номер Эконом'
           and h.title = 'Отель Москва Красносельская'),
        (select feed.id from ct_hotel_feed_types feed where title = 'none'),
        3000, 'card', false),
       ('Экономный без питания',
        (select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Двухместный номер Эконом'
           and h.title = 'Отель Москва Красносельская'),
        (select feed.id from ct_hotel_feed_types feed where title = 'none'),
        3200, 'card', true),
       ('Экономный с питанием',
        (select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Двухместный номер Эконом'
           and h.title = 'Отель Москва Красносельская'),
        (select feed.id from ct_hotel_feed_types feed where title = 'breakfast'),
        4000, 'card', true),
       ('Стандартный, невозвратный, без питания',
        (select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Двухместный номер Стандарт'
           and h.title = 'Отель Москва Красносельская'),
        (select feed.id from ct_hotel_feed_types feed where title = 'none'),
        3500, 'card', false),
       ('Стандартный без питания',
        (select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Двухместный номер Стандарт'
           and h.title = 'Отель Москва Красносельская'),
        (select feed.id from ct_hotel_feed_types feed where title = 'none'),
        3700, 'card', true),
       ('Стандартный с питанием',
        (select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Двухместный номер Стандарт'
           and h.title = 'Отель Москва Красносельская'),
        (select feed.id from ct_hotel_feed_types feed where title = 'breakfast'),
        5500, 'card', true),

       ('Семейный, невозвратный, без питания',
        (select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Двухместный номер Семейный'
           and h.title = 'Отель Москва Красносельская'),
        (select feed.id from ct_hotel_feed_types feed where title = 'none'),
        4000, 'card', false),
       ('Семейный без питания',
        (select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Двухместный номер Семейный'
           and h.title = 'Отель Москва Красносельская'),
        (select feed.id from ct_hotel_feed_types feed where title = 'none'),
        4200, 'card', true),
       ('Семейный с питанием',
        (select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Двухместный номер Семейный'
           and h.title = 'Отель Москва Красносельская'),
        (select feed.id from ct_hotel_feed_types feed where title = 'breakfast'),
        5800, 'card', true),

       ('Семейный, невозвратный, без питания',
        (select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Четырехместный номер Семейный'
           and h.title = 'Отель Москва Красносельская'),
        (select feed.id from ct_hotel_feed_types feed where title = 'none'),
        6000, 'card', false),
       ('Семейный без питания',
        (select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Четырехместный номер Семейный'
           and h.title = 'Отель Москва Красносельская'),
        (select feed.id from ct_hotel_feed_types feed where title = 'none'),
        6400, 'card', true),
       ('Семейный с питанием',
        (select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Четырехместный номер Семейный'
           and h.title = 'Отель Москва Красносельская'),
        (select feed.id from ct_hotel_feed_types feed where title = 'breakfast'),
        8500, 'card', true);