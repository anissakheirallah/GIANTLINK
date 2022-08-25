package com.giantlink.project.services;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;

import com.giantlink.project.exceptions.GlAlreadyExistException;
import com.giantlink.project.exceptions.GlNotFoundException;
import com.giantlink.project.models.requests.AppointmentRequest;
import com.giantlink.project.models.responses.AppointmentResponse;

public interface AppointmentService {
	
	AppointmentResponse addAppointment(AppointmentRequest appointmentRequest) throws GlAlreadyExistException, GlNotFoundException;

	void deleteAppointment(Long id) throws GlNotFoundException;

	AppointmentResponse updateAppointment(Long id, AppointmentRequest appointmentRequest) throws GlNotFoundException;

	AppointmentResponse getAppointment(Long id) throws GlNotFoundException;

	List<AppointmentResponse> getAppointments();

	Map<String, Object> getAllPaginations(Pageable pageable);

}
