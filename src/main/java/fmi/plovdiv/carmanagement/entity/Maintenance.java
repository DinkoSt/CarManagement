package fmi.plovdiv.carmanagement.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Maintenance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private long carId;

    @Column(nullable = false)
    private long garageId;

    @Column(nullable = false)
    private String serviceType;

    @Column(nullable = false)
    private String scheduledDate;
}
