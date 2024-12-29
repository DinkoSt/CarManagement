package fmi.plovdiv.carmanagement.service;


import fmi.plovdiv.carmanagement.dto.*;
import fmi.plovdiv.carmanagement.entity.Garage;
import fmi.plovdiv.carmanagement.entity.Maintenance;
import fmi.plovdiv.carmanagement.mapper.GarageMapper;
import fmi.plovdiv.carmanagement.mapper.MaintenanceMapper;
import fmi.plovdiv.carmanagement.repository.GarageRepository;
import fmi.plovdiv.carmanagement.repository.GarageSpecifications;
import fmi.plovdiv.carmanagement.repository.MaintenanceRepository;
import fmi.plovdiv.carmanagement.repository.MaintenanceSpecifications;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class MaintenanceService {
    private final MaintenanceRepository maintenanceRepository;
    private final CarService carService;
    private final GarageService garageService;
    private final GarageRepository garageRepository;
    private final MaintenanceMapper maintenanceMapper;


    public ResponseMaintenanceDto getById(Long id) {
        Maintenance maintenance = maintenanceRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException(String.format("Maintenance with id %s not found", id)));
        ResponseCarDto car = carService.getById(maintenance.getCarId());
        ResponseGarageDto garage = garageService.getById(maintenance.getGarageId());
        //get car info and garage info
        return maintenanceMapper.entityToResponseDto(maintenance, car.getMake(), garage.getName());
    }

    public ResponseMaintenanceDto update(Long id, UpdateMaintenanceDto updateMaintenanceDTO) {
        Maintenance maintenance = maintenanceRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException(String.format("Maintenance with id %s not found", id)));
        //check if there is free space
        String scheduledDate = updateMaintenanceDTO.getScheduledDate();
        //check if there are free spots in the service for the date
        List<Maintenance> scheduledMaintenances = maintenanceRepository
                .findByGarageIdAndScheduleDate(updateMaintenanceDTO.getGarageId(), scheduledDate);
        Integer garageCapacity = garageRepository.findCapacityById(updateMaintenanceDTO.getGarageId());
        if (scheduledMaintenances.size() >= garageCapacity) {
            throw new IllegalArgumentException("Scheduled maintenance capacity exceeds garage capacity, please choose another day");
        }
        maintenanceMapper.updateEntityFromDto(updateMaintenanceDTO, maintenance);
        Maintenance updatedMaintenance = maintenanceRepository.save(maintenance);
        ResponseCarDto car = carService.getById(maintenance.getCarId());
        ResponseGarageDto garage = garageService.getById(maintenance.getGarageId());
        //get car info and garage info
        return maintenanceMapper.entityToResponseDto(updatedMaintenance, car.getMake(), garage.getName());
    }

    public void delete(Long id) {
        maintenanceRepository.deleteById(id);
    }

    public List<ResponseMaintenanceDto> getAll() {
        List<Maintenance> maintenances = new ArrayList<>();
        maintenanceRepository.findAll().forEach(maintenances::add);
        List<ResponseMaintenanceDto> responseMaintenanceDtos = new ArrayList<>();
        for (Maintenance maintenance : maintenances) {
            ResponseCarDto car = carService.getById(maintenance.getCarId());
            ResponseGarageDto garage = garageService.getById(maintenance.getGarageId());
            responseMaintenanceDtos.add(maintenanceMapper.entityToResponseDto(maintenance, car.getMake(), garage.getName()));
        }
        return responseMaintenanceDtos;
    }

    public ResponseMaintenanceDto create(CreateMaintenanceDto createMaintenanceDTO) {
        //check if there is free space
        String scheduledDate = createMaintenanceDTO.getScheduledDate();
        //check if there are free spots in the service for the date
        List<Maintenance> scheduledMaintenances = maintenanceRepository
                .findByGarageIdAndScheduleDate(createMaintenanceDTO.getGarageId(), scheduledDate);
        Integer garageCapacity = garageRepository.findCapacityById(createMaintenanceDTO.getGarageId());
        if (scheduledMaintenances.size() >= garageCapacity) {
            throw new IllegalArgumentException("Scheduled maintenance capacity exceeds garage capacity, please choose another day");
        }

        Maintenance savedMaintenance = maintenanceRepository.save(maintenanceMapper.createDtoToEntity(createMaintenanceDTO));
        ResponseCarDto car = carService.getById(createMaintenanceDTO.getCarId());
        ResponseGarageDto garage = garageService.getById(createMaintenanceDTO.getGarageId());
        return maintenanceMapper.entityToResponseDto(savedMaintenance, car.getMake(), garage.getName());
    }

    public List<MonthlyRequestsReportDto> getMonthlyRequestReport(Long garageId, String startMonth, String endMonth) {
        List<MonthlyRequestsReportDto> monthlyRequestsReportDtos = new ArrayList<>();
        List<Maintenance> maintenances = maintenanceRepository.findByGarageId(garageId);

        YearMonth currentYearMonth = YearMonth.parse(startMonth);
        YearMonth yearMonthEnd = YearMonth.parse(endMonth);

        Map<YearMonth, Integer> yearMonthRequestsMap = new HashMap<>();

        do {
            yearMonthRequestsMap.put(currentYearMonth, 0);
            currentYearMonth = currentYearMonth.plusMonths(1);
        } while (currentYearMonth.isBefore(yearMonthEnd.plusMonths(1)));

        for (Maintenance maintenance : maintenances) {
            YearMonth maintenanceMonth = YearMonth.parse(maintenance.getScheduledDate(), DateTimeFormatter.ISO_LOCAL_DATE);
            if (yearMonthRequestsMap.containsKey(maintenanceMonth)) {
                yearMonthRequestsMap.put(maintenanceMonth, yearMonthRequestsMap.get(maintenanceMonth) + 1);
            }
        }

        for (YearMonth yearMonth : yearMonthRequestsMap.keySet()) {
            MonthlyRequestsReportDto requestsReportDto = new MonthlyRequestsReportDto();
            requestsReportDto.setYearMonth(yearMonth);
            requestsReportDto.setRequests(yearMonthRequestsMap.get(yearMonth));
            monthlyRequestsReportDtos.add(requestsReportDto);
        }
        return monthlyRequestsReportDtos;
    }

    public List<ResponseMaintenanceDto> filter(Long carId, Long garageId) {
        List<ResponseMaintenanceDto> responseMaintenanceDtos = new ArrayList<>();

        Specification<Maintenance> spec = Specification
                .where(MaintenanceSpecifications.hasCarId(carId))
                .and(MaintenanceSpecifications.hasGarageId(garageId));

       maintenanceRepository.findAll(spec).forEach(maintenance -> {
            ResponseCarDto car = carService.getById(maintenance.getCarId());
            ResponseGarageDto garage = garageService.getById(maintenance.getGarageId());
            ResponseMaintenanceDto responseMaintenanceDto = maintenanceMapper.entityToResponseDto(maintenance, car.getMake(), garage.getName());
            responseMaintenanceDtos.add(responseMaintenanceDto);
        });

       return responseMaintenanceDtos;
    }
}
