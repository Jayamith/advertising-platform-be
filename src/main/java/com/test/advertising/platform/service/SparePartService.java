package com.test.advertising.platform.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.advertising.platform.domain.SparePart;
import com.test.advertising.platform.repository.SparePartRepository;

@Service
public class SparePartService {

	@Autowired
	private SparePartRepository repository;

	public SparePart addSparepart(SparePart sparepart) {
		return repository.save(sparepart);
	}

	public List<SparePart> getAllSpareparts() {
		return repository.findAll();
	}

	public void deleteSparepart(Integer id) {
		repository.deleteById(id);
	}

	public SparePart getSparepartById(Integer id) {
		return repository.findById(id).get();
	}

	public SparePart updateSparepart(Integer id, SparePart sparepart) {
		return repository.save(sparepart);
	}
}
