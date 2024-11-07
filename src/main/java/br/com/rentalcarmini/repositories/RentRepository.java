package br.com.rentalcarmini.repositories;

import br.com.rentalcarmini.models.RentModel;
import br.com.rentalcarmini.models.VehicleModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RentRepository extends JpaRepository<RentModel, UUID> {

    Optional<RentModel> findByVehicleModelAndReturnDateIsNull(VehicleModel vehicleModel);

}
