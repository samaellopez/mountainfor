package org.comunity.mountainfor.domain.model.workorder;

/**
 * Value object representing work order status
 */
public enum WorkOrderStatus {
    OPEN,
    IN_PROGRESS,
    WAITING_FOR_PARTS,
    CLOSED,
    CANCELLED
}
