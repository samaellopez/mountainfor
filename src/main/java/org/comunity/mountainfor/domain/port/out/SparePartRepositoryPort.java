package org.comunity.mountainfor.domain.port.out;

import org.comunity.mountainfor.domain.model.sparepart.SparePart;
import java.util.List;
import java.util.Optional;

public interface SparePartRepositoryPort {
    SparePart save(SparePart sparePart);
    Optional<SparePart> findById(Long id);
    Optional<SparePart> findByCode(String code);
    List<SparePart> findAll();
    List<SparePart> findBelowMinimum();
    boolean existsByCode(String code);
}
