package org.comunity.mountainfor.domain.model.maintenanceplan;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Domain entity representing a preventive maintenance plan.
 * Pure domain entity without JPA annotations.
 */
public class MaintenancePlan {
    
    private Long id;
    private String code;
    private String description;
    private Long equipmentId;
    private MaintenanceType type;
    private FrequencyType frequencyType;
    private Integer frequencyValue;
    private Integer toleranceDays;
    private LocalDate nextMaintenanceDate;
    private Integer estimatedDurationHours;
    private String responsibleRole;
    private List<String> checklist;
    private boolean active;
    
    private MaintenancePlan(String code, String description, Long equipmentId,
                           MaintenanceType type, FrequencyType frequencyType,
                           Integer frequencyValue, Integer toleranceDays,
                           LocalDate nextMaintenanceDate, Integer estimatedDurationHours,
                           String responsibleRole, List<String> checklist) {
        this.code = code;
        this.description = description;
        this.equipmentId = equipmentId;
        this.type = type;
        this.frequencyType = frequencyType;
        this.frequencyValue = frequencyValue;
        this.toleranceDays = toleranceDays;
        this.nextMaintenanceDate = nextMaintenanceDate;
        this.estimatedDurationHours = estimatedDurationHours;
        this.responsibleRole = responsibleRole;
        this.checklist = checklist != null ? new ArrayList<>(checklist) : new ArrayList<>();
        this.active = true;
    }
    
    public static MaintenancePlan create(String code, String description, Long equipmentId,
                                        MaintenanceType type, FrequencyType frequencyType,
                                        Integer frequencyValue, Integer toleranceDays,
                                        LocalDate nextMaintenanceDate, Integer estimatedDurationHours,
                                        String responsibleRole, List<String> checklist) {
        validateCode(code);
        validateDescription(description);
        validateEquipmentId(equipmentId);
        validateFrequency(frequencyType, frequencyValue);
        validateNextDate(nextMaintenanceDate);
        
        return new MaintenancePlan(code, description, equipmentId, type, frequencyType,
                                 frequencyValue, toleranceDays, nextMaintenanceDate,
                                 estimatedDurationHours, responsibleRole, checklist);
    }
    
    private static void validateCode(String code) {
        if (code == null || code.trim().isEmpty()) {
            throw new IllegalArgumentException("Maintenance plan code cannot be empty");
        }
    }
    
    private static void validateDescription(String description) {
        if (description == null || description.trim().isEmpty()) {
            throw new IllegalArgumentException("Maintenance plan description cannot be empty");
        }
    }
    
    private static void validateEquipmentId(Long equipmentId) {
        if (equipmentId == null) {
            throw new IllegalArgumentException("Equipment ID cannot be null");
        }
    }
    
    private static void validateFrequency(FrequencyType frequencyType, Integer frequencyValue) {
        if (frequencyType == null) {
            throw new IllegalArgumentException("Frequency type cannot be null");
        }
        if (frequencyValue == null || frequencyValue <= 0) {
            throw new IllegalArgumentException("Frequency value must be positive");
        }
    }
    
    private static void validateNextDate(LocalDate nextDate) {
        if (nextDate == null) {
            throw new IllegalArgumentException("Next maintenance date cannot be null");
        }
    }
    
    // Business rule: Calculate next maintenance date based on frequency
    public void calculateNextMaintenanceDate(LocalDate lastMaintenanceDate) {
        if (lastMaintenanceDate == null) {
            lastMaintenanceDate = LocalDate.now();
        }
        
        switch (frequencyType) {
            case DAILY:
                this.nextMaintenanceDate = lastMaintenanceDate.plusDays(frequencyValue);
                break;
            case WEEKLY:
                this.nextMaintenanceDate = lastMaintenanceDate.plusWeeks(frequencyValue);
                break;
            case MONTHLY:
                this.nextMaintenanceDate = lastMaintenanceDate.plusMonths(frequencyValue);
                break;
            case YEARLY:
                this.nextMaintenanceDate = lastMaintenanceDate.plusYears(frequencyValue);
                break;
            default:
                throw new IllegalStateException("Unsupported frequency type: " + frequencyType);
        }
    }
    
    // Business rule: Check if maintenance is due
    public boolean isDue() {
        if (!active) return false;
        LocalDate today = LocalDate.now();
        LocalDate dueDate = nextMaintenanceDate.plusDays(toleranceDays != null ? toleranceDays : 0);
        return !today.isBefore(nextMaintenanceDate) && !today.isAfter(dueDate);
    }
    
    // Business rule: Check if maintenance is overdue
    public boolean isOverdue() {
        if (!active) return false;
        LocalDate today = LocalDate.now();
        LocalDate dueDate = nextMaintenanceDate.plusDays(toleranceDays != null ? toleranceDays : 0);
        return today.isAfter(dueDate);
    }
    
    public void activate() {
        this.active = true;
    }
    
    public void deactivate() {
        this.active = false;
    }
    
    // Getters
    public Long getId() { return id; }
    public String getCode() { return code; }
    public String getDescription() { return description; }
    public Long getEquipmentId() { return equipmentId; }
    public MaintenanceType getType() { return type; }
    public FrequencyType getFrequencyType() { return frequencyType; }
    public Integer getFrequencyValue() { return frequencyValue; }
    public Integer getToleranceDays() { return toleranceDays; }
    public LocalDate getNextMaintenanceDate() { return nextMaintenanceDate; }
    public Integer getEstimatedDurationHours() { return estimatedDurationHours; }
    public String getResponsibleRole() { return responsibleRole; }
    public List<String> getChecklist() { return new ArrayList<>(checklist); }
    public boolean isActive() { return active; }
    
    // Setters for infrastructure
    public void setId(Long id) { this.id = id; }
    public void setCode(String code) { this.code = code; }
    public void setDescription(String description) { this.description = description; }
    public void setEquipmentId(Long equipmentId) { this.equipmentId = equipmentId; }
    public void setType(MaintenanceType type) { this.type = type; }
    public void setFrequencyType(FrequencyType frequencyType) { this.frequencyType = frequencyType; }
    public void setFrequencyValue(Integer frequencyValue) { this.frequencyValue = frequencyValue; }
    public void setToleranceDays(Integer toleranceDays) { this.toleranceDays = toleranceDays; }
    public void setNextMaintenanceDate(LocalDate nextMaintenanceDate) { this.nextMaintenanceDate = nextMaintenanceDate; }
    public void setEstimatedDurationHours(Integer estimatedDurationHours) { this.estimatedDurationHours = estimatedDurationHours; }
    public void setResponsibleRole(String responsibleRole) { this.responsibleRole = responsibleRole; }
    public void setChecklist(List<String> checklist) { this.checklist = checklist; }
    public void setActive(boolean active) { this.active = active; }
}
