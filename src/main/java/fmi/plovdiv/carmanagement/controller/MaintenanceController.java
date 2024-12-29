package fmi.plovdiv.carmanagement.controller;

import fmi.plovdiv.carmanagement.dto.CreateMaintenanceDto;
import fmi.plovdiv.carmanagement.dto.MonthlyRequestsReportDto;
import fmi.plovdiv.carmanagement.dto.ResponseMaintenanceDto;
import fmi.plovdiv.carmanagement.dto.UpdateMaintenanceDto;
import fmi.plovdiv.carmanagement.service.MaintenanceService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/maintenance")
@CrossOrigin(origins = "http://localhost:3000")
@Validated
@AllArgsConstructor
public class MaintenanceController {

    private final MaintenanceService maintenanceService;

    @GetMapping("/{id}")
    public ResponseEntity<ResponseMaintenanceDto> getMaintenanceById(@PathVariable Long id) {
        ResponseMaintenanceDto maintenance = maintenanceService.getById(id);
        return ResponseEntity.ok(maintenance);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseMaintenanceDto> updateMaintenance(@PathVariable Long id, @RequestBody @Valid UpdateMaintenanceDto updateMaintenanceDTO) {
        ResponseMaintenanceDto responseMaintenanceDto = maintenanceService.update(id, updateMaintenanceDTO);
        return ResponseEntity.ok(responseMaintenanceDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMaintenance(@PathVariable Long id) {
        maintenanceService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<ResponseMaintenanceDto>> getAllMaintenances(@RequestParam(required = false) Long carId,
                                                                           @RequestParam(required = false) Long garageId) {
        //TODO: apply filtering
        List<ResponseMaintenanceDto> responseMaintenanceDtosList = maintenanceService.filter(carId, garageId);
        return ResponseEntity.ok(responseMaintenanceDtosList);
    }

    @PostMapping
    public ResponseEntity<ResponseMaintenanceDto> createMaintenance(@RequestBody @Valid CreateMaintenanceDto createMaintenanceDTO) {
        ResponseMaintenanceDto responseMaintenanceDto = maintenanceService.create(createMaintenanceDTO);
        return ResponseEntity.ok(responseMaintenanceDto);
    }

    @GetMapping("/monthlyRequestsReport")
    public ResponseEntity<List<MonthlyRequestsReportDto>> monthlyRequestsReport(@RequestParam Long garageId,
                                                                                @RequestParam String startMonth,
                                                                                @RequestParam String endMonth) {
        List<MonthlyRequestsReportDto> monthlyRequestsReportDtos =
                maintenanceService.getMonthlyRequestReport(garageId, startMonth, endMonth);
        return ResponseEntity.ok(monthlyRequestsReportDtos);
    }
}

