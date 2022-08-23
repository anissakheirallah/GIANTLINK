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

import com.giantlink.project.entities.ServiceType;
import com.giantlink.project.exceptions.GlAlreadyExistException;
import com.giantlink.project.exceptions.GlNotFoundException;
import com.giantlink.project.mappers.ServiceMapper;
import com.giantlink.project.mappers.ServiceTypeMapper;
import com.giantlink.project.models.requests.ServiceTypeRequest;
import com.giantlink.project.models.responses.ServiceTypeResponse;
import com.giantlink.project.repositories.ServiceTypeRepository;
import com.giantlink.project.services.ServiceTypeService;

@Service
public class ServiceTypeServiceImpl implements ServiceTypeService {

	@Autowired
	private ServiceTypeRepository typeRepository;

	@Override
	public ServiceTypeResponse add(ServiceTypeRequest serviceTypeRequest)
			throws GlAlreadyExistException, GlNotFoundException {

		Optional<ServiceType> findType = typeRepository.findBylibelle(serviceTypeRequest.getLibelle());

		if (findType.isPresent()) {
			throw new GlAlreadyExistException(serviceTypeRequest.getLibelle(), ServiceType.class.getSimpleName());
		}
		return ServiceTypeMapper.INSTANCE
				.entityToResponse(typeRepository.save(ServiceTypeMapper.INSTANCE.requestToEntity(serviceTypeRequest)));

	}

	@Override
	public List<ServiceTypeResponse> getAll() {
		return ServiceTypeMapper.INSTANCE.mapServiceType(typeRepository.findAll());
	}

	@Override
	public ServiceTypeResponse get(Long id) throws GlNotFoundException {

		Optional<ServiceType> findType = typeRepository.findById(id);

		if (!findType.isPresent()) {
			throw new GlNotFoundException(id.toString(), ServiceType.class.getSimpleName());
		}

		return ServiceTypeMapper.INSTANCE.entityToResponse(typeRepository.findById(id).get());

	}

	@Override
	public void delete(Long id) throws GlNotFoundException {

		Optional<ServiceType> findType = typeRepository.findById(id);

		if (!findType.isPresent()) {
			throw new GlNotFoundException(id.toString(), ServiceType.class.getSimpleName());
		}

		typeRepository.deleteById(id);
	}

	@Override
	public ServiceTypeResponse update(Long id, ServiceTypeRequest serviceTypeRequest) throws GlNotFoundException {

		Optional<ServiceType> findType = typeRepository.findById(id);

		if (!findType.isPresent()) {
			throw new GlNotFoundException(id.toString(), ServiceType.class.getSimpleName());
		}

		ServiceType serviceType = typeRepository.findById(id).get();

		serviceType.setLibelle(serviceTypeRequest.getLibelle());

		typeRepository.save(serviceType);

		return ServiceTypeMapper.INSTANCE.entityToResponse(serviceType);

	}

	@Override
	public Map<String, Object> getAllPaginations(String name, Pageable pageable) {

		List<ServiceTypeResponse> typeList = new ArrayList<>();
		Page<ServiceType> types = (name.isBlank()) ? typeRepository.findAll(pageable)
				: typeRepository.findBylibelleContainingIgnoreCase(name, pageable);
		types.getContent().forEach(type -> {
			ServiceTypeResponse response = ServiceTypeResponse.builder().id(type.getId()).libelle(type.getLibelle())
					.services(ServiceMapper.INSTANCE.mapService(type.getServices())).build();

			typeList.add(response);
		});
		Map<String, Object> typeMap = new HashMap<>();
		typeMap.put("content", typeList);
		typeMap.put("currentPage", types.getNumber());
		typeMap.put("totalElements", types.getTotalElements());
		typeMap.put("totalPages", types.getTotalPages());

		return typeMap;
	}

}
