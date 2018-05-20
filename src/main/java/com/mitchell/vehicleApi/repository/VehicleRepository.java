package com.mitchell.vehicleApi.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.mitchell.vehicleApi.model.Vehicle;

@Repository
public interface VehicleRepository extends MongoRepository<Vehicle, Integer> {
	List<Vehicle> findByMake(String make);
	List<Vehicle> findByModel(String model);
}
