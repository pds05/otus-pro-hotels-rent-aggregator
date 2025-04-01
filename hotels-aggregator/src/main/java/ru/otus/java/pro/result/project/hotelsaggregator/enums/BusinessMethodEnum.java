package ru.otus.java.pro.result.project.hotelsaggregator.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BusinessMethodEnum {
    FIND_ALL_HOTELS_IN_CITY("Поиск всех предложений жилья в населенном пункте"),
    FIND_HOTELS_WITH_FILTER("Поиск жилья с фильтрами параметров жилья"),
    FIND_HOTEL_BY_ID("Поиск отеля по его идентификатору"),
    MAKE_ORDER("Бронирование жилья"),
    REGISTER_USER("Регистрация нового пользователя"),
    FIND_USER_BY_ID("Запрос данных пользователя"),
    FIND_USER_ORDERS("Запрос всех бронирований пользователя"),
    FIND_USER_ORDER_BY_ID("Запрос бронирования пользователя"),
    CANCEL_ORDER("Отмена бронирования пользователя");

    private final String description;

}
