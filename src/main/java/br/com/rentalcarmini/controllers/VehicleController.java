package br.com.rentalcarmini.controllers;

import br.com.rentalcarmini.dtos.VehicleDTO;
import br.com.rentalcarmini.exceptions.RentException;
import br.com.rentalcarmini.exceptions.VehicleException;
import br.com.rentalcarmini.models.RentModel;
import br.com.rentalcarmini.models.VehicleModel;
import br.com.rentalcarmini.services.RentService;
import br.com.rentalcarmini.services.VehicleService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/vehicles")
public class VehicleController {

    private final VehicleService vehicleService;
    private final RentService rentService;

    public VehicleController(VehicleService vehicleService, RentService rentService) {
        this.vehicleService = vehicleService;
        this.rentService = rentService;
    }

    @PostMapping()
    public ResponseEntity<Object> createVehicle(@RequestBody @Valid VehicleDTO vehicleDTO) {
        Optional<VehicleModel> vehicle = vehicleService
                .getByLicensePlate(vehicleDTO.getLicensePlate());

        if(vehicle.isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body("A placa desse veículo já está cadastrada, tente outra placa!");
        }

        VehicleModel vehicleModel = new VehicleModel();
        BeanUtils.copyProperties(vehicleDTO, vehicleModel);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(vehicleService.save(vehicleModel));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateVehicle(@PathVariable UUID id, @RequestBody @Valid VehicleDTO vehicleDTO) {
        Optional<VehicleModel> vehicle = vehicleService.getOne(id);
        if(!vehicle.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Veículo não encontrado!");
        }

        VehicleModel vehicleModelNew = new VehicleModel();

        BeanUtils.copyProperties(vehicleDTO, vehicleModelNew);
        vehicleModelNew.setId(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(vehicleService.save(vehicleModelNew));
    }

    @GetMapping()
    public ResponseEntity<Object> getAllVehicles() {
        List<VehicleModel> vehicles = vehicleService.getAll();
        if(vehicles.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Veículos não encontrados!");
        }

        return ResponseEntity.status(HttpStatus.OK).body(vehicles);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneVehicle(@PathVariable UUID id) {
        Optional<VehicleModel> vehicle = vehicleService.getOne(id);
        if(!vehicle.isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Veículo não encontrado!");
        }

        return ResponseEntity.status(HttpStatus.OK).body(vehicle);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteVehicle(@PathVariable UUID id) {
        Optional<VehicleModel> vehicle = vehicleService.getOne(id);
        if(!vehicle.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Veículo não existe!");
        }

        vehicleService.delete(vehicle.get());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Veículo deletado com sucesso!");
    }

    @PostMapping("/{licensePlate}/rent")
    public ResponseEntity<Object> rentVehicle(@PathVariable String licensePlate) {
        try {
            RentModel rentModel = rentService.rentVehicle(licensePlate);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(rentModel);
        } catch (VehicleException e) {
            throw new RuntimeException("VehicleException => "
                    + e.getMessage());
        } catch (RentException e) {
            throw new RuntimeException("RentException => "
                    + e.getMessage());
        }
    }

    @PostMapping("/{licensePlate}/return")
    public ResponseEntity<Object> returnVehicle(@PathVariable String licensePlate) {
        try {
            RentModel rentModel = rentService.returnVehicle(licensePlate);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(rentModel);
        } catch (VehicleException e) {
            throw new RuntimeException("VehicleException => "
                    + e.getMessage());
        } catch (RentException e) {
            throw new RuntimeException("RentException => "
                    + e.getMessage());
        }
    }
}
