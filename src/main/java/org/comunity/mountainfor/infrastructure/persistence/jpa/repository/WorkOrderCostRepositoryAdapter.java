package org.comunity.mountainfor.infrastructure.persistence.jpa.repository;

import org.comunity.mountainfor.domain.model.cost.WorkOrderCost;
import org.comunity.mountainfor.domain.port.out.WorkOrderCostRepositoryPort;
import org.comunity.mountainfor.infrastructure.persistence.mapper.WorkOrderCostMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.Optional;

@ApplicationScoped
public class WorkOrderCostRepositoryAdapter implements WorkOrderCostRepositoryPort {
    
    private final WorkOrderCostJpaRepository jpaRepository;
    private final WorkOrderCostMapper mapper;
    
    @Inject
    public WorkOrderCostRepositoryAdapter(WorkOrderCostJpaRepository jpaRepository,
                                         WorkOrderCostMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }
    
    @Override
    public WorkOrderCost save(WorkOrderCost cost) {
        var jpaEntity = mapper.toJpaEntity(cost);
        var saved = jpaRepository.save(jpaEntity);
        return mapper.toDomain(saved);
    }
    
    @Override
    public Optional<WorkOrderCost> findByWorkOrderId(Long workOrderId) {
        return jpaRepository.findByWorkOrderId(workOrderId).map(mapper::toDomain);
    }
}
