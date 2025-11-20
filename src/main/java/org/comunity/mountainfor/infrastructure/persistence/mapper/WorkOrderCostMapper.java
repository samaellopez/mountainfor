package org.comunity.mountainfor.infrastructure.persistence.mapper;

import org.comunity.mountainfor.domain.model.cost.WorkOrderCost;
import org.comunity.mountainfor.infrastructure.persistence.jpa.entity.WorkOrderCostJpaEntity;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class WorkOrderCostMapper {
    
    public WorkOrderCost toDomain(WorkOrderCostJpaEntity jpaEntity) {
        if (jpaEntity == null) return null;
        
        WorkOrderCost cost = WorkOrderCost.create(
            jpaEntity.getWorkOrderId(),
            jpaEntity.getLaborCost(),
            jpaEntity.getSparePartsCost(),
            jpaEntity.getOtherCosts()
        );
        cost.setId(jpaEntity.getId());
        
        return cost;
    }
    
    public WorkOrderCostJpaEntity toJpaEntity(WorkOrderCost domain) {
        if (domain == null) return null;
        
        WorkOrderCostJpaEntity jpaEntity = new WorkOrderCostJpaEntity();
        jpaEntity.setId(domain.getId());
        jpaEntity.setWorkOrderId(domain.getWorkOrderId());
        jpaEntity.setLaborCost(domain.getLaborCost());
        jpaEntity.setSparePartsCost(domain.getSparePartsCost());
        jpaEntity.setOtherCosts(domain.getOtherCosts());
        jpaEntity.setTotalCost(domain.getTotalCost());
        
        return jpaEntity;
    }
}
