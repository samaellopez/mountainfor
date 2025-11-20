package org.comunity.mountainfor.web.rest.equipment;

import org.comunity.mountainfor.domain.model.equipment.EquipmentType;
import java.time.LocalDate;

/**
 * Request DTO for creating equipment.
 */
public record CreateEquipmentRequest(
    String code,
    String description,
    EquipmentType type,
    String location,
    String costCenter,
    LocalDate installationDate,
    Integer estimatedLifeYears,
    Long parentEquipmentId
) {}
