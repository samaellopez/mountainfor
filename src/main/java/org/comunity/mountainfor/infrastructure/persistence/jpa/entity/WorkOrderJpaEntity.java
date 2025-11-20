package org.comunity.mountainfor.infrastructure.persistence.jpa.entity;

import jakarta.persistence.*;
import org.comunity.mountainfor.domain.model.workorder.WorkOrderPriority;
import org.comunity.mountainfor.domain.model.workorder.WorkOrderStatus;
import org.comunity.mountainfor.domain.model.workorder.WorkOrderType;
import java.time.LocalDateTime;

/**
 * JPA entity for WorkOrder persistence.
 * This is the infrastructure representation with JPA annotations.
 */
@Entity
@Table(name = "work_order")
public class WorkOrderJpaEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "equipment_id", nullable = false)
    private Long equipmentId;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, length = 50)
    private WorkOrderType type;
    
    @Column(name = "description", nullable = false, length = 500)
    private String description;
    
    @Column(name = "failure_description", length = 1000)
    private String failureDescription;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "priority", nullable = false, length = 50)
    private WorkOrderPriority priority;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 50)
    private WorkOrderStatus status;
    
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "started_at")
    private LocalDateTime startedAt;
    
    @Column(name = "completed_at")
    private LocalDateTime completedAt;
    
    @Column(name = "assigned_to", length = 100)
    private String assignedTo;
    
    @Column(name = "maintenance_plan_id")
    private Long maintenancePlanId;
    
    // Default constructor for JPA
    public WorkOrderJpaEntity() {
    }
    
    // Getters and setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getEquipmentId() {
        return equipmentId;
    }
    
    public void setEquipmentId(Long equipmentId) {
        this.equipmentId = equipmentId;
    }
    
    public WorkOrderType getType() {
        return type;
    }
    
    public void setType(WorkOrderType type) {
        this.type = type;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getFailureDescription() {
        return failureDescription;
    }
    
    public void setFailureDescription(String failureDescription) {
        this.failureDescription = failureDescription;
    }
    
    public WorkOrderPriority getPriority() {
        return priority;
    }
    
    public void setPriority(WorkOrderPriority priority) {
        this.priority = priority;
    }
    
    public WorkOrderStatus getStatus() {
        return status;
    }
    
    public void setStatus(WorkOrderStatus status) {
        this.status = status;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getStartedAt() {
        return startedAt;
    }
    
    public void setStartedAt(LocalDateTime startedAt) {
        this.startedAt = startedAt;
    }
    
    public LocalDateTime getCompletedAt() {
        return completedAt;
    }
    
    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }
    
    public String getAssignedTo() {
        return assignedTo;
    }
    
    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }
    
    public Long getMaintenancePlanId() {
        return maintenancePlanId;
    }
    
    public void setMaintenancePlanId(Long maintenancePlanId) {
        this.maintenancePlanId = maintenancePlanId;
    }
}
