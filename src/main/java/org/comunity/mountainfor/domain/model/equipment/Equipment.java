package org.comunity.mountainfor.domain.model.equipment;

import java.time.LocalDate;

/**
 * Domain entity representing an equipment/asset in the maintenance system.
 * This is a pure domain entity without JPA annotations (clean architecture).
 */
public class Equipment {
    
    private Long id;
    private String code;
    private String description;
    private EquipmentType type;
    private String location;
    private String costCenter;
    private EquipmentStatus status;
    private LocalDate installationDate;
    private Integer estimatedLifeYears;
    private Long parentEquipmentId; // For hierarchical equipment structure
    
    // Private constructor for creating new equipment
    private Equipment(String code, String description, EquipmentType type, 
                     String location, String costCenter, LocalDate installationDate, 
                     Integer estimatedLifeYears, Long parentEquipmentId) {
        this.code = code;
        this.description = description;
        this.type = type;
        this.location = location;
        this.costCenter = costCenter;
        this.status = EquipmentStatus.ACTIVE;
        this.installationDate = installationDate;
        this.estimatedLifeYears = estimatedLifeYears;
        this.parentEquipmentId = parentEquipmentId;
    }
    
    // Factory method for creating new equipment
    public static Equipment create(String code, String description, EquipmentType type,
                                  String location, String costCenter, LocalDate installationDate,
                                  Integer estimatedLifeYears, Long parentEquipmentId) {
        validateCode(code);
        validateDescription(description);
        return new Equipment(code, description, type, location, costCenter, 
                           installationDate, estimatedLifeYears, parentEquipmentId);
    }
    
    // Business logic: validate equipment code
    private static void validateCode(String code) {
        if (code == null || code.trim().isEmpty()) {
            throw new IllegalArgumentException("Equipment code cannot be empty");
        }
    }
    
    // Business logic: validate description
    private static void validateDescription(String description) {
        if (description == null || description.trim().isEmpty()) {
            throw new IllegalArgumentException("Equipment description cannot be empty");
        }
    }
    
    // Business rule: Can only deactivate if not in maintenance
    public void deactivate() {
        if (this.status == EquipmentStatus.IN_MAINTENANCE) {
            throw new IllegalStateException("Cannot deactivate equipment while in maintenance");
        }
        this.status = EquipmentStatus.INACTIVE;
    }
    
    // Business rule: Mark as in maintenance
    public void markAsInMaintenance() {
        if (this.status == EquipmentStatus.INACTIVE) {
            throw new IllegalStateException("Cannot perform maintenance on inactive equipment");
        }
        this.status = EquipmentStatus.IN_MAINTENANCE;
    }
    
    // Mark as active after maintenance
    public void markAsActive() {
        this.status = EquipmentStatus.ACTIVE;
    }
    
    // Getters
    public Long getId() {
        return id;
    }
    
    public String getCode() {
        return code;
    }
    
    public String getDescription() {
        return description;
    }
    
    public EquipmentType getType() {
        return type;
    }
    
    public String getLocation() {
        return location;
    }
    
    public String getCostCenter() {
        return costCenter;
    }
    
    public EquipmentStatus getStatus() {
        return status;
    }
    
    public LocalDate getInstallationDate() {
        return installationDate;
    }
    
    public Integer getEstimatedLifeYears() {
        return estimatedLifeYears;
    }
    
    public Long getParentEquipmentId() {
        return parentEquipmentId;
    }
    
    // Setters (for infrastructure layer - JPA mapping)
    public void setId(Long id) {
        this.id = id;
    }
    
    public void setCode(String code) {
        this.code = code;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public void setType(EquipmentType type) {
        this.type = type;
    }
    
    public void setLocation(String location) {
        this.location = location;
    }
    
    public void setCostCenter(String costCenter) {
        this.costCenter = costCenter;
    }
    
    public void setStatus(EquipmentStatus status) {
        this.status = status;
    }
    
    public void setInstallationDate(LocalDate installationDate) {
        this.installationDate = installationDate;
    }
    
    public void setEstimatedLifeYears(Integer estimatedLifeYears) {
        this.estimatedLifeYears = estimatedLifeYears;
    }
    
    public void setParentEquipmentId(Long parentEquipmentId) {
        this.parentEquipmentId = parentEquipmentId;
    }
}
