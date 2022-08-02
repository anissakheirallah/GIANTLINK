package com.giantlink.project.models.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LeadRequest {

	private Long user_id;
	private Long commercial_id;
	private Long client_id;
}
