package fmi.plovdiv.carmanagement.entity;//package fmi.plovdiv.carmanagement.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String make;

    @Column(nullable = false)
    private String model;

    @Column(nullable = false)
    private Integer productionYear;

    @Column(nullable = false)
    private String licensePlate;

    @ElementCollection
    @CollectionTable(name = "car_garage_ids", joinColumns = @JoinColumn(name = "car_id"))
    @Column(name = "garage_id")
    private List<Long> garageIds;

}



