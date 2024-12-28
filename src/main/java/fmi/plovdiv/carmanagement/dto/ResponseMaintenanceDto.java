package fmi.plovdiv.carmanagement.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseMaintenanceDto {
    private Long id;
    private Long carId;
    private String carName;
    private String serviceType;
    private String scheduledDate;
    private Long garageId;
    private String garageName;

}