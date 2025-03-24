package ru.otus.java.pro.result.project.hotels.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.java.pro.result.project.hotels.dtos.UserOrderCreateDtoRq;
import ru.otus.java.pro.result.project.hotels.entities.*;
import ru.otus.java.pro.result.project.hotels.exceptions.BusinessLogicException;
import ru.otus.java.pro.result.project.hotels.exceptions.ResourceNotFoundException;
import ru.otus.java.pro.result.project.hotels.lib.UserOrderStatus;
import ru.otus.java.pro.result.project.hotels.repositories.UserOrdersRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static java.time.temporal.ChronoUnit.DAYS;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserOrderServiceImpl implements UserOrderService {
    private final UserOrdersRepository userOrderRepository;
    private final HotelsService hotelsService;
    private final UserProfileService userProfileService;

    @Override
    public UserOrder findUserOrder(String order) {
        try {
            String userProfileId = order.split("-")[0];
            int userOrderId = Integer.parseInt(order.split("-")[1]);
            return userOrderRepository.findUserOrder(userOrderId, userProfileId).orElseThrow(() -> new ResourceNotFoundException("Order not exist"));
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            throw new ResourceNotFoundException("Order is invalid");
        }
    }

    @Override
    public List<UserOrder> findUserOrders(String userId) {
        return userOrderRepository.findUserOrderByUserProfile_Id(userId);
    }

    @Transactional
    @Override
    public UserOrder createUserOrder(UserOrderCreateDtoRq orderDtoRq) {
        log.debug("Request to create user order, {}", orderDtoRq);

        UserProfile user = userProfileService.findUserProfile(orderDtoRq.getUserId());
        Hotel hotel = hotelsService.findHotel(orderDtoRq.getHotelId());
        //TODO доработать пересчет свободных номеров после бронирования по датам
        HotelRoom hotelRoom = hotelsService.findHotelRoom(orderDtoRq.getRoomId(), hotel.getId());
        if (hotelRoom.getMaxGuests() < (orderDtoRq.getGuests() + Optional.ofNullable(orderDtoRq.getChildren()).orElse(0))) {
            throw new BusinessLogicException("GUESTS_EXCESS", "too many guests, room not suitable");
        }
        HotelRoomRate rate = hotelRoom.getHotelRoomRates().stream().filter(r -> r.getId().equals(orderDtoRq.getRateId())).findFirst().orElseThrow(() -> new ResourceNotFoundException("Room rate not exist"));
        UserOrder userOrder = new UserOrder();
        userOrder.setUserOrderId(new UserOrderKey(userOrderRepository.getNextValueUserOrderId(user.getId()), user.getId()));
        userOrder.setUserProfile(user);
        userOrder.setDateIn(orderDtoRq.getDateIn());
        userOrder.setDateOut(orderDtoRq.getDateOut());
        userOrder.setHotelRoom(hotelRoom);
        userOrder.setHotelRoomRate(rate);
        userOrder.setOrderPrice(rate.getPrice().multiply(new BigDecimal(DAYS.between(orderDtoRq.getDateIn(), orderDtoRq.getDateOut()))));
        userOrder.setStatus(UserOrderStatus.CREATED);
        userOrder.setIsRefund(rate.getIsRefund());
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
            if (userOrder.getIsRefund()) {
                userOrder.setStatus(UserOrderStatus.CANCELED);
                userOrder = userOrderRepository.save(userOrder);
                log.debug("Order canceled {}", order);
                return userOrder;
            } else {
                throw new BusinessLogicException("ORDER_NOT_REFUNDED", "order not refunded");
            }
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            throw new ResourceNotFoundException("Order is invalid");
        }
    }
}
