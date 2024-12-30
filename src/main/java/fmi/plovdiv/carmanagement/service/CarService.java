package fmi.plovdiv.carmanagement.service;


import fmi.plovdiv.carmanagement.dto.CreateCarDto;
import fmi.plovdiv.carmanagement.dto.ResponseCarDto;
import fmi.plovdiv.carmanagement.dto.ResponseGarageDto;
import fmi.plovdiv.carmanagement.dto.UpdateCarDto;
import fmi.plovdiv.carmanagement.entity.Car;
import fmi.plovdiv.carmanagement.entity.Garage;
import fmi.plovdiv.carmanagement.mapper.CarMapper;
import fmi.plovdiv.carmanagement.mapper.GarageMapper;
import fmi.plovdiv.carmanagement.repository.CarRepository;
import fmi.plovdiv.carmanagement.repository.CarSpecifications;
import fmi.plovdiv.carmanagement.repository.GarageRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.beans.Transient;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
@AllArgsConstructor
public class CarService {
    private final CarRepository carRepository;

    @Autowired
    private final GarageRepository garageRepository;
    private final GarageMapper garageMapper;
    private final CarMapper carMapper;

    public ResponseCarDto getById(Long id) {
        Car car = carRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(String.format("Car with ID %s not found", id)));
        return carMapper.sourceToResponseDto(car);
    }

public ResponseCarDto update(Long id, UpdateCarDto updateCarDTO) {
    Car existingCar = carRepository.findById(id).orElseThrow(() ->
            new IllegalArgumentException(String.format("Car with ID %s not found", id)));

    // Use the mapper to update fields on the existing entity
    carMapper.updateCarFromDto(updateCarDTO, existingCar);

    // Save the updated car and return the mapped ResponseCarDto
    return carMapper.sourceToResponseDto(carRepository.save(existingCar));
}

    public void delete(Long id) {
        carRepository.deleteById(id);
    }

    public List<ResponseCarDto> getAll(String make, Long garageId,Integer fromYear, Integer toYear) {
        List<ResponseCarDto> responseCarDtosList = new ArrayList<>();
        ResponseCarDto responseCarDto = new ResponseCarDto();
        responseCarDto.setGarages(new ArrayList<>());

        Specification<Car> spec = Specification
                .where(CarSpecifications.hasCarMake(make))
                .and(CarSpecifications.hasGarageId(garageId))
                .and(CarSpecifications.hasProductionYearBetween(fromYear, toYear));

        carRepository.findAll(spec).forEach(car -> responseCarDtosList.add(garageMapper.entityToResponseDto(car)));


        return responseCarDtosList;
    }

    public ResponseCarDto create(CreateCarDto createCarDTO) {
        Car entityCar = carMapper.createDtoToSource(createCarDTO);
        for (Garage it : garageRepository.findAllById(createCarDTO.getGarageIds())) {
            entityCar.getGarages().add(it);

        }

        Car savedCar = carRepository.save(entityCar);
        ResponseCarDto responseCarDto = carMapper.sourceToResponseDto(savedCar);
        responseCarDto.setGarages(garageMapper.entitiesToResponseDtos(savedCar.getGarages()));
        return responseCarDto;
    }
}
