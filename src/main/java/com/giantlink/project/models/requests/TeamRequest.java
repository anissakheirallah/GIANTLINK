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
public class TeamRequest {

	private String teamName;

	private Set<Long> idsSup;

	private Set<Long> idsProject;
}
