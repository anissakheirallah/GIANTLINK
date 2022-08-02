package com.giantlink.project.services;

import java.util.List;

import com.giantlink.project.models.requests.UserRequest;
import com.giantlink.project.models.responses.UserResponse;

public interface UserService {

	UserResponse addUser(UserRequest user);

	void deleteUser(Long id);

	UserResponse updateUser(Long id, UserRequest user);

	UserResponse getUser(Long id);

	UserResponse getUser(String userName);

	List<UserResponse> getUsers();

	void changeRole(Long userId, Long roleId);
}
