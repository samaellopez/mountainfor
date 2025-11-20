package org.comunity.mountainfor.web.rest.workorder;

import org.comunity.mountainfor.application.usecase.workorder.CreateCorrectiveWorkOrderUseCase;
import org.comunity.mountainfor.domain.model.workorder.WorkOrder;
import org.comunity.mountainfor.domain.port.out.WorkOrderRepositoryPort;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

/**
 * REST Resource for WorkOrder operations.
 * This is the web layer that exposes the API.
 */
@Path("/api/workorders")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class WorkOrderResource {
    
    private final CreateCorrectiveWorkOrderUseCase createCorrectiveWorkOrderUseCase;
    private final WorkOrderRepositoryPort workOrderRepository;
    
    @Inject
    public WorkOrderResource(CreateCorrectiveWorkOrderUseCase createCorrectiveWorkOrderUseCase,
                           WorkOrderRepositoryPort workOrderRepository) {
        this.createCorrectiveWorkOrderUseCase = createCorrectiveWorkOrderUseCase;
        this.workOrderRepository = workOrderRepository;
    }
    
    @POST
    @Path("/corrective")
    public Response createCorrectiveWorkOrder(CreateCorrectiveWorkOrderRequest request) {
        try {
            var command = new CreateCorrectiveWorkOrderUseCase.CreateCorrectiveWorkOrderCommand(
                request.equipmentId(),
                request.failureDescription(),
                request.priority(),
                request.assignedTo()
            );
            
            WorkOrder workOrder = createCorrectiveWorkOrderUseCase.execute(command);
            return Response.status(Response.Status.CREATED)
                .entity(WorkOrderResponse.from(workOrder))
                .build();
        } catch (IllegalArgumentException | IllegalStateException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                .entity(new ErrorResponse(e.getMessage()))
                .build();
        }
    }
    
    @GET
    @Path("/{id}")
    public Response getWorkOrder(@PathParam("id") Long id) {
        return workOrderRepository.findById(id)
            .map(workOrder -> Response.ok(WorkOrderResponse.from(workOrder)).build())
            .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }
    
    @GET
    public Response getAllWorkOrders() {
        List<WorkOrderResponse> workOrders = workOrderRepository.findAll().stream()
            .map(WorkOrderResponse::from)
            .toList();
        return Response.ok(workOrders).build();
    }
    
    @GET
    @Path("/equipment/{equipmentId}")
    public Response getWorkOrdersByEquipment(@PathParam("equipmentId") Long equipmentId) {
        List<WorkOrderResponse> workOrders = workOrderRepository.findByEquipmentId(equipmentId).stream()
            .map(WorkOrderResponse::from)
            .toList();
        return Response.ok(workOrders).build();
    }
    
    record ErrorResponse(String message) {}
}
