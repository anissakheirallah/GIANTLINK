package com.giantlink.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.giantlink.project.entities.Appointment;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

}
