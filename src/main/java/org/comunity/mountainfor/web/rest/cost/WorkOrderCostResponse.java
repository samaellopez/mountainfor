package org.comunity.mountainfor.web.rest.cost;

import org.comunity.mountainfor.domain.model.cost.WorkOrderCost;
import java.math.BigDecimal;

public record WorkOrderCostResponse(
    Long id,
    Long workOrderId,
    BigDecimal laborCost,
    BigDecimal sparePartsCost,
    BigDecimal otherCosts,
    BigDecimal totalCost
) {
    public static WorkOrderCostResponse from(WorkOrderCost cost) {
        return new WorkOrderCostResponse(
            cost.getId(),
            cost.getWorkOrderId(),
            cost.getLaborCost(),
            cost.getSparePartsCost(),
            cost.getOtherCosts(),
            cost.getTotalCost()
        );
    }
}
