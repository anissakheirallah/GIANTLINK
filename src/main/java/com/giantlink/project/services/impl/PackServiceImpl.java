package com.giantlink.project.services.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.giantlink.project.entities.Pack;
import com.giantlink.project.entities.Project;
import com.giantlink.project.exceptions.GlAlreadyExistException;
import com.giantlink.project.exceptions.GlNotFoundException;
import com.giantlink.project.mappers.PackMapper;
import com.giantlink.project.models.requests.PackRequest;
import com.giantlink.project.models.responses.PackResponse;
import com.giantlink.project.repositories.PackRepository;
import com.giantlink.project.repositories.ProjectRepository;
import com.giantlink.project.services.PackService;

@Service
public class PackServiceImpl implements PackService {

	@Autowired
	PackRepository packRepository;

	@Autowired
	ProjectRepository projectRepository;

	@Override
	public PackResponse add(PackRequest packRequest) throws GlAlreadyExistException, GlNotFoundException {
		Optional<Pack> findPack = packRepository.findByPackName(packRequest.getPackName());
		if (findPack.isPresent()) {
			throw new GlAlreadyExistException("pack", Pack.class.getSimpleName());
		}
		Optional<Project> findProject = projectRepository.findById(packRequest.getProject_id());
		if (findProject.isEmpty()) {
			throw new GlNotFoundException("project", Project.class.getSimpleName());
		}
		Pack pack = PackMapper.INSTANCE.requestToEntity(packRequest);
		pack.setProject(findProject.get());

		return PackMapper.INSTANCE.entityToResponse(packRepository.save(pack));
	}

	@Override
	public List<PackResponse> getAll() {
		return PackMapper.INSTANCE.mapPack(packRepository.findAll());
	}

	@Override
	public PackResponse get(Long id) throws GlNotFoundException {
		Optional<Pack> findPack = packRepository.findById(id);
		if (findPack.isEmpty()) {
			throw new GlNotFoundException("pack", Pack.class.getSimpleName());
		}
		return PackMapper.INSTANCE.entityToResponse(findPack.get());
	}

	@Override
	public void delete(Long id) throws GlNotFoundException {
		Optional<Pack> findPack = packRepository.findById(id);
		if (findPack.isPresent()) {
			throw new GlNotFoundException("pack", Pack.class.getSimpleName());
		}
		packRepository.delete(findPack.get());
	}

	@Override
	public PackResponse update(Long id, PackRequest packRequest) throws GlNotFoundException {
		Optional<Pack> findPack = packRepository.findById(id);
		if (findPack.isEmpty()) {
			throw new GlNotFoundException("pack", Pack.class.getSimpleName());
		}
		Optional<Project> findProject = projectRepository.findById(packRequest.getProject_id());
		if (findProject.isEmpty()) {
			throw new GlNotFoundException("project", Project.class.getSimpleName());
		}
		findPack.get().setPackName(packRequest.getPackName());
		findPack.get().setProject(findProject.get());

		return PackMapper.INSTANCE.entityToResponse(packRepository.save(findPack.get()));
	}

	@Override
	public Map<String, Object> getAllPaginations(String name, Pageable pageable) {
		List<PackResponse> packResponses = new ArrayList<>();
		Page<Pack> packs = (name.isBlank()) ? packRepository.findAll(pageable)
				: packRepository.findByPackName(name, pageable);

		packs.getContent().forEach(pack -> {
			packResponses.add(PackMapper.INSTANCE.entityToResponse(pack));
		});

		Map<String, Object> requestResponse = new HashMap<>();
		requestResponse.put("content", packResponses);
		requestResponse.put("currentPage", packs.getNumber());
		requestResponse.put("totalElements", packs.getTotalElements());
		requestResponse.put("totalPages", packs.getTotalPages());
		return requestResponse;
	}

}
