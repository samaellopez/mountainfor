package org.comunity.mountainfor.web.rest.equipment;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.comunity.mountainfor.domain.model.equipment.EquipmentType;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;

/**
 * Integration test for EquipmentResource.
 * This test uses the full stack (REST -> Use Case -> Repository -> Database).
 */
@QuarkusTest
class EquipmentResourceIT {
    
    @Test
    void shouldCreateAndRetrieveEquipment() {
        // Given - Create equipment request
        var request = new CreateEquipmentRequest(
            "EQ-TEST-001",
            "Test Equipment for Integration Test",
            EquipmentType.MECHANICAL,
            "Building A - Floor 2",
            "CC-1000",
            LocalDate.of(2024, 1, 15),
            10,
            null
        );
        
        // When - Create equipment
        var response = given()
            .contentType(ContentType.JSON)
            .body(request)
            .when()
            .post("/api/equipment")
            .then()
            .statusCode(201)
            .body("code", equalTo("EQ-TEST-001"))
            .body("description", equalTo("Test Equipment for Integration Test"))
            .body("type", equalTo("MECHANICAL"))
            .body("status", equalTo("ACTIVE"))
            .body("location", equalTo("Building A - Floor 2"))
            .body("costCenter", equalTo("CC-1000"))
            .body("id", notNullValue())
            .extract();
        
        Long equipmentId = response.path("id");
        
        // Then - Retrieve equipment by ID
        given()
            .when()
            .get("/api/equipment/" + equipmentId)
            .then()
            .statusCode(200)
            .body("id", equalTo(equipmentId.intValue()))
            .body("code", equalTo("EQ-TEST-001"))
            .body("status", equalTo("ACTIVE"));
    }
    
    @Test
    void shouldReturnBadRequestWhenCodeAlreadyExists() {
        // Given - Create first equipment
        var request1 = new CreateEquipmentRequest(
            "EQ-DUP-001",
            "First Equipment",
            EquipmentType.ELECTRICAL,
            "Building B",
            "CC-2000",
            LocalDate.now(),
            5,
            null
        );
        
        given()
            .contentType(ContentType.JSON)
            .body(request1)
            .when()
            .post("/api/equipment")
            .then()
            .statusCode(201);
        
        // When - Try to create equipment with same code
        var request2 = new CreateEquipmentRequest(
            "EQ-DUP-001",  // Same code
            "Second Equipment",
            EquipmentType.MECHANICAL,
            "Building C",
            "CC-3000",
            LocalDate.now(),
            8,
            null
        );
        
        // Then - Should return bad request
        given()
            .contentType(ContentType.JSON)
            .body(request2)
            .when()
            .post("/api/equipment")
            .then()
            .statusCode(400)
            .body("message", containsString("already exists"));
    }
    
    @Test
    void shouldReturnNotFoundForNonExistentEquipment() {
        given()
            .when()
            .get("/api/equipment/999999")
            .then()
            .statusCode(404);
    }
    
    @Test
    void shouldRetrieveEquipmentByCode() {
        // Given - Create equipment
        var request = new CreateEquipmentRequest(
            "EQ-CODE-001",
            "Equipment for Code Lookup",
            EquipmentType.HVAC,
            "Building D",
            "CC-4000",
            LocalDate.now(),
            12,
            null
        );
        
        given()
            .contentType(ContentType.JSON)
            .body(request)
            .when()
            .post("/api/equipment")
            .then()
            .statusCode(201);
        
        // When & Then - Retrieve by code
        given()
            .when()
            .get("/api/equipment/code/EQ-CODE-001")
            .then()
            .statusCode(200)
            .body("code", equalTo("EQ-CODE-001"))
            .body("description", equalTo("Equipment for Code Lookup"));
    }
    
    @Test
    void shouldListAllEquipment() {
        // When & Then - Get all equipment
        given()
            .when()
            .get("/api/equipment")
            .then()
            .statusCode(200)
            .body("$", notNullValue());
    }
}
