package org.comunity.mountainfor.web.rest.equipment;

import org.comunity.mountainfor.application.usecase.equipment.CreateEquipmentUseCase;
import org.comunity.mountainfor.domain.model.equipment.Equipment;
import org.comunity.mountainfor.domain.port.out.EquipmentRepositoryPort;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

/**
 * REST Resource for Equipment operations.
 * This is the web layer that exposes the API.
 */
@Path("/api/equipment")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EquipmentResource {
    
    private final CreateEquipmentUseCase createEquipmentUseCase;
    private final EquipmentRepositoryPort equipmentRepository;
    
    @Inject
    public EquipmentResource(CreateEquipmentUseCase createEquipmentUseCase,
                           EquipmentRepositoryPort equipmentRepository) {
        this.createEquipmentUseCase = createEquipmentUseCase;
        this.equipmentRepository = equipmentRepository;
    }
    
    @POST
    public Response createEquipment(CreateEquipmentRequest request) {
        try {
            var command = new CreateEquipmentUseCase.CreateEquipmentCommand(
                request.code(),
                request.description(),
                request.type(),
                request.location(),
                request.costCenter(),
                request.installationDate(),
                request.estimatedLifeYears(),
                request.parentEquipmentId()
            );
            
            Equipment equipment = createEquipmentUseCase.execute(command);
            return Response.status(Response.Status.CREATED)
                .entity(EquipmentResponse.from(equipment))
                .build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                .entity(new ErrorResponse(e.getMessage()))
                .build();
        }
    }
    
    @GET
    @Path("/{id}")
    public Response getEquipment(@PathParam("id") Long id) {
        return equipmentRepository.findById(id)
            .map(equipment -> Response.ok(EquipmentResponse.from(equipment)).build())
            .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }
    
    @GET
    public Response getAllEquipment() {
        List<EquipmentResponse> equipment = equipmentRepository.findAll().stream()
            .map(EquipmentResponse::from)
            .toList();
        return Response.ok(equipment).build();
    }
    
    @GET
    @Path("/code/{code}")
    public Response getEquipmentByCode(@PathParam("code") String code) {
        return equipmentRepository.findByCode(code)
            .map(equipment -> Response.ok(EquipmentResponse.from(equipment)).build())
            .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }
    
    record ErrorResponse(String message) {}
}
