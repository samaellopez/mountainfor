package org.comunity.mountainfor.web.rest.sparepart;

import org.comunity.mountainfor.domain.model.sparepart.SparePart;
import java.math.BigDecimal;

public record SparePartResponse(
    Long id,
    String code,
    String description,
    String unitOfMeasure,
    Integer currentStock,
    Integer minimumStock,
    Integer maximumStock,
    String location,
    BigDecimal unitPrice,
    boolean belowMinimum
) {
    public static SparePartResponse from(SparePart sparePart) {
        return new SparePartResponse(
            sparePart.getId(),
            sparePart.getCode(),
            sparePart.getDescription(),
            sparePart.getUnitOfMeasure(),
            sparePart.getCurrentStock(),
            sparePart.getMinimumStock(),
            sparePart.getMaximumStock(),
            sparePart.getLocation(),
            sparePart.getUnitPrice(),
            sparePart.isBelowMinimum()
        );
    }
}
