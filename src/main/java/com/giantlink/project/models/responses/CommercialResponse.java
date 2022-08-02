package com.giantlink.project.models.responses;

import java.util.Set;

import com.giantlink.project.entities.Lead;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommercialResponse {

	private Long id;
	
	private String commercialName;
	private Boolean statut;
	private Long idCalendar;
	
	//private Set<LeadResponse> leads;
}
