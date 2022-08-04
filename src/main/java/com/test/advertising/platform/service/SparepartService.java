package com.test.advertising.platform.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.advertising.platform.service.SparepartService;
import com.test.advertising.platform.domain.Sparepart;
import com.test.advertising.platform.repository.SparepartRepository;

@Service
public class SparepartService {
	@Autowired
	private SparepartRepository repository;
		
		public Sparepart addSparepart(Sparepart sparepart) {
			return repository.save(sparepart);
		}
		
		public List<Sparepart>getAllSpareparts(){
			return repository.findAll();
		}
		
		public void deleteSparepart(Integer id) {
			repository.deleteById(id);
		}
		
		public Sparepart getSparepartById(Integer id) {
			return repository.findById(id).get();
		}
		
		public Sparepart updateSparepart(Integer id,Sparepart sparepart) {
			return repository.save(sparepart);
		}
	}

