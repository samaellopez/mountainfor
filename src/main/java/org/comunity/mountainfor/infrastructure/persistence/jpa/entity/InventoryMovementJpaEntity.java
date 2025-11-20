package org.comunity.mountainfor.infrastructure.persistence.jpa.entity;

import org.comunity.mountainfor.domain.model.sparepart.MovementType;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "inventory_movement")
public class InventoryMovementJpaEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "spare_part_id", nullable = false)
    private Long sparePartId;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, length = 50)
    private MovementType type;
    
    @Column(name = "quantity", nullable = false)
    private Integer quantity;
    
    @Column(name = "reason", length = 500)
    private String reason;
    
    @Column(name = "work_order_id")
    private Long workOrderId;
    
    @Column(name = "movement_date", nullable = false)
    private LocalDateTime movementDate;
    
    @Column(name = "performed_by", length = 100)
    private String performedBy;
    
    public InventoryMovementJpaEntity() {}
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getSparePartId() { return sparePartId; }
    public void setSparePartId(Long sparePartId) { this.sparePartId = sparePartId; }
    public MovementType getType() { return type; }
    public void setType(MovementType type) { this.type = type; }
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }
    public Long getWorkOrderId() { return workOrderId; }
    public void setWorkOrderId(Long workOrderId) { this.workOrderId = workOrderId; }
    public LocalDateTime getMovementDate() { return movementDate; }
    public void setMovementDate(LocalDateTime movementDate) { this.movementDate = movementDate; }
    public String getPerformedBy() { return performedBy; }
    public void setPerformedBy(String performedBy) { this.performedBy = performedBy; }
}
