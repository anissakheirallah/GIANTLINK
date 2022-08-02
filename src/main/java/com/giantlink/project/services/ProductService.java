package com.giantlink.project.services;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;

import com.giantlink.project.exceptions.GlAlreadyExistException;
import com.giantlink.project.exceptions.GlNotFoundException;
import com.giantlink.project.models.requests.ProductRequest;
import com.giantlink.project.models.responses.ProductResponse;

public interface ProductService {

	ProductResponse add(ProductRequest productRequest) throws GlAlreadyExistException, GlNotFoundException;

	List<ProductResponse> getAll();

	ProductResponse get(Long id) throws GlNotFoundException;

	void delete(Long id) throws GlNotFoundException;

	ProductResponse update(Long id, ProductRequest productRequest) throws GlNotFoundException ;
	
	Map<String, Object> getAllPaginations(String name, Pageable pageable);
}
