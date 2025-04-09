insert into hotels (hotel_type_id, title, city_id, location, phone_number, email, rooms_value,
                    user_rating, location_desc, hotel_desc, rooms_desc, guest_info, time_check_in, time_check_out,
                    is_active)
values ((select ct_hotel_types.id from ct_hotel_types where ct_hotel_types.title = 'apartments'),
        'Апартаменты на Толстого',
        (select ct_cities.id from ct_cities where ct_cities.title = 'Казань'),
        'улица Толстого, д.41',
        '78431112237',
        'tolstogo_appartaments@mail.ru',
        1,
        8.2,
        'Залог хорошего отдыха — чувствовать себя в новом месте как дома: апартаменты «Апартаменты на Толстого» находятся в Казани. Эти апартаменты располагаются 2 км от центра города. Рядом с апартаментами — Парк Горького, Татарский академический государственный театр оперы и балета и Парк Черное озеро.',
        '«Хотите оставаться на связи? В апартаментах есть бесплатный Wi-Fi. Для путешественников на машине организована бесплатная парковка. А ещё в распоряжении гостей гладильные услуги.',
        'Номер уютно обставлен и оснащён необходимым, чтобы отдохнуть после долгого и насыщенного дня. Имеются телевизор. Оснащение зависит от выбранной категории номера.',
        'Российским гражданам при заезде обязательно нужно иметь оригинал действующего паспорта РФ.',
        '15:00',
        '11:00',
        true);

insert into hotels_hotel_amenities (hotel_id, hotel_amenity_id, is_special, is_additional_cost, description)
VALUES ((select hotels.id from hotels where title = 'Апартаменты на Толстого'),
        (select hotel_amenities.id from hotel_amenities where hotel_amenities.title = 'Кондиционер'), true, false, ''),
       ((select hotels.id from hotels where title = 'Апартаменты на Толстого'),
        (select hotel_amenities.id from hotel_amenities where hotel_amenities.title = 'Лифт'), false, false, ''),
       ((select hotels.id from hotels where title = 'Апартаменты на Толстого'),
        (select hotel_amenities.id from hotel_amenities where hotel_amenities.title = 'Телевизор'), true, false, ''),
       ((select hotels.id from hotels where title = 'Апартаменты на Толстого'),
        (select hotel_amenities.id from hotel_amenities where hotel_amenities.title = 'Душ'), false, true, ''),
       ((select hotels.id from hotels where title = 'Апартаменты на Толстого'),
        (select hotel_amenities.id from hotel_amenities where hotel_amenities.title = 'Фен'), false, false, ''),
       ((select hotels.id from hotels where title = 'Апартаменты на Толстого'),
        (select hotel_amenities.id from hotel_amenities where hotel_amenities.title = 'Доступ в интернет'), true, false,
        ''),
       ((select hotels.id from hotels where title = 'Апартаменты на Толстого'),
        (select hotel_amenities.id from hotel_amenities where hotel_amenities.title = 'Утюг'), false, true, ''),
       ((select hotels.id from hotels where title = 'Апартаменты на Толстого'),
        (select hotel_amenities.id from hotel_amenities where hotel_amenities.title = 'Общественная парковка'), false,
        true, 'Во дворе дома');

insert into hotel_rooms(hotel_id, title, description, size, inside_rooms_number, max_guests)
values ((select hotels.id from hotels where title = 'Апартаменты на Толстого'),
        'Двухкомнатная квартира', 'двуспальная кровать и две отдельные кровати', 62, 2, 4);

insert into hotel_room_beds (hotel_room_id, bed_type_id, amount)
values ((select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Двухкомнатная квартира'
           and h.title = 'Апартаменты на Толстого'),
        (select ct_hotel_bed_types.id from ct_hotel_bed_types where title = 'single'), 2),
       ((select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Двухкомнатная квартира'
           and h.title = 'Апартаменты на Толстого'),
        (select ct_hotel_bed_types.id from ct_hotel_bed_types where title = 'queen'), 1);

insert into hotel_rooms_hotel_room_amenities_rel
values ((select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Двухкомнатная квартира'
           and h.title = 'Апартаменты на Толстого'),
        (select id from hotel_room_amenities where title = 'Телевизор')),
       ((select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Двухкомнатная квартира'
           and h.title = 'Апартаменты на Толстого'),
        (select id from hotel_room_amenities where title = 'Чайник')),
       ((select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Двухкомнатная квартира'
           and h.title = 'Апартаменты на Толстого'),
        (select id from hotel_room_amenities where title = 'Постельное бельё')),
       ((select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Двухкомнатная квартира'
           and h.title = 'Апартаменты на Толстого'),
        (select id from hotel_room_amenities where title = 'Душ')),
       ((select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Двухкомнатная квартира'
           and h.title = 'Апартаменты на Толстого'),
        (select id from hotel_room_amenities where title = 'Холодильник')),
       ((select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Двухкомнатная квартира'
           and h.title = 'Апартаменты на Толстого'),
        (select id from hotel_room_amenities where title = 'Кондиционер'));

insert into hotel_room_rate (title, hotel_rooms_id, feed_type_id, price, payment_type, is_refund)
values ('Невозвратный',
        (select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Двухкомнатная квартира'
           and h.title = 'Апартаменты на Толстого'),
        (select feed.id from ct_hotel_feed_types feed where title = 'none'),
        8000, 'card', false),
       ('Возвратный',
        (select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Двухкомнатная квартира'
           and h.title = 'Апартаменты на Толстого'),
        (select feed.id from ct_hotel_feed_types feed where title = 'none'),
        8500, 'card', true);