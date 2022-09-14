package com.giantlink.project.controlers;

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
import com.giantlink.project.models.requests.CommercialRequest;
import com.giantlink.project.models.responses.CommercialResponse;
import com.giantlink.project.services.CommercialService;

@RestController
@RequestMapping("/api/commercial")
@CrossOrigin(origins = { "http://localhost:4200" })
public class CommercialController {
	
	@Autowired
	private  CommercialService commercialService;

	@GetMapping
	public ResponseEntity<Map<String, Object>> getAll(@RequestParam(defaultValue = "0") int page,
													  @RequestParam(defaultValue = "2") int size,
													  @RequestParam(defaultValue = "", name = "name") String name) 
	{
		Pageable pageable = PageRequest.of(page, size);
		return new ResponseEntity<Map<String,Object>>(commercialService.getAllPaginations(name, pageable), HttpStatus.OK);
		
	}

	@PostMapping
	public ResponseEntity<CommercialResponse> add(@RequestBody CommercialRequest commercialRequest)
			throws GlNotFoundException,GlAlreadyExistException {
		return new ResponseEntity<CommercialResponse>(commercialService.add(commercialRequest), HttpStatus.CREATED);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<CommercialResponse> getOne(@PathVariable Long id) throws GlNotFoundException {
		return new ResponseEntity<CommercialResponse>(commercialService.get(id), HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> delete(@PathVariable("id") Long id) throws GlNotFoundException{
		commercialService.delete(id);
		return new ResponseEntity<String>("deleted !", HttpStatus.OK);
	}
	

	@PutMapping("/{id}")
	public ResponseEntity<CommercialResponse> edit(@PathVariable("id") Long id, @RequestBody CommercialRequest commercialRequest) throws GlAlreadyExistException, GlNotFoundException  {
		return new ResponseEntity<CommercialResponse>(commercialService.update(id, commercialRequest), HttpStatus.OK);
	}
	
	@PutMapping("/status/{id}")
	public ResponseEntity<String> changeStatus(@PathVariable("id") Long id, @RequestBody Boolean status)
			throws GlNotFoundException {
		commercialService.changeStatus(id, status);
		return new ResponseEntity<String>("status changed!", HttpStatus.OK);
	}


}
