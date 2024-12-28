package fmi.plovdiv.carmanagement.service;


import fmi.plovdiv.carmanagement.dto.CreateGarageDto;
import fmi.plovdiv.carmanagement.dto.GarageDailyAvailabilityReportDto;
import fmi.plovdiv.carmanagement.dto.ResponseGarageDto;
import fmi.plovdiv.carmanagement.dto.UpdateGarageDto;
import fmi.plovdiv.carmanagement.entity.Garage;
import fmi.plovdiv.carmanagement.repository.GarageRepository;
import fmi.plovdiv.carmanagement.repository.GarageSpecifications;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GarageService {
    private GarageRepository garageRepository;

    public ResponseGarageDto getById(Long id) {
        Garage garage = garageRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(String.format("Garage with id %s not found", id)));
        return ResponseGarageDto.fromGarage(garage);
    }

    public ResponseGarageDto update(Long id, UpdateGarageDto updateGarageDTO) {
        Garage garage = garageRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(String.format("Garage with id %s not found", id)));
        Garage updatedGarage = garageRepository.save(Garage.fromUpdateGarageDto(garage.getId(), updateGarageDTO));
        return ResponseGarageDto.fromGarage(updatedGarage);
    }

    public void delete(Long id) {
        garageRepository.deleteById(id);
    }

    public List<ResponseGarageDto> getAll(String city) {
        List<ResponseGarageDto> responseGarageDtoList = new ArrayList<>();
        Specification<Garage> spec = Specification
                .where(GarageSpecifications.hasCity(city));

        garageRepository.findAll(spec).forEach(garage -> {
            ResponseGarageDto responseGarageDto = ResponseGarageDto.fromGarage(garage);
            responseGarageDtoList.add(responseGarageDto);
        });
        return responseGarageDtoList;
    }

    public ResponseGarageDto create(CreateGarageDto createGarageDTO) {
        Garage savedGarage = garageRepository.save(Garage.fromCreateGarageDto(createGarageDTO));
        return ResponseGarageDto.fromGarage(savedGarage);
    }

    public List<GarageDailyAvailabilityReportDto> getReport(Long garageId, String startDate, String endDate) {
        List<GarageDailyAvailabilityReportDto> responseGarageDailyAvailabilityReportDtoList = new ArrayList<>();
        return responseGarageDailyAvailabilityReportDtoList;
    }
}
