package org.comunity.mountainfor.web.rest.equipment;

import org.comunity.mountainfor.domain.model.equipment.Equipment;
import org.comunity.mountainfor.domain.model.equipment.EquipmentStatus;
import org.comunity.mountainfor.domain.model.equipment.EquipmentType;
import java.time.LocalDate;

/**
 * Response DTO for equipment.
 */
public record EquipmentResponse(
    Long id,
    String code,
    String description,
    EquipmentType type,
    String location,
    String costCenter,
    EquipmentStatus status,
    LocalDate installationDate,
    Integer estimatedLifeYears,
    Long parentEquipmentId
) {
    public static EquipmentResponse from(Equipment equipment) {
        return new EquipmentResponse(
            equipment.getId(),
            equipment.getCode(),
            equipment.getDescription(),
            equipment.getType(),
            equipment.getLocation(),
            equipment.getCostCenter(),
            equipment.getStatus(),
            equipment.getInstallationDate(),
            equipment.getEstimatedLifeYears(),
            equipment.getParentEquipmentId()
        );
    }
}
