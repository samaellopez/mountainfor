package org.comunity.mountainfor.web.rest.sparepart;

public record ConsumeSparePartRequest(
    Long sparePartId,
    Integer quantity,
    String reason,
    Long workOrderId,
    String performedBy
) {}
