package org.comunity.mountainfor.application.usecase.sparepart;

import org.comunity.mountainfor.domain.model.sparepart.SparePart;
import org.comunity.mountainfor.domain.port.out.SparePartRepositoryPort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.math.BigDecimal;

@ApplicationScoped
public class CreateSparePartUseCase {
    
    private final SparePartRepositoryPort sparePartRepository;
    
    @Inject
    public CreateSparePartUseCase(SparePartRepositoryPort sparePartRepository) {
        this.sparePartRepository = sparePartRepository;
    }
    
    public SparePart execute(CreateSparePartCommand command) {
        if (sparePartRepository.existsByCode(command.code())) {
            throw new IllegalArgumentException("Spare part with code " + command.code() + " already exists");
        }
        
        SparePart sparePart = SparePart.create(
            command.code(),
            command.description(),
            command.unitOfMeasure(),
            command.minimumStock(),
            command.maximumStock(),
            command.location(),
            command.unitPrice()
        );
        
        return sparePartRepository.save(sparePart);
    }
    
    public record CreateSparePartCommand(
        String code,
        String description,
        String unitOfMeasure,
        Integer minimumStock,
        Integer maximumStock,
        String location,
        BigDecimal unitPrice
    ) {}
}
