package org.comunity.mountainfor.application.usecase.sparepart;

import org.comunity.mountainfor.domain.model.sparepart.InventoryMovement;
import org.comunity.mountainfor.domain.model.sparepart.SparePart;
import org.comunity.mountainfor.domain.port.out.InventoryMovementRepositoryPort;
import org.comunity.mountainfor.domain.port.out.SparePartRepositoryPort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class ConsumeSparePartUseCase {
    
    private final SparePartRepositoryPort sparePartRepository;
    private final InventoryMovementRepositoryPort movementRepository;
    
    @Inject
    public ConsumeSparePartUseCase(SparePartRepositoryPort sparePartRepository,
                                   InventoryMovementRepositoryPort movementRepository) {
        this.sparePartRepository = sparePartRepository;
        this.movementRepository = movementRepository;
    }
    
    public InventoryMovement execute(ConsumeSparePartCommand command) {
        SparePart sparePart = sparePartRepository.findById(command.sparePartId())
            .orElseThrow(() -> new IllegalArgumentException("Spare part not found: " + command.sparePartId()));
        
        sparePart.consumeStock(command.quantity());
        sparePartRepository.save(sparePart);
        
        InventoryMovement movement = InventoryMovement.createExit(
            command.sparePartId(),
            command.quantity(),
            command.reason(),
            command.workOrderId(),
            command.performedBy()
        );
        
        return movementRepository.save(movement);
    }
    
    public record ConsumeSparePartCommand(
        Long sparePartId,
        Integer quantity,
        String reason,
        Long workOrderId,
        String performedBy
    ) {}
}
