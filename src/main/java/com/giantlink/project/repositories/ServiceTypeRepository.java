package com.giantlink.project.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.giantlink.project.entities.ServiceType;

@Repository
public interface ServiceTypeRepository extends JpaRepository<ServiceType, Long> {

	Optional<ServiceType> findBylibelle(String libelle);

	Page<ServiceType> findBylibelleContainingIgnoreCase(String name, Pageable pageable);

}
