package com.giantlink.project.services;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;

import com.giantlink.project.exceptions.GlAlreadyExistException;
import com.giantlink.project.exceptions.GlNotFoundException;
import com.giantlink.project.models.requests.UserRequest;
import com.giantlink.project.models.responses.UserResponse;

public interface UserService {

	UserResponse addUser(UserRequest userRequest) throws GlAlreadyExistException, GlNotFoundException;

	void deleteUser(Long id) throws GlNotFoundException;

	UserResponse updateUser(Long id, UserRequest userRequest) throws GlNotFoundException;

	UserResponse getUser(Long id) throws GlNotFoundException;

	UserResponse getUser(String userName) throws GlNotFoundException;

	List<UserResponse> getUsers();

	Map<String, Object> getAllPaginations(Pageable pageable);

	void changeRole(Long userId, Long roleId) throws GlNotFoundException;
}
