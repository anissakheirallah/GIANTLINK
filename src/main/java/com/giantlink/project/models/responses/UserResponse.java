package com.giantlink.project.models.responses;

import java.util.Set;

import com.giantlink.project.entities.Lead;
import com.giantlink.project.entities.Role;
import com.giantlink.project.entities.Team;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {

	private Long id;
	private String firstName;
	private String lastName;
	private String userName;
	private String password;
	private String language;

	private Role role;
	private Team team;

	private Set<Lead> leads;
}
