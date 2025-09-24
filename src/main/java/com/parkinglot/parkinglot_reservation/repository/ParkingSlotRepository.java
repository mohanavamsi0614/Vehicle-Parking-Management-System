package com.parkinglot.parkinglot_reservation.repository;

import com.parkinglot.parkinglot_reservation.model.ParkingSlot;
import com.parkinglot.parkinglot_reservation.model.ParkingFloor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParkingSlotRepository extends JpaRepository<ParkingSlot, Long> {

    // Find all slots for a floor
    List<ParkingSlot> findByFloor(ParkingFloor floor);
}
