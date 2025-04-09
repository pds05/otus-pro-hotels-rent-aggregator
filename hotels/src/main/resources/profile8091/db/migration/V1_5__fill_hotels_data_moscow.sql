insert into hotels (hotel_type_id, title, city_id, location, phone_number, email, building_year, rooms_value,
                    user_rating, location_desc, hotel_desc, rooms_desc, guest_info, time_check_in, time_check_out,
                    is_active, star_grade)
values ((select ct_hotel_types.id from ct_hotel_types where ct_hotel_types.title = 'hotel'),
        'Отель Метрополь',
        (select ct_cities.id from ct_cities where ct_cities.title = 'Москва'),
        'Театральный пр-д, д.2',
        '74951112235',
        'metropol@mail.ru', 1905,
        126,
        9.0,
        'Отель «Метрополь» расположен в историческом здании в центре столицы. Станция метро находится в 500 метрах отсюда.',
        '«Метрополь» — исторический отель, построенный по инициативе мецената и покровителя искусств Саввы Мамонтова в 1905 году. Здание представляет собой памятник московского модерна. Отель гордится своим номерным фондом: историческими люксами с подлинными интерьерами в стиле московского модерна и новыми номерами, выполненными в стиле Art Deco. 84 новых номера в стиле Art Deco уже представлены для гостей отеля после первого этапа реновации.',
        'Всего в отеле 126 номеров: 84 новых номера в стиле Art Deco и 42 исторических номера в классическом стиле.',
        'Российским гражданам при заезде обязательно нужно иметь оригинал действующего паспорта РФ.',
        '15:00',
        '12:00',
        true,
        5);

insert into hotels_hotel_amenities (hotel_id, hotel_amenity_id, is_special, is_additional_cost, description)
VALUES ((select hotels.id from hotels where title = 'Отель Метрополь'),
        (select hotel_amenities.id from hotel_amenities where hotel_amenities.title = 'Банкомат'), false, false, ''),
       ((select hotels.id from hotels where title = 'Отель Метрополь'),
        (select hotel_amenities.id from hotel_amenities where hotel_amenities.title = 'Компьютер'), false, false, ''),
       ((select hotels.id from hotels where title = 'Отель Метрополь'),
        (select hotel_amenities.id from hotel_amenities where hotel_amenities.title = 'Кондиционер'), false, false, ''),
       ((select hotels.id from hotels where title = 'Отель Метрополь'),
        (select hotel_amenities.id
         from hotel_amenities
         where hotel_amenities.title = 'Круглосуточная стойка регистрации'), false, false, ''),
       ((select hotels.id from hotels where title = 'Отель Метрополь'),
        (select hotel_amenities.id from hotel_amenities where hotel_amenities.title = 'Лифт'), false, false, ''),
       ((select hotels.id from hotels where title = 'Отель Метрополь'),
        (select hotel_amenities.id from hotel_amenities where hotel_amenities.title = 'Отель для некурящих'), false,
        false, ''),
       ((select hotels.id from hotels where title = 'Отель Метрополь'),
        (select hotel_amenities.id from hotel_amenities where hotel_amenities.title = 'Телевизор'), false, false, ''),
       ((select hotels.id from hotels where title = 'Отель Метрополь'),
        (select hotel_amenities.id from hotel_amenities where hotel_amenities.title = 'Обслуживание номеров'), false,
        false, ''),
       ((select hotels.id from hotels where title = 'Отель Метрополь'),
        (select hotel_amenities.id from hotel_amenities where hotel_amenities.title = 'Гладильные услуги'), false, true,
        ''),
       ((select hotels.id from hotels where title = 'Отель Метрополь'),
        (select hotel_amenities.id from hotel_amenities where hotel_amenities.title = 'Прачечная'), false, true, ''),
       ((select hotels.id from hotels where title = 'Отель Метрополь'),
        (select hotel_amenities.id from hotel_amenities where hotel_amenities.title = 'Ресторан'), true, false, ''),
       ((select hotels.id from hotels where title = 'Отель Метрополь'),
        (select hotel_amenities.id from hotel_amenities where hotel_amenities.title = 'Бесплатный Wi-Fi'), true, false,
        ''),
       ((select hotels.id from hotels where title = 'Отель Метрополь'),
        (select hotel_amenities.id from hotel_amenities where hotel_amenities.title = 'Частная парковка'), true, true,
        '1500 RUB за каждый автомобиль за ночь');

insert into hotel_rooms(hotel_id, title, description, size, inside_rooms_number, total_in_hotel_number, max_guests)
values ((select hotels.id from hotels where title = 'Отель Метрополь'),
        'Двухместный номер Гранд Супериор Сингл', '2 отдельные кровати', 30, 1, 35, 2),
       ((select hotels.id from hotels where title = 'Отель Метрополь'),
        'Двухместный номер Гранд Супериор', 'Двуспальная кровать', 30, 1, 40, 2),
       ((select hotels.id from hotels where title = 'Отель Метрополь'),
        'Авторский Представительский', 'Двуспальная кровать', 35, 1, 25, 2),
       ((select hotels.id from hotels where title = 'Отель Метрополь'),
        'Люкс Делюкс', 'Двухкомнатный номер с двухспальной кроватью и 2 отдельными кроватями', 50, 2, 20, 4),
       ((select hotels.id from hotels where title = 'Отель Метрополь'),
        'Люкс Метрополь', 'Двуспальная кровать и две отдельные кровати', 65, 2, 6, 4);

