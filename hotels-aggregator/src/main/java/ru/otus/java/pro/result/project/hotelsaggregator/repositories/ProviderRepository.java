package ru.otus.java.pro.result.project.hotelsaggregator.repositories;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.otus.java.pro.result.project.hotelsaggregator.entities.Provider;
import ru.otus.java.pro.result.project.hotelsaggregator.entities.Provider_;

import java.util.List;

@Repository
public interface ProviderRepository extends JpaRepository<Provider, Integer> {

    @Query(value = "from Provider")
    @EntityGraph(attributePaths = {Provider_.PROVIDER_APIS})
    List<Provider> findAllProvidersWithApis();
}
