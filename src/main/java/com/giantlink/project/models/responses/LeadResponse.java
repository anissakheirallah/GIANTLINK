package com.giantlink.project.models.responses;

import java.util.Date;
import java.util.Set;

import com.giantlink.glintranetdto.models.responses.EmployeeResponse;

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

	//private UserResponse user;
	private EmployeeResponse employee;
	private ClientResponse client;
	private CommercialResponse commercial;
	private ProductResponse product;

	private Set<ServiceResponse> services;

	private Date appointmentDate;

	private Date appointmentTime;
	
	private float totalPoint;
	private String voice;
	private String callType;

}
