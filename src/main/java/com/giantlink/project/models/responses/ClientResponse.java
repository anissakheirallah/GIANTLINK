package com.giantlink.project.models.responses;

import java.util.Set;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClientResponse {

	private Long id;
	
	private String firstName;
	private String lastName;
	private String adress;
	private String tele;
	private String email;
	private String gsm;
	private String cp;
	private String city;
	
	//private Set<LeadResponse> leads;
	
}
