package ru.otus.java.pro.result.project.hotels.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.otus.java.pro.result.project.hotels.entities.UserOrder;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserOrdersRepository extends JpaRepository<UserOrder, Integer> {

    List<UserOrder> findUserOrderByUserProfile_Id(String id);

    @Query(value = "from UserOrder where userOrderId.orderId=:orderId and userOrderId.userProfileId=:userProfileId")
    Optional<UserOrder> findUserOrder(Integer orderId, String userProfileId);

    @Query(value = "select coalesce((select order_id + 1 from user_orders where user_profile_id = ?1 order by order_id desc limit 1), 1)", nativeQuery = true)
    int getNextValueUserOrderId(String userProfileId);
}
