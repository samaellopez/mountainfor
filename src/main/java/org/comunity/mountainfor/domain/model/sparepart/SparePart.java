package org.comunity.mountainfor.domain.model.sparepart;

import java.math.BigDecimal;

/**
 * Domain entity representing a spare part in the inventory.
 * Pure domain entity without JPA annotations.
 */
public class SparePart {
    
    private Long id;
    private String code;
    private String description;
    private String unitOfMeasure;
    private Integer currentStock;
    private Integer minimumStock;
    private Integer maximumStock;
    private String location;
    private BigDecimal unitPrice;
    
    private SparePart(String code, String description, String unitOfMeasure,
                     Integer minimumStock, Integer maximumStock, String location,
                     BigDecimal unitPrice) {
        this.code = code;
        this.description = description;
        this.unitOfMeasure = unitOfMeasure;
        this.currentStock = 0;
        this.minimumStock = minimumStock;
        this.maximumStock = maximumStock;
        this.location = location;
        this.unitPrice = unitPrice;
    }
    
    // Factory method for creating new spare part
    public static SparePart create(String code, String description, String unitOfMeasure,
                                  Integer minimumStock, Integer maximumStock, String location,
                                  BigDecimal unitPrice) {
        validateCode(code);
        validateDescription(description);
        validateStockLevels(minimumStock, maximumStock);
        return new SparePart(code, description, unitOfMeasure, minimumStock, maximumStock, 
                           location, unitPrice);
    }
    
    private static void validateCode(String code) {
        if (code == null || code.trim().isEmpty()) {
            throw new IllegalArgumentException("Spare part code cannot be empty");
        }
    }
    
    private static void validateDescription(String description) {
        if (description == null || description.trim().isEmpty()) {
            throw new IllegalArgumentException("Spare part description cannot be empty");
        }
    }
    
    private static void validateStockLevels(Integer minimumStock, Integer maximumStock) {
        if (minimumStock != null && maximumStock != null && minimumStock > maximumStock) {
            throw new IllegalArgumentException("Minimum stock cannot be greater than maximum stock");
        }
    }
    
    // Business rule: Add stock
    public void addStock(Integer quantity) {
        if (quantity == null || quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        this.currentStock += quantity;
    }
    
    // Business rule: Consume stock
    public void consumeStock(Integer quantity) {
        if (quantity == null || quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        if (this.currentStock < quantity) {
            throw new IllegalStateException("Insufficient stock. Available: " + this.currentStock);
        }
        this.currentStock -= quantity;
    }
    
    // Business rule: Check if stock is below minimum
    public boolean isBelowMinimum() {
        return minimumStock != null && currentStock < minimumStock;
    }
    
    // Business rule: Check if stock is above maximum
    public boolean isAboveMaximum() {
        return maximumStock != null && currentStock > maximumStock;
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
    
    public String getUnitOfMeasure() {
        return unitOfMeasure;
    }
    
    public Integer getCurrentStock() {
        return currentStock;
    }
    
    public Integer getMinimumStock() {
        return minimumStock;
    }
    
    public Integer getMaximumStock() {
        return maximumStock;
    }
    
    public String getLocation() {
        return location;
    }
    
    public BigDecimal getUnitPrice() {
        return unitPrice;
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
    
    public void setUnitOfMeasure(String unitOfMeasure) {
        this.unitOfMeasure = unitOfMeasure;
    }
    
    public void setCurrentStock(Integer currentStock) {
        this.currentStock = currentStock;
    }
    
    public void setMinimumStock(Integer minimumStock) {
        this.minimumStock = minimumStock;
    }
    
    public void setMaximumStock(Integer maximumStock) {
        this.maximumStock = maximumStock;
    }
    
    public void setLocation(String location) {
        this.location = location;
    }
    
    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }
}
