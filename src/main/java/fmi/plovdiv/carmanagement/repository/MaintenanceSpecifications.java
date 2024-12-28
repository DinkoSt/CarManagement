package fmi.plovdiv.carmanagement.repository;

import fmi.plovdiv.carmanagement.entity.Maintenance;
import org.springframework.data.jpa.domain.Specification;

public class MaintenanceSpecifications {
    public static Specification<Maintenance> hasCarId(Long carId) {
        return (root, _, criteriaBuilder) ->
                carId != null ? criteriaBuilder.equal(root.get("carId"), carId) : criteriaBuilder.conjunction();
    }

    public static Specification<Maintenance> hasGarageId(Long garageId) {
        return (root, _, criteriaBuilder) ->
                garageId != null ? criteriaBuilder.equal(root.get("garageId"), garageId) : criteriaBuilder.conjunction();
    }
}
