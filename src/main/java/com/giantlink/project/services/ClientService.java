package com.giantlink.project.services;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;

import com.giantlink.project.exceptions.GlAlreadyExistException;
import com.giantlink.project.exceptions.GlNotFoundException;
import com.giantlink.project.models.requests.ClientRequest;
import com.giantlink.project.models.responses.ClientResponse;

public interface ClientService {
	
	ClientResponse add(ClientRequest clientRequest) throws GlAlreadyExistException, GlNotFoundException;

	List<ClientResponse> getAll();

	ClientResponse get(Long id) throws GlNotFoundException;

	void delete(Long id) throws GlNotFoundException;

	ClientResponse update(Long id, ClientRequest clientRequest) throws GlNotFoundException ;
	
	Map<String, Object> getAllPaginations(String name, Pageable pageable);

}
