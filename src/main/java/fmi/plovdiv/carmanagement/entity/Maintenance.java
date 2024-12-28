package fmi.plovdiv.carmanagement.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Maintenance extends BaseEntity{

    @Column(nullable = false)
    private long carId;
    @Column(nullable = false)
    private long garageId;
    @Column(nullable = false)
    private String serviceType;
    @Column(nullable = false)
    private String scheduledDate;


}
