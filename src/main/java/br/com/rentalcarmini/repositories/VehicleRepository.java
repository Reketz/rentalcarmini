package br.com.rentalcarmini.repositories;

import br.com.rentalcarmini.models.VehicleModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface VehicleRepository extends JpaRepository<VehicleModel, UUID> {
    public Optional<VehicleModel> findByLicensePlate(String licensePlate);
}
