package com.parkinglot.parkinglot_reservation.controller;

import com.parkinglot.parkinglot_reservation.model.ParkingSlot;
import com.parkinglot.parkinglot_reservation.model.Reservation;
import com.parkinglot.parkinglot_reservation.repository.ParkingSlotRepository;
import com.parkinglot.parkinglot_reservation.repository.ReservationRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/availability")
public class AvailabilityController {

    private final ParkingSlotRepository slotRepository;
    private final ReservationRepository reservationRepository;

    public AvailabilityController(ParkingSlotRepository slotRepository, ReservationRepository reservationRepository) {
        this.slotRepository = slotRepository;
        this.reservationRepository = reservationRepository;
    }

    @GetMapping
    public Page<ParkingSlot> getAvailableSlots(
            @RequestParam LocalDateTime startTime,
            @RequestParam LocalDateTime endTime,
            Pageable pageable
    ) {
        // 1. Find conflicting reservations
        List<Reservation> conflicts = reservationRepository
                .findByStartTimeLessThanEqualAndEndTimeGreaterThanEqual(endTime, startTime);

        // 2. Get reserved slot IDs
        Set<Long> reservedSlotIds = conflicts.stream()
                .map(r -> r.getSlot().getId())
                .collect(Collectors.toSet());

        // 3. Filter available slots
        List<ParkingSlot> availableSlots = slotRepository.findAll().stream()
                .filter(slot -> !reservedSlotIds.contains(slot.getId()))
                .collect(Collectors.toList());

        // 4. Convert into pageable response manually
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), availableSlots.size());
        List<ParkingSlot> pageContent = availableSlots.subList(start, end);

        return new PageImpl<>(pageContent, pageable, availableSlots.size());
    }
}
