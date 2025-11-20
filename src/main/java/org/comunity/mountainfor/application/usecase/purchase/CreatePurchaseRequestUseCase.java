package org.comunity.mountainfor.application.usecase.purchase;

import org.comunity.mountainfor.domain.model.purchase.PurchaseRequest;
import org.comunity.mountainfor.domain.port.out.PurchaseRequestRepositoryPort;
import org.comunity.mountainfor.domain.port.out.SparePartRepositoryPort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class CreatePurchaseRequestUseCase {
    
    private final PurchaseRequestRepositoryPort requestRepository;
    private final SparePartRepositoryPort sparePartRepository;
    
    @Inject
    public CreatePurchaseRequestUseCase(PurchaseRequestRepositoryPort requestRepository,
                                       SparePartRepositoryPort sparePartRepository) {
        this.requestRepository = requestRepository;
        this.sparePartRepository = sparePartRepository;
    }
    
    public PurchaseRequest execute(CreatePurchaseRequestCommand command) {
        if (requestRepository.existsByRequestNumber(command.requestNumber())) {
            throw new IllegalArgumentException("Purchase request with number " + command.requestNumber() + " already exists");
        }
        
        sparePartRepository.findById(command.sparePartId())
            .orElseThrow(() -> new IllegalArgumentException("Spare part not found: " + command.sparePartId()));
        
        PurchaseRequest request = PurchaseRequest.create(
            command.requestNumber(),
            command.sparePartId(),
            command.requestedQuantity(),
            command.justification(),
            command.requestedBy()
        );
        
        return requestRepository.save(request);
    }
    
    public record CreatePurchaseRequestCommand(
        String requestNumber,
        Long sparePartId,
        Integer requestedQuantity,
        String justification,
        String requestedBy
    ) {}
}
