//package fmi.plovdiv.carmanagement.repository;
//
//import fmi.plovdiv.carmanagement.entity.Car;
//import org.springframework.data.jpa.domain.Specification;
//
//public class CarSpecifications {
//
//    public static Specification<Car> hasCarMake(String carMake) {
//        System.out.println("Car is serached by IDDDDDDDDDDD");
//        return (root, _, criteriaBuilder) ->
//                carMake != null ? criteriaBuilder.like(root.get("make"), carMake + "%") : criteriaBuilder.conjunction();
//    }
//
//    public static Specification<Car> hasGarageId(Long garageId) {
//        return (root, _, criteriaBuilder) ->
//                garageId != null ? criteriaBuilder.equal(root.get("garages"), garageId) : criteriaBuilder.conjunction();
//    }
//}

package fmi.plovdiv.carmanagement.repository;

import fmi.plovdiv.carmanagement.entity.Car;
import org.springframework.data.jpa.domain.Specification;

public class CarSpecifications {

    public static Specification<Car> hasCarMake(String carMake) {
        return (root, query, criteriaBuilder) ->
                carMake != null ? criteriaBuilder.like(root.get("make"), carMake + "%") : criteriaBuilder.conjunction();
    }

    public static Specification<Car> hasGarageId(Long garageId) {
        return (root, query, criteriaBuilder) -> {
            if (garageId != null) {
                // Perform an inner join with the garages table
                return criteriaBuilder.equal(root.join("garages").get("id"), garageId);
            }
            return criteriaBuilder.conjunction();
        };
    }
    public static Specification<Car> hasProductionYearBetween(Integer fromYear, Integer toYear) {
        return (root, query, criteriaBuilder) -> {
            if (fromYear != null && toYear != null) {
                return criteriaBuilder.between(root.get("productionYear"), fromYear, toYear);
            } else if (fromYear != null) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get("productionYear"), fromYear);
            } else if (toYear != null) {
                return criteriaBuilder.lessThanOrEqualTo(root.get("productionYear"), toYear);
            }
            return criteriaBuilder.conjunction();
        };
    }

}
