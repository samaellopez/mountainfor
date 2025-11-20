package org.comunity.mountainfor.domain.model.sparepart;

import java.time.LocalDateTime;

public class InventoryMovement {
    
    private Long id;
    private Long sparePartId;
    private MovementType type;
    private Integer quantity;
    private String reason;
    private Long workOrderId;
    private LocalDateTime movementDate;
    private String performedBy;
    
    private InventoryMovement(Long sparePartId, MovementType type, Integer quantity,
                             String reason, Long workOrderId, String performedBy) {
        this.sparePartId = sparePartId;
        this.type = type;
        this.quantity = quantity;
        this.reason = reason;
        this.workOrderId = workOrderId;
        this.movementDate = LocalDateTime.now();
        this.performedBy = performedBy;
    }
    
    public static InventoryMovement createEntry(Long sparePartId, Integer quantity, 
                                               String reason, String performedBy) {
        if (quantity == null || quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        if (sparePartId == null) {
            throw new IllegalArgumentException("Spare part ID cannot be null");
        }
        return new InventoryMovement(sparePartId, MovementType.ENTRY, quantity, 
                                    reason, null, performedBy);
    }
    
    public static InventoryMovement createExit(Long sparePartId, Integer quantity,
                                              String reason, Long workOrderId, String performedBy) {
        if (quantity == null || quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        if (sparePartId == null) {
            throw new IllegalArgumentException("Spare part ID cannot be null");
        }
        return new InventoryMovement(sparePartId, MovementType.EXIT, quantity,
                                    reason, workOrderId, performedBy);
    }
    
    public Long getId() { return id; }
    public Long getSparePartId() { return sparePartId; }
    public MovementType getType() { return type; }
    public Integer getQuantity() { return quantity; }
    public String getReason() { return reason; }
    public Long getWorkOrderId() { return workOrderId; }
    public LocalDateTime getMovementDate() { return movementDate; }
    public String getPerformedBy() { return performedBy; }
    
    public void setId(Long id) { this.id = id; }
    public void setSparePartId(Long sparePartId) { this.sparePartId = sparePartId; }
    public void setType(MovementType type) { this.type = type; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    public void setReason(String reason) { this.reason = reason; }
    public void setWorkOrderId(Long workOrderId) { this.workOrderId = workOrderId; }
    public void setMovementDate(LocalDateTime movementDate) { this.movementDate = movementDate; }
    public void setPerformedBy(String performedBy) { this.performedBy = performedBy; }
}
