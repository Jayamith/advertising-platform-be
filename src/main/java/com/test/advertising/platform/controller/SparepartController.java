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
import com.test.advertising.platform.service.SparepartService;

import com.test.advertising.platform.domain.Sparepart;

import com.test.advertising.platform.service.SparepartService;



@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class SparepartController {
	@Autowired
	private SparepartService sparepartService;

	@PostMapping(value = { "/sparepart/add" }, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public Sparepart addSparepart(@RequestPart("sparepart") Sparepart sparepart, @RequestPart("imageFile") MultipartFile[] file) {

		try {
			Set<ImageModel> images = uploadImage(file);
			sparepart.setVehicleImages(images);
			return sparepartService.addSparepart(sparepart);

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
	
	@GetMapping("/spareparts")
	public List<Sparepart> getAllgetSpareparts() {
		return sparepartService.getAllSpareparts();
	}
	
	@DeleteMapping("/spareparts/{id}")
	public ResponseEntity<Void> deleteSparepart(@PathVariable int id) {

		sparepartService.deleteSparepart(id);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/spareparts/{id}")
	public Sparepart getSparepart( @PathVariable int id) {
		return sparepartService.getSparepartById(id);

	}
	@PutMapping("/spareparts/{id}")
	public ResponseEntity<Sparepart> updateSparepart(@PathVariable Integer id,
			@RequestBody Sparepart sparepart) {

		Sparepart updatedSparepart= sparepartService.updateSparepart(id,sparepart);
		return new ResponseEntity<Sparepart>(updatedSparepart, HttpStatus.OK);
	}

}
