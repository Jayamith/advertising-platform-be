package com.test.advertising.platform.controller;

import java.io.IOException;
import java.net.URI;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.test.advertising.platform.domain.ImageModel;
import com.test.advertising.platform.domain.Vehicle;
import com.test.advertising.platform.service.VehicleService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class VehicleController {

	@Autowired
	private VehicleService vehicleService;

	@PostMapping(value = { "/vehicle/add" }, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public Vehicle addVehicle(@RequestPart("vehicle") Vehicle vehicle, @RequestPart("imageFile") MultipartFile[] file) {

		try {
			Set<ImageModel> images = uploadImage(file);
			vehicle.setVehicleImages(images);
			return vehicleService.addVehicle(vehicle);

		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}

	}

	public Set<ImageModel> uploadImage(MultipartFile[] multipartFiles) throws IOException {
		Set<ImageModel> imageModels = new HashSet<>();

		for (MultipartFile file : multipartFiles) {
			ImageModel imageModel = new ImageModel(file.getOriginalFilename(), file.getContentType(), file.getBytes());
			imageModels.add(imageModel);
		}
		return imageModels;
	}
	
	@GetMapping("/vehicles")
	public List<Vehicle> getAllVehicles() {
		return vehicleService.getAllVehicles();
	}
	
	@DeleteMapping("/vehicles/{id}")
	public ResponseEntity<Void> deleteVehicle(@PathVariable int id) {

		vehicleService.deleteVehicle(id);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/vehicles/{id}")
	public Vehicle getVehicle( @PathVariable int id) {
		return vehicleService.getVehicleById(id);

	}
	
	@GetMapping("/vehiclesBySeller")
	public List<Vehicle> getVehicle(@PathParam("seller") String seller) {
		return vehicleService.findBySeller(seller);

	}
	
//	@PutMapping("/vehicles/{id}")
//	public ResponseEntity<Vehicle> updateVehicle(@PathVariable Integer id,
//			@RequestBody Vehicle vehicle) {
//
//		Vehicle updatedVehicle = vehicleService.updateVehicle(id,vehicle);
//		return new ResponseEntity<Vehicle>(updatedVehicle, HttpStatus.OK);
//	}

	@PutMapping(value = { "/vehicles/{id}" }, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public Vehicle updateVehicle(@PathVariable Integer id,@RequestPart("vehicle") Vehicle vehicle, @RequestPart("imageFile") MultipartFile[] file) {

		try {
			Set<ImageModel> images = uploadImage(file);
			vehicle.setVehicleImages(images);
			return vehicleService.updateVehicle(id,vehicle);

		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}

	}
	
//	@Autowired
//	private VehicleRepository vehicleDAO;
//
//	@GetMapping("/users/{username}/vehicles")
//	public List<Vehicle> getAllVehicles(@PathVariable String username) {
//		return vehicleDAO.getAll();
//
//	}
//
//	@DeleteMapping("/users/{username}/vehicles/{id}")
//	public ResponseEntity<Void> deleteVehicle(@PathVariable String username, @PathVariable long id) {
//
//		Vehicle vehicle = vehicleDAO.deleteById(id);
//
//		if (vehicle != null) {
//			return ResponseEntity.noContent().build();
//		}
//		return ResponseEntity.notFound().build();
//	}
//
//	@GetMapping("/users/{username}/vehicles/{id}")
//	public Vehicle getVehicle(@PathVariable String username, @PathVariable long id) {
//		return vehicleDAO.getById(id);
//
//	}
//
//	@PutMapping("/users/{username}/vehicles/{id}")
//	public ResponseEntity<Vehicle> updateVehicle(@PathVariable String username, @PathVariable long id,
//			@RequestBody Vehicle vehicle) {
//
//		Vehicle updatedVehicle = vehicleDAO.save(vehicle);
//		return new ResponseEntity<Vehicle>(updatedVehicle, HttpStatus.OK);
//	}
//
//	@PostMapping("/users/{username}/vehicles")
//	public ResponseEntity<Vehicle> createVehicle(@PathVariable String username, @RequestBody Vehicle vehicle) {
//
//		Vehicle createdVehicle = vehicleDAO.save(vehicle);
//
//		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
//		.path("/{id}").buildAndExpand(createdVehicle.getId()).toUri();
//		
//		return ResponseEntity.created(uri).build();
//	}

}
