package fmi.plovdiv.carmanagement.entity;//package fmi.plovdiv.carmanagement.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.ArrayList;
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

    @ManyToMany
    @JoinTable(name = "car_garage_ids", joinColumns = @JoinColumn(name = "car_id"), inverseJoinColumns = @JoinColumn(name = "garage_id"))
    private List<Garage> garages = new ArrayList<>();

}