insert into hotel_room_beds (hotel_room_id, bed_type_id, amount)
values ((select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Двухместный номер Гранд Супериор Сингл'
           and h.title = 'Отель Метрополь'),
        (select ct_hotel_bed_types.id from ct_hotel_bed_types where title = 'separate'), 1),
       ((select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Двухместный номер Гранд Супериор'
           and h.title = 'Отель Метрополь'),
        (select ct_hotel_bed_types.id from ct_hotel_bed_types where title = 'queen'), 1),
       ((select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Авторский Представительский'
           and h.title = 'Отель Метрополь'),
        (select ct_hotel_bed_types.id from ct_hotel_bed_types where title = 'king'), 1),
       ((select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Люкс Делюкс'
           and h.title = 'Отель Метрополь'),
        (select ct_hotel_bed_types.id from ct_hotel_bed_types where title = 'king'), 1),
       ((select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Люкс Делюкс'
           and h.title = 'Отель Метрополь'),
        (select ct_hotel_bed_types.id from ct_hotel_bed_types where title = 'single'), 2),
       ((select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Люкс Метрополь'
           and h.title = 'Отель Метрополь'),
        (select ct_hotel_bed_types.id from ct_hotel_bed_types where title = 'king'), 1),
       ((select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Люкс Метрополь'
           and h.title = 'Отель Метрополь'),
        (select ct_hotel_bed_types.id from ct_hotel_bed_types where title = 'queen'), 1);

insert into hotel_rooms_hotel_room_amenities_rel
values ((select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Двухместный номер Гранд Супериор Сингл'
           and h.title = 'Отель Метрополь'),
        (select id from hotel_room_amenities where title = 'Телевизор')),
       ((select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Двухместный номер Гранд Супериор Сингл'
           and h.title = 'Отель Метрополь'),
        (select id from hotel_room_amenities where title = 'Чайник')),
       ((select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Двухместный номер Гранд Супериор Сингл'
           and h.title = 'Отель Метрополь'),
        (select id from hotel_room_amenities where title = 'Постельное бельё')),
       ((select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Двухместный номер Гранд Супериор'
           and h.title = 'Отель Метрополь'),
        (select id from hotel_room_amenities where title = 'Телевизор')),
       ((select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Двухместный номер Гранд Супериор'
           and h.title = 'Отель Метрополь'),
        (select id from hotel_room_amenities where title = 'Чайник')),
       ((select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Двухместный номер Гранд Супериор'
           and h.title = 'Отель Метрополь'),
        (select id from hotel_room_amenities where title = 'Постельное бельё')),
       ((select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Двухместный номер Гранд Супериор'
           and h.title = 'Отель Метрополь'),
        (select id from hotel_room_amenities where title = 'Собственная ванная комната')),
       ((select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Двухместный номер Гранд Супериор'
           and h.title = 'Отель Метрополь'),
        (select id from hotel_room_amenities where title = 'Туалетные принадлежности')),
       ((select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Двухместный номер Гранд Супериор'
           and h.title = 'Отель Метрополь'),
        (select id from hotel_room_amenities where title = 'Холодильник')),
       ((select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Авторский Представительский'
           and h.title = 'Отель Метрополь'),
        (select id from hotel_room_amenities where title = 'Телевизор')),
       ((select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Авторский Представительский'
           and h.title = 'Отель Метрополь'),
        (select id from hotel_room_amenities where title = 'Чайник')),
       ((select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Авторский Представительский'
           and h.title = 'Отель Метрополь'),
        (select id from hotel_room_amenities where title = 'Постельное бельё')),
       ((select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Авторский Представительский'
           and h.title = 'Отель Метрополь'),
        (select id from hotel_room_amenities where title = 'Собственная ванная комната')),
       ((select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Авторский Представительский'
           and h.title = 'Отель Метрополь'),
        (select id from hotel_room_amenities where title = 'Туалетные принадлежности')),
       ((select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Авторский Представительский'
           and h.title = 'Отель Метрополь'),
        (select id from hotel_room_amenities where title = 'Холодильник')),
       ((select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Авторский Представительский'
           and h.title = 'Отель Метрополь'),
        (select id from hotel_room_amenities where title = 'Красивый вид')),
       ((select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Авторский Представительский'
           and h.title = 'Отель Метрополь'),
        (select id from hotel_room_amenities where title = 'Кондиционер')),
       ((select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Авторский Представительский'
           and h.title = 'Отель Метрополь'),
        (select id from hotel_room_amenities where title = 'Халат')),
       ((select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Люкс Делюкс'
           and h.title = 'Отель Метрополь'),
        (select id from hotel_room_amenities where title = 'Телевизор')),
       ((select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Люкс Делюкс'
           and h.title = 'Отель Метрополь'),
        (select id from hotel_room_amenities where title = 'Чайник')),
       ((select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Люкс Делюкс'
           and h.title = 'Отель Метрополь'),
        (select id from hotel_room_amenities where title = 'Постельное бельё')),
       ((select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Люкс Делюкс'
           and h.title = 'Отель Метрополь'),
        (select id from hotel_room_amenities where title = 'Собственная ванная комната')),
       ((select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Люкс Делюкс'
           and h.title = 'Отель Метрополь'),
        (select id from hotel_room_amenities where title = 'Туалетные принадлежности')),
       ((select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Люкс Делюкс'
           and h.title = 'Отель Метрополь'),
        (select id from hotel_room_amenities where title = 'Халат')),
       ((select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Люкс Делюкс'
           and h.title = 'Отель Метрополь'),
        (select id from hotel_room_amenities where title = 'Холодильник')),
       ((select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Люкс Делюкс'
           and h.title = 'Отель Метрополь'),
        (select id from hotel_room_amenities where title = 'Кондиционер')),
       ((select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Люкс Метрополь'
           and h.title = 'Отель Метрополь'),
        (select id from hotel_room_amenities where title = 'Телевизор')),
       ((select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Люкс Метрополь'
           and h.title = 'Отель Метрополь'),
        (select id from hotel_room_amenities where title = 'Чайник')),
       ((select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Люкс Метрополь'
           and h.title = 'Отель Метрополь'),
        (select id from hotel_room_amenities where title = 'Постельное бельё')),
       ((select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Люкс Метрополь'
           and h.title = 'Отель Метрополь'),
        (select id from hotel_room_amenities where title = 'Собственная ванная комната')),
       ((select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Люкс Метрополь'
           and h.title = 'Отель Метрополь'),
        (select id from hotel_room_amenities where title = 'Туалетные принадлежности')),
       ((select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Люкс Метрополь'
           and h.title = 'Отель Метрополь'),
        (select id from hotel_room_amenities where title = 'Холодильник')),
       ((select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Люкс Метрополь'
           and h.title = 'Отель Метрополь'),
        (select id from hotel_room_amenities where title = 'Кондиционер')),
       ((select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Люкс Метрополь'
           and h.title = 'Отель Метрополь'),
        (select id from hotel_room_amenities where title = 'Халат')),
       ((select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Люкс Метрополь'
           and h.title = 'Отель Метрополь'),
        (select id from hotel_room_amenities where title = 'Красивый вид'));;

