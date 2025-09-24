package com.parkinglot.parkinglot_reservation.controller;

import com.parkinglot.parkinglot_reservation.model.ParkingFloor;
import com.parkinglot.parkinglot_reservation.repository.ParkingFloorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/floors")
public class ParkingFloorController {

    @Autowired
    private ParkingFloorRepository floorRepository;

    // POST /floors - Create a parking floor
    @PostMapping
    public ResponseEntity<ParkingFloor> createFloor(@Valid @RequestBody ParkingFloor floor) {
        ParkingFloor savedFloor = floorRepository.save(floor);
        return ResponseEntity.ok(savedFloor);
    }

    // GET /floors - List all floors
    @GetMapping
    public List<ParkingFloor> getAllFloors() {
        return floorRepository.findAll();
    }
}
