package fmi.plovdiv.carmanagement.repository;

import fmi.plovdiv.carmanagement.entity.Car;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends CrudRepository<Car, Long>, JpaSpecificationExecutor<Car> {
}
