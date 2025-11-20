package org.comunity.mountainfor.infrastructure.persistence.jpa.repository;

import org.comunity.mountainfor.domain.model.sparepart.SparePart;
import org.comunity.mountainfor.domain.port.out.SparePartRepositoryPort;
import org.comunity.mountainfor.infrastructure.persistence.mapper.SparePartMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
public class SparePartRepositoryAdapter implements SparePartRepositoryPort {
    
    private final SparePartJpaRepository jpaRepository;
    private final SparePartMapper mapper;
    
    @Inject
    public SparePartRepositoryAdapter(SparePartJpaRepository jpaRepository, SparePartMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }
    
    @Override
    public SparePart save(SparePart sparePart) {
        var jpaEntity = mapper.toJpaEntity(sparePart);
        var saved = jpaRepository.save(jpaEntity);
        return mapper.toDomain(saved);
    }
    
    @Override
    public Optional<SparePart> findById(Long id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }
    
    @Override
    public Optional<SparePart> findByCode(String code) {
        return jpaRepository.findByCode(code).map(mapper::toDomain);
    }
    
    @Override
    public List<SparePart> findAll() {
        return jpaRepository.findAll().stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }
    
    @Override
    public List<SparePart> findBelowMinimum() {
        return jpaRepository.findBelowMinimum().stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }
    
    @Override
    public boolean existsByCode(String code) {
        return jpaRepository.existsByCode(code);
    }
}
