package com.test.advertising.platform.repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.test.advertising.platform.domain.Sparepart;


@Service
public interface SparepartRepository extends JpaRepository<Sparepart, Integer>{
	

}
