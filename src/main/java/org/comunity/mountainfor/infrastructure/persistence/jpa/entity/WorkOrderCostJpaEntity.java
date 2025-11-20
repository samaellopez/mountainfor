package org.comunity.mountainfor.infrastructure.persistence.jpa.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "work_order_cost")
public class WorkOrderCostJpaEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "work_order_id", nullable = false, unique = true)
    private Long workOrderId;
    
    @Column(name = "labor_cost", precision = 19, scale = 2)
    private BigDecimal laborCost;
    
    @Column(name = "spare_parts_cost", precision = 19, scale = 2)
    private BigDecimal sparePartsCost;
    
    @Column(name = "other_costs", precision = 19, scale = 2)
    private BigDecimal otherCosts;
    
    @Column(name = "total_cost", precision = 19, scale = 2)
    private BigDecimal totalCost;
    
    public WorkOrderCostJpaEntity() {}
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getWorkOrderId() { return workOrderId; }
    public void setWorkOrderId(Long workOrderId) { this.workOrderId = workOrderId; }
    public BigDecimal getLaborCost() { return laborCost; }
    public void setLaborCost(BigDecimal laborCost) { this.laborCost = laborCost; }
    public BigDecimal getSparePartsCost() { return sparePartsCost; }
    public void setSparePartsCost(BigDecimal sparePartsCost) { this.sparePartsCost = sparePartsCost; }
    public BigDecimal getOtherCosts() { return otherCosts; }
    public void setOtherCosts(BigDecimal otherCosts) { this.otherCosts = otherCosts; }
    public BigDecimal getTotalCost() { return totalCost; }
    public void setTotalCost(BigDecimal totalCost) { this.totalCost = totalCost; }
}
