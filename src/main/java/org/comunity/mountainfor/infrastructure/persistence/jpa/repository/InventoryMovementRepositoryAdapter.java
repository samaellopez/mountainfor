package org.comunity.mountainfor.infrastructure.persistence.jpa.repository;

import org.comunity.mountainfor.domain.model.sparepart.InventoryMovement;
import org.comunity.mountainfor.domain.port.out.InventoryMovementRepositoryPort;
import org.comunity.mountainfor.infrastructure.persistence.mapper.InventoryMovementMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class InventoryMovementRepositoryAdapter implements InventoryMovementRepositoryPort {
    
    private final InventoryMovementJpaRepository jpaRepository;
    private final InventoryMovementMapper mapper;
    
    @Inject
    public InventoryMovementRepositoryAdapter(InventoryMovementJpaRepository jpaRepository,
                                             InventoryMovementMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }
    
    @Override
    public InventoryMovement save(InventoryMovement movement) {
        var jpaEntity = mapper.toJpaEntity(movement);
        var saved = jpaRepository.save(jpaEntity);
        return mapper.toDomain(saved);
    }
    
    @Override
    public List<InventoryMovement> findBySparePartId(Long sparePartId) {
        return jpaRepository.findBySparePartId(sparePartId).stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }
    
    @Override
    public List<InventoryMovement> findByWorkOrderId(Long workOrderId) {
        return jpaRepository.findByWorkOrderId(workOrderId).stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }
}
