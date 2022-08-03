package com.giantlink.project.controlers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.util.MimeTypeUtils;
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

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.giantlink.project.entities.User;
import com.giantlink.project.exceptions.GlAlreadyExistException;
import com.giantlink.project.exceptions.GlNotFoundException;
import com.giantlink.project.mappers.UserMapper;
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
	public ResponseEntity<List<UserResponse>> getUsers() {
		return new ResponseEntity<List<UserResponse>>(userService.getUsers(), HttpStatus.OK);
	}

	@PostMapping()
	public ResponseEntity<UserResponse> add(@RequestBody UserRequest userRequest)
			throws GlAlreadyExistException, GlNotFoundException {
		return new ResponseEntity<UserResponse>(userService.addUser(userRequest), HttpStatus.CREATED);
	}

	@GetMapping("/{id}")
	public ResponseEntity<UserResponse> getUser(@PathVariable Long id) throws GlNotFoundException {
		return new ResponseEntity<UserResponse>(userService.getUser(id), HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> delete(@PathVariable("id") Long id) throws GlNotFoundException {
		userService.deleteUser(id);
		return new ResponseEntity<String>("deleted !", HttpStatus.OK);
	}

	@PutMapping("/{id}")
	public ResponseEntity<UserResponse> update(@PathVariable("id") Long id, @RequestBody UserRequest userRequest)
			throws GlNotFoundException {
		return new ResponseEntity<UserResponse>(userService.updateUser(id, userRequest), HttpStatus.OK);
	}

	@PutMapping("/role/{id}/{idRole}")
	public ResponseEntity<String> grantRole(@PathVariable("id") Long id, @PathVariable("idRole") Long idRole)
			throws GlNotFoundException {
		userService.changeRole(id, idRole);
		return new ResponseEntity<String>("Role Granted !", HttpStatus.ACCEPTED);
	}

	@GetMapping
	public ResponseEntity<Map<String, Object>> getAll(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "2") int size, @RequestParam(defaultValue = "", name = "name") String name) {
		Pageable pageable = PageRequest.of(page, size);
		return new ResponseEntity<Map<String, Object>>(userService.getAllPaginations(pageable), HttpStatus.OK);
	}

	@GetMapping("/refreshtoken")
	public void refreashToken(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException {
		String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
			try {
				String refresh_token = authorizationHeader.substring("Bearer ".length());
				Algorithm algorithm = Algorithm.HMAC256("GiantLink_Vente".getBytes());
				JWTVerifier jwtVerifier = JWT.require(algorithm).build();
				DecodedJWT decodedJWT = jwtVerifier.verify(refresh_token);
				String username = decodedJWT.getSubject();

				User user = UserMapper.INSTANCE.mapResponse(userService.getUser(username));

				List<GrantedAuthority> authorities = new ArrayList<>();

				authorities.add(new SimpleGrantedAuthority("" + user.getRole().getName()));

				String access_token = JWT.create().withSubject(user.getUserName())
						.withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
						.withIssuer(request.getRequestURL().toString())
						.withClaim("roles",
								authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
						.sign(algorithm);

				Map<String, String> tokens = new HashMap<>();
				tokens.put("access_token", access_token);
				tokens.put("refresh_token", refresh_token);

				response.setContentType(MediaType.APPLICATION_JSON_VALUE);
				new ObjectMapper().writeValue(response.getOutputStream(), tokens);
			} catch (Exception e) {
				response.setHeader("error", e.getMessage());
				response.setStatus(403);
				Map<String, String> error = new HashMap<>();
				error.put("error_message", e.getMessage());
				response.setContentType(MimeTypeUtils.APPLICATION_JSON_VALUE);
				new ObjectMapper().writeValue(response.getOutputStream(), error);
			}
		} else {
			throw new RuntimeException("Refresh token not valid");
		}
	}

}
