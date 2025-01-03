package fmi.plovdiv.carmanagement.service;


import fmi.plovdiv.carmanagement.dto.CreateGarageDto;
import fmi.plovdiv.carmanagement.dto.GarageDailyAvailabilityReportDto;
import fmi.plovdiv.carmanagement.dto.ResponseGarageDto;
import fmi.plovdiv.carmanagement.dto.UpdateGarageDto;
import fmi.plovdiv.carmanagement.entity.Garage;
import fmi.plovdiv.carmanagement.entity.Maintenance;
import fmi.plovdiv.carmanagement.mapper.GarageMapper;
import fmi.plovdiv.carmanagement.repository.GarageRepository;
import fmi.plovdiv.carmanagement.repository.GarageSpecifications;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

//@Service
//@RequiredArgsConstructor
//public class GarageService {
//    private GarageRepository garageRepository;
//
//    public ResponseGarageDto getById(Long id) {
//        Garage garage = garageRepository.findById(id)
//                .orElseThrow(() -> new IllegalArgumentException(String.format("Garage with id %s not found", id)));
//        return ResponseGarageDto.fromGarage(garage);
//    }
//
//    public ResponseGarageDto update(Long id, UpdateGarageDto updateGarageDTO) {
//        Garage garage = garageRepository.findById(id)
//                .orElseThrow(() -> new IllegalArgumentException(String.format("Garage with id %s not found", id)));
//        Garage updatedGarage = garageRepository.save(Garage.fromUpdateGarageDto(garage.getId(), updateGarageDTO));
//        return ResponseGarageDto.fromGarage(updatedGarage);
//    }
//
//    public void delete(Long id) {
//        garageRepository.deleteById(id);
//    }
//
//    public List<ResponseGarageDto> getAll(String city) {
//        List<ResponseGarageDto> responseGarageDtoList = new ArrayList<>();
//        Specification<Garage> spec = Specification
//                .where(GarageSpecifications.hasCity(city));
//
//        garageRepository.findAll(spec).forEach(garage -> {
//            ResponseGarageDto responseGarageDto = ResponseGarageDto.fromGarage(garage);
//            responseGarageDtoList.add(responseGarageDto);
//        });
//        return responseGarageDtoList;
//    }
//
//    public ResponseGarageDto create(CreateGarageDto createGarageDTO) {
//        Garage savedGarage = garageRepository.save(Garage.fromCreateGarageDto(createGarageDTO));
//        return ResponseGarageDto.fromGarage(savedGarage);
//    }
//
//    public List<GarageDailyAvailabilityReportDto> getReport(Long garageId, String startDate, String endDate) {
//        List<GarageDailyAvailabilityReportDto> responseGarageDailyAvailabilityReportDtoList = new ArrayList<>();
//        return responseGarageDailyAvailabilityReportDtoList;
//    }
//}

@Service
@AllArgsConstructor
public class GarageService {
    private final GarageRepository garageRepository;
    private final GarageMapper garageMapper;

    public ResponseGarageDto getById(Long id) {
        Garage garage = garageRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(String.format("Garage with id %s not found", id)));
        return garageMapper.entityToResponseDto(garage);
    }

    public ResponseGarageDto update(Long id, UpdateGarageDto updateGarageDTO) {
        Garage garage = garageRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(String.format("Garage with id %s not found", id)));
        garageMapper.updateEntityFromDto(updateGarageDTO, garage);
        Garage updatedGarage = garageRepository.save(garage);
        return garageMapper.entityToResponseDto(updatedGarage);
    }

    public ResponseGarageDto create(CreateGarageDto createGarageDTO) {
        Garage savedGarage = garageRepository.save(garageMapper.createDtoToEntity(createGarageDTO));
        return garageMapper.entityToResponseDto(savedGarage);
    }

    public List<ResponseGarageDto> getAll(String city) {
        Specification<Garage> spec = Specification.where(GarageSpecifications.hasCity(city));
        List<Garage> garages = garageRepository.findAll(spec);
        return garageMapper.entitiesToResponseDtos(garages);
    }

    public void delete(Long id) {
        garageRepository.deleteById(id);
    }

//    public List<GarageDailyAvailabilityReportDto> getReport(Long garageId, String startDate, String endDate) {
//        List<GarageDailyAvailabilityReportDto> responseGarageDailyAvailabilityReportDtoList = new ArrayList<>();
//        System.out.println("Garage report ");
//        return responseGarageDailyAvailabilityReportDtoList;
//    }
//public List<GarageDailyAvailabilityReportDto> getGarageDailyAvailabilityReport(Long garageId, String startDate, String endDate) {
//    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//    LocalDate start = LocalDate.parse(startDate, formatter);
//    LocalDate end = LocalDate.parse(endDate, formatter);
//    List<GarageDailyAvailabilityReportDto> report = new ArrayList<>();
//    for (LocalDate currentDate = start; !currentDate.isAfter(end); currentDate = currentDate.plusDays(1)) {
//        List<Maintenance> requests = garageRepository.countByGarageIdAndScheduledDate(garageId, currentDate.toString());
//        Garage garage = garageRepository.findById(garageId)
//                .orElseThrow(() -> new EntityNotFoundException("Garage not found with id: " + garageId));
//        int availableCapacity = garage.getCapacity() - requests;
//        GarageDailyAvailabilityReportDto dailyReport = new GarageDailyAvailabilityReportDto(
//                currentDate.toString(),
//                requests,
//                availableCapacity
//        );
//        report.add(dailyReport);
//    }
//    return report;
//}
public List<GarageDailyAvailabilityReportDto> getGarageDailyAvailabilityReport(Long garageId, String startDate, String endDate) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    LocalDate start = LocalDate.parse(startDate, formatter);
    LocalDate end = LocalDate.parse(endDate, formatter);
    List<GarageDailyAvailabilityReportDto> report = new ArrayList<>();

    for (LocalDate currentDate = start; !currentDate.isAfter(end); currentDate = currentDate.plusDays(1)) {
        List<Maintenance> maintenanceRequests = garageRepository.countByGarageIdAndScheduledDate(garageId, currentDate.toString());
        int requestsCount = maintenanceRequests.size(); // Get the number of requests

        Garage garage = garageRepository.findById(garageId)
                .orElseThrow(() -> new EntityNotFoundException("Garage not found with id: " + garageId));
        int availableCapacity = garage.getCapacity() - requestsCount; // Subtract the count from capacity

        GarageDailyAvailabilityReportDto dailyReport = new GarageDailyAvailabilityReportDto(
                currentDate.toString(),
                requestsCount, // Use the size of the list as the requests count
                availableCapacity
        );
        report.add(dailyReport);
    }

    return report;
}

}
