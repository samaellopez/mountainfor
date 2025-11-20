package org.comunity.mountainfor.web.rest.sparepart;

import org.comunity.mountainfor.application.usecase.sparepart.ConsumeSparePartUseCase;
import org.comunity.mountainfor.application.usecase.sparepart.CreateSparePartUseCase;
import org.comunity.mountainfor.domain.model.sparepart.SparePart;
import org.comunity.mountainfor.domain.port.out.SparePartRepositoryPort;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/api/spareparts")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SparePartResource {
    
    private final CreateSparePartUseCase createSparePartUseCase;
    private final ConsumeSparePartUseCase consumeSparePartUseCase;
    private final SparePartRepositoryPort sparePartRepository;
    
    @Inject
    public SparePartResource(CreateSparePartUseCase createSparePartUseCase,
                           ConsumeSparePartUseCase consumeSparePartUseCase,
                           SparePartRepositoryPort sparePartRepository) {
        this.createSparePartUseCase = createSparePartUseCase;
        this.consumeSparePartUseCase = consumeSparePartUseCase;
        this.sparePartRepository = sparePartRepository;
    }
    
    @POST
    public Response createSparePart(CreateSparePartRequest request) {
        try {
            var command = new CreateSparePartUseCase.CreateSparePartCommand(
                request.code(),
                request.description(),
                request.unitOfMeasure(),
                request.minimumStock(),
                request.maximumStock(),
                request.location(),
                request.unitPrice()
            );
            
            SparePart sparePart = createSparePartUseCase.execute(command);
            return Response.status(Response.Status.CREATED)
                .entity(SparePartResponse.from(sparePart))
                .build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                .entity(new ErrorResponse(e.getMessage()))
                .build();
        }
    }
    
    @POST
    @Path("/consume")
    public Response consumeSparePart(ConsumeSparePartRequest request) {
        try {
            var command = new ConsumeSparePartUseCase.ConsumeSparePartCommand(
                request.sparePartId(),
                request.quantity(),
                request.reason(),
                request.workOrderId(),
                request.performedBy()
            );
            
            consumeSparePartUseCase.execute(command);
            return Response.ok().build();
        } catch (IllegalArgumentException | IllegalStateException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                .entity(new ErrorResponse(e.getMessage()))
                .build();
        }
    }
    
    @GET
    @Path("/{id}")
    public Response getSparePart(@PathParam("id") Long id) {
        return sparePartRepository.findById(id)
            .map(sparePart -> Response.ok(SparePartResponse.from(sparePart)).build())
            .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }
    
    @GET
    public Response getAllSpareParts() {
        List<SparePartResponse> spareParts = sparePartRepository.findAll().stream()
            .map(SparePartResponse::from)
            .toList();
        return Response.ok(spareParts).build();
    }
    
    @GET
    @Path("/below-minimum")
    public Response getSparePartsBelowMinimum() {
        List<SparePartResponse> spareParts = sparePartRepository.findBelowMinimum().stream()
            .map(SparePartResponse::from)
            .toList();
        return Response.ok(spareParts).build();
    }
    
    record ErrorResponse(String message) {}
}
