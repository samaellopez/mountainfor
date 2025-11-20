package org.comunity.mountainfor.infrastructure.persistence.jpa.repository;

import org.comunity.mountainfor.infrastructure.persistence.jpa.entity.SparePartJpaEntity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class SparePartJpaRepository {
    
    @PersistenceContext
    EntityManager entityManager;
    
    @Transactional
    public SparePartJpaEntity save(SparePartJpaEntity entity) {
        if (entity.getId() == null) {
            entityManager.persist(entity);
            return entity;
        } else {
            return entityManager.merge(entity);
        }
    }
    
    public Optional<SparePartJpaEntity> findById(Long id) {
        return Optional.ofNullable(entityManager.find(SparePartJpaEntity.class, id));
    }
    
    public Optional<SparePartJpaEntity> findByCode(String code) {
        List<SparePartJpaEntity> results = entityManager
            .createQuery("SELECT s FROM SparePartJpaEntity s WHERE s.code = :code", SparePartJpaEntity.class)
            .setParameter("code", code)
            .getResultList();
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }
    
    public List<SparePartJpaEntity> findAll() {
        return entityManager
            .createQuery("SELECT s FROM SparePartJpaEntity s", SparePartJpaEntity.class)
            .getResultList();
    }
    
    public List<SparePartJpaEntity> findBelowMinimum() {
        return entityManager
            .createQuery("SELECT s FROM SparePartJpaEntity s WHERE s.currentStock < s.minimumStock", SparePartJpaEntity.class)
            .getResultList();
    }
    
    public boolean existsByCode(String code) {
        Long count = entityManager
            .createQuery("SELECT COUNT(s) FROM SparePartJpaEntity s WHERE s.code = :code", Long.class)
            .setParameter("code", code)
            .getSingleResult();
        return count > 0;
    }
}
