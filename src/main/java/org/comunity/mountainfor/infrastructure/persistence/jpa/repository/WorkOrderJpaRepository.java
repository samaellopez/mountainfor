package org.comunity.mountainfor.infrastructure.persistence.jpa.repository;

import org.comunity.mountainfor.domain.model.workorder.WorkOrderStatus;
import org.comunity.mountainfor.infrastructure.persistence.jpa.entity.WorkOrderJpaEntity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * JPA Repository for WorkOrder persistence operations.
 */
@ApplicationScoped
public class WorkOrderJpaRepository {
    
    @PersistenceContext
    EntityManager entityManager;
    
    @Transactional
    public WorkOrderJpaEntity save(WorkOrderJpaEntity entity) {
        if (entity.getId() == null) {
            entityManager.persist(entity);
            return entity;
        } else {
            return entityManager.merge(entity);
        }
    }
    
    public Optional<WorkOrderJpaEntity> findById(Long id) {
        return Optional.ofNullable(entityManager.find(WorkOrderJpaEntity.class, id));
    }
    
    public List<WorkOrderJpaEntity> findAll() {
        return entityManager
            .createQuery("SELECT w FROM WorkOrderJpaEntity w", WorkOrderJpaEntity.class)
            .getResultList();
    }
    
    public List<WorkOrderJpaEntity> findByEquipmentId(Long equipmentId) {
        return entityManager
            .createQuery("SELECT w FROM WorkOrderJpaEntity w WHERE w.equipmentId = :equipmentId", WorkOrderJpaEntity.class)
            .setParameter("equipmentId", equipmentId)
            .getResultList();
    }
    
    public List<WorkOrderJpaEntity> findByStatus(WorkOrderStatus status) {
        return entityManager
            .createQuery("SELECT w FROM WorkOrderJpaEntity w WHERE w.status = :status", WorkOrderJpaEntity.class)
            .setParameter("status", status)
            .getResultList();
    }
    
    public List<WorkOrderJpaEntity> findByAssignedTo(String assignedTo) {
        return entityManager
            .createQuery("SELECT w FROM WorkOrderJpaEntity w WHERE w.assignedTo = :assignedTo", WorkOrderJpaEntity.class)
            .setParameter("assignedTo", assignedTo)
            .getResultList();
    }
    
    @Transactional
    public void deleteById(Long id) {
        WorkOrderJpaEntity entity = entityManager.find(WorkOrderJpaEntity.class, id);
        if (entity != null) {
            entityManager.remove(entity);
        }
    }
}
