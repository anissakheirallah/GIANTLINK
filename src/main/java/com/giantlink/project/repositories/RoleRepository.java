package com.giantlink.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.giantlink.project.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{
	
	Role findByName(String name);
}
