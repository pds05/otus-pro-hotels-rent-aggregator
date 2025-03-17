insert into ct_cities (phone_prefix, title)
values (7495, 'Москва'),
       (7812, 'Санкт-Петербург'),
       (7843, 'Казань'),
       (7861, 'Краснодар'),
       (7401, 'Калининград'),
       (7862, 'Сочи'),
       (7485, 'Ярославль'),
       (7863, 'Ростов-на-Дону'),
       (7844, 'Волгоград'),
       (7383, 'Новосибирск');

insert into ct_hotel_bed_types (title, description)
values ('single', 'односпальная кровать'),
       ('separate', 'раздельные кровати'),
       ('queen', 'двуспальная кровать'),
       ('king', 'vip-двуспальная кровать'),
       ('sofa', 'дополнительное спальное место'),
       ('baby', 'детская кроватка');

insert into ct_hotel_feed_types (title, description)
values ('all inclusive', 'все включено'),
       ('breakfast', 'завтрак включен'),
       ('half board', 'завтрак, обед или ужин включен'),
       ('full board', 'завтрак, обед и ужин включены'),
       ('none', 'без питания');

insert into ct_hotel_types (title)
values ('Отель'),
       ('Хостел'),
       ('Апартаменты, квартира'),
       ('Гостевой дом'),
       ('Коттедж, вилла, бунгало'),
       ('Санаторий'),
       ('Кемпинг');

insert into hotel_amenity_groups(title, order_num)
values ('Общее', 1),
       ('В номерах', 2),
       ('Услуги и удобства', 3),
       ('Питание', 4),
       ('Интернет', 5),
       ('Трансфер', 6),
       ('Персонал говорит', 7),
       ('Туризм', 8),
       ('Развлечения', 9),
       ('Парковка', 10),
       ('Бассейн и пляж', 11),
       ('Спорт', 12),
       ('Красота и здоровье', 13),
       ('Дети', 14),
       ('Животные', 15);

