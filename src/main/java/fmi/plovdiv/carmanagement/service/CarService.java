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
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        List<ResponseGarageDto> responseGarageDtosList = new ArrayList<>();

        Specification<Car> spec = Specification
                .where(CarSpecifications.hasCarMake(make))
                .and(CarSpecifications.hasGarageId(garageId));

        garageRepository.findAll().forEach(garage ->{

            ResponseGarageDto responseGarageDto = garageMapper.entityToResponseDto(garage);
            responseGarageDtosList.add(responseGarageDto);
                }
        );

        carRepository.findAll(spec).forEach(car -> {
            ResponseCarDto responseCarDto = carMapper.sourceToResponseDto(car);
            responseCarDto.setGarages(responseGarageDtosList);
            responseCarDtosList.add(responseCarDto);
        });


        return responseCarDtosList;
    }

    public ResponseCarDto create(CreateCarDto createCarDTO) {
        Car savedCar = carRepository.save(carMapper.createDtoToSource(createCarDTO));
        ResponseCarDto responseCarDto = carMapper.sourceToResponseDto(savedCar);
        responseCarDto.setGarages(new ArrayList<>());
        savedCar.getGarageIds().forEach(g -> {
            Optional<Garage> garage = garageRepository.findById(g);
            if (garage.isPresent()) {
                responseCarDto.getGarages().add(garageMapper.entityToResponseDto(garage.get()));
            }

        });
        return responseCarDto;
    }
}
