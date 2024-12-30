package fmi.plovdiv.carmanagement.repository;

import fmi.plovdiv.carmanagement.entity.Car;
import org.springframework.data.jpa.domain.Specification;

public class CarSpecifications {

    public static Specification<Car> hasCarMake(String carMake) {
        return (root, _, criteriaBuilder) ->
                carMake != null ? criteriaBuilder.like(root.get("make"), carMake + "%") : criteriaBuilder.conjunction();
    }

    public static Specification<Car> hasGarageId(Long garageId) {
        return (root, _, criteriaBuilder) ->
                garageId != null ? criteriaBuilder.equal(root.get("garages"), garageId) : criteriaBuilder.conjunction();
    }
}