insert into hotel_amenities(hotel_amenity_group_id, title)
VALUES ((select id from hotel_amenity_groups where hotel_amenity_groups.title = 'Общее'), 'Компьютер'),
       ((select id from hotel_amenity_groups where hotel_amenity_groups.title = 'Общее'), 'Кондиционер'),
       ((select id from hotel_amenity_groups where hotel_amenity_groups.title = 'Общее'),'Круглосуточная стойка регистрации'),
       ((select id from hotel_amenity_groups where hotel_amenity_groups.title = 'Общее'), 'Лифт'),
       ((select id from hotel_amenity_groups where hotel_amenity_groups.title = 'Общее'), 'Места для курения'),
       ((select id from hotel_amenity_groups where hotel_amenity_groups.title = 'Общее'), 'Обмен валюты'),
       ((select id from hotel_amenity_groups where hotel_amenity_groups.title = 'Общее'), 'Отель для некурящих'),
       ((select id from hotel_amenity_groups where hotel_amenity_groups.title = 'Общее'), 'Отопление'),
       ((select id from hotel_amenity_groups where hotel_amenity_groups.title = 'Общее'), 'Сад'),
       ((select id from hotel_amenity_groups where hotel_amenity_groups.title = 'Общее'), 'Банкомат'),
       ((select id from hotel_amenity_groups where hotel_amenity_groups.title = 'Общее'), 'Ранняя регистрация заезда'),
       ((select id from hotel_amenity_groups where hotel_amenity_groups.title = 'Общее'), 'Поздняя регистрация выезда'),
       ((select id from hotel_amenity_groups where hotel_amenity_groups.title = 'В номерах'), 'Обслуживание номеров'),
       ((select id from hotel_amenity_groups where hotel_amenity_groups.title = 'В номерах'), 'Фен'),
       ((select id from hotel_amenity_groups where hotel_amenity_groups.title = 'В номерах'), 'Душ'),
       ((select id from hotel_amenity_groups where hotel_amenity_groups.title = 'В номерах'), 'Халат'),
       ((select id from hotel_amenity_groups where hotel_amenity_groups.title = 'В номерах'), 'Тапочки'),
       ((select id from hotel_amenity_groups where hotel_amenity_groups.title = 'В номерах'), 'Семейные номера'),
       ((select id from hotel_amenity_groups where hotel_amenity_groups.title = 'В номерах'), 'Телевизор'),
       ((select id from hotel_amenity_groups where hotel_amenity_groups.title = 'В номерах'), 'Сейф (в номере)'),
       ((select id from hotel_amenity_groups where hotel_amenity_groups.title = 'В номерах'),'Туалетные принадлежности'),
       ((select id from hotel_amenity_groups where hotel_amenity_groups.title = 'Услуги и удобства'),'Гладильные услуги'),
       ((select id from hotel_amenity_groups where hotel_amenity_groups.title = 'Услуги и удобства'), 'Прачечная'),
       ((select id from hotel_amenity_groups where hotel_amenity_groups.title = 'Услуги и удобства'), 'Химчистка'),
       ((select id from hotel_amenity_groups where hotel_amenity_groups.title = 'Услуги и удобства'),'Хранение багажа'),
       ((select id from hotel_amenity_groups where hotel_amenity_groups.title = 'Услуги и удобства'), 'Телефон'),
       ((select id from hotel_amenity_groups where hotel_amenity_groups.title = 'Услуги и удобства'),'Фен по запросу'),
       ((select id from hotel_amenity_groups where hotel_amenity_groups.title = 'Услуги и удобства'), 'Утюг'),
       ((select id from hotel_amenity_groups where hotel_amenity_groups.title = 'Питание'), 'Бар'),
       ((select id from hotel_amenity_groups where hotel_amenity_groups.title = 'Питание'), 'Завтрак'),
       ((select id from hotel_amenity_groups where hotel_amenity_groups.title = 'Питание'), 'Завтрак в номер'),
       ((select id from hotel_amenity_groups where hotel_amenity_groups.title = 'Питание'), 'Ресторан'),
       ((select id from hotel_amenity_groups where hotel_amenity_groups.title = 'Питание'),'Шведский стол)'),
       ((select id from hotel_amenity_groups where hotel_amenity_groups.title = 'Питание'), 'Упакованные ланчи'),
       ((select id from hotel_amenity_groups where hotel_amenity_groups.title = 'Интернет'), 'Бесплатный Wi-Fi'),
       ((select id from hotel_amenity_groups where hotel_amenity_groups.title = 'Интернет'), 'Доступ в интернет'),
       ((select id from hotel_amenity_groups where hotel_amenity_groups.title = 'Трансфер'), 'Трансфер'),
       ((select id from hotel_amenity_groups where hotel_amenity_groups.title = 'Персонал говорит'), 'Русский'),
       ((select id from hotel_amenity_groups where hotel_amenity_groups.title = 'Персонал говорит'), 'Английский'),
       ((select id from hotel_amenity_groups where hotel_amenity_groups.title = 'Туризм'), 'Экскурсионное бюро'),
       ((select id from hotel_amenity_groups where hotel_amenity_groups.title = 'Развлечения'), 'Библиотека'),
       ((select id from hotel_amenity_groups where hotel_amenity_groups.title = 'Развлечения'), 'Боулинг'),
       ((select id from hotel_amenity_groups where hotel_amenity_groups.title = 'Развлечения'), 'Ночной клуб'),
       ((select id from hotel_amenity_groups where hotel_amenity_groups.title = 'Парковка'), 'Частная парковка'),
       ((select id from hotel_amenity_groups where hotel_amenity_groups.title = 'Парковка'), 'Общественная парковка'),
       ((select id from hotel_amenity_groups where hotel_amenity_groups.title = 'Бассейн и пляж'), 'Крытый бассейн'),
       ((select id from hotel_amenity_groups where hotel_amenity_groups.title = 'Бассейн и пляж'), 'Открытый бассейн'),
       ((select id from hotel_amenity_groups where hotel_amenity_groups.title = 'Бассейн и пляж'), 'Частный пляж'),
       ((select id from hotel_amenity_groups where hotel_amenity_groups.title = 'Спорт'), 'Теннисный корт'),
       ((select id from hotel_amenity_groups where hotel_amenity_groups.title = 'Спорт'), 'Фитнес-центр'),
       ((select id from hotel_amenity_groups where hotel_amenity_groups.title = 'Спорт'), 'Тренажерный зал'),
       ((select id from hotel_amenity_groups where hotel_amenity_groups.title = 'Красота и здоровье'), 'Баня'),
       ((select id from hotel_amenity_groups where hotel_amenity_groups.title = 'Красота и здоровье'), 'Массаж'),
       ((select id from hotel_amenity_groups where hotel_amenity_groups.title = 'Красота и здоровье'), 'Салон красоты'),
       ((select id from hotel_amenity_groups where hotel_amenity_groups.title = 'Красота и здоровье'), 'Сауна'),
       ((select id from hotel_amenity_groups where hotel_amenity_groups.title = 'Красота и здоровье'), 'Спа'),
       ((select id from hotel_amenity_groups where hotel_amenity_groups.title = 'Дети'), 'Игровая комната'),
       ((select id from hotel_amenity_groups where hotel_amenity_groups.title = 'Дети'), 'Услуги няни и уход за детьм'),
       ((select id from hotel_amenity_groups where hotel_amenity_groups.title = 'Животные'),'Домашние животные не допускается'),
       ((select id from hotel_amenity_groups where hotel_amenity_groups.title = 'Животные'),'Можно с домашними животными');

insert into hotel_room_amenities(title)
values ('Собственная ванная комната'),
       ('Красивый вид'),
       ('Кондиционер'),
       ('Телевизор'),
       ('Сейф'),
       ('Полотенца'),
       ('Душ'),
       ('Туалетные принадлежности'),
       ('Постельное бельё'),
       ('Халат'),
       ('Чайник'),
       ('Холодильник')

























































