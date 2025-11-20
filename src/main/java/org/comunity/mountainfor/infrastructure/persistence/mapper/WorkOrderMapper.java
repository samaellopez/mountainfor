package org.comunity.mountainfor.infrastructure.persistence.mapper;

import org.comunity.mountainfor.domain.model.workorder.WorkOrder;
import org.comunity.mountainfor.infrastructure.persistence.jpa.entity.WorkOrderJpaEntity;
import jakarta.enterprise.context.ApplicationScoped;

/**
 * Mapper between WorkOrder domain entity and WorkOrderJpaEntity.
 * This keeps the domain layer clean from JPA concerns.
 */
@ApplicationScoped
public class WorkOrderMapper {
    
    public WorkOrder toDomain(WorkOrderJpaEntity jpaEntity) {
        if (jpaEntity == null) {
            return null;
        }
        
        // Create domain entity based on type
        WorkOrder workOrder;
        if (jpaEntity.getType() == org.comunity.mountainfor.domain.model.workorder.WorkOrderType.CORRECTIVE) {
            workOrder = WorkOrder.createCorrective(
                jpaEntity.getEquipmentId(),
                jpaEntity.getFailureDescription(),
                jpaEntity.getPriority(),
                jpaEntity.getAssignedTo()
            );
        } else {
            workOrder = WorkOrder.createPreventive(
                jpaEntity.getEquipmentId(),
                jpaEntity.getDescription(),
                jpaEntity.getAssignedTo(),
                jpaEntity.getMaintenancePlanId()
            );
        }
        
        // Set other fields
        workOrder.setId(jpaEntity.getId());
        workOrder.setStatus(jpaEntity.getStatus());
        workOrder.setCreatedAt(jpaEntity.getCreatedAt());
        workOrder.setStartedAt(jpaEntity.getStartedAt());
        workOrder.setCompletedAt(jpaEntity.getCompletedAt());
        
        return workOrder;
    }
    
    public WorkOrderJpaEntity toJpaEntity(WorkOrder domain) {
        if (domain == null) {
            return null;
        }
        
        WorkOrderJpaEntity jpaEntity = new WorkOrderJpaEntity();
        jpaEntity.setId(domain.getId());
        jpaEntity.setEquipmentId(domain.getEquipmentId());
        jpaEntity.setType(domain.getType());
        jpaEntity.setDescription(domain.getDescription());
        jpaEntity.setFailureDescription(domain.getFailureDescription());
        jpaEntity.setPriority(domain.getPriority());
        jpaEntity.setStatus(domain.getStatus());
        jpaEntity.setCreatedAt(domain.getCreatedAt());
        jpaEntity.setStartedAt(domain.getStartedAt());
        jpaEntity.setCompletedAt(domain.getCompletedAt());
        jpaEntity.setAssignedTo(domain.getAssignedTo());
        jpaEntity.setMaintenancePlanId(domain.getMaintenancePlanId());
        
        return jpaEntity;
    }
}
