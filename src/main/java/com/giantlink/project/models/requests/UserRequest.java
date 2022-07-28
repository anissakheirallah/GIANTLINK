package com.giantlink.project.models.requests;

import java.util.Set;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {

	private String name;
	private String userName;
	private String password;
	
	private Set<Long> roles;
}
