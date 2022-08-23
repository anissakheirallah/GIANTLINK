package com.giantlink.project.models.responses;

import java.util.Set;

import com.giantlink.project.entities.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
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
	private Set<TeamResponse> teams;

	private Set<LeadResponse> leads;
}
