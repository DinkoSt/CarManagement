package fmi.plovdiv.carmanagement.mapper;


import fmi.plovdiv.carmanagement.dto.CreateMaintenanceDto;
import fmi.plovdiv.carmanagement.dto.ResponseMaintenanceDto;
import fmi.plovdiv.carmanagement.dto.UpdateMaintenanceDto;
import fmi.plovdiv.carmanagement.entity.Maintenance;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MaintenanceMapper {

        @Mapping(target = "carId", source = "carId")
        @Mapping(target = "garageId", source = "garageId")
        @Mapping(target = "scheduledDate", source = "scheduledDate")
        Maintenance createDtoToEntity(CreateMaintenanceDto createMaintenanceDto);

        @Mapping(target = "carId", source = "carId")
        @Mapping(target = "garageId", source = "garageId")
        @Mapping(target = "scheduledDate", source = "scheduledDate")
        @Mapping(target = "id", ignore = true)
        void updateEntityFromDto(UpdateMaintenanceDto updateMaintenanceDto, @MappingTarget Maintenance maintenance);

        @Mapping(target = "carName", source = "carName")
        @Mapping(target = "garageName", source = "garageName")
        @Mapping(target = "id", source = "maintenance.id")
        @Mapping(target = "carId", source = "maintenance.carId")
        @Mapping(target = "garageId", source = "maintenance.garageId")
        @Mapping(target = "scheduledDate", source = "maintenance.scheduledDate")
        ResponseMaintenanceDto entityToResponseDto(Maintenance maintenance, String carName, String garageName);


    default Maintenance fromUpdateDto(Long id, UpdateMaintenanceDto updateDto) {
        Maintenance maintenance = new Maintenance();
        maintenance.setId(id);
        maintenance.setCarId(updateDto.getCarId());
        maintenance.setGarageId(updateDto.getGarageId());
        maintenance.setScheduledDate(updateDto.getScheduledDate());
        return maintenance;
    }

        List<ResponseMaintenanceDto> entitiesToResponseDtos(List<Maintenance> maintenances);

}
