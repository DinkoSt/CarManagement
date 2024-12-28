package fmi.plovdiv.carmanagement.repository;

import fmi.plovdiv.carmanagement.entity.Garage;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GarageRepository extends CrudRepository<Garage,Long>, JpaSpecificationExecutor<Garage> {
    @Query("select g.capacity from Garage g where g.id = :id")
    Integer findCapacityById(@Param("id") Long id);
}
