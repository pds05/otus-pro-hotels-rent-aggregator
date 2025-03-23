package ru.otus.java.pro.result.project.hotels.daos;

import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.java.pro.result.project.hotels.dtos.HotelDtoRq;
import ru.otus.java.pro.result.project.hotels.entities.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class HotelDao {
    private final EntityManager em;

    public List<Hotel> getFilterHotels(HotelDtoRq hotelDtoRq) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Hotel> cq = cb.createQuery(Hotel.class);
        cq.distinct(true);
        Root<Hotel> hotelRoot = cq.from(Hotel.class);
        List<Predicate> predicates = new ArrayList<>();
        if (hotelDtoRq.getHotel() != null) {
            predicates.add(cb.equal(hotelRoot.get(Hotel_.TITLE), hotelDtoRq.getHotel()));
        }
        if (hotelDtoRq.getCity() != null) {
            predicates.add(cb.equal(hotelRoot.join(Hotel_.CITY).get(CtCity_.TITLE), hotelDtoRq.getCity()));
        }
        if (hotelDtoRq.getStars() != null) {
            predicates.add(cb.equal(hotelRoot.get(Hotel_.STAR_GRADE), hotelDtoRq.getStars()));
        }
        if (hotelDtoRq.getHotelTypes() != null && !hotelDtoRq.getHotelTypes().isEmpty()) {
            Expression<String> expression = hotelRoot.join(Hotel_.HOTEL_TYPE).get(CtHotelType_.TITLE);
            predicates.add(expression.in(hotelDtoRq.getHotelTypes()));
        }
        if (hotelDtoRq.getHotelAmenities() != null && !hotelDtoRq.getHotelAmenities().isEmpty()) {
            Expression<String> expression = hotelRoot.join(Hotel_.HOTELS_HOTEL_AMENITIES).join(HotelsHotelAmenity_.HOTEL_AMENITY).get(HotelAmenity_.TITLE);
            predicates.add(expression.in(hotelDtoRq.getHotelAmenities()));
        }
        if (hotelDtoRq.getHotelRoomAmenities() != null && !hotelDtoRq.getHotelRoomAmenities().isEmpty()) {
            Expression<String> expression = hotelRoot.join(Hotel_.HOTEL_ROOMS).join(HotelRoom_.HOTEL_ROOM_AMENITIES).get(HotelRoomAmenity_.TITLE);
            predicates.add(expression.in(hotelDtoRq.getHotelRoomAmenities()));
        }
        if (hotelDtoRq.getFoods() != null && !hotelDtoRq.getFoods().isEmpty()) {
            Expression<BigDecimal> expression = hotelRoot.join(Hotel_.HOTEL_ROOMS).join(HotelRoom_.HOTEL_ROOM_RATES).join(HotelRoomRate_.FEED_TYPE).get(CtHotelFeedType_.TITLE);
            predicates.add(expression.in(hotelDtoRq.getFoods()));
        }
        if (hotelDtoRq.getBeds() != null && !hotelDtoRq.getBeds().isEmpty()) {
            Expression<String> expression = hotelRoot.join(Hotel_.HOTEL_ROOMS).join(HotelRoom_.HOTEL_ROOM_BEDS).join(HotelRoomBed_.BED_TYPE).get(CtHotelBedType_.TITLE);
            predicates.add(expression.in(hotelDtoRq.getBeds()));
        }
        if (hotelDtoRq.getPriceFrom() > 0) {
            Expression<BigDecimal> expression = hotelRoot.join(Hotel_.HOTEL_ROOMS).join(HotelRoom_.HOTEL_ROOM_RATES).get(HotelRoomRate_.PRICE);
            predicates.add(cb.ge(expression, hotelDtoRq.getPriceFrom()));
        }
        if (hotelDtoRq.getPriceTo() > 0) {
            Expression<BigDecimal> expression = hotelRoot.join(Hotel_.HOTEL_ROOMS).join(HotelRoom_.HOTEL_ROOM_RATES).get(HotelRoomRate_.PRICE);
            predicates.add(cb.le(expression, hotelDtoRq.getPriceTo()));
        }
        if (hotelDtoRq.getGuests() != null) {
            Expression<Integer> expression = hotelRoot.join(Hotel_.HOTEL_ROOMS).get(HotelRoom_.MAX_GUESTS);
            predicates.add(cb.le(expression, (hotelDtoRq.getChildren() != null) ?
                    hotelDtoRq.getGuests() + hotelDtoRq.getChildren() :
                    hotelDtoRq.getGuests()));
        }
        cq.where(predicates.toArray(new Predicate[0]));

        EntityGraph<?> graph = em.createEntityGraph("Graph.Hotel.Detailed");
        TypedQuery<Hotel> query = em.createQuery(cq);
        query.setHint("jakarta.persistence.fetchgraph", graph);
        return query.getResultList();
    }

}
