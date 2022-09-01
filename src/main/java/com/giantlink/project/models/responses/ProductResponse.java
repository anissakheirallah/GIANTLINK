package com.giantlink.project.models.responses;

import java.util.Set;

import com.giantlink.project.entities.Lead;

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
public class ProductResponse {

	private Long id;
	private String productName;
	

	//private Set<LeadResponse> leads;
}
