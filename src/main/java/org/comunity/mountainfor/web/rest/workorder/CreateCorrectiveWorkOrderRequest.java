package org.comunity.mountainfor.web.rest.workorder;

import org.comunity.mountainfor.domain.model.workorder.WorkOrderPriority;

/**
 * Request DTO for creating corrective work order.
 */
public record CreateCorrectiveWorkOrderRequest(
    Long equipmentId,
    String failureDescription,
    WorkOrderPriority priority,
    String assignedTo
) {}
