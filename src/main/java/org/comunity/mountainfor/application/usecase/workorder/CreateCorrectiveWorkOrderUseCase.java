package org.comunity.mountainfor.application.usecase.workorder;

import org.comunity.mountainfor.domain.model.equipment.Equipment;
import org.comunity.mountainfor.domain.model.equipment.EquipmentStatus;
import org.comunity.mountainfor.domain.model.workorder.WorkOrder;
import org.comunity.mountainfor.domain.model.workorder.WorkOrderPriority;
import org.comunity.mountainfor.domain.port.out.EquipmentRepositoryPort;
import org.comunity.mountainfor.domain.port.out.WorkOrderRepositoryPort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

/**
 * Use case for creating a corrective work order.
 * Implements business rules for corrective maintenance.
 */
@ApplicationScoped
public class CreateCorrectiveWorkOrderUseCase {
    
    private final WorkOrderRepositoryPort workOrderRepository;
    private final EquipmentRepositoryPort equipmentRepository;
    
    @Inject
    public CreateCorrectiveWorkOrderUseCase(WorkOrderRepositoryPort workOrderRepository,
                                           EquipmentRepositoryPort equipmentRepository) {
        this.workOrderRepository = workOrderRepository;
        this.equipmentRepository = equipmentRepository;
    }
    
    public WorkOrder execute(CreateCorrectiveWorkOrderCommand command) {
        // Business rule: Equipment must exist
        Equipment equipment = equipmentRepository.findById(command.equipmentId())
            .orElseThrow(() -> new IllegalArgumentException("Equipment not found: " + command.equipmentId()));
        
        // Business rule: Cannot create work order for inactive equipment
        if (equipment.getStatus() == EquipmentStatus.INACTIVE) {
            throw new IllegalStateException("Cannot create work order for inactive equipment");
        }
        
        // Create work order using domain factory method
        WorkOrder workOrder = WorkOrder.createCorrective(
            command.equipmentId(),
            command.failureDescription(),
            command.priority(),
            command.assignedTo()
        );
        
        // Mark equipment as in maintenance
        equipment.markAsInMaintenance();
        equipmentRepository.save(equipment);
        
        // Save work order
        return workOrderRepository.save(workOrder);
    }
    
    public record CreateCorrectiveWorkOrderCommand(
        Long equipmentId,
        String failureDescription,
        WorkOrderPriority priority,
        String assignedTo
    ) {}
}
