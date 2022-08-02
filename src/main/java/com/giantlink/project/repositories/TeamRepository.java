package com.giantlink.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.giantlink.project.entities.Team;

public interface TeamRepository extends JpaRepository<Team, Long>{

}
