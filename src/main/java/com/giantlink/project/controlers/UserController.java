package com.giantlink.project.controlers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.RestController;

import com.giantlink.project.models.requests.UserRequest;
import com.giantlink.project.models.responses.UserResponse;
import com.giantlink.project.services.UserService;


@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = { "http://localhost:4200" })
public class UserController {
	
	@Autowired
	UserService userService;
	
	
	@GetMapping("/users")
	public ResponseEntity<List<UserResponse>>getUsers(){
		return new ResponseEntity<List<UserResponse>>(userService.getUsers(), HttpStatus.OK);
	}
	
	
	@PostMapping()
	public ResponseEntity<UserResponse> add(@RequestBody UserRequest userRequest) {
		return new ResponseEntity<UserResponse>(userService.addUser(userRequest), HttpStatus.CREATED);
	}
	
	
	@GetMapping("/{id}")
	public ResponseEntity<UserResponse> getUser(@PathVariable Long id) {
		return new ResponseEntity<UserResponse>(userService.getUser(id), HttpStatus.OK);
	}

	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> delete(@PathVariable("id") Long id) {
		userService.deleteUser(id);
		return new ResponseEntity<String>("deleted !", HttpStatus.OK);
	}

	
	@PutMapping("/{id}")
	public ResponseEntity<UserResponse> update(@PathVariable("id") Long id,
			@RequestBody UserRequest userRequest) {
		return new ResponseEntity<UserResponse>(userService.updateUser(id, userRequest), HttpStatus.OK);
	}
	
	
	@PutMapping("/grant/{id}")
	public ResponseEntity<String> grantRole(@PathVariable("id") Long id,
			@PathVariable("idRole") Long idRole) {
		userService.grantRole(id, idRole);
		return new ResponseEntity<String>("Role Granted !", HttpStatus.ACCEPTED);
	}
	
	@PutMapping("/revoke/{id}")
	public ResponseEntity<String> revokeRole(@PathVariable("id") Long id,
			@PathVariable("idRole") Long idRole) {
		userService.revokeRole(id, idRole);
		return new ResponseEntity<String>("Role Revoked !", HttpStatus.NOT_ACCEPTABLE);
	}
}










