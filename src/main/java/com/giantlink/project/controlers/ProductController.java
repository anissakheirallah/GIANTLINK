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
import com.giantlink.project.models.requests.ProductRequest;
import com.giantlink.project.models.responses.ProductResponse;
import com.giantlink.project.services.ProductService;

@RestController
@RequestMapping("/api/product")
@CrossOrigin(origins = { "http://localhost:4200" })
public class ProductController {
	
	@Autowired
	private ProductService productService;

	@GetMapping
	public ResponseEntity<Map<String, Object>> getAll(@RequestParam(defaultValue = "0") int page,
													  @RequestParam(defaultValue = "2") int size,
													  @RequestParam(defaultValue = "", name = "name") String name) 
	{
		Pageable pageable = PageRequest.of(page, size);
		return new ResponseEntity<Map<String,Object>>(productService.getAllPaginations(name, pageable), HttpStatus.OK);
		
	}

	@PostMapping
	public ResponseEntity<ProductResponse> add(@RequestBody ProductRequest productRequest)
			throws GlNotFoundException,GlAlreadyExistException {
		return new ResponseEntity<ProductResponse>(productService.add(productRequest), HttpStatus.CREATED);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ProductResponse> getOne(@PathVariable Long id) throws GlNotFoundException {
		return new ResponseEntity<ProductResponse>(productService.get(id), HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> delete(@PathVariable("id") Long id) throws GlNotFoundException{
		productService.delete(id);
		return new ResponseEntity<String>("deleted !", HttpStatus.OK);
	}
	

	@PutMapping("/{id}")
	public ResponseEntity<ProductResponse> edit(@PathVariable("id") Long id, @RequestBody ProductRequest productRequest) throws GlAlreadyExistException, GlNotFoundException  {
		return new ResponseEntity<ProductResponse>(productService.update(id, productRequest), HttpStatus.OK);
	}


}
