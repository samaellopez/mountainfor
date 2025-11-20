package org.comunity.mountainfor.infrastructure.persistence.jpa.repository;

import org.comunity.mountainfor.domain.model.equipment.Equipment;
import org.comunity.mountainfor.domain.port.out.EquipmentRepositoryPort;
import org.comunity.mountainfor.infrastructure.persistence.mapper.EquipmentMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Adapter that implements the EquipmentRepositoryPort using JPA.
 * This is the infrastructure implementation of the domain port.
 */
@ApplicationScoped
public class EquipmentRepositoryAdapter implements EquipmentRepositoryPort {
    
    private final EquipmentJpaRepository jpaRepository;
    private final EquipmentMapper mapper;
    
    @Inject
    public EquipmentRepositoryAdapter(EquipmentJpaRepository jpaRepository, EquipmentMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }
    
    @Override
    public Equipment save(Equipment equipment) {
        var jpaEntity = mapper.toJpaEntity(equipment);
        var saved = jpaRepository.save(jpaEntity);
        return mapper.toDomain(saved);
    }
    
    @Override
    public Optional<Equipment> findById(Long id) {
        return jpaRepository.findById(id)
            .map(mapper::toDomain);
    }
    
    @Override
    public Optional<Equipment> findByCode(String code) {
        return jpaRepository.findByCode(code)
            .map(mapper::toDomain);
    }
    
    @Override
    public List<Equipment> findAll() {
        return jpaRepository.findAll().stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }
    
    @Override
    public List<Equipment> findByLocation(String location) {
        return jpaRepository.findByLocation(location).stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }
    
    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }
    
    @Override
    public boolean existsByCode(String code) {
        return jpaRepository.existsByCode(code);
    }
}
