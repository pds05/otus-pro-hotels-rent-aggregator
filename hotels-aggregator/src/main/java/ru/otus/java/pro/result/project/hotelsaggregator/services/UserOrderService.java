package ru.otus.java.pro.result.project.hotelsaggregator.services;

import ru.otus.java.pro.result.project.hotelsaggregator.dtos.UserOrderCreateDtoRq;
import ru.otus.java.pro.result.project.hotelsaggregator.entities.UserOrder;

import java.util.List;

public interface UserOrderService {

    UserOrder findUserOrder(String order);

    List<UserOrder> findUserOrders(String userId);

    UserOrder createUserOrder(UserOrderCreateDtoRq orderDtoRq);

    UserOrder canceledUserOrder(String order);
}
