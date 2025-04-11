package ru.otus.java.pro.result.project.hotelsaggregator.repositories;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.otus.java.pro.result.project.hotelsaggregator.entities.ProviderUserProfile_;
import ru.otus.java.pro.result.project.hotelsaggregator.entities.UserOrder;
import ru.otus.java.pro.result.project.hotelsaggregator.entities.UserOrderKey;
import ru.otus.java.pro.result.project.hotelsaggregator.entities.UserOrder_;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserOrderRepository extends JpaRepository<UserOrder, UserOrderKey> {

    @EntityGraph(attributePaths = {
            UserOrder_.USER_PROFILE,
            UserOrder_.PROVIDER_USER_PROFILE,
            UserOrder_.PROVIDER_USER_PROFILE + "." + ProviderUserProfile_.PROVIDER
    })
    List<UserOrder> findUserOrderByUserProfile_Id(String id);

    @EntityGraph(attributePaths = {
            UserOrder_.USER_PROFILE,
            UserOrder_.PROVIDER_USER_PROFILE,
            UserOrder_.PROVIDER_USER_PROFILE + "." + ProviderUserProfile_.PROVIDER
    })
    @Query(value = "from UserOrder where userOrderId.orderId=:orderId and userOrderId.userProfileId=:userProfileId")
    Optional<UserOrder> findUserOrder(Integer orderId, String userProfileId);

    @Query(value = "select coalesce((select order_id + 1 from user_orders where user_profile_id = ?1 order by order_id desc limit 1), 1)", nativeQuery = true)
    int getNextValueUserOrderId(String userProfileId);

}
