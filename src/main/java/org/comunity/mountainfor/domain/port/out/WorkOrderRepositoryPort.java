package org.comunity.mountainfor.domain.port.out;

import org.comunity.mountainfor.domain.model.workorder.WorkOrder;
import org.comunity.mountainfor.domain.model.workorder.WorkOrderStatus;
import java.util.List;
import java.util.Optional;

/**
 * Port (interface) for WorkOrder repository.
 * This is the contract that infrastructure adapters must implement.
 */
public interface WorkOrderRepositoryPort {
    
    WorkOrder save(WorkOrder workOrder);
    
    Optional<WorkOrder> findById(Long id);
    
    List<WorkOrder> findAll();
    
    List<WorkOrder> findByEquipmentId(Long equipmentId);
    
    List<WorkOrder> findByStatus(WorkOrderStatus status);
    
    List<WorkOrder> findByAssignedTo(String assignedTo);
    
    void deleteById(Long id);
}
