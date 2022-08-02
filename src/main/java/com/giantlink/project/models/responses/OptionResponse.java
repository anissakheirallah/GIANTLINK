package com.giantlink.project.models.responses;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OptionResponse {

	private Long id;
	
	private String optionName;
	private Long idProject;
	
	private LeadResponse lead;
}
