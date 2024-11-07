package br.com.rentalcarmini.services;

import br.com.rentalcarmini.models.VehicleModel;
import br.com.rentalcarmini.repositories.VehicleRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class VehicleService {

    private final VehicleRepository vehicleReposity;

    public VehicleService(VehicleRepository vehicleReposity) {
        this.vehicleReposity = vehicleReposity;
    }

    public List<VehicleModel> getAll() {
        return this.vehicleReposity.findAll();
    }

    public Optional<VehicleModel> getOne(UUID id) {
        return this.vehicleReposity.findById(id);
    }

    @Transactional
    public VehicleModel save(VehicleModel vehicleModel) {
        return this.vehicleReposity.save(vehicleModel);
    }

    @Transactional
    public void delete(VehicleModel vehicleModel) {
        this.vehicleReposity.delete(vehicleModel);
    }

    public Optional<VehicleModel> getByLicensePlate(String licensePlate) {
        return this.vehicleReposity.findByLicensePlate(licensePlate);
    }
}