package com.giantlink.project.services;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;

import com.giantlink.project.exceptions.GlAlreadyExistException;
import com.giantlink.project.exceptions.GlNotFoundException;
import com.giantlink.project.models.requests.ServiceTypeRequest;
import com.giantlink.project.models.responses.ServiceTypeResponse;

public interface ServiceTypeService {
	
	ServiceTypeResponse add(ServiceTypeRequest serviceTypeRequest) throws GlAlreadyExistException, GlNotFoundException;

	List<ServiceTypeResponse> getAll();

	ServiceTypeResponse get(Long id) throws GlNotFoundException;

	void delete(Long id) throws GlNotFoundException;

	ServiceTypeResponse update(Long id, ServiceTypeRequest serviceTypeRequest) throws GlNotFoundException ;
	
	Map<String, Object> getAllPaginations(String name, Pageable pageable);


}
