package fmi.plovdiv.carmanagement.repository;

import fmi.plovdiv.carmanagement.entity.Garage;
import org.springframework.data.jpa.domain.Specification;

public class GarageSpecifications {

        public static Specification<Garage> hasCity(String city) {
            return (root, _, criteriaBuilder) ->
                    city != null ? criteriaBuilder.like(root.get("city"), city + "%") : criteriaBuilder.conjunction();
        }
}
