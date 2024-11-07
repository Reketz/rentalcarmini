package br.com.rentalcarmini.services;

import br.com.rentalcarmini.exceptions.RentException;
import br.com.rentalcarmini.exceptions.VehicleException;
import br.com.rentalcarmini.models.RentModel;
import br.com.rentalcarmini.models.VehicleModel;
import br.com.rentalcarmini.repositories.RentRepository;
import br.com.rentalcarmini.repositories.VehicleRepository;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class RentService {

    private final RentRepository rentRepository;
    private final VehicleRepository vehicleRepository;

    public RentService(RentRepository rentRepository, VehicleRepository vehicleRepository) {
        this.rentRepository = rentRepository;
        this.vehicleRepository = vehicleRepository;
    }

    public RentModel rentVehicle(String licensePlate) throws VehicleException, RentException {
        Optional<VehicleModel> vehicleModelOptional = vehicleRepository.findByLicensePlate(licensePlate);
        if(!vehicleModelOptional.isPresent()) {
            throw new VehicleException("Essa placa não está vinculada a nenhum veículo!");
        }

        VehicleModel vehicleModel = vehicleModelOptional.get();
        if(vehicleModel.isRented()) {
            throw new RentException("O veículo já está alugado!");
        }

        vehicleModel.setRented(true);
        vehicleRepository.save(vehicleModel);

        RentModel rentModel = new RentModel();
        rentModel.setVehicleModel(vehicleModel);
        rentModel.setRentDate(LocalDateTime.now());

        return rentRepository.save(rentModel);
    }

    public RentModel returnVehicle(String licensePlate) throws VehicleException, RentException {

        Optional<VehicleModel> vehicleModelOptional = vehicleRepository.findByLicensePlate(licensePlate);
        if(!vehicleModelOptional.isPresent()) {
            throw new VehicleException("Essa placa não está vinculada a nenhum veículo!");
        }

        VehicleModel vehicleModel = vehicleModelOptional.get();
        if(!vehicleModel.isRented()) {
            throw new RentException("O veículo não está alugado!");
        }

        Optional<RentModel> rentModel = rentRepository
                .findByVehicleModelAndReturnDateIsNull(vehicleModel);
        if(!rentModel.isPresent()) {
            throw new RentException("Aluguel não encontrado, ou veículo já foi devolvido!");
        }

        RentModel rent = rentModel.get();

        rent.setReturnDate(LocalDateTime.now());

        long days = Duration.between(rent.getRentDate(), rent.getReturnDate()).toDays() + 1;
        rent.setTotalPrice(rent.getVehicleModel().getRentalPricePerDay() * days);

        vehicleModel.setRented(false);
        vehicleRepository.save(vehicleModel);

        return rentRepository.save(rent);
    }
}
