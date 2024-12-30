package fmi.plovdiv.carmanagement.repository;

import fmi.plovdiv.carmanagement.entity.Garage;
import fmi.plovdiv.carmanagement.entity.Maintenance;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GarageRepository extends CrudRepository<Garage,Long>, JpaSpecificationExecutor<Garage> {
    @Query("select g.capacity from Garage g where g.id = :id")
    Integer findCapacityById(@Param("id") Long id);
    @Query("SELECT m from Maintenance m where m.garageId = :garageId and m.scheduledDate = :scheduledDate")
    List<Maintenance> countByGarageIdAndScheduledDate(@Param("garageId") Long garageId, @Param("scheduledDate") String scheduledDate);
}
