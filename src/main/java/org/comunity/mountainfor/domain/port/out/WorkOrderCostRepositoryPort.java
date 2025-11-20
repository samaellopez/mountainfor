package org.comunity.mountainfor.domain.port.out;

import org.comunity.mountainfor.domain.model.cost.WorkOrderCost;
import java.util.Optional;

public interface WorkOrderCostRepositoryPort {
    WorkOrderCost save(WorkOrderCost cost);
    Optional<WorkOrderCost> findByWorkOrderId(Long workOrderId);
}
