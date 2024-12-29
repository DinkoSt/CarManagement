package fmi.plovdiv.carmanagement.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GarageDailyAvailabilityReportDto {
    private String date;
    private Integer requests;
    private Integer availableCapacity;
}