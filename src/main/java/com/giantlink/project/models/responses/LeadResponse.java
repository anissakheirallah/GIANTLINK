package com.giantlink.project.models.responses;

import java.util.Set;

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
public class LeadResponse {

	private Long id;

	private UserResponse user;
	private ClientResponse client;
	private CommercialResponse commercial;
	private ProductResponse product;

	private Set<ServiceResponse> services;
	

}
