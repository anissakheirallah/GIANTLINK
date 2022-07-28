package com.giantlink.project.controlers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.giantlink.project.models.responses.UserResponse;
import com.giantlink.project.services.UserService;


@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = { "http://localhost:4200" })
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/users")
	public ResponseEntity<List<UserResponse>>getUsers(){
		return new ResponseEntity<List<UserResponse>>(userService.getUsers(), HttpStatus.OK);
	}
}