insert into hotel_room_rate (title, hotel_rooms_id, feed_type_id, price, payment_type, is_refund)
values ('Супериор',
        (select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Двухместный номер Гранд Супериор Сингл'
           and h.title = 'Отель Метрополь'),
        (select feed.id from ct_hotel_feed_types feed where title = 'breakfast'),
        25000, 'card', true),
       ('Супериор Все включено',
        (select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Двухместный номер Гранд Супериор Сингл'
           and h.title = 'Отель Метрополь'),
        (select feed.id from ct_hotel_feed_types feed where title = 'all_inclusive'),
        30000, 'card', true),
       ('Супериор',
        (select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Двухместный номер Гранд Супериор'
           and h.title = 'Отель Метрополь'),
        (select feed.id from ct_hotel_feed_types feed where title = 'breakfast'),
        25000, 'card', true),
       ('Супериор Все включено',
        (select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Двухместный номер Гранд Супериор'
           and h.title = 'Отель Метрополь'),
        (select feed.id from ct_hotel_feed_types feed where title = 'all_inclusive'),
        35000, 'card', true),
       ('Авторский',
        (select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Авторский Представительский'
           and h.title = 'Отель Метрополь'),
        (select feed.id from ct_hotel_feed_types feed where title = 'breakfast'),
        80000, 'card', true),
       ('Авторский Все включено',
        (select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Авторский Представительский'
           and h.title = 'Отель Метрополь'),
        (select feed.id from ct_hotel_feed_types feed where title = 'all_inclusive'),
        115000, 'card', true),
       ('Люкс',
        (select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Люкс Делюкс'
           and h.title = 'Отель Метрополь'),
        (select feed.id from ct_hotel_feed_types feed where title = 'breakfast'),
        70000, 'card', true),
       ('Люкс Все включено',
        (select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Люкс Делюкс'
           and h.title = 'Отель Метрополь'),
        (select feed.id from ct_hotel_feed_types feed where title = 'all_inclusive'),
        80000, 'card', true),
       ('Люкс',
        (select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Люкс Метрополь'
           and h.title = 'Отель Метрополь'),
        (select feed.id from ct_hotel_feed_types feed where title = 'breakfast'),
        115000, 'card', true),
       ('Люкс Все включено',
        (select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Люкс Метрополь'
           and h.title = 'Отель Метрополь'),
        (select feed.id from ct_hotel_feed_types feed where title = 'all_inclusive'),
        115000, 'card', true);