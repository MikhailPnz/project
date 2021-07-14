package ru.burenkov.weatherBroker.entities;

import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Accessors(chain = true)
@Entity
@Data
public class WeatherEntity {

    @Id
    @Column
    @GenericGenerator(name = "generator", strategy = "increment")
    @GeneratedValue(generator = "generator")
    Integer id;

    @Column
    String name;

    @Column
    Double temp;
}
