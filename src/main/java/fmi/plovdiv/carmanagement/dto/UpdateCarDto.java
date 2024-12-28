package fmi.plovdiv.carmanagement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UpdateCarDto {
    @NotBlank(message = "Make is mandatory")
    private String make;
    @NotBlank(message = "Model is mandatory")
    private String model;
    @NotNull(message = "Production year is mandatory")
    private Integer productionYear;
    @NotBlank(message = "License plate is mandatory")
    private String licensePlate;
    private List<Long> garageIds;
}