package com.giantlink.project.models.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServiceResponse {

	private Long id;

	private String serviceName;
	private Float point;
	private Long idProject;
	private Boolean statut;

	// private Set<LeadResponse> leads;
}
