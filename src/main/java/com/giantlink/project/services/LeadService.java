package com.giantlink.project.services;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;

import com.giantlink.project.exceptions.GlAlreadyExistException;
import com.giantlink.project.exceptions.GlNotFoundException;
import com.giantlink.project.models.requests.LeadRequest;
import com.giantlink.project.models.responses.LeadResponse;

public interface LeadService {
	
	LeadResponse add(LeadRequest leadRequest) throws GlAlreadyExistException, GlNotFoundException;

	List<LeadResponse> getAll();

	LeadResponse get(Long id) throws GlNotFoundException;

	void delete(Long id) throws GlNotFoundException;

	LeadResponse update(Long id, LeadRequest leadRequest) throws GlNotFoundException ;
	
	Map<String, Object> getAllPaginations(String name, Pageable pageable);

}
