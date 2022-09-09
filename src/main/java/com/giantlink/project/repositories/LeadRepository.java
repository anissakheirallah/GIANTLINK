package com.giantlink.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.giantlink.project.entities.Lead;

@Repository
public interface LeadRepository extends JpaRepository<Lead, Long>{
	
	//Optional<User> findByEmployeeId(Long employeeId);
	
	

}
