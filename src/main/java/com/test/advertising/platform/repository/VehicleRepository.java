package com.test.advertising.platform.repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.test.advertising.platform.domain.Vehicle;


@Service
public interface VehicleRepository extends JpaRepository<Vehicle, Integer>{

//	private static List<Vehicle> vehicleList = new ArrayList<>();
//	private static long idCounter = 0;
//	
//	static {
//		vehicleList.add(new Vehicle(++idCounter, "Nissan","Nissan Good Car",new Date(), true));
//		vehicleList.add(new Vehicle(++idCounter, "Toyota","Toyota Good Car",new Date(), true));
//		vehicleList.add(new Vehicle(++idCounter, "Tesla","Tesla Good Car",new Date(), true));
//		vehicleList.add(new Vehicle(++idCounter, "Hyundai","Hyundai Good Car",new Date(), true));
//		vehicleList.add(new Vehicle(++idCounter, "BMW","BMW Good Car",new Date(), true));
//	}
//	
//	public List<Vehicle> getAll(){
//		return vehicleList;
//	}
	
//	public Vehicle save(Vehicle vehicle) {
//		if(vehicle.getId()==-1) {
//			vehicle.setId(++idCounter);
//			vehicleList.add(vehicle);
//		}else {
//			deleteById(vehicle.getId());
//			vehicleList.add(vehicle);
//		}
//		return vehicle;
//	}
	
//	public Vehicle deleteById(long id) {
//		Vehicle vehicle = getById(id);
//		
//		if(vehicle == null) return null;
//		
//		if(vehicleList.remove(vehicle)) {
//			return vehicle;
//		}
//		return null;
//	}
	
//	public Vehicle findById(long id) {
//		for(Vehicle v: vehicleList) {
//			if(v.getId() == id) {
//				return v;
//			}
//		}
//		return null;
//	}

//	public Vehicle getById(Long id) {
//		Vehicle vehicle = null;
//		vehicle = vehicleList.stream().filter(v-> v.getId()==id).findFirst().get();
//		return vehicle;
//	}
}
