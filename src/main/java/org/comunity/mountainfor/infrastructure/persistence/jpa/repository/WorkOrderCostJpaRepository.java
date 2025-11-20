package org.comunity.mountainfor.infrastructure.persistence.jpa.repository;

import org.comunity.mountainfor.infrastructure.persistence.jpa.entity.WorkOrderCostJpaEntity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class WorkOrderCostJpaRepository {
    
    @PersistenceContext
    EntityManager entityManager;
    
    @Transactional
    public WorkOrderCostJpaEntity save(WorkOrderCostJpaEntity entity) {
        if (entity.getId() == null) {
            entityManager.persist(entity);
            return entity;
        } else {
            return entityManager.merge(entity);
        }
    }
    
    public Optional<WorkOrderCostJpaEntity> findByWorkOrderId(Long workOrderId) {
        List<WorkOrderCostJpaEntity> results = entityManager
            .createQuery("SELECT c FROM WorkOrderCostJpaEntity c WHERE c.workOrderId = :workOrderId", WorkOrderCostJpaEntity.class)
            .setParameter("workOrderId", workOrderId)
            .getResultList();
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }
}
