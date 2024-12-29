package fmi.plovdiv.carmanagement.controller;

import fmi.plovdiv.carmanagement.dto.CreateGarageDto;
import fmi.plovdiv.carmanagement.dto.GarageDailyAvailabilityReportDto;
import fmi.plovdiv.carmanagement.dto.ResponseGarageDto;
import fmi.plovdiv.carmanagement.dto.UpdateGarageDto;
import fmi.plovdiv.carmanagement.service.GarageService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/garages")
@CrossOrigin(origins = "http://localhost:3000")
@Validated
@AllArgsConstructor
public class GarageController {

    private final GarageService garageService;

    @GetMapping("/{id}")
    public ResponseEntity<ResponseGarageDto> getGarageById(@PathVariable Long id) {
        ResponseGarageDto responseGarageDto = garageService.getById(id);
        return ResponseEntity.ok(responseGarageDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseGarageDto> updateGarage(@PathVariable Long id, @RequestBody @Valid UpdateGarageDto updateGarageDTO) {
        ResponseGarageDto responseGarageDto = garageService.update(id, updateGarageDTO);
        return ResponseEntity.ok(responseGarageDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGarageById(@PathVariable Long id) {
        garageService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<ResponseGarageDto>> getAllGarages(@RequestParam(required = false) String city) {
        List<ResponseGarageDto> responseGarageDtosList = garageService.getAll(city);
        return ResponseEntity.ok(responseGarageDtosList);
    }

    @PostMapping
    public ResponseEntity<ResponseGarageDto> createGarage(@RequestBody @Valid CreateGarageDto createGarageDTO) {
        ResponseGarageDto responseGarageDto = garageService.create(createGarageDTO);
        return ResponseEntity.ok(responseGarageDto);
    }

    @GetMapping("/dailyAvailabilityReport")
    public ResponseEntity<List<GarageDailyAvailabilityReportDto>> getGarageReport(@RequestParam Long garageId,
                                                                                  @RequestParam String startDate,
                                                                                  @RequestParam String endDate) {
        List<GarageDailyAvailabilityReportDto> availabilityReports = garageService.getReport(garageId, startDate, endDate);
        return ResponseEntity.ok(availabilityReports);
    }
}
