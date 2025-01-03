package fmi.plovdiv.carmanagement.mapper;

import fmi.plovdiv.carmanagement.dto.*;
import fmi.plovdiv.carmanagement.entity.Car;
import fmi.plovdiv.carmanagement.entity.Garage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GarageMapper {

    // Map from Garage entity to ResponseGarageDto
    ResponseGarageDto entityToResponseDto(Garage car);
    ResponseCarDto entityToResponseDto(Car car);

    // Map from CreateGarageDto to Garage entity
    Garage createDtoToEntity(CreateGarageDto createGarageDto);

    // Map from UpdateGarageDto to Garage entity (merge fields)
    @Mapping(target = "id", ignore = true) // Ensure ID is not overwritten
    void updateEntityFromDto(UpdateGarageDto updateGarageDto, @MappingTarget Garage garage);

    // Map list of Garage entities to list of ResponseGarageDto
    List<ResponseGarageDto> entitiesToResponseDtos(List<Garage> garages);

    // Placeholder for mapping a complex daily availability report (implement as needed)
    GarageDailyAvailabilityReportDto mapDailyAvailabilityReport(Object report);
}
