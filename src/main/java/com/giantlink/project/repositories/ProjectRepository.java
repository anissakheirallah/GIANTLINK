package com.giantlink.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.giantlink.project.entities.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {

}
