package com.giantlink.project.models.responses;

import java.util.Set;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LeadResponse {

	private Long id;
	
	private UserResponse user;
	private ClientResponse client;
	private CommercialResponse commercial;
	
	private Set<ServiceResponse> Services;
	private Set<ProductResponse> products;
	private Set<OptionResponse> options;
	
}
