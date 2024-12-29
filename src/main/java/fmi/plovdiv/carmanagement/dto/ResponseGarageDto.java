package fmi.plovdiv.carmanagement.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseGarageDto {
    private Long id;
    private String name;
    private String location;
    private String city;
    private Integer capacity;
}


