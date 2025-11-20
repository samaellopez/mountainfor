package org.comunity.mountainfor.infrastructure.persistence.mapper;

import org.comunity.mountainfor.domain.model.equipment.Equipment;
import org.comunity.mountainfor.infrastructure.persistence.jpa.entity.EquipmentJpaEntity;
import jakarta.enterprise.context.ApplicationScoped;

/**
 * Mapper between Equipment domain entity and EquipmentJpaEntity.
 * This keeps the domain layer clean from JPA concerns.
 */
@ApplicationScoped
public class EquipmentMapper {
    
    public Equipment toDomain(EquipmentJpaEntity jpaEntity) {
        if (jpaEntity == null) {
            return null;
        }
        
        Equipment equipment = Equipment.create(
            jpaEntity.getCode(),
            jpaEntity.getDescription(),
            jpaEntity.getType(),
            jpaEntity.getLocation(),
            jpaEntity.getCostCenter(),
            jpaEntity.getInstallationDate(),
            jpaEntity.getEstimatedLifeYears(),
            jpaEntity.getParentEquipmentId()
        );
        equipment.setId(jpaEntity.getId());
        equipment.setStatus(jpaEntity.getStatus());
        
        return equipment;
    }
    
    public EquipmentJpaEntity toJpaEntity(Equipment domain) {
        if (domain == null) {
            return null;
        }
        
        EquipmentJpaEntity jpaEntity = new EquipmentJpaEntity();
        jpaEntity.setId(domain.getId());
        jpaEntity.setCode(domain.getCode());
        jpaEntity.setDescription(domain.getDescription());
        jpaEntity.setType(domain.getType());
        jpaEntity.setLocation(domain.getLocation());
        jpaEntity.setCostCenter(domain.getCostCenter());
        jpaEntity.setStatus(domain.getStatus());
        jpaEntity.setInstallationDate(domain.getInstallationDate());
        jpaEntity.setEstimatedLifeYears(domain.getEstimatedLifeYears());
        jpaEntity.setParentEquipmentId(domain.getParentEquipmentId());
        
        return jpaEntity;
    }
}
