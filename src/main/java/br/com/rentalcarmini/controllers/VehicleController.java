package br.com.rentalcarmini.controllers;

import br.com.rentalcarmini.dtos.VehicleDTO;
import br.com.rentalcarmini.models.VehicleModel;
import br.com.rentalcarmini.services.VehicleService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vehicles")
public class VehicleController {

    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @PostMapping()
    public ResponseEntity<Object> createVehicle(@RequestBody @Valid VehicleDTO vehicleDTO) {
        VehicleModel vehicleModel = new VehicleModel();
        BeanUtils.copyProperties(vehicleDTO, vehicleModel);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(vehicleService.save(vehicleModel));
    }
}
