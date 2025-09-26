package com.parkinglot.parkinglot_reservation.repository;

import com.parkinglot.parkinglot_reservation.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findBySlotIdAndStartTimeLessThanEqualAndEndTimeGreaterThanEqual(
            Long slotId, LocalDateTime endTime, LocalDateTime startTime
    );
      List<Reservation> findByStartTimeLessThanEqualAndEndTimeGreaterThanEqual(
            LocalDateTime endTime, LocalDateTime startTime
    );
}
