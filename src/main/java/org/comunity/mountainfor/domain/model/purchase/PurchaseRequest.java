package org.comunity.mountainfor.domain.model.purchase;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Domain entity representing a purchase request for spare parts.
 * Pure domain entity without JPA annotations.
 */
public class PurchaseRequest {
    
    private Long id;
    private String requestNumber;
    private Long sparePartId;
    private Integer requestedQuantity;
    private String justification;
    private PurchaseRequestStatus status;
    private String requestedBy;
    private LocalDateTime requestDate;
    private String approvedBy;
    private LocalDateTime approvedDate;
    private String purchaseOrderNumber;
    private LocalDateTime orderedDate;
    private Integer receivedQuantity;
    private LocalDateTime receivedDate;
    private BigDecimal unitPrice;
    private BigDecimal totalCost;
    private String supplier;
    private String rejectionReason;
    
    private PurchaseRequest(String requestNumber, Long sparePartId, Integer requestedQuantity,
                           String justification, String requestedBy) {
        this.requestNumber = requestNumber;
        this.sparePartId = sparePartId;
        this.requestedQuantity = requestedQuantity;
        this.justification = justification;
        this.requestedBy = requestedBy;
        this.requestDate = LocalDateTime.now();
        this.status = PurchaseRequestStatus.PENDING;
    }
    
    public static PurchaseRequest create(String requestNumber, Long sparePartId,
                                        Integer requestedQuantity, String justification,
                                        String requestedBy) {
        validateRequestNumber(requestNumber);
        validateSparePartId(sparePartId);
        validateQuantity(requestedQuantity);
        validateRequestedBy(requestedBy);
        
        return new PurchaseRequest(requestNumber, sparePartId, requestedQuantity,
                                  justification, requestedBy);
    }
    
    private static void validateRequestNumber(String requestNumber) {
        if (requestNumber == null || requestNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Request number cannot be empty");
        }
    }
    
    private static void validateSparePartId(Long sparePartId) {
        if (sparePartId == null) {
            throw new IllegalArgumentException("Spare part ID cannot be null");
        }
    }
    
    private static void validateQuantity(Integer quantity) {
        if (quantity == null || quantity <= 0) {
            throw new IllegalArgumentException("Requested quantity must be positive");
        }
    }
    
    private static void validateRequestedBy(String requestedBy) {
        if (requestedBy == null || requestedBy.trim().isEmpty()) {
            throw new IllegalArgumentException("Requested by cannot be empty");
        }
    }
    
    // Business rule: Approve purchase request
    public void approve(String approvedBy, BigDecimal unitPrice, String supplier) {
        if (this.status != PurchaseRequestStatus.PENDING) {
            throw new IllegalStateException("Only PENDING requests can be approved");
        }
        if (approvedBy == null || approvedBy.trim().isEmpty()) {
            throw new IllegalArgumentException("Approver name is required");
        }
        
        this.status = PurchaseRequestStatus.APPROVED;
        this.approvedBy = approvedBy;
        this.approvedDate = LocalDateTime.now();
        this.unitPrice = unitPrice;
        this.supplier = supplier;
        
        if (unitPrice != null) {
            this.totalCost = unitPrice.multiply(BigDecimal.valueOf(requestedQuantity));
        }
    }
    
    // Business rule: Reject purchase request
    public void reject(String rejectedBy, String reason) {
        if (this.status != PurchaseRequestStatus.PENDING) {
            throw new IllegalStateException("Only PENDING requests can be rejected");
        }
        if (reason == null || reason.trim().isEmpty()) {
            throw new IllegalArgumentException("Rejection reason is required");
        }
        
        this.status = PurchaseRequestStatus.REJECTED;
        this.approvedBy = rejectedBy;
        this.approvedDate = LocalDateTime.now();
        this.rejectionReason = reason;
    }
    
