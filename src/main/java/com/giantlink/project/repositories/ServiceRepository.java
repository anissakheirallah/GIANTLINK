package com.giantlink.project.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.giantlink.project.entities.Service;

@Repository
public interface ServiceRepository extends JpaRepository<Service, Long>{
	
	Optional<Service> findByServiceName(String serviceName);
	
	Page<Service> findByServiceNameContainingIgnoreCase(String name, Pageable pageable);

}
