package com.example.backend.controller;

import com.example.backend.model.Vehicle;
import com.example.backend.model.VehicleType;
import com.example.backend.dto.VehicleTypeDto;
import com.example.backend.repository.VehicleRepository;
import com.example.backend.service.VehicleTypeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@lombok.extern.slf4j.Slf4j
public class VehicleController {

    private final VehicleRepository vehicleRepository;
    private final VehicleTypeService vehicleTypeService;

    public VehicleController(VehicleRepository vehicleRepository, VehicleTypeService vehicleTypeService) {
        this.vehicleRepository = vehicleRepository;
        this.vehicleTypeService = vehicleTypeService;
    }

    @GetMapping("/vehicles")
    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }

    @GetMapping("/vehicle-types")
    public ResponseEntity<List<VehicleTypeDto>> getAllVehicleTypes() {
        try {
            List<VehicleTypeDto> types = vehicleTypeService.getAllTypes();
            return ResponseEntity.ok(types);
        } catch (Exception e) {
            log.error("Failed to fetch vehicle types", e);
            return ResponseEntity.status(500).build();
        }
    }
}
