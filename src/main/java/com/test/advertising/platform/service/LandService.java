package com.test.advertising.platform.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.advertising.platform.domain.Land;
import com.test.advertising.platform.repository.LandRepository;

@Service
public class LandService {

	@Autowired
	private LandRepository repository;
		
		public Land addLand(Land land) {
			return repository.save(land);
		}
		
		public List<Land>getAllLands(){
			return repository.findAll();
		}
		
		public void deleteLand(Integer id) {
			repository.deleteById(id);
		}
		
		public Land getLandById(Integer id) {
			return repository.findById(id).get();
		}
		
		public Land updateLand(Integer id,Land land) {
			return repository.save(land);
		}
}
