package com.giantlink.project.models.responses;

import com.giantlink.project.entities.Role;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponse {

	private Long id;
	private String name;
	private String userName;
	private String password;

	private Role role;
}
