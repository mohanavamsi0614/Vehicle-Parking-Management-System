package com.parkinglot.parkinglot_reservation.controller;

import com.parkinglot.parkinglot_reservation.model.*;
import com.parkinglot.parkinglot_reservation.repository.ParkingSlotRepository;
import com.parkinglot.parkinglot_reservation.repository.ReservationRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationRepository reservationRepository;
    private final ParkingSlotRepository slotRepository;

    public ReservationController(ReservationRepository reservationRepository, ParkingSlotRepository slotRepository) {
        this.reservationRepository = reservationRepository;
        this.slotRepository = slotRepository;
    }

    @PostMapping
    public ResponseEntity<?> reserve(@Valid @RequestBody Reservation reservation) {
        // 1. Validate slot exists
        ParkingSlot slot = slotRepository.findById(reservation.getSlot().getId())
                .orElseThrow(() -> new RuntimeException("Slot not found"));

        // 2. Check start < end
        if (reservation.getStartTime().isAfter(reservation.getEndTime())) {
            return ResponseEntity.badRequest().body("Start time must be before end time");
        }

        // 3. Check max 24 hours
        Duration duration = Duration.between(reservation.getStartTime(), reservation.getEndTime());
        if (duration.toHours() > 24) {
            return ResponseEntity.badRequest().body("Reservation cannot exceed 24 hours");
        }

        // 4. Check overlapping reservations
        List<Reservation> conflicts = reservationRepository
                .findBySlotIdAndStartTimeLessThanEqualAndEndTimeGreaterThanEqual(
                        slot.getId(), reservation.getEndTime(), reservation.getStartTime());

        if (!conflicts.isEmpty()) {
            return ResponseEntity.badRequest().body("Slot already booked for given time range");
        }

        // 5. Calculate cost (round up hours)
        long hours = (long) Math.ceil((double) duration.toMinutes() / 60);
        double rate = (reservation.getVehicleType() == VehicleType.FOUR_WHEELER) ? 30 : 20;
        reservation.setCost(hours * rate);

        // 6. Save
        reservation.setSlot(slot);
        Reservation saved = reservationRepository.save(reservation);

        return ResponseEntity.ok(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getReservation(@PathVariable Long id) {
        return reservationRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> cancel(@PathVariable Long id) {
        if (reservationRepository.existsById(id)) {
            reservationRepository.deleteById(id);
            return ResponseEntity.ok("Reservation cancelled");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
