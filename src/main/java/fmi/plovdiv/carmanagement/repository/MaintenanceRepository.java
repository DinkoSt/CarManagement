package fmi.plovdiv.carmanagement.repository;

import fmi.plovdiv.carmanagement.entity.Maintenance;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MaintenanceRepository extends CrudRepository<Maintenance, Long>, JpaSpecificationExecutor<Maintenance> {
    @Query("SELECT m from Maintenance m where m.garageId = :garageId and m.scheduledDate = :scheduledDate")
    List<Maintenance> findByGarageIdAndScheduleDate(@Param("garageId") Long garageId, @Param("scheduledDate") String scheduledDate);
    @Query("SELECT m from Maintenance m where m.garageId = :garageId")
    List<Maintenance> findByGarageId(@Param("garageId") Long garageId);
}
