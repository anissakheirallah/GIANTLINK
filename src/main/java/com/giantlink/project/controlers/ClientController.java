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
import com.giantlink.project.models.requests.ClientRequest;
import com.giantlink.project.models.responses.ClientResponse;
import com.giantlink.project.services.ClientService;

@RestController
@RequestMapping("/api/client")
@CrossOrigin(origins = { "http://localhost:4200" })
public class ClientController {
	
	
	@Autowired
	private ClientService clientService;

	@GetMapping
	public ResponseEntity<Map<String, Object>> getAll(@RequestParam(defaultValue = "0") int page,
													  @RequestParam(defaultValue = "6") int size,
													  @RequestParam(defaultValue = "", name = "name") String name) 
	{
		Pageable pageable = PageRequest.of(page, size);
		return new ResponseEntity<Map<String,Object>>(clientService.getAllPaginations(name, pageable), HttpStatus.OK);
		
	}

	@PostMapping
	public ResponseEntity<ClientResponse> add(@RequestBody ClientRequest clientRequest)
			throws GlNotFoundException,GlAlreadyExistException {
		return new ResponseEntity<ClientResponse>(clientService.add(clientRequest), HttpStatus.CREATED);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ClientResponse> getOne(@PathVariable Long id) throws GlNotFoundException {
		return new ResponseEntity<ClientResponse>(clientService.get(id), HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> delete(@PathVariable("id") Long id) throws GlNotFoundException{
		clientService.delete(id);
		return new ResponseEntity<String>("deleted !", HttpStatus.OK);
	}
	

	@PutMapping("/{id}")
	public ResponseEntity<ClientResponse> edit(@PathVariable("id") Long id, @RequestBody ClientRequest clientRequest) throws GlAlreadyExistException, GlNotFoundException  {
		return new ResponseEntity<ClientResponse>(clientService.update(id, clientRequest), HttpStatus.OK);
	}


}
