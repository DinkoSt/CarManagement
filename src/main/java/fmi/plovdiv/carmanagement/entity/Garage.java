package fmi.plovdiv.carmanagement.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Garage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String location;

    private String city;

    @Column(nullable = false)
    private Integer capacity;

    @ManyToMany(mappedBy = "garages")
    private List<Car> cars = new ArrayList<>();

}
