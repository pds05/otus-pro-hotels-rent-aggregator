package ru.otus.java.pro.result.project.hotels.repositories;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.otus.java.pro.result.project.hotels.entities.HotelRoom;
import ru.otus.java.pro.result.project.hotels.entities.HotelRoomBed_;
import ru.otus.java.pro.result.project.hotels.entities.HotelRoom_;
import ru.otus.java.pro.result.project.hotels.entities.UserOrder_;

import java.util.List;
import java.util.Optional;

@Repository
public interface HotelRoomsRepository extends JpaRepository <HotelRoom, Integer> {

    @EntityGraph(attributePaths = {
            HotelRoom_.HOTEL,
            HotelRoom_.HOTEL_ROOM_RATES,
            HotelRoom_.HOTEL_ROOM_BEDS,
            HotelRoom_.HOTEL_ROOM_AMENITIES,
            HotelRoom_.HOTEL_ROOM_BEDS + "." + HotelRoomBed_.BED_TYPE})
    Optional<HotelRoom> findByIdAndHotel_Id(Integer hotelRoomId, Integer hotelId);

    @EntityGraph(attributePaths = {
            HotelRoom_.HOTEL,
            HotelRoom_.HOTEL_ROOM_RATES,
            HotelRoom_.HOTEL_ROOM_BEDS,
            HotelRoom_.HOTEL_ROOM_AMENITIES,
            HotelRoom_.USER_ORDERS,
            HotelRoom_.USER_ORDERS + "." + UserOrder_.USER_PROFILE,
            HotelRoom_.HOTEL_ROOM_BEDS + "." + HotelRoomBed_.BED_TYPE
    })
    Optional<HotelRoom> findWithUserOrdersByIdAndHotel_Id(Integer hotelRoomId, Integer hotelId);

    @EntityGraph(attributePaths = {
            HotelRoom_.HOTEL,
            HotelRoom_.HOTEL_ROOM_RATES,
            HotelRoom_.HOTEL_ROOM_BEDS,
            HotelRoom_.HOTEL_ROOM_AMENITIES,
            HotelRoom_.HOTEL_ROOM_BEDS + "." + HotelRoomBed_.BED_TYPE})
    List<HotelRoom> findByHotel_Id(Integer hotelId);

    @EntityGraph(attributePaths = {
            HotelRoom_.HOTEL,
            HotelRoom_.HOTEL_ROOM_RATES,
            HotelRoom_.HOTEL_ROOM_BEDS,
            HotelRoom_.HOTEL_ROOM_AMENITIES,
            HotelRoom_.USER_ORDERS,
            HotelRoom_.USER_ORDERS + "." + UserOrder_.USER_PROFILE,
            HotelRoom_.HOTEL_ROOM_BEDS + "." + HotelRoomBed_.BED_TYPE})
    List<HotelRoom> findWithUserOrdersByHotel_Id(Integer hotelId);


}
