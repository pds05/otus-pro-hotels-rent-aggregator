package ru.otus.java.pro.result.project.hotels.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.otus.java.pro.result.project.hotels.entities.CtHotelBedType;

@Repository
public interface CtHotelBedTypeRepository extends JpaRepository<CtHotelBedType, Short> {
}
