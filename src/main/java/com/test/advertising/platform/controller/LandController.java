package com.test.advertising.platform.controller;
import java.io.IOException;
import java.net.URI;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.test.advertising.platform.domain.ImageModel;
import com.test.advertising.platform.domain.Land;
import com.test.advertising.platform.service.LandService;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class LandController {
	@Autowired
	private LandService landService;

	@PostMapping(value = { "/land/add" }, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public Land addLand(@RequestPart("land") Land land, @RequestPart("imageFile") MultipartFile[] file) {

		try {
			Set<ImageModel> images = uploadImage(file);
			land.setVehicleImages(images);
			return landService.addLand(land);

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
	
	@GetMapping("/lands")
	public List<Land> getAllgetLands() {
		return landService.getAllLands();
	}
	
	@DeleteMapping("/lands/{id}")
	public ResponseEntity<Void> deleteLand(@PathVariable int id) {

		landService.deleteLand(id);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/lands/{id}")
	public Land getLand( @PathVariable int id) {
		return landService.getLandById(id);

	}
	@PutMapping("/lands/{id}")
	public ResponseEntity<Land> updateSparepart(@PathVariable Integer id,
			@RequestBody Land land) {

		Land updatedLand= landService.updateLand(id,land);
		return new ResponseEntity<Land>(updatedLand, HttpStatus.OK);
	}

}