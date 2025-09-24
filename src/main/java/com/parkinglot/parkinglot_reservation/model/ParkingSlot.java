package com.parkinglot.parkinglot_reservation.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ParkingSlot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Slot name cannot be blank")
    private String name;

    @Enumerated(EnumType.STRING)
    private VehicleType vehicleType;  // 2-wheeler or 4-wheeler

    @ManyToOne
    @JoinColumn(name = "floor_id")
    private ParkingFloor floor;       // The floor this slot belongs to

    private boolean isAvailable = true;  // default true
}
