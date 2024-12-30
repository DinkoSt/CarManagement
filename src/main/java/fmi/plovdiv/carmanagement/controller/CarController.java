package fmi.plovdiv.carmanagement.controller;

import fmi.plovdiv.carmanagement.dto.CreateCarDto;
import fmi.plovdiv.carmanagement.dto.ResponseCarDto;
import fmi.plovdiv.carmanagement.dto.UpdateCarDto;
import fmi.plovdiv.carmanagement.service.CarService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cars")
@CrossOrigin(origins = "http://localhost:3000")
@Validated
@AllArgsConstructor
public class CarController {
    private final CarService carService;

    @GetMapping("/{id}")
    public ResponseEntity<ResponseCarDto> getCarById(@PathVariable Long id) {
        ResponseCarDto responseCarDto = carService.getById(id);
        System.out.println(responseCarDto + "get");
        return ResponseEntity.ok(responseCarDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseCarDto> updateCar(@PathVariable Long id, @RequestBody @Valid UpdateCarDto updateCarDTO) {
        ResponseCarDto responseCarDto = carService.update(id, updateCarDTO);
        System.out.println(responseCarDto);
        return ResponseEntity.ok(responseCarDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCarById(@PathVariable Long id) {
        carService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<ResponseCarDto>> getAllCars(@RequestParam(required = false) String carMake,
                                                           @RequestParam(required = false) Long garageId) {
        List<ResponseCarDto> responseCarDtosList = carService.getAll(carMake, garageId);
        System.out.println( responseCarDtosList + "get111111");
        return ResponseEntity.ok(responseCarDtosList);
    }
//    @GetMapping
//    public List <ResponseCarDto> getAllCars(){
//        return carService.getAll();
//    }

    @PostMapping
    public ResponseEntity<ResponseCarDto> createCar(@RequestBody @Valid CreateCarDto createCarDTO) {
        ResponseCarDto responseCarDto = carService.create(createCarDTO);
        return ResponseEntity.ok(responseCarDto);
    }
}