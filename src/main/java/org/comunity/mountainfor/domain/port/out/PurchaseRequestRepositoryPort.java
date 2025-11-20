package org.comunity.mountainfor.domain.port.out;

import org.comunity.mountainfor.domain.model.purchase.PurchaseRequest;
import org.comunity.mountainfor.domain.model.purchase.PurchaseRequestStatus;
import java.util.List;
import java.util.Optional;

public interface PurchaseRequestRepositoryPort {
    PurchaseRequest save(PurchaseRequest request);
    Optional<PurchaseRequest> findById(Long id);
    Optional<PurchaseRequest> findByRequestNumber(String requestNumber);
    List<PurchaseRequest> findAll();
    List<PurchaseRequest> findByStatus(PurchaseRequestStatus status);
    List<PurchaseRequest> findBySparePartId(Long sparePartId);
    boolean existsByRequestNumber(String requestNumber);
}
