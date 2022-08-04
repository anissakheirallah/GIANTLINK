package com.giantlink.project.models.responses;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ServiceResponse {

	private Long id;

	private String serviceName;
	private Float point;
	private Long idProject;
	private Boolean statut;

	// private Set<LeadResponse> leads;
}
