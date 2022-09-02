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

import com.giantlink.project.entities.Option;
import com.giantlink.project.entities.Project;
import com.giantlink.project.exceptions.GlAlreadyExistException;
import com.giantlink.project.exceptions.GlNotFoundException;
import com.giantlink.project.mappers.OptionMapper;
import com.giantlink.project.models.requests.OptionRequest;
import com.giantlink.project.models.responses.OptionResponse;
import com.giantlink.project.repositories.OptionRepository;
import com.giantlink.project.repositories.ProjectRepository;
import com.giantlink.project.services.OptionService;

@Service
public class OptionServiceImpl implements OptionService {

	@Autowired
	private OptionRepository optionRepository;

	@Autowired
	private ProjectRepository projectRepository;

	@Override
	public OptionResponse add(OptionRequest optionRequest) throws GlAlreadyExistException, GlNotFoundException {

		Optional<Option> findOption = optionRepository.findByOptionName(optionRequest.getOptionName());
		Optional<Project> findProject = projectRepository.findById(optionRequest.getIdProject());
		if (!findProject.isPresent()) {
			throw new GlNotFoundException(optionRequest.getIdProject().toString(), Project.class.getSimpleName());
		}
		if (findOption.isPresent()) {
			throw new GlAlreadyExistException(optionRequest.getOptionName(), Option.class.getSimpleName());
		}
		Option op = OptionMapper.INSTANCE.requestToEntity(optionRequest);
		op.setProject(findProject.get());
		return OptionMapper.INSTANCE.entityToResponse(optionRepository.save(op));

	}

	@Override
	public List<OptionResponse> getAll() {
		return OptionMapper.INSTANCE.mapOption(optionRepository.findAll());
	}

	@Override
	public OptionResponse get(Long id) throws GlNotFoundException {
		Optional<Option> findOption = optionRepository.findById(id);
		if (!findOption.isPresent()) {
			throw new GlNotFoundException(id.toString(), Option.class.getSimpleName());
		}
		return OptionMapper.INSTANCE.entityToResponse(optionRepository.findById(id).get());
	}

	@Override
	public void delete(Long id) throws GlNotFoundException {
		Optional<Option> findOption = optionRepository.findById(id);
		if (!findOption.isPresent()) {
			throw new GlNotFoundException(id.toString(), Option.class.getSimpleName());
		}
		optionRepository.deleteById(id);
	}

	@Override
	public OptionResponse update(Long id, OptionRequest optionRequest) throws GlNotFoundException {
		Optional<Option> findOption = optionRepository.findById(id);
		Optional<Project> findProject = projectRepository.findById(optionRequest.getIdProject());
		if (!findProject.isPresent()) {
			throw new GlNotFoundException(optionRequest.getIdProject().toString(), Project.class.getSimpleName());
		}
		if (!findOption.isPresent()) {
			throw new GlNotFoundException(id.toString(), Option.class.getSimpleName());
		}
		Option option = optionRepository.findById(id).get();
		option.setOptionName(optionRequest.getOptionName());
		option.setProject(findProject.get());

		return OptionMapper.INSTANCE.entityToResponse(optionRepository.save(option));
	}

	@Override
	public Map<String, Object> getAllPaginations(String name, Pageable pageable) {
		List<OptionResponse> optionList = new ArrayList<>();
		Page<Option> options = (name.isBlank()) ? optionRepository.findAll(pageable)
				: optionRepository.findByOptionNameContainingIgnoreCase(name, pageable);
		options.getContent().forEach(option -> {
			OptionResponse response = OptionResponse.builder().id(option.getId()).optionName(option.getOptionName())
					.build();
			optionList.add(response);
		});
		Map<String, Object> optionMap = new HashMap<>();
		optionMap.put("content", optionList);
		optionMap.put("currentPage", options.getNumber());
		optionMap.put("totalElements", options.getTotalElements());
		optionMap.put("totalPages", options.getTotalPages());

		return optionMap;

	}

}
