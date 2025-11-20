package org.comunity.mountainfor.domain.model.workorder;

import java.time.LocalDateTime;

/**
 * Domain entity representing a work order (both corrective and preventive maintenance).
 * Pure domain entity without JPA annotations.
 */
public class WorkOrder {
    
    private Long id;
    private Long equipmentId;
    private WorkOrderType type;
    private String description;
    private String failureDescription;
    private WorkOrderPriority priority;
    private WorkOrderStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime startedAt;
    private LocalDateTime completedAt;
    private String assignedTo;
    private Long maintenancePlanId; // Only for preventive maintenance
    
    private WorkOrder(Long equipmentId, WorkOrderType type, String description,
                     String failureDescription, WorkOrderPriority priority, String assignedTo,
                     Long maintenancePlanId) {
        this.equipmentId = equipmentId;
        this.type = type;
        this.description = description;
        this.failureDescription = failureDescription;
        this.priority = priority;
        this.status = WorkOrderStatus.OPEN;
        this.createdAt = LocalDateTime.now();
        this.assignedTo = assignedTo;
        this.maintenancePlanId = maintenancePlanId;
    }
    
    // Factory method for corrective maintenance work order
    public static WorkOrder createCorrective(Long equipmentId, String failureDescription, 
                                            WorkOrderPriority priority, String assignedTo) {
        validateEquipmentId(equipmentId);
        validateFailureDescription(failureDescription);
        return new WorkOrder(equipmentId, WorkOrderType.CORRECTIVE, 
                           "Corrective Maintenance", failureDescription, priority, assignedTo, null);
    }
    
    // Factory method for preventive maintenance work order
    public static WorkOrder createPreventive(Long equipmentId, String description,
                                           String assignedTo, Long maintenancePlanId) {
        validateEquipmentId(equipmentId);
        if (description == null || description.trim().isEmpty()) {
            throw new IllegalArgumentException("Description cannot be empty");
        }
        return new WorkOrder(equipmentId, WorkOrderType.PREVENTIVE, description,
                           null, WorkOrderPriority.MEDIUM, assignedTo, maintenancePlanId);
    }
    
    private static void validateEquipmentId(Long equipmentId) {
        if (equipmentId == null) {
            throw new IllegalArgumentException("Equipment ID cannot be null");
        }
    }
    
    private static void validateFailureDescription(String failureDescription) {
        if (failureDescription == null || failureDescription.trim().isEmpty()) {
            throw new IllegalArgumentException("Failure description cannot be empty");
        }
    }
    
    // Business rule: Start work order
    public void start() {
        if (this.status != WorkOrderStatus.OPEN) {
            throw new IllegalStateException("Can only start work orders that are OPEN");
        }
        this.status = WorkOrderStatus.IN_PROGRESS;
        this.startedAt = LocalDateTime.now();
    }
    
    // Business rule: Wait for spare parts
    public void waitForSpareParts() {
        if (this.status != WorkOrderStatus.IN_PROGRESS) {
            throw new IllegalStateException("Can only wait for parts when work order is IN_PROGRESS");
        }
        this.status = WorkOrderStatus.WAITING_FOR_PARTS;
    }
    
    // Business rule: Resume from waiting
    public void resume() {
        if (this.status != WorkOrderStatus.WAITING_FOR_PARTS) {
            throw new IllegalStateException("Can only resume from WAITING_FOR_PARTS status");
        }
        this.status = WorkOrderStatus.IN_PROGRESS;
    }
    
    // Business rule: Complete work order
    public void complete() {
        if (this.status != WorkOrderStatus.IN_PROGRESS) {
            throw new IllegalStateException("Can only complete work orders that are IN_PROGRESS");
        }
        this.status = WorkOrderStatus.CLOSED;
        this.completedAt = LocalDateTime.now();
    }
    
    // Getters
    public Long getId() {
        return id;
    }
    
    public Long getEquipmentId() {
        return equipmentId;
    }
    
    public WorkOrderType getType() {
        return type;
    }
    
    public String getDescription() {
        return description;
    }
    
    public String getFailureDescription() {
        return failureDescription;
    }
    
    public WorkOrderPriority getPriority() {
        return priority;
    }
    
    public WorkOrderStatus getStatus() {
        return status;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public LocalDateTime getStartedAt() {
        return startedAt;
    }
    
    public LocalDateTime getCompletedAt() {
        return completedAt;
    }
    
    public String getAssignedTo() {
        return assignedTo;
    }
    
    public Long getMaintenancePlanId() {
        return maintenancePlanId;
    }
    
    // Setters (for infrastructure layer - JPA mapping)
    public void setId(Long id) {
        this.id = id;
    }
    
    public void setEquipmentId(Long equipmentId) {
        this.equipmentId = equipmentId;
    }
    
    public void setType(WorkOrderType type) {
        this.type = type;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public void setFailureDescription(String failureDescription) {
        this.failureDescription = failureDescription;
    }
    
    public void setPriority(WorkOrderPriority priority) {
        this.priority = priority;
    }
    
    public void setStatus(WorkOrderStatus status) {
        this.status = status;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public void setStartedAt(LocalDateTime startedAt) {
        this.startedAt = startedAt;
    }
    
    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }
    
    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }
    
    public void setMaintenancePlanId(Long maintenancePlanId) {
        this.maintenancePlanId = maintenancePlanId;
    }
}
