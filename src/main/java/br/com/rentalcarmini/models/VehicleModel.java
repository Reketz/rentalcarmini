package br.com.rentalcarmini.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
public class VehicleModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(nullable = false)
    private String brand;
    @Column(nullable = false)
    private String model;
    @Column(nullable = false, length = 7, unique = true)
    private String licensePlate;
    @Column(nullable = false)
    private boolean rented;
    @Column(nullable = false)
    private double rentalPricePerDay;
}
