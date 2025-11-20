package org.comunity.mountainfor.domain.port.out;

import org.comunity.mountainfor.domain.model.sparepart.InventoryMovement;
import java.util.List;

public interface InventoryMovementRepositoryPort {
    InventoryMovement save(InventoryMovement movement);
    List<InventoryMovement> findBySparePartId(Long sparePartId);
    List<InventoryMovement> findByWorkOrderId(Long workOrderId);
}
