package com.giantlink.project.services;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;

import com.giantlink.project.exceptions.GlAlreadyExistException;
import com.giantlink.project.exceptions.GlNotFoundException;
import com.giantlink.project.models.requests.PackRequest;
import com.giantlink.project.models.responses.PackResponse;

public interface PackService {

	PackResponse add(PackRequest packRequest) throws GlAlreadyExistException, GlNotFoundException;

	List<PackResponse> getAll();

	PackResponse get(Long id) throws GlNotFoundException;

	void delete(Long id) throws GlNotFoundException;

	PackResponse update(Long id, PackRequest packRequest) throws GlNotFoundException;

	Map<String, Object> getAllPaginations(String name, Pageable pageable);
}
