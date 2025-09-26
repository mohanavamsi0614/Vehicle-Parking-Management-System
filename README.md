# Parking Lot Reservation Backend

## Overview
The **Parking Lot Reservation Backend** is a RESTful service built using **Java 17+** and **Spring Boot 3+**.  
It allows administrators to manage parking floors and slots, and enables customers to reserve slots for specific time ranges without conflicts. The system ensures no overlapping reservations and calculates parking fees based on vehicle type and duration.

## Features
- **Floor Management**: Create parking floors (`POST /floors`)  
- **Slot Management**: Create parking slots for each floor (`POST /slots`)  
- **Reservation**: Reserve a parking slot (`POST /reserve`)  
  - Checks availability to prevent overlapping bookings  
  - Calculates cost based on vehicle type and duration  
- **Availability**: List available slots for a given time range (`GET /availability`)  
- **Reservation Details**: Fetch reservation by ID (`GET /reservations/{id}`)  
- **Cancellation**: Cancel a reservation (`DELETE /reservations/{id}`)  

## Business Rules
- Reservation start time must be before end time  
- Reservation duration cannot exceed 24 hours  
- Vehicle number format: `XX00XX0000` (e.g., `KA05MH1234`)  
- Partial hours are rounded up to the next full hour  

## Tech Stack
- Java 17+  
- Spring Boot 3+  
- MySQL  
- Maven  
- Bean Validation (`@Valid`)  
- VS Code (IDE)  

## Setup and Run Instructions

### Prerequisites
- Install **Java 17+**, **Maven**, and **MySQL**  
- Use **VS Code** or any Java IDE  

### 1. Clone the repository
```bash
git clone <your-github-repo-link>
cd parking-lot-reservation
