package org.comunity.mountainfor.infrastructure.persistence.jpa.entity;

import jakarta.persistence.*;
import org.comunity.mountainfor.domain.model.equipment.EquipmentStatus;
import org.comunity.mountainfor.domain.model.equipment.EquipmentType;
import java.time.LocalDate;

/**
 * JPA entity for Equipment persistence.
 * This is the infrastructure representation with JPA annotations.
 */
@Entity
@Table(name = "equipment")
public class EquipmentJpaEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "code", nullable = false, unique = true, length = 50)
    private String code;
    
    @Column(name = "description", nullable = false, length = 500)
    private String description;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, length = 50)
    private EquipmentType type;
    
    @Column(name = "location", length = 200)
    private String location;
    
    @Column(name = "cost_center", length = 50)
    private String costCenter;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 50)
    private EquipmentStatus status;
    
    @Column(name = "installation_date")
    private LocalDate installationDate;
    
    @Column(name = "estimated_life_years")
    private Integer estimatedLifeYears;
    
    @Column(name = "parent_equipment_id")
    private Long parentEquipmentId;
    
    // Default constructor for JPA
    public EquipmentJpaEntity() {
    }
    
    // Getters and setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getCode() {
        return code;
    }
    
    public void setCode(String code) {
        this.code = code;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public EquipmentType getType() {
        return type;
    }
    
    public void setType(EquipmentType type) {
        this.type = type;
    }
    
    public String getLocation() {
        return location;
    }
    
    public void setLocation(String location) {
        this.location = location;
    }
    
    public String getCostCenter() {
        return costCenter;
    }
    
    public void setCostCenter(String costCenter) {
        this.costCenter = costCenter;
    }
    
    public EquipmentStatus getStatus() {
        return status;
    }
    
    public void setStatus(EquipmentStatus status) {
        this.status = status;
    }
    
    public LocalDate getInstallationDate() {
        return installationDate;
    }
    
    public void setInstallationDate(LocalDate installationDate) {
        this.installationDate = installationDate;
    }
    
    public Integer getEstimatedLifeYears() {
        return estimatedLifeYears;
    }
    
    public void setEstimatedLifeYears(Integer estimatedLifeYears) {
        this.estimatedLifeYears = estimatedLifeYears;
    }
    
    public Long getParentEquipmentId() {
        return parentEquipmentId;
    }
    
    public void setParentEquipmentId(Long parentEquipmentId) {
        this.parentEquipmentId = parentEquipmentId;
    }
}
