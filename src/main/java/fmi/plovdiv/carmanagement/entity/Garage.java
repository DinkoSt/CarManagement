package fmi.plovdiv.carmanagement.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter

public class Garage extends BaseEntity{

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String location;

    private String city;

    @Column(nullable = false)
    private Integer capacity;
}


