package fmi.plovdiv.carmanagement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateMaintenanceDto {
    @NotNull(message = "Car Id is mandatory")
    private Long carId;

    @NotNull(message = "Garage Id is mandatory")
    private Long garageId;

    @NotBlank(message = "Service type is mandatory")
    private String serviceType;

    @NotBlank(message = "Schedule date is mandatory")
    private String scheduledDate;
}