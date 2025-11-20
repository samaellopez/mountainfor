package org.comunity.mountainfor.application.usecase.workorder;

import org.comunity.mountainfor.domain.model.equipment.Equipment;
import org.comunity.mountainfor.domain.model.equipment.EquipmentStatus;
import org.comunity.mountainfor.domain.model.equipment.EquipmentType;
import org.comunity.mountainfor.domain.model.workorder.WorkOrder;
import org.comunity.mountainfor.domain.model.workorder.WorkOrderPriority;
import org.comunity.mountainfor.domain.port.out.EquipmentRepositoryPort;
import org.comunity.mountainfor.domain.port.out.WorkOrderRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Unit test for CreateCorrectiveWorkOrderUseCase.
 * This test validates the business rules using mocked repositories.
 */
@ExtendWith(MockitoExtension.class)
class CreateCorrectiveWorkOrderUseCaseTest {
    
    @Mock
    private WorkOrderRepositoryPort workOrderRepository;
    
    @Mock
    private EquipmentRepositoryPort equipmentRepository;
    
    private CreateCorrectiveWorkOrderUseCase useCase;
    
    @BeforeEach
    void setUp() {
        useCase = new CreateCorrectiveWorkOrderUseCase(workOrderRepository, equipmentRepository);
    }
    
    @Test
    void shouldCreateCorrectiveWorkOrderForActiveEquipment() {
        // Given
        Long equipmentId = 1L;
        Equipment activeEquipment = createActiveEquipment(equipmentId);
        
        when(equipmentRepository.findById(equipmentId)).thenReturn(Optional.of(activeEquipment));
        when(workOrderRepository.save(any(WorkOrder.class))).thenAnswer(invocation -> {
            WorkOrder wo = invocation.getArgument(0);
            wo.setId(100L);
            return wo;
        });
        when(equipmentRepository.save(any(Equipment.class))).thenAnswer(invocation -> invocation.getArgument(0));
        
        var command = new CreateCorrectiveWorkOrderUseCase.CreateCorrectiveWorkOrderCommand(
            equipmentId,
            "Motor failure detected",
            WorkOrderPriority.HIGH,
            "John Doe"
        );
        
        // When
        WorkOrder result = useCase.execute(command);
        
        // Then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(100L);
        assertThat(result.getEquipmentId()).isEqualTo(equipmentId);
        assertThat(result.getFailureDescription()).isEqualTo("Motor failure detected");
        assertThat(result.getPriority()).isEqualTo(WorkOrderPriority.HIGH);
        assertThat(result.getAssignedTo()).isEqualTo("John Doe");
        
        // Verify equipment was marked as in maintenance
        verify(equipmentRepository).save(argThat(equipment -> 
            equipment.getStatus() == EquipmentStatus.IN_MAINTENANCE
        ));
        
        // Verify work order was saved
        verify(workOrderRepository).save(any(WorkOrder.class));
    }
    
    @Test
    void shouldThrowExceptionWhenEquipmentNotFound() {
        // Given
        Long nonExistentEquipmentId = 999L;
        when(equipmentRepository.findById(nonExistentEquipmentId)).thenReturn(Optional.empty());
        
        var command = new CreateCorrectiveWorkOrderUseCase.CreateCorrectiveWorkOrderCommand(
            nonExistentEquipmentId,
            "Some failure",
            WorkOrderPriority.MEDIUM,
            "Jane Doe"
        );
        
        // When & Then
        assertThatThrownBy(() -> useCase.execute(command))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Equipment not found");
        
        // Verify no work order was saved
        verify(workOrderRepository, never()).save(any(WorkOrder.class));
    }
    
    @Test
    void shouldThrowExceptionWhenEquipmentIsInactive() {
        // Given
        Long equipmentId = 1L;
        Equipment inactiveEquipment = createActiveEquipment(equipmentId);
        inactiveEquipment.deactivate();
        
        when(equipmentRepository.findById(equipmentId)).thenReturn(Optional.of(inactiveEquipment));
        
        var command = new CreateCorrectiveWorkOrderUseCase.CreateCorrectiveWorkOrderCommand(
            equipmentId,
            "Some failure",
            WorkOrderPriority.LOW,
            "Bob Smith"
        );
        
        // When & Then
        assertThatThrownBy(() -> useCase.execute(command))
            .isInstanceOf(IllegalStateException.class)
            .hasMessageContaining("Cannot create work order for inactive equipment");
        
        // Verify no work order was saved
        verify(workOrderRepository, never()).save(any(WorkOrder.class));
    }
    
    @Test
    void shouldThrowExceptionWhenFailureDescriptionIsEmpty() {
        // Given
        Long equipmentId = 1L;
        Equipment activeEquipment = createActiveEquipment(equipmentId);
        
        when(equipmentRepository.findById(equipmentId)).thenReturn(Optional.of(activeEquipment));
        
        var command = new CreateCorrectiveWorkOrderUseCase.CreateCorrectiveWorkOrderCommand(
            equipmentId,
            "",  // Empty failure description
            WorkOrderPriority.MEDIUM,
            "Alice Johnson"
        );
        
        // When & Then
        assertThatThrownBy(() -> useCase.execute(command))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Failure description cannot be empty");
        
        // Verify no work order was saved
        verify(workOrderRepository, never()).save(any(WorkOrder.class));
    }
    
    private Equipment createActiveEquipment(Long id) {
        Equipment equipment = Equipment.create(
            "EQ-001",
            "Test Equipment",
            EquipmentType.MECHANICAL,
            "Building A",
            "CC-100",
            LocalDate.now(),
            10,
            null
        );
        equipment.setId(id);
        return equipment;
    }
}
