package com.giantlink.project.controlers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.giantlink.project.exceptions.GlAlreadyExistException;
import com.giantlink.project.exceptions.GlNotFoundException;
import com.giantlink.project.models.requests.AppointmentRequest;
import com.giantlink.project.models.responses.AppointmentResponse;
import com.giantlink.project.services.AppointmentService;

@RestController
@RequestMapping("/api/appointment")
@CrossOrigin(origins = { "http://localhost:4200" })
public class AppointmentController {

	@Autowired
	AppointmentService appointmentService;

	@GetMapping("/all")
	public ResponseEntity<List<AppointmentResponse>> getAppointments() {
		return new ResponseEntity<List<AppointmentResponse>>(appointmentService.getAppointments(), HttpStatus.OK);
	}

	@PostMapping()
	public ResponseEntity<AppointmentResponse> addAppointment(@RequestBody AppointmentRequest AppointmentRequest)
			throws GlAlreadyExistException, GlNotFoundException {
		return new ResponseEntity<AppointmentResponse>(appointmentService.addAppointment(AppointmentRequest),
				HttpStatus.CREATED);
	}

	@GetMapping("/{id}")
	public ResponseEntity<AppointmentResponse> getAppointment(@PathVariable Long id) throws GlNotFoundException {
		return new ResponseEntity<AppointmentResponse>(appointmentService.getAppointment(id), HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> delete(@PathVariable("id") Long id) throws GlNotFoundException {
		appointmentService.deleteAppointment(id);
		return new ResponseEntity<String>("deleted !", HttpStatus.OK);
	}

	@PutMapping("/{id}")
	public ResponseEntity<AppointmentResponse> update(@PathVariable("id") Long id,
			@RequestBody AppointmentRequest AppointmentRequest) throws GlNotFoundException {
		return new ResponseEntity<AppointmentResponse>(appointmentService.updateAppointment(id, AppointmentRequest),
				HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<Map<String, Object>> getAll(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "2") int size, @RequestParam(defaultValue = "", name = "name") String name) {
		Pageable pageable = PageRequest.of(page, size);
		return new ResponseEntity<Map<String, Object>>(appointmentService.getAllPaginations(pageable), HttpStatus.OK);
	}
}
