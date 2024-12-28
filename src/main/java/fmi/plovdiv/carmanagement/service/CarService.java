package fmi.plovdiv.carmanagement.service;


import fmi.plovdiv.carmanagement.dto.CreateCarDto;
import fmi.plovdiv.carmanagement.dto.ResponseCarDto;
import fmi.plovdiv.carmanagement.dto.UpdateCarDto;
import fmi.plovdiv.carmanagement.entity.Car;
import fmi.plovdiv.carmanagement.repository.CarRepository;
import fmi.plovdiv.carmanagement.repository.CarSpecifications;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CarService {
    private final CarRepository carRepository;
    private final CarMapper carMapper;
    public ResponseCarDto getById(Long id) {
        Car car = carRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(String.format("Car with ID %s not found", id)));
        return carMapper.sourceToResponseDto(car);
    }
//
//    public ResponseCarDto update(Long id, UpdateCarDto updateCarDTO) {
//        Car car = carRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(String.format("Car with ID %s not found", id)));
//        Car updatedCar = carRepository.save(carMapper.updateDtoToSource(updateCarDTO));
//        return carMapper.sourceToResponseDto(updatedCar);
//    }
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

    public List<ResponseCarDto> getAll(String make, Long garageId) {
        List<ResponseCarDto> responseCarDtosList = new ArrayList<>();

        Specification<Car> spec = Specification
                .where(CarSpecifications.hasCarMake(make))
                .and(CarSpecifications.hasGarageId(garageId));

        carRepository.findAll(spec).forEach(car -> {
            ResponseCarDto responseCarDto = carMapper.sourceToResponseDto(car);
            responseCarDtosList.add(responseCarDto);
        });

        return responseCarDtosList;
    }

    public ResponseCarDto create(CreateCarDto createCarDTO) {
        Car savedCar = carRepository.save(carMapper.createDtoToSource(createCarDTO));
        return carMapper.sourceToResponseDto(savedCar);
    }
}
