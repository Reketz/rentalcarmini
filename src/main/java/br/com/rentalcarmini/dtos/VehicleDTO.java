package br.com.rentalcarmini.dtos;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class VehicleDTO {

    @NotBlank
    private String brand;
    @NotBlank
    private String model;
    @Size(min = 7, max = 7)
    private String licensePlate;
    @NotNull
    private double rentalPricePerDay;
}