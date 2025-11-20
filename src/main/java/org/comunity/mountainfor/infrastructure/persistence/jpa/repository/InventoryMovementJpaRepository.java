package org.comunity.mountainfor.infrastructure.persistence.jpa.repository;

import org.comunity.mountainfor.infrastructure.persistence.jpa.entity.InventoryMovementJpaEntity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class InventoryMovementJpaRepository {
    
    @PersistenceContext
    EntityManager entityManager;
    
    @Transactional
    public InventoryMovementJpaEntity save(InventoryMovementJpaEntity entity) {
        if (entity.getId() == null) {
            entityManager.persist(entity);
            return entity;
        } else {
            return entityManager.merge(entity);
        }
    }
    
    public List<InventoryMovementJpaEntity> findBySparePartId(Long sparePartId) {
        return entityManager
            .createQuery("SELECT m FROM InventoryMovementJpaEntity m WHERE m.sparePartId = :sparePartId ORDER BY m.movementDate DESC", InventoryMovementJpaEntity.class)
            .setParameter("sparePartId", sparePartId)
            .getResultList();
    }
    
    public List<InventoryMovementJpaEntity> findByWorkOrderId(Long workOrderId) {
        return entityManager
            .createQuery("SELECT m FROM InventoryMovementJpaEntity m WHERE m.workOrderId = :workOrderId", InventoryMovementJpaEntity.class)
            .setParameter("workOrderId", workOrderId)
            .getResultList();
    }
}
