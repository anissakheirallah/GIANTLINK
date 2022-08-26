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
public class LeadRequest {

	private Long userId;
	private Long commercialId;
	private ClientRequest client;
	//private Long clientId;
	private Long productId;
	
	private Set<ServiceRequest> services;
}
