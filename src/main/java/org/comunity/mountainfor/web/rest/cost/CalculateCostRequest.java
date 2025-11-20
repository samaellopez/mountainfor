package org.comunity.mountainfor.web.rest.cost;

import java.math.BigDecimal;

public record CalculateCostRequest(
    Long workOrderId,
    BigDecimal laborCost,
    BigDecimal otherCosts
) {}
