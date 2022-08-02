package com.giantlink.project.models.responses;

import java.util.Set;

import com.giantlink.project.entities.Role;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponse {

	private Long id;
	private String name;
	private String firstName;
	private String lastName;
	private String userName;
	private String password;
	private String language;

	private Role role;
	private TeamResponse team;

	private Set<LeadResponse> leads;
}
