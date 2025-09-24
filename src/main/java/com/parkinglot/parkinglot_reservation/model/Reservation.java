package com.parkinglot.parkinglot_reservation.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDateTime;

@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "slot_id")
    private ParkingSlot slot;

    @NotNull
    private LocalDateTime startTime;

    @NotNull
    private LocalDateTime endTime;

    @NotNull
    @Pattern(regexp = "^[A-Z]{2}[0-9]{2}[A-Z]{2}[0-9]{4}$", message = "Invalid vehicle number format")
    private String vehicleNumber;

    @Enumerated(EnumType.STRING)
    private VehicleType vehicleType;

    private double cost;

    // Getters and Setters
    public Long getId() { 
        return id;
        }
    public void setId(Long id) {
         this.id = id; 
        }

    public ParkingSlot getSlot() { 
        return slot; 
        }
    public void setSlot(ParkingSlot slot) {
         this.slot = slot; 
        }

    public LocalDateTime getStartTime() { 
        return startTime; 
    }
    public void setStartTime(LocalDateTime startTime) { 
        this.startTime = startTime; 
    }

    public LocalDateTime getEndTime() { 
        return endTime; 
    }
    public void setEndTime(LocalDateTime endTime) { 
        this.endTime = endTime; 
    }

    public String getVehicleNumber() { 
        return vehicleNumber; 
    }
    public void setVehicleNumber(String vehicleNumber) { 
        this.vehicleNumber = vehicleNumber; 
    }

    public VehicleType getVehicleType() { 
        return vehicleType; 
    }
    public void setVehicleType(VehicleType vehicleType) { 
        this.vehicleType = vehicleType; 
    }

    public double getCost() {
         return cost; 
        }
    public void setCost(double cost) {
         this.cost = cost; 
        }
}
