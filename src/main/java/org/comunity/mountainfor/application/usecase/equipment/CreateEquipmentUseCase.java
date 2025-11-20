package org.comunity.mountainfor.application.usecase.equipment;

import org.comunity.mountainfor.domain.model.equipment.Equipment;
import org.comunity.mountainfor.domain.model.equipment.EquipmentType;
import org.comunity.mountainfor.domain.port.out.EquipmentRepositoryPort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.time.LocalDate;

/**
 * Use case for creating a new equipment.
 * This orchestrates the domain logic and repository operations.
 */
@ApplicationScoped
public class CreateEquipmentUseCase {
    
    private final EquipmentRepositoryPort equipmentRepository;
    
    @Inject
    public CreateEquipmentUseCase(EquipmentRepositoryPort equipmentRepository) {
        this.equipmentRepository = equipmentRepository;
    }
    
    public Equipment execute(CreateEquipmentCommand command) {
        // Business rule: Check if code already exists
        if (equipmentRepository.existsByCode(command.code())) {
            throw new IllegalArgumentException("Equipment with code " + command.code() + " already exists");
        }
        
        // Create domain entity using factory method
        Equipment equipment = Equipment.create(
            command.code(),
            command.description(),
            command.type(),
            command.location(),
            command.costCenter(),
            command.installationDate(),
            command.estimatedLifeYears(),
            command.parentEquipmentId()
        );
        
        // Save using repository port
        return equipmentRepository.save(equipment);
    }
    
    public record CreateEquipmentCommand(
        String code,
        String description,
        EquipmentType type,
        String location,
        String costCenter,
        LocalDate installationDate,
        Integer estimatedLifeYears,
        Long parentEquipmentId
    ) {}
}
