package fmi.plovdiv.carmanagement.dto;


import fmi.plovdiv.carmanagement.repository.GarageRepository;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GarageDailyAvailabilityReportDto {
    private String date;
    private Integer requests;
    private Integer availableCapacity;



    public GarageDailyAvailabilityReportDto() {
    }

    public GarageDailyAvailabilityReportDto(String date, int requests, int availableCapacity) {
        this.date = date;
        this.requests = requests;
        this.availableCapacity = availableCapacity;
    }


}