package org.comunity.mountainfor.infrastructure.persistence.jpa.repository;

import org.comunity.mountainfor.domain.model.workorder.WorkOrder;
import org.comunity.mountainfor.domain.model.workorder.WorkOrderStatus;
import org.comunity.mountainfor.domain.port.out.WorkOrderRepositoryPort;
import org.comunity.mountainfor.infrastructure.persistence.mapper.WorkOrderMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Adapter that implements the WorkOrderRepositoryPort using JPA.
 * This is the infrastructure implementation of the domain port.
 */
@ApplicationScoped
public class WorkOrderRepositoryAdapter implements WorkOrderRepositoryPort {
    
    private final WorkOrderJpaRepository jpaRepository;
    private final WorkOrderMapper mapper;
    
    @Inject
    public WorkOrderRepositoryAdapter(WorkOrderJpaRepository jpaRepository, WorkOrderMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }
    
    @Override
    public WorkOrder save(WorkOrder workOrder) {
        var jpaEntity = mapper.toJpaEntity(workOrder);
        var saved = jpaRepository.save(jpaEntity);
        return mapper.toDomain(saved);
    }
    
    @Override
    public Optional<WorkOrder> findById(Long id) {
        return jpaRepository.findById(id)
            .map(mapper::toDomain);
    }
    
    @Override
    public List<WorkOrder> findAll() {
        return jpaRepository.findAll().stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }
    
    @Override
    public List<WorkOrder> findByEquipmentId(Long equipmentId) {
        return jpaRepository.findByEquipmentId(equipmentId).stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }
    
    @Override
    public List<WorkOrder> findByStatus(WorkOrderStatus status) {
        return jpaRepository.findByStatus(status).stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }
    
    @Override
    public List<WorkOrder> findByAssignedTo(String assignedTo) {
        return jpaRepository.findByAssignedTo(assignedTo).stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }
    
    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }
}
