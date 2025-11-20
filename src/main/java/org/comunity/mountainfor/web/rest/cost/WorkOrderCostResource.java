package org.comunity.mountainfor.web.rest.cost;

import org.comunity.mountainfor.application.usecase.cost.CalculateWorkOrderCostUseCase;
import org.comunity.mountainfor.domain.model.cost.WorkOrderCost;
import org.comunity.mountainfor.domain.port.out.WorkOrderCostRepositoryPort;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api/costs")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class WorkOrderCostResource {
    
    private final CalculateWorkOrderCostUseCase calculateCostUseCase;
    private final WorkOrderCostRepositoryPort costRepository;
    
    @Inject
    public WorkOrderCostResource(CalculateWorkOrderCostUseCase calculateCostUseCase,
                                WorkOrderCostRepositoryPort costRepository) {
        this.calculateCostUseCase = calculateCostUseCase;
        this.costRepository = costRepository;
    }
    
    @POST
    @Path("/calculate")
    public Response calculateCost(CalculateCostRequest request) {
        try {
            var command = new CalculateWorkOrderCostUseCase.CalculateWorkOrderCostCommand(
                request.workOrderId(),
                request.laborCost(),
                request.otherCosts()
            );
            
            WorkOrderCost cost = calculateCostUseCase.execute(command);
            return Response.status(Response.Status.CREATED)
                .entity(WorkOrderCostResponse.from(cost))
                .build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                .entity(new ErrorResponse(e.getMessage()))
                .build();
        }
    }
    
    @GET
    @Path("/workorder/{workOrderId}")
    public Response getCostByWorkOrder(@PathParam("workOrderId") Long workOrderId) {
        return costRepository.findByWorkOrderId(workOrderId)
            .map(cost -> Response.ok(WorkOrderCostResponse.from(cost)).build())
            .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }
    
    record ErrorResponse(String message) {}
}
