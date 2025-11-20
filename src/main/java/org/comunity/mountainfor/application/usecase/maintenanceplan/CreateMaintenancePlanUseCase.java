package org.comunity.mountainfor.application.usecase.maintenanceplan;

import org.comunity.mountainfor.domain.model.maintenanceplan.FrequencyType;
import org.comunity.mountainfor.domain.model.maintenanceplan.MaintenancePlan;
import org.comunity.mountainfor.domain.model.maintenanceplan.MaintenanceType;
import org.comunity.mountainfor.domain.port.out.EquipmentRepositoryPort;
import org.comunity.mountainfor.domain.port.out.MaintenancePlanRepositoryPort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.time.LocalDate;
import java.util.List;

@ApplicationScoped
public class CreateMaintenancePlanUseCase {
    
    private final MaintenancePlanRepositoryPort planRepository;
    private final EquipmentRepositoryPort equipmentRepository;
    
    @Inject
    public CreateMaintenancePlanUseCase(MaintenancePlanRepositoryPort planRepository,
                                       EquipmentRepositoryPort equipmentRepository) {
        this.planRepository = planRepository;
        this.equipmentRepository = equipmentRepository;
    }
    
    public MaintenancePlan execute(CreateMaintenancePlanCommand command) {
        if (planRepository.existsByCode(command.code())) {
            throw new IllegalArgumentException("Maintenance plan with code " + command.code() + " already exists");
        }
        
        equipmentRepository.findById(command.equipmentId())
            .orElseThrow(() -> new IllegalArgumentException("Equipment not found: " + command.equipmentId()));
        
        MaintenancePlan plan = MaintenancePlan.create(
            command.code(),
            command.description(),
            command.equipmentId(),
            command.type(),
            command.frequencyType(),
            command.frequencyValue(),
            command.toleranceDays(),
            command.nextMaintenanceDate(),
            command.estimatedDurationHours(),
            command.responsibleRole(),
            command.checklist()
        );
        
        return planRepository.save(plan);
    }
    
    public record CreateMaintenancePlanCommand(
        String code,
        String description,
        Long equipmentId,
        MaintenanceType type,
        FrequencyType frequencyType,
        Integer frequencyValue,
        Integer toleranceDays,
        LocalDate nextMaintenanceDate,
        Integer estimatedDurationHours,
        String responsibleRole,
        List<String> checklist
    ) {}
}
