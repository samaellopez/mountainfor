package org.comunity.mountainfor.application.usecase.cost;

import org.comunity.mountainfor.domain.model.cost.WorkOrderCost;
import org.comunity.mountainfor.domain.model.sparepart.InventoryMovement;
import org.comunity.mountainfor.domain.model.sparepart.SparePart;
import org.comunity.mountainfor.domain.port.out.InventoryMovementRepositoryPort;
import org.comunity.mountainfor.domain.port.out.SparePartRepositoryPort;
import org.comunity.mountainfor.domain.port.out.WorkOrderCostRepositoryPort;
import org.comunity.mountainfor.domain.port.out.WorkOrderRepositoryPort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.math.BigDecimal;
import java.util.List;

@ApplicationScoped
public class CalculateWorkOrderCostUseCase {
    
    private final WorkOrderCostRepositoryPort costRepository;
    private final WorkOrderRepositoryPort workOrderRepository;
    private final InventoryMovementRepositoryPort movementRepository;
    private final SparePartRepositoryPort sparePartRepository;
    
    @Inject
    public CalculateWorkOrderCostUseCase(WorkOrderCostRepositoryPort costRepository,
                                         WorkOrderRepositoryPort workOrderRepository,
                                         InventoryMovementRepositoryPort movementRepository,
                                         SparePartRepositoryPort sparePartRepository) {
        this.costRepository = costRepository;
        this.workOrderRepository = workOrderRepository;
        this.movementRepository = movementRepository;
        this.sparePartRepository = sparePartRepository;
    }
    
    public WorkOrderCost execute(CalculateWorkOrderCostCommand command) {
        workOrderRepository.findById(command.workOrderId())
            .orElseThrow(() -> new IllegalArgumentException("Work order not found: " + command.workOrderId()));
        
        BigDecimal sparePartsCost = calculateSparePartsCost(command.workOrderId());
        
        WorkOrderCost cost = WorkOrderCost.create(
            command.workOrderId(),
            command.laborCost(),
            sparePartsCost,
            command.otherCosts()
        );
        
        return costRepository.save(cost);
    }
    
    private BigDecimal calculateSparePartsCost(Long workOrderId) {
        List<InventoryMovement> movements = movementRepository.findByWorkOrderId(workOrderId);
        BigDecimal total = BigDecimal.ZERO;
        
        for (InventoryMovement movement : movements) {
            SparePart sparePart = sparePartRepository.findById(movement.getSparePartId()).orElse(null);
            if (sparePart != null && sparePart.getUnitPrice() != null) {
                BigDecimal itemCost = sparePart.getUnitPrice()
                    .multiply(BigDecimal.valueOf(movement.getQuantity()));
                total = total.add(itemCost);
            }
        }
        
        return total;
    }
    
    public record CalculateWorkOrderCostCommand(
        Long workOrderId,
        BigDecimal laborCost,
        BigDecimal otherCosts
    ) {}
}
