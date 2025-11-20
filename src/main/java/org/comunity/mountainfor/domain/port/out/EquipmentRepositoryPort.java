package org.comunity.mountainfor.domain.port.out;

import org.comunity.mountainfor.domain.model.equipment.Equipment;
import java.util.List;
import java.util.Optional;

/**
 * Port (interface) for Equipment repository.
 * This is the contract that infrastructure adapters must implement.
 */
public interface EquipmentRepositoryPort {
    
    Equipment save(Equipment equipment);
    
    Optional<Equipment> findById(Long id);
    
    Optional<Equipment> findByCode(String code);
    
    List<Equipment> findAll();
    
    List<Equipment> findByLocation(String location);
    
    void deleteById(Long id);
    
    boolean existsByCode(String code);
}
