package com.giantlink.project.models.responses;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductResponse {

	private Long id;
	private String productName;
	private Long idProject;

	private LeadResponse lead;
}
