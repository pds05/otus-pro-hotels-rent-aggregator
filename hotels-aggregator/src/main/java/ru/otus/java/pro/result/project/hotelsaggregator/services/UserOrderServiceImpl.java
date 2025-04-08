package ru.otus.java.pro.result.project.hotelsaggregator.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.otus.java.pro.result.project.hotelsaggregator.dtos.UserOrderCreateDtoRq;
import ru.otus.java.pro.result.project.hotelsaggregator.dtos.messages.*;
import ru.otus.java.pro.result.project.hotelsaggregator.entities.*;
import ru.otus.java.pro.result.project.hotelsaggregator.enums.BusinessMethodEnum;
import ru.otus.java.pro.result.project.hotelsaggregator.enums.UserOrderStatus;
import ru.otus.java.pro.result.project.hotelsaggregator.exceptions.BusinessLogicException;
import ru.otus.java.pro.result.project.hotelsaggregator.exceptions.ResourceNotFoundException;
import ru.otus.java.pro.result.project.hotelsaggregator.messaging.MessageService;
import ru.otus.java.pro.result.project.hotelsaggregator.repositories.UserOrderRepository;
import ru.otus.java.pro.result.project.hotelsaggregator.security.PasswordProtector;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserOrderServiceImpl implements UserOrderService {
    private final UserOrderRepository userOrderRepository;
    private final UserProfileService userProfileService;
    private final ProviderUserProfileService providerUserProfileService;
    private final ProviderServiceImpl providerService;
    private final MessageService messageService;
    private final PasswordProtector passwordSecurity;
    private final ModelMapper modelMapper;

    @Override
    public UserOrder getUserOrder(String order) {
        try {
            String userProfileId = order.split("-")[0];
            int userOrderId = Integer.parseInt(order.split("-")[1]);
            UserProfile user = userProfileService.getUserProfile(userProfileId);
            return userOrderRepository.findUserOrder(userOrderId, user.getId()).orElseThrow(() -> new ResourceNotFoundException("Order not exist"));
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            throw new ResourceNotFoundException("Order is invalid");
        }
    }

    @Override
    public List<UserOrder> getUserOrders(String userId) {
        UserProfile user = userProfileService.getUserProfile(userId);
        return userOrderRepository.findUserOrderByUserProfile_Id(user.getId());
    }

    @Transactional
    @Override
    public UserOrder createUserOrder(UserOrderCreateDtoRq orderDtoRq) {
        log.info("Creating new order, {}", orderDtoRq);

        UserProfile user = userProfileService.getUserProfile(orderDtoRq.getUserId());
        Provider provider = providerService.getProviderById(orderDtoRq.getServiceId());
        //проверяем профиль клиента, связанного с сервисом, если профиля нет, то регистрируем новый через процессор сообщений
        Optional<ProviderUserProfile> providerUserOptional = providerUserProfileService.getProviderUserProfile(user.getId());
        ProviderUserProfile providerUser = providerUserOptional.orElseGet(() -> providerUserProfileService.createProviderUserProfile(user, provider));
        //отправляем в процессор сообщений запрос на бронирование для внешнего сервиса
        UserOrderCreateDtoRqMsg providerRq = modelMapper.map(orderDtoRq, UserOrderCreateDtoRqMsg.class);
        providerRq.setUserId(providerUser.getProvidersUserId());
        ProviderResponseDto<UserOrderDtoMsg> providerResponse = messageService.sendMessage(provider, providerRq, BusinessMethodEnum.CREATE_ORDER);
        UserOrderDtoMsg providerOrder = providerResponse.getData();

        UserOrder userOrder = new UserOrder();
        userOrder.setUserOrderId(new UserOrderKey(userOrderRepository.getNextValueUserOrderId(user.getId()), user.getId()));
        userOrder.setUserProfile(user);
        userOrder.setProviderUserProfile(providerUser);
        userOrder.setDateIn(orderDtoRq.getDateIn());
        userOrder.setDateOut(orderDtoRq.getDateOut());
        userOrder.setHotel(providerOrder.getHotel());
        userOrder.setLocation(providerOrder.getAddress());
        userOrder.setRoomName(providerOrder.getRoom());
        userOrder.setRateName(providerOrder.getRate());
        userOrder.setStatus(UserOrderStatus.ACTIVE);
        userOrder.setDescription(providerOrder.getDescription());
        userOrder.setOrderPrice(providerOrder.getPrice());
        userOrder.setProviderOrderId(providerOrder.getOrder());
        userOrder.setIsRefund(providerOrder.getRefund());
        userOrder = userOrderRepository.save(userOrder);
        log.info("Order is created {}", userOrder);
        return userOrder;
    }

    @Override
    public UserOrder cancelUserOrder(String order) {
        log.info("Order canceling, {}", order);
        try {
            String userProfileId = order.split("-")[0];
            int userOrderId = Integer.parseInt(order.split("-")[1]);
            UserProfile user = userProfileService.getUserProfile(userProfileId);
            UserOrder userOrder = userOrderRepository.findUserOrder(userOrderId, user.getId()).orElseThrow(() -> new ResourceNotFoundException("Order not exist"));
            if (userOrder.getIsRefund()) {
                UserOrderCancelDtoRqMsg providerRq = new UserOrderCancelDtoRqMsg();
                providerRq.setOrder(userOrder.getProviderOrderId());
                providerRq.setLogin(userOrder.getProviderUserProfile().getLogin());

                String password = userOrder.getProviderUserProfile().getPassword();
                String salt = userOrder.getProviderUserProfile().getPasswordSalt();
                String decryptedPassword = passwordSecurity.decryptPassword(password, salt);
                providerRq.setPassword(passwordSecurity.simpleEncryptPassword(decryptedPassword));

                messageService.sendMessage(userOrder.getProviderUserProfile().getProvider(), providerRq, BusinessMethodEnum.CANCEL_ORDER);

                userOrder.setStatus(UserOrderStatus.CANCELED);
                userOrder = userOrderRepository.save(userOrder);
                log.info("Order is canceled, {}", order);
                return userOrder;
            } else {
                throw new BusinessLogicException("ORDER_NOT_REFUNDED", "order not refunded");
            }
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            throw new ResourceNotFoundException("Order is invalid");
        }
    }
}
