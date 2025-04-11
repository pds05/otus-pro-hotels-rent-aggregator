insert into providers (title, property_name, api_url, api_login, api_password, read_timeout, connect_timeout)
values ('Nif-Nif hotels', 'nifNif', 'http://hotels8090:8090/api/v1/', 'aggregator', 'password', 10, 5),
       ('Naf-Naf hotels', 'nafNaf', 'http://hotels8091:8091/api/v1/', 'aggregator', 'password', 10, 5),
       ('Nuf-Nuf hotels', 'nufNuf', 'http://hotels8092:8092/api/v1/', 'aggregator', 'password', 10, 5);

insert into provider_apis(path, rest_method, business_method, description, provider_id)
values ('hotels', 'GET', 'FIND_ALL_HOTELS_IN_CITY', 'Поиск всех предложений жилья в населенном пункте',
        (select id from providers where title = 'Nif-Nif hotels')),
       ('hotels/filter', 'GET', 'FIND_HOTELS_WITH_FILTER', 'Поиск жилья с фильтрами параметров жилья',
        (select id from providers where title = 'Nif-Nif hotels')),
       ('hotels/{%d}', 'GET', 'FIND_HOTEL_BY_ID', 'Поиск отеля по его идентификатору',
        (select id from providers where title = 'Nif-Nif hotels')),
       ('hotels/reserve', 'POST', 'CREATE_ORDER', 'Бронирование жилья',
        (select id from providers where title = 'Nif-Nif hotels')),
       ('user/{%d}', 'GET', 'FIND_USER_BY_ID', 'Запрос данных пользователя',
        (select id from providers where title = 'Nif-Nif hotels')),
       ('user', 'POST', 'REGISTER_USER', 'Регистрация нового пользователя',
        (select id from providers where title = 'Nif-Nif hotels')),
       ('user/order', 'GET', 'FIND_USER_ORDERS', 'Запрос всех бронирований пользователя',
        (select id from providers where title = 'Nif-Nif hotels')),
       ('user/order/{%d}', 'GET', 'FIND_USER_ORDER_BY_ID', 'Запрос бронирования пользователя',
        (select id from providers where title = 'Nif-Nif hotels')),
       ('user/order/cancel', 'GET', 'CANCEL_ORDER', 'Отмена бронирования пользователя',
        (select id from providers where title = 'Nif-Nif hotels')),

       ('hotels', 'GET', 'FIND_ALL_HOTELS_IN_CITY', 'Поиск всех предложений жилья в населенном пункте',
        (select id from providers where title = 'Naf-Naf hotels')),
       ('hotels/filter', 'GET', 'FIND_HOTELS_WITH_FILTER', 'Поиск жилья с фильтрами параметров жилья',
        (select id from providers where title = 'Naf-Naf hotels')),
       ('hotels/{%d}', 'GET', 'FIND_HOTEL_BY_ID', 'Поиск отеля по его идентификатору',
        (select id from providers where title = 'Naf-Naf hotels')),
       ('hotels/reserve', 'POST', 'CREATE_ORDER', 'Бронирование жилья',
        (select id from providers where title = 'Naf-Naf hotels')),
       ('user/{%d}', 'GET', 'FIND_USER_BY_ID', 'Запрос данных пользователя',
        (select id from providers where title = 'Naf-Naf hotels')),
       ('user', 'POST', 'REGISTER_USER', 'Регистрация нового пользователя',
        (select id from providers where title = 'Naf-Naf hotels')),
       ('user/order', 'GET', 'FIND_USER_ORDERS', 'Запрос всех бронирований пользователя',
        (select id from providers where title = 'Naf-Naf hotels')),
       ('user/order/{%d}', 'GET', 'FIND_USER_ORDER_BY_ID', 'Запрос бронирования пользователя',
        (select id from providers where title = 'Naf-Naf hotels')),
       ('user/order/cancel', 'GET', 'CANCEL_ORDER', 'Отмена бронирования пользователя',
        (select id from providers where title = 'Naf-Naf hotels')),

       ('hotels', 'GET', 'FIND_ALL_HOTELS_IN_CITY', 'Поиск всех предложений жилья в населенном пункте',
        (select id from providers where title = 'Nuf-Nuf hotels')),
       ('hotels/filter', 'GET', 'FIND_HOTELS_WITH_FILTER', 'Поиск жилья с фильтрами параметров жилья',
        (select id from providers where title = 'Nuf-Nuf hotels')),
       ('hotels/{%d}', 'GET', 'FIND_HOTEL_BY_ID', 'Поиск отеля по его идентификатору',
        (select id from providers where title = 'Nuf-Nuf hotels')),
       ('hotels/reserve', 'POST', 'CREATE_ORDER', 'Бронирование жилья',
        (select id from providers where title = 'Nuf-Nuf hotels')),
       ('user/{%d}', 'GET', 'FIND_USER_BY_ID', 'Запрос данных пользователя',
        (select id from providers where title = 'Nuf-Nuf hotels')),
       ('user', 'POST', 'REGISTER_USER', 'Регистрация нового пользователя',
        (select id from providers where title = 'Nuf-Nuf hotels')),
       ('user/order', 'GET', 'FIND_USER_ORDERS', 'Запрос всех бронирований пользователя',
        (select id from providers where title = 'Nuf-Nuf hotels')),
       ('user/order/{%d}', 'GET', 'FIND_USER_ORDER_BY_ID', 'Запрос бронирования пользователя',
        (select id from providers where title = 'Nuf-Nuf hotels')),
       ('user/order/cancel', 'GET', 'CANCEL_ORDER', 'Отмена бронирования пользователя',
        (select id from providers where title = 'Nuf-Nuf hotels'));