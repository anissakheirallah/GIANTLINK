package com.giantlink.project.models.requests;

import java.util.Set;

import com.giantlink.project.entities.Lead;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {

	private String firstName;
	private String lastName;
	private String userName;
	private String password;
	private String language;

	private Long idRole;
	private Long idTeam;

	private Set<Lead> leads;
}
