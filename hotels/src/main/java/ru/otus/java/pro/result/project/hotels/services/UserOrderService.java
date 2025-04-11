package ru.otus.java.pro.result.project.hotels.services;

import ru.otus.java.pro.result.project.hotels.dtos.UserOrderCreateDtoRq;
import ru.otus.java.pro.result.project.hotels.entities.UserOrder;

import java.util.List;

public interface UserOrderService {

    UserOrder getUserOrder(String order);

    List<UserOrder> getUserOrders(String userId);

    UserOrder createUserOrder(UserOrderCreateDtoRq orderDtoRq);

    UserOrder cancelUserOrder(String order);

}
