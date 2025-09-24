package com.parkinglot.parkinglot_reservation.repository;

import com.parkinglot.parkinglot_reservation.model.ParkingFloor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingFloorRepository extends JpaRepository<ParkingFloor, Long> {
}
