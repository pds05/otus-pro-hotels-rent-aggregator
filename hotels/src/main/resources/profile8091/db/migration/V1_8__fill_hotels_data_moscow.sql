insert into hotels (hotel_type_id, title, city_id, location, phone_number, email, rooms_value,
                    user_rating, location_desc, hotel_desc, rooms_desc, guest_info, time_check_in, time_check_out,
                    is_active)
values ((select ct_hotel_types.id from ct_hotel_types where ct_hotel_types.title = 'apartments'),
        'Квартира на Волгоградском проспекте',
        (select ct_cities.id from ct_cities where ct_cities.title = 'Москва'),
        'Волгоградский проспект, д. 48Б',
        '74951112237',
        'volgogradskaya_kvartira@mail.ru',
        1,
        8.2,
        'Выбор туристов, которые хотят везде чувствовать себя как дома — апартаменты «Апартаменты В Небоскребе с видом на Останкинскую башню» находятся в Москве. Эти апартаменты располагаются в 6 км от центра города. Рядом с апартаментами можно прогуляться. Неподалёку: Дизайн-завод «Флакон», Хлебозавод 9 и Дмитровская.',
        '«Хотите оставаться на связи? В апартаментах есть бесплатный Wi-Fi. Для путешественников на машине организована платная парковка. Доступная среда: работает лифт. Гостям доступны и другие услуги. Например, индивидуальная регистрация заезда и отъезда и гладильные услуги.',
        'В номере вас будут ждать телевизор. Оснащение зависит от выбранной категории номера.',
        'Российским гражданам при заезде обязательно нужно иметь оригинал действующего паспорта РФ.',
        '14:00',
        '12:00',
        true);

insert into hotels_hotel_amenities (hotel_id, hotel_amenity_id, is_special, is_additional_cost, description)
VALUES ((select hotels.id from hotels where title = 'Квартира на Волгоградском проспекте'),
        (select hotel_amenities.id from hotel_amenities where hotel_amenities.title = 'Кондиционер'), true, false, ''),
       ((select hotels.id from hotels where title = 'Квартира на Волгоградском проспекте'),
        (select hotel_amenities.id from hotel_amenities where hotel_amenities.title = 'Лифт'), false, false, ''),
       ((select hotels.id from hotels where title = 'Квартира на Волгоградском проспекте'),
        (select hotel_amenities.id from hotel_amenities where hotel_amenities.title = 'Телевизор'), true, false, ''),
       ((select hotels.id from hotels where title = 'Квартира на Волгоградском проспекте'),
        (select hotel_amenities.id from hotel_amenities where hotel_amenities.title = 'Душ'), false, true, ''),
       ((select hotels.id from hotels where title = 'Квартира на Волгоградском проспекте'),
        (select hotel_amenities.id from hotel_amenities where hotel_amenities.title = 'Фен'), false, false, ''),
       ((select hotels.id from hotels where title = 'Квартира на Волгоградском проспекте'),
        (select hotel_amenities.id from hotel_amenities where hotel_amenities.title = 'Доступ в интернет'), true, false,
        ''),
       ((select hotels.id from hotels where title = 'Квартира на Волгоградском проспекте'),
        (select hotel_amenities.id from hotel_amenities where hotel_amenities.title = 'Утюг'), false, true, ''),
       ((select hotels.id from hotels where title = 'Квартира на Волгоградском проспекте'),
        (select hotel_amenities.id from hotel_amenities where hotel_amenities.title = 'Общественная парковка'), false,
        true, 'Во дворе дома');

insert into hotel_rooms(hotel_id, title, description, size, inside_rooms_number, max_guests)
values ((select hotels.id from hotels where title = 'Квартира на Волгоградском проспекте'),
        'Двухкомнатная квартира', 'двуспальная кровать и две отдельные кровати', 62, 2, 4);

insert into hotel_room_beds (hotel_room_id, bed_type_id, amount)
values ((select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Двухкомнатная квартира'
           and h.title = 'Квартира на Волгоградском проспекте'),
        (select ct_hotel_bed_types.id from ct_hotel_bed_types where title = 'single'), 2),
       ((select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Двухкомнатная квартира'
           and h.title = 'Квартира на Волгоградском проспекте'),
        (select ct_hotel_bed_types.id from ct_hotel_bed_types where title = 'queen'), 1);

insert into hotel_rooms_hotel_room_amenities_rel
values ((select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Двухкомнатная квартира'
           and h.title = 'Квартира на Волгоградском проспекте'),
        (select id from hotel_room_amenities where title = 'Телевизор')),
       ((select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Двухкомнатная квартира'
           and h.title = 'Квартира на Волгоградском проспекте'),
        (select id from hotel_room_amenities where title = 'Чайник')),
       ((select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Двухкомнатная квартира'
           and h.title = 'Квартира на Волгоградском проспекте'),
        (select id from hotel_room_amenities where title = 'Постельное бельё')),
       ((select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Двухкомнатная квартира'
           and h.title = 'Квартира на Волгоградском проспекте'),
        (select id from hotel_room_amenities where title = 'Душ')),
       ((select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Двухкомнатная квартира'
           and h.title = 'Квартира на Волгоградском проспекте'),
        (select id from hotel_room_amenities where title = 'Холодильник')),
       ((select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Двухкомнатная квартира'
           and h.title = 'Квартира на Волгоградском проспекте'),
        (select id from hotel_room_amenities where title = 'Кондиционер'));

insert into hotel_room_rate (title, hotel_rooms_id, feed_type_id, price, payment_type, is_refund)
values ('Невозвратный',
        (select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Двухкомнатная квартира'
           and h.title = 'Квартира на Волгоградском проспекте'),
        (select feed.id from ct_hotel_feed_types feed where title = 'none'),
        9000, 'card', false),
       ('Возвратный',
        (select hr.id
         from hotel_rooms hr
                  join hotels h ON hr.hotel_id = h.id
         where hr.title = 'Двухкомнатная квартира'
           and h.title = 'Квартира на Волгоградском проспекте'),
        (select feed.id from ct_hotel_feed_types feed where title = 'none'),
        9500, 'card', true);