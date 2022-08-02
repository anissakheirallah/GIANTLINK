package com.giantlink.project.services;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;

import com.giantlink.project.models.requests.TeamRequest;
import com.giantlink.project.models.responses.TeamResponse;

public interface TeamService {

	TeamResponse addTeam(TeamRequest team);

	void deleteTeam(Long id);

	TeamResponse updateTeam(Long id, TeamRequest team);

	TeamResponse getTeam(Long id);

	List<TeamResponse> getTeams();

	Map<String, Object> getAllTeamsPaginations(Pageable pageable);
}
