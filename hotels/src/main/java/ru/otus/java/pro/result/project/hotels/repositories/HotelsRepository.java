package ru.otus.java.pro.result.project.hotels.repositories;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.otus.java.pro.result.project.hotels.entities.Hotel;

import java.util.List;
import java.util.Optional;

@Repository
public interface HotelsRepository extends JpaRepository<Hotel, Integer> {

    @EntityGraph(value = "Graph.Hotel.Detailed")
    List<Hotel> findAllByCity_Title(String cityTitle);

    @EntityGraph(value = "Graph.Hotel.Detailed")
    Optional<Hotel> findByIdAndCity_Title(int id, String cityTitle);
}
