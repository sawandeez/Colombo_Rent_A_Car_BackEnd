package com.example.backend.controller;

import com.example.backend.dto.VehicleTypeDto;
import com.example.backend.service.VehicleTypeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class VehicleControllerTest {

    private VehicleTypeService vehicleTypeService;
    private VehicleController controller;

    @BeforeEach
    void setup() {
        vehicleTypeService = new VehicleTypeService(null) {
            @Override
            public List<VehicleTypeDto> getAllTypes() {
                return List.of(new VehicleTypeDto("1", "SUV"));
            }
        };
        controller = new VehicleController(null, vehicleTypeService);
    }

    @Test
    void getAllVehicleTypesReturnsList() {
        var response = controller.getAllVehicleTypes();
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertEquals(1, response.getBody().size());
        assertEquals("SUV", response.getBody().get(0).getName());
    }

    @Test
    void getAllVehicleTypesHandlesException() {
        vehicleTypeService = new VehicleTypeService(null) {
            @Override
            public List<VehicleTypeDto> getAllTypes() {
                throw new RuntimeException("db error");
            }
        };
        controller = new VehicleController(null, vehicleTypeService);
        var response = controller.getAllVehicleTypes();
        assertEquals(500, response.getStatusCode().value());
    }
}
