package ru.otus.java.pro.result.project.hotels.services;

import ru.otus.java.pro.result.project.hotels.dtos.UserOrderCreateDtoRq;
import ru.otus.java.pro.result.project.hotels.entities.UserOrder;

import java.util.List;
import java.util.Optional;

public interface UserOrderService {

    Optional<UserOrder> findUserOrder(int orderId, String userId);

    List<UserOrder> findUserOrders(String userId);

    UserOrder createUserOrder(UserOrderCreateDtoRq orderDtoRq);

    UserOrder canceledUserOrder(String order);

}
