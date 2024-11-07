package br.com.rentalcarmini.models;

import br.com.rentalcarmini.dtos.VehicleDTO;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
public class RentModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    private VehicleModel vehicleModel;

    @Column(nullable = false)
    private LocalDateTime rentDate;

    private LocalDateTime returnDate;
    private double totalPrice;

}
