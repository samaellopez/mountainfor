package org.comunity.mountainfor.web.rest.workorder;

import org.comunity.mountainfor.domain.model.workorder.WorkOrder;
import org.comunity.mountainfor.domain.model.workorder.WorkOrderPriority;
import org.comunity.mountainfor.domain.model.workorder.WorkOrderStatus;
import org.comunity.mountainfor.domain.model.workorder.WorkOrderType;
import java.time.LocalDateTime;

/**
 * Response DTO for work order.
 */
public record WorkOrderResponse(
    Long id,
    Long equipmentId,
    WorkOrderType type,
    String description,
    String failureDescription,
    WorkOrderPriority priority,
    WorkOrderStatus status,
    LocalDateTime createdAt,
    LocalDateTime startedAt,
    LocalDateTime completedAt,
    String assignedTo,
    Long maintenancePlanId
) {
    public static WorkOrderResponse from(WorkOrder workOrder) {
        return new WorkOrderResponse(
            workOrder.getId(),
            workOrder.getEquipmentId(),
            workOrder.getType(),
            workOrder.getDescription(),
            workOrder.getFailureDescription(),
            workOrder.getPriority(),
            workOrder.getStatus(),
            workOrder.getCreatedAt(),
            workOrder.getStartedAt(),
            workOrder.getCompletedAt(),
            workOrder.getAssignedTo(),
            workOrder.getMaintenancePlanId()
        );
    }
}
