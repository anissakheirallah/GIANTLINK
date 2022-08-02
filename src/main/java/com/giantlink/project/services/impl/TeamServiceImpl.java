package com.giantlink.project.services.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

import com.giantlink.project.models.requests.TeamRequest;
import com.giantlink.project.models.responses.TeamResponse;
import com.giantlink.project.repositories.TeamRepository;
import com.giantlink.project.services.TeamService;

public class TeamServiceImpl implements TeamService{
	
	@Autowired
	TeamRepository teamRepository;

	@Override
	public TeamResponse addTeam(TeamRequest team) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteTeam(Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public TeamResponse updateTeam(Long id, TeamRequest team) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TeamResponse getTeam(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TeamResponse> getTeams() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> getAllTeamsPaginations(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

}
