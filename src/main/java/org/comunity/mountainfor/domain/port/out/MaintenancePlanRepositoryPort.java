package org.comunity.mountainfor.domain.port.out;

import org.comunity.mountainfor.domain.model.maintenanceplan.MaintenancePlan;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface MaintenancePlanRepositoryPort {
    MaintenancePlan save(MaintenancePlan plan);
    Optional<MaintenancePlan> findById(Long id);
    Optional<MaintenancePlan> findByCode(String code);
    List<MaintenancePlan> findAll();
    List<MaintenancePlan> findByEquipmentId(Long equipmentId);
    List<MaintenancePlan> findDueMaintenancePlans(LocalDate date);
    List<MaintenancePlan> findOverdueMaintenancePlans(LocalDate date);
    boolean existsByCode(String code);
}
