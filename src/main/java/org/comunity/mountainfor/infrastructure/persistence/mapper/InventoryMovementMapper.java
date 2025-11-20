package org.comunity.mountainfor.infrastructure.persistence.mapper;

import org.comunity.mountainfor.domain.model.sparepart.InventoryMovement;
import org.comunity.mountainfor.domain.model.sparepart.MovementType;
import org.comunity.mountainfor.infrastructure.persistence.jpa.entity.InventoryMovementJpaEntity;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class InventoryMovementMapper {
    
    public InventoryMovement toDomain(InventoryMovementJpaEntity jpaEntity) {
        if (jpaEntity == null) return null;
        
        InventoryMovement movement;
        if (jpaEntity.getType() == MovementType.ENTRY) {
            movement = InventoryMovement.createEntry(
                jpaEntity.getSparePartId(),
                jpaEntity.getQuantity(),
                jpaEntity.getReason(),
                jpaEntity.getPerformedBy()
            );
        } else {
            movement = InventoryMovement.createExit(
                jpaEntity.getSparePartId(),
                jpaEntity.getQuantity(),
                jpaEntity.getReason(),
                jpaEntity.getWorkOrderId(),
                jpaEntity.getPerformedBy()
            );
        }
        
        movement.setId(jpaEntity.getId());
        movement.setMovementDate(jpaEntity.getMovementDate());
        
        return movement;
    }
    
    public InventoryMovementJpaEntity toJpaEntity(InventoryMovement domain) {
        if (domain == null) return null;
        
        InventoryMovementJpaEntity jpaEntity = new InventoryMovementJpaEntity();
        jpaEntity.setId(domain.getId());
        jpaEntity.setSparePartId(domain.getSparePartId());
        jpaEntity.setType(domain.getType());
        jpaEntity.setQuantity(domain.getQuantity());
        jpaEntity.setReason(domain.getReason());
        jpaEntity.setWorkOrderId(domain.getWorkOrderId());
        jpaEntity.setMovementDate(domain.getMovementDate());
        jpaEntity.setPerformedBy(domain.getPerformedBy());
        
        return jpaEntity;
    }
}
