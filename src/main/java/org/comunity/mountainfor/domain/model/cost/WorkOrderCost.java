package org.comunity.mountainfor.domain.model.cost;

import java.math.BigDecimal;

public class WorkOrderCost {
    
    private Long id;
    private Long workOrderId;
    private BigDecimal laborCost;
    private BigDecimal sparePartsCost;
    private BigDecimal otherCosts;
    private BigDecimal totalCost;
    
    private WorkOrderCost(Long workOrderId, BigDecimal laborCost, 
                         BigDecimal sparePartsCost, BigDecimal otherCosts) {
        this.workOrderId = workOrderId;
        this.laborCost = laborCost != null ? laborCost : BigDecimal.ZERO;
        this.sparePartsCost = sparePartsCost != null ? sparePartsCost : BigDecimal.ZERO;
        this.otherCosts = otherCosts != null ? otherCosts : BigDecimal.ZERO;
        this.totalCost = calculateTotal();
    }
    
    public static WorkOrderCost create(Long workOrderId, BigDecimal laborCost,
                                      BigDecimal sparePartsCost, BigDecimal otherCosts) {
        if (workOrderId == null) {
            throw new IllegalArgumentException("Work order ID cannot be null");
        }
        return new WorkOrderCost(workOrderId, laborCost, sparePartsCost, otherCosts);
    }
    
    private BigDecimal calculateTotal() {
        return laborCost.add(sparePartsCost).add(otherCosts);
    }
    
    public void updateLaborCost(BigDecimal laborCost) {
        this.laborCost = laborCost != null ? laborCost : BigDecimal.ZERO;
        this.totalCost = calculateTotal();
    }
    
    public void updateSparePartsCost(BigDecimal sparePartsCost) {
        this.sparePartsCost = sparePartsCost != null ? sparePartsCost : BigDecimal.ZERO;
        this.totalCost = calculateTotal();
    }
    
    public void addOtherCost(BigDecimal amount) {
        if (amount != null && amount.compareTo(BigDecimal.ZERO) > 0) {
            this.otherCosts = this.otherCosts.add(amount);
            this.totalCost = calculateTotal();
        }
    }
    
    public Long getId() { return id; }
    public Long getWorkOrderId() { return workOrderId; }
    public BigDecimal getLaborCost() { return laborCost; }
    public BigDecimal getSparePartsCost() { return sparePartsCost; }
    public BigDecimal getOtherCosts() { return otherCosts; }
    public BigDecimal getTotalCost() { return totalCost; }
    
    public void setId(Long id) { this.id = id; }
    public void setWorkOrderId(Long workOrderId) { this.workOrderId = workOrderId; }
    public void setLaborCost(BigDecimal laborCost) { this.laborCost = laborCost; }
    public void setSparePartsCost(BigDecimal sparePartsCost) { this.sparePartsCost = sparePartsCost; }
    public void setOtherCosts(BigDecimal otherCosts) { this.otherCosts = otherCosts; }
    public void setTotalCost(BigDecimal totalCost) { this.totalCost = totalCost; }
}
