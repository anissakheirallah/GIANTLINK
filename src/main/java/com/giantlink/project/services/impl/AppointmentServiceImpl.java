package com.giantlink.project.services.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.giantlink.project.entities.Appointment;
import com.giantlink.project.exceptions.GlAlreadyExistException;
import com.giantlink.project.exceptions.GlNotFoundException;
import com.giantlink.project.mappers.AppointmentMapper;
import com.giantlink.project.models.requests.AppointmentRequest;
import com.giantlink.project.models.responses.AppointmentResponse;
import com.giantlink.project.repositories.AppointmentRepository;
import com.giantlink.project.services.AppointmentService;

@Service
public class AppointmentServiceImpl implements AppointmentService {

	@Autowired
	AppointmentRepository appointmentRepository;

	@Override
	public AppointmentResponse addAppointment(AppointmentRequest appointmentRequest)
			throws GlAlreadyExistException, GlNotFoundException {
		System.out.println(appointmentRequest.getAppointmentTime());
		return AppointmentMapper.INSTANCE
				.mapEntity(appointmentRepository.save(AppointmentMapper.INSTANCE.mapRequest(appointmentRequest)));
	}

	@Override
	public void deleteAppointment(Long id) throws GlNotFoundException {
		Optional<Appointment> appointmentOptional = appointmentRepository.findById(id);
		if (appointmentOptional.isEmpty()) {
			throw new GlNotFoundException("Appointement", Appointment.class.getSimpleName());
		}
		appointmentRepository.delete(appointmentOptional.get());
	}

	@Override
	public AppointmentResponse updateAppointment(Long id, AppointmentRequest appointmentRequest)
			throws GlNotFoundException {
		Optional<Appointment> findAppointment = appointmentRepository.findById(id);
		if (findAppointment.isEmpty()) {
			throw new GlNotFoundException("Appointement", Appointment.class.getSimpleName());
		}
		findAppointment.get().setAppointmentDate(appointmentRequest.getAppointmentDate());
		findAppointment.get().setAppointmentTime(appointmentRequest.getAppointmentTime());

		return AppointmentMapper.INSTANCE.mapEntity(appointmentRepository.save(findAppointment.get()));
	}

	@Override
	public AppointmentResponse getAppointment(Long id) throws GlNotFoundException {
		Optional<Appointment> findAppointment = appointmentRepository.findById(id);
		if (findAppointment.isEmpty()) {
			throw new GlNotFoundException("Appointement", Appointment.class.getSimpleName());
		}
		return AppointmentMapper.INSTANCE.mapEntity(findAppointment.get());
	}

	@Override
	public List<AppointmentResponse> getAppointments() {
		return AppointmentMapper.INSTANCE.mapResponses(appointmentRepository.findAll());
	}

	@Override
	public Map<String, Object> getAllPaginations(Pageable pageable) {
		List<AppointmentResponse> appointementResponses = new ArrayList<>();
		Page<Appointment> appointements = appointmentRepository.findAll(pageable);

		appointements.getContent().forEach(appointment -> {
			appointementResponses.add(AppointmentMapper.INSTANCE.mapEntity(appointment));
		});

		Map<String, Object> requestResponse = new HashMap<>();
		requestResponse.put("content", appointementResponses);
		requestResponse.put("currentPage", appointements.getNumber());
		requestResponse.put("totalElements", appointements.getTotalElements());
		requestResponse.put("totalPages", appointements.getTotalPages());
		return requestResponse;
	}

}
