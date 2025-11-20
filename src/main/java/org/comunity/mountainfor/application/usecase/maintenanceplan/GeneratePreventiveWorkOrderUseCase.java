package org.comunity.mountainfor.application.usecase.maintenanceplan;

import org.comunity.mountainfor.domain.model.equipment.Equipment;
import org.comunity.mountainfor.domain.model.maintenanceplan.MaintenancePlan;
import org.comunity.mountainfor.domain.model.workorder.WorkOrder;
import org.comunity.mountainfor.domain.model.workorder.WorkOrderPriority;
import org.comunity.mountainfor.domain.port.out.EquipmentRepositoryPort;
import org.comunity.mountainfor.domain.port.out.MaintenancePlanRepositoryPort;
import org.comunity.mountainfor.domain.port.out.WorkOrderRepositoryPort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class GeneratePreventiveWorkOrderUseCase {
    
    private final MaintenancePlanRepositoryPort planRepository;
    private final WorkOrderRepositoryPort workOrderRepository;
    private final EquipmentRepositoryPort equipmentRepository;
    
    @Inject
    public GeneratePreventiveWorkOrderUseCase(MaintenancePlanRepositoryPort planRepository,
                                             WorkOrderRepositoryPort workOrderRepository,
                                             EquipmentRepositoryPort equipmentRepository) {
        this.planRepository = planRepository;
        this.workOrderRepository = workOrderRepository;
        this.equipmentRepository = equipmentRepository;
    }
    
    public List<WorkOrder> execute() {
        LocalDate today = LocalDate.now();
        List<MaintenancePlan> duePlans = planRepository.findDueMaintenancePlans(today);
        List<WorkOrder> generatedOrders = new ArrayList<>();
        
        for (MaintenancePlan plan : duePlans) {
            Equipment equipment = equipmentRepository.findById(plan.getEquipmentId())
                .orElse(null);
            
            if (equipment != null && equipment.getStatus().toString().equals("ACTIVE")) {
                WorkOrder workOrder = WorkOrder.createPreventive(
                    equipment.getId(),
                    "Mantenimiento Preventivo: " + plan.getDescription(),
                    plan.getResponsibleRole(),
                    plan.getId()
                );
                
                equipment.markAsInMaintenance();
                equipmentRepository.save(equipment);
                
                WorkOrder saved = workOrderRepository.save(workOrder);
                generatedOrders.add(saved);
                
                plan.calculateNextMaintenanceDate(today);
                planRepository.save(plan);
            }
        }
        
        return generatedOrders;
    }
}
