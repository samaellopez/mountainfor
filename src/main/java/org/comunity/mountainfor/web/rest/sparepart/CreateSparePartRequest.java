package org.comunity.mountainfor.web.rest.sparepart;

import java.math.BigDecimal;

public record CreateSparePartRequest(
    String code,
    String description,
    String unitOfMeasure,
    Integer minimumStock,
    Integer maximumStock,
    String location,
    BigDecimal unitPrice
) {}