    // Business rule: Mark as ordered
    public void markAsOrdered(String purchaseOrderNumber) {
        if (this.status != PurchaseRequestStatus.APPROVED) {
            throw new IllegalStateException("Only APPROVED requests can be ordered");
        }
        if (purchaseOrderNumber == null || purchaseOrderNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Purchase order number is required");
        }
        
        this.status = PurchaseRequestStatus.ORDERED;
        this.purchaseOrderNumber = purchaseOrderNumber;
        this.orderedDate = LocalDateTime.now();
    }
    
    // Business rule: Mark as received
    public void markAsReceived(Integer receivedQuantity) {
        if (this.status != PurchaseRequestStatus.ORDERED) {
            throw new IllegalStateException("Only ORDERED requests can be received");
        }
        if (receivedQuantity == null || receivedQuantity <= 0) {
            throw new IllegalArgumentException("Received quantity must be positive");
        }
        
        this.status = PurchaseRequestStatus.RECEIVED;
        this.receivedQuantity = receivedQuantity;
        this.receivedDate = LocalDateTime.now();
    }
    
    // Business rule: Check if partially received
    public boolean isPartiallyReceived() {
        return status == PurchaseRequestStatus.RECEIVED && 
               receivedQuantity != null && 
               receivedQuantity < requestedQuantity;
    }
    
    // Getters
    public Long getId() { return id; }
    public String getRequestNumber() { return requestNumber; }
    public Long getSparePartId() { return sparePartId; }
    public Integer getRequestedQuantity() { return requestedQuantity; }
    public String getJustification() { return justification; }
    public PurchaseRequestStatus getStatus() { return status; }
    public String getRequestedBy() { return requestedBy; }
    public LocalDateTime getRequestDate() { return requestDate; }
    public String getApprovedBy() { return approvedBy; }
    public LocalDateTime getApprovedDate() { return approvedDate; }
    public String getPurchaseOrderNumber() { return purchaseOrderNumber; }
    public LocalDateTime getOrderedDate() { return orderedDate; }
    public Integer getReceivedQuantity() { return receivedQuantity; }
    public LocalDateTime getReceivedDate() { return receivedDate; }
    public BigDecimal getUnitPrice() { return unitPrice; }
    public BigDecimal getTotalCost() { return totalCost; }
    public String getSupplier() { return supplier; }
    public String getRejectionReason() { return rejectionReason; }
    
    // Setters for infrastructure
    public void setId(Long id) { this.id = id; }
    public void setRequestNumber(String requestNumber) { this.requestNumber = requestNumber; }
    public void setSparePartId(Long sparePartId) { this.sparePartId = sparePartId; }
    public void setRequestedQuantity(Integer requestedQuantity) { this.requestedQuantity = requestedQuantity; }
    public void setJustification(String justification) { this.justification = justification; }
    public void setStatus(PurchaseRequestStatus status) { this.status = status; }
    public void setRequestedBy(String requestedBy) { this.requestedBy = requestedBy; }
    public void setRequestDate(LocalDateTime requestDate) { this.requestDate = requestDate; }
    public void setApprovedBy(String approvedBy) { this.approvedBy = approvedBy; }
    public void setApprovedDate(LocalDateTime approvedDate) { this.approvedDate = approvedDate; }
    public void setPurchaseOrderNumber(String purchaseOrderNumber) { this.purchaseOrderNumber = purchaseOrderNumber; }
    public void setOrderedDate(LocalDateTime orderedDate) { this.orderedDate = orderedDate; }
    public void setReceivedQuantity(Integer receivedQuantity) { this.receivedQuantity = receivedQuantity; }
    public void setReceivedDate(LocalDateTime receivedDate) { this.receivedDate = receivedDate; }
    public void setUnitPrice(BigDecimal unitPrice) { this.unitPrice = unitPrice; }
    public void setTotalCost(BigDecimal totalCost) { this.totalCost = totalCost; }
    public void setSupplier(String supplier) { this.supplier = supplier; }
    public void setRejectionReason(String rejectionReason) { this.rejectionReason = rejectionReason; }
}
