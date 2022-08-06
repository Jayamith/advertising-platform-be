package com.test.advertising.platform.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.test.advertising.platform.domain.Land;

@Service
public interface LandRepository extends JpaRepository<Land, Integer>{

}
