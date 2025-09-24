package com.parkinglot.parkinglot_reservation.controller;

import com.parkinglot.parkinglot_reservation.model.ParkingSlot;
import com.parkinglot.parkinglot_reservation.model.ParkingFloor;
import com.parkinglot.parkinglot_reservation.repository.ParkingSlotRepository;
import com.parkinglot.parkinglot_reservation.repository.ParkingFloorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/slots")
public class ParkingSlotController {

    @Autowired
    private ParkingSlotRepository slotRepository;

    @Autowired
    private ParkingFloorRepository floorRepository;

    // POST /slots - create a slot for a floor
    @PostMapping
    public ResponseEntity<?> createSlot(@Valid @RequestBody ParkingSlot slot) {

        if (slot.getFloor() == null || slot.getFloor().getId() == null) {
            return ResponseEntity.badRequest().body("Floor id is required");
        }

        ParkingFloor floor = floorRepository.findById(slot.getFloor().getId())
                .orElse(null);

        if (floor == null) {
            return ResponseEntity.badRequest().body("Floor not found");
        }

        slot.setFloor(floor);
        ParkingSlot savedSlot = slotRepository.save(slot);
        return ResponseEntity.ok(savedSlot);
    }

    // GET /slots - list all slots
    @GetMapping
    public List<ParkingSlot> getAllSlots() {
        return slotRepository.findAll();
    }
}
