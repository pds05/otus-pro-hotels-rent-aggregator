package ru.otus.java.pro.result.project.hotels.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.java.pro.result.project.hotels.dtos.UserOrderCreateDtoRq;
import ru.otus.java.pro.result.project.hotels.entities.*;
import ru.otus.java.pro.result.project.hotels.exceptions.ResourceNotFoundException;
import ru.otus.java.pro.result.project.hotels.lib.UserOrderStatus;
import ru.otus.java.pro.result.project.hotels.repositories.UserOrdersRepository;
import ru.otus.java.pro.result.project.hotels.validators.UserOrderValidator;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserOrderServiceImpl implements UserOrderService {
    private final UserOrdersRepository userOrderRepository;
    private final HotelsService hotelsService;
    private final UserProfileService userProfileService;
    private final UserOrderValidator requestValidator;

    @Override
    public Optional<UserOrder> findUserOrder(int orderId, String userId) {
        return userOrderRepository.findUserOrder(orderId, userId);
    }

    @Override
    public List<UserOrder> findUserOrders(String userId) {
        return userOrderRepository.findUserOrderByUserProfile_Id(userId);
    }

    @Transactional
    @Override
    public UserOrder createUserOrder(UserOrderCreateDtoRq orderDtoRq) {
        log.debug("Request to create user order, {}", orderDtoRq);
        requestValidator.validate(orderDtoRq);

        UserProfile user = userProfileService.findUserProfile(orderDtoRq.getUserId()).orElseThrow(() -> new ResourceNotFoundException("User not exist"));
        Hotel hotel = hotelsService.findHotel(orderDtoRq.getHotelId()).orElseThrow(() -> new ResourceNotFoundException("Hotel not exist"));
        HotelRoom hotelRoom = hotelsService.findHotelRoom(orderDtoRq.getRoomId(), hotel.getId()).orElseThrow(() -> new ResourceNotFoundException("Room not exist"));
        HotelRoomRate rate = hotelRoom.getHotelRoomRates().stream().filter(r -> r.getId().equals(orderDtoRq.getRateId())).findFirst().orElseThrow(() -> new ResourceNotFoundException("Room rate not exist"));

        UserOrder userOrder = new UserOrder();
        userOrder.setUserOrderId(new UserOrderKey(userOrderRepository.getNextValueUserOrderId(user.getId()), user.getId()));
        userOrder.setUserProfile(user);
        userOrder.setDateIn(orderDtoRq.getDateIn());
        userOrder.setDateOut(orderDtoRq.getDateOut());
        userOrder.setHotelRoom(hotelRoom);
        userOrder.setHotelRoomRate(rate);
        userOrder.setOrderPrice(rate.getPrice());
        userOrder.setStatus(UserOrderStatus.CREATED);
        userOrder = userOrderRepository.save(userOrder);
        log.debug("Request completed, user order created {}", userOrder);
        return userOrder;
    }

    @Transactional
    @Override
    public UserOrder canceledUserOrder(String order) {
        log.debug("Request to cancel an order {}", order);
        try {
            String userProfileId = order.split("-")[0];
            int userOrderId = Integer.parseInt(order.split("-")[1]);
            UserOrder userOrder = userOrderRepository.findUserOrder(userOrderId, userProfileId).orElseThrow(() -> new ResourceNotFoundException("Order not exist"));
            userOrder.setStatus(UserOrderStatus.CANCELED);
            userOrder = userOrderRepository.save(userOrder);
            log.debug("Order canceled {}", order);
            return userOrder;
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            throw new ResourceNotFoundException("Order is invalid");
        }
    }
}
