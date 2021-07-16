package ru.burenkov.weatherBroker.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.burenkov.weatherBroker.entities.WeatherEntity;

import java.util.List;

@Repository
public interface WeatherRepositories extends JpaRepository<WeatherEntity,Integer> {

    List<WeatherEntity> findAllByName (String name);

}
