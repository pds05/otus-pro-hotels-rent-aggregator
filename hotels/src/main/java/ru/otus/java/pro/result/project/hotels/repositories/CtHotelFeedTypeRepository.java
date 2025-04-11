package ru.otus.java.pro.result.project.hotels.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.otus.java.pro.result.project.hotels.entities.CtHotelFeedType;

@Repository
public interface CtHotelFeedTypeRepository extends JpaRepository<CtHotelFeedType, Short> {
}
