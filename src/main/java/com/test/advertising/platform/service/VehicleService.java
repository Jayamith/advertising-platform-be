package com.test.advertising.platform.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.advertising.platform.domain.Vehicle;
import com.test.advertising.platform.repository.VehicleRepository;

@Service
public class VehicleService {

@Autowired
private VehicleRepository repository;
	
	public Vehicle addVehicle(Vehicle vehicle) {
		return repository.save(vehicle);
	}
	
	public List<Vehicle> getAllVehicles(){
		return repository.findAll();
	}
	
	public void deleteVehicle(Integer id) {
		repository.deleteById(id);
	}
	
	public Vehicle getVehicleById(Integer id) {
		return repository.findById(id).get();
	}
}