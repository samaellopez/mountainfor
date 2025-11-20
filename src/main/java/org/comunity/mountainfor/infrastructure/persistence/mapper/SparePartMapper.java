package org.comunity.mountainfor.infrastructure.persistence.mapper;

import org.comunity.mountainfor.domain.model.sparepart.SparePart;
import org.comunity.mountainfor.infrastructure.persistence.jpa.entity.SparePartJpaEntity;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class SparePartMapper {
    
    public SparePart toDomain(SparePartJpaEntity jpaEntity) {
        if (jpaEntity == null) return null;
        
        SparePart sparePart = SparePart.create(
            jpaEntity.getCode(),
            jpaEntity.getDescription(),
            jpaEntity.getUnitOfMeasure(),
            jpaEntity.getMinimumStock(),
            jpaEntity.getMaximumStock(),
            jpaEntity.getLocation(),
            jpaEntity.getUnitPrice()
        );
        sparePart.setId(jpaEntity.getId());
        sparePart.setCurrentStock(jpaEntity.getCurrentStock());
        
        return sparePart;
    }
    
    public SparePartJpaEntity toJpaEntity(SparePart domain) {
        if (domain == null) return null;
        
        SparePartJpaEntity jpaEntity = new SparePartJpaEntity();
        jpaEntity.setId(domain.getId());
        jpaEntity.setCode(domain.getCode());
        jpaEntity.setDescription(domain.getDescription());
        jpaEntity.setUnitOfMeasure(domain.getUnitOfMeasure());
        jpaEntity.setCurrentStock(domain.getCurrentStock());
        jpaEntity.setMinimumStock(domain.getMinimumStock());
        jpaEntity.setMaximumStock(domain.getMaximumStock());
        jpaEntity.setLocation(domain.getLocation());
        jpaEntity.setUnitPrice(domain.getUnitPrice());
        
        return jpaEntity;
    }
}
