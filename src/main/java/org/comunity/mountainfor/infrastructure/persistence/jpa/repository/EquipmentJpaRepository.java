package org.comunity.mountainfor.infrastructure.persistence.jpa.repository;

import org.comunity.mountainfor.infrastructure.persistence.jpa.entity.EquipmentJpaEntity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * JPA Repository for Equipment persistence operations.
 */
@ApplicationScoped
public class EquipmentJpaRepository {
    
    @PersistenceContext
    EntityManager entityManager;
    
    @Transactional
    public EquipmentJpaEntity save(EquipmentJpaEntity entity) {
        if (entity.getId() == null) {
            entityManager.persist(entity);
            return entity;
        } else {
            return entityManager.merge(entity);
        }
    }
    
    public Optional<EquipmentJpaEntity> findById(Long id) {
        return Optional.ofNullable(entityManager.find(EquipmentJpaEntity.class, id));
    }
    
    public Optional<EquipmentJpaEntity> findByCode(String code) {
        List<EquipmentJpaEntity> results = entityManager
            .createQuery("SELECT e FROM EquipmentJpaEntity e WHERE e.code = :code", EquipmentJpaEntity.class)
            .setParameter("code", code)
            .getResultList();
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }
    
    public List<EquipmentJpaEntity> findAll() {
        return entityManager
            .createQuery("SELECT e FROM EquipmentJpaEntity e", EquipmentJpaEntity.class)
            .getResultList();
    }
    
    public List<EquipmentJpaEntity> findByLocation(String location) {
        return entityManager
            .createQuery("SELECT e FROM EquipmentJpaEntity e WHERE e.location = :location", EquipmentJpaEntity.class)
            .setParameter("location", location)
            .getResultList();
    }
    
    @Transactional
    public void deleteById(Long id) {
        EquipmentJpaEntity entity = entityManager.find(EquipmentJpaEntity.class, id);
        if (entity != null) {
            entityManager.remove(entity);
        }
    }
    
    public boolean existsByCode(String code) {
        Long count = entityManager
            .createQuery("SELECT COUNT(e) FROM EquipmentJpaEntity e WHERE e.code = :code", Long.class)
            .setParameter("code", code)
            .getSingleResult();
        return count > 0;
    }
}
