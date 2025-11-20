package org.comunity.mountainfor.application.usecase.purchase;

import org.comunity.mountainfor.domain.model.purchase.PurchaseRequest;
import org.comunity.mountainfor.domain.port.out.PurchaseRequestRepositoryPort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.math.BigDecimal;

@ApplicationScoped
public class ApprovePurchaseRequestUseCase {
    
    private final PurchaseRequestRepositoryPort requestRepository;
    
    @Inject
    public ApprovePurchaseRequestUseCase(PurchaseRequestRepositoryPort requestRepository) {
        this.requestRepository = requestRepository;
    }
    
    public PurchaseRequest execute(ApprovePurchaseRequestCommand command) {
        PurchaseRequest request = requestRepository.findById(command.requestId())
            .orElseThrow(() -> new IllegalArgumentException("Purchase request not found: " + command.requestId()));
        
        request.approve(command.approvedBy(), command.unitPrice(), command.supplier());
        
        return requestRepository.save(request);
    }
    
    public record ApprovePurchaseRequestCommand(
        Long requestId,
        String approvedBy,
        BigDecimal unitPrice,
        String supplier
    ) {}
}
