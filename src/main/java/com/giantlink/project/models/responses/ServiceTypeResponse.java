package com.giantlink.project.models.responses;

import java.util.Set;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ServiceTypeResponse {
	
	private Long id;
	private String libelle;
	
	private Set<ServiceResponse> services;

}
