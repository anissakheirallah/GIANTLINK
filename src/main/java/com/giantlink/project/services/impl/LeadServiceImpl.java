package com.giantlink.project.services.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.giantlink.project.entities.Lead;
import com.giantlink.project.exceptions.GlAlreadyExistException;
import com.giantlink.project.exceptions.GlNotFoundException;
import com.giantlink.project.mappers.LeadMapper;
import com.giantlink.project.models.requests.LeadRequest;
import com.giantlink.project.models.responses.LeadResponse;
import com.giantlink.project.repositories.LeadRepository;
import com.giantlink.project.services.LeadService;

@Service
public class LeadServiceImpl implements LeadService {

	@Autowired
	private LeadRepository leadRepository;

	@Override
	public LeadResponse add(LeadRequest leadRequest) throws GlAlreadyExistException, GlNotFoundException {

		return LeadMapper.INSTANCE
				.entityToResponse(leadRepository.save(LeadMapper.INSTANCE.requestToEntity(leadRequest)));

	}

	@Override
	public List<LeadResponse> getAll() {

		return LeadMapper.INSTANCE.mapLead(leadRepository.findAll());
	}

	@Override
	public LeadResponse get(Long id) throws GlNotFoundException {

		Optional<Lead> findLead = leadRepository.findById(id);

		if (!findLead.isPresent()) {
			throw new GlNotFoundException(id.toString(), Lead.class.getSimpleName());
		}

		return LeadMapper.INSTANCE.entityToResponse(leadRepository.findById(id).get());

	}

	@Override
	public void delete(Long id) throws GlNotFoundException {

		Optional<Lead> findLead = leadRepository.findById(id);

		if (!findLead.isPresent()) {
			throw new GlNotFoundException(id.toString(), Lead.class.getSimpleName());
		}

		leadRepository.deleteById(id);

	}

	@Override
	public LeadResponse update(Long id, LeadRequest leadRequest) throws GlNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> getAllPaginations(String name, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

}
