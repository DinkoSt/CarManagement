package fmi.plovdiv.carmanagement.mapper;

import fmi.plovdiv.carmanagement.dto.CreateCarDto;
import fmi.plovdiv.carmanagement.dto.ResponseCarDto;
import fmi.plovdiv.carmanagement.dto.UpdateCarDto;
import fmi.plovdiv.carmanagement.entity.Car;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;


@Mapper(componentModel = "spring")
public interface CarMapper {
    // Assuming garages are populated elsewher
    ResponseCarDto sourceToResponseDto(Car source);

    Car createDtoToSource(CreateCarDto createCarDto);

    List<ResponseCarDto> sourceListToResponseDto(List<Car> sourceList);

//    Car updateDtoToSource(UpdateCarDto updateCarDto);
@Mapping(target = "id", ignore = true) // Prevent overwriting the ID
void updateCarFromDto(UpdateCarDto updateCarDto, @MappingTarget Car car);
}







