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
import com.giantlink.project.models.requests.LeadRequest;
import com.giantlink.project.models.responses.LeadResponse;
import com.giantlink.project.services.LeadService;

@RestController
@RequestMapping("/api/lead")
@CrossOrigin(origins = { "http://localhost:4200" })
public class LeadController {
	
	@Autowired
	private LeadService leadService;

	@GetMapping
	public ResponseEntity<Map<String, Object>> getAll(@RequestParam(defaultValue = "0") int page,
													  @RequestParam(defaultValue = "2") int size,
													  @RequestParam(defaultValue = "", name = "name") String name) 
	{
		Pageable pageable = PageRequest.of(page, size);
		return new ResponseEntity<Map<String,Object>>(leadService.getAllPaginations(name, pageable), HttpStatus.OK);
		
	}
	
	

	@PostMapping
	public ResponseEntity<LeadResponse> add(@RequestBody LeadRequest leadRequest)
			throws GlNotFoundException,GlAlreadyExistException, Exception {
		return new ResponseEntity<LeadResponse>(leadService.add(leadRequest), HttpStatus.CREATED);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<LeadResponse> getOne(@PathVariable Long id) throws GlNotFoundException {
		return new ResponseEntity<LeadResponse>(leadService.get(id), HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> delete(@PathVariable("id") Long id) throws GlNotFoundException{
		leadService.delete(id);
		return new ResponseEntity<String>("deleted !", HttpStatus.OK);
	}
	

	@PutMapping("/{id}")
	public ResponseEntity<LeadResponse> edit(@PathVariable("id") Long id, @RequestBody LeadRequest leadRequest) throws GlAlreadyExistException, GlNotFoundException, Exception  {
		return new ResponseEntity<LeadResponse>(leadService.update(id, leadRequest), HttpStatus.OK);
	}


}
