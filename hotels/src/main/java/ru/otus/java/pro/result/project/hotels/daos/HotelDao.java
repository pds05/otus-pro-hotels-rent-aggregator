package ru.otus.java.pro.result.project.hotels.daos;

import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.java.pro.result.project.hotels.dtos.HotelDtoRq;
import ru.otus.java.pro.result.project.hotels.entities.CtCity_;
import ru.otus.java.pro.result.project.hotels.entities.Hotel;
import ru.otus.java.pro.result.project.hotels.entities.Hotel_;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class HotelDao {
    private final EntityManager em;

    public List<Hotel> getFilterHotels(HotelDtoRq hotelDtoRq) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Hotel> cq = cb.createQuery(Hotel.class);
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
            Expression<String> expression = hotelRoot.join(Hotel_.HOTEL_TYPE).get(CtCity_.TITLE);
            predicates.add(expression.in(hotelDtoRq.getHotelTypes().stream().map(HotelDtoRq.HotelTypeDtoRq::getDescription).toList()));
        }
        if (hotelDtoRq.getHotelType() != null) {
            predicates.add(cb.equal(hotelRoot.join(Hotel_.HOTEL_TYPE).get(CtCity_.TITLE), hotelDtoRq.getHotelType().getDescription()));
        }
        cq.where(predicates.toArray(new Predicate[0]));

        EntityGraph<?> graph = em.createEntityGraph("Graph.Hotel.Detailed");
        TypedQuery<Hotel> query = em.createQuery(cq);
        query.setHint("jakarta.persistence.fetchgraph", graph);
        return query.getResultList();
    }

}
