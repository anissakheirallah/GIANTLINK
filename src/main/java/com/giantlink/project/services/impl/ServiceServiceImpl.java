package com.giantlink.project.services.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.giantlink.project.entities.Client;
import com.giantlink.project.entities.Service;
import com.giantlink.project.entities.ServiceType;
import com.giantlink.project.exceptions.GlAlreadyExistException;
import com.giantlink.project.exceptions.GlNotFoundException;
import com.giantlink.project.mappers.ServiceMapper;
import com.giantlink.project.models.requests.ServiceRequest;
import com.giantlink.project.models.responses.ServiceResponse;
import com.giantlink.project.repositories.ServiceRepository;
import com.giantlink.project.repositories.ServiceTypeRepository;
import com.giantlink.project.services.ServiceService;

@org.springframework.stereotype.Service
public class ServiceServiceImpl implements ServiceService {

	@Autowired
	private ServiceRepository serviceRepository;

	@Autowired
	private ServiceTypeRepository typeRepository;

	
	@Override
	public ServiceResponse add(ServiceRequest serviceRequest) throws GlAlreadyExistException, GlNotFoundException {

		Optional<Service> findService = serviceRepository.findByServiceName(serviceRequest.getServiceName());
		Optional<ServiceType> findServiceType = typeRepository.findById(serviceRequest.getServiceTypeId());

		if (!findServiceType.isPresent()) {
			throw new GlNotFoundException(serviceRequest.getServiceName(), Service.class.getSimpleName());
		}
		
		if (findService.isPresent()) {
			throw new GlAlreadyExistException(serviceRequest.getServiceName(), Service.class.getSimpleName());
		}
		
		
		Service service = ServiceMapper.INSTANCE.requestToEntity(serviceRequest);
		service.setServiceType(findServiceType.get());
		
		return ServiceMapper.INSTANCE
				.entityToResponse(serviceRepository.save(service));

	}

	@Override
	public List<ServiceResponse> getAll() {

		return ServiceMapper.INSTANCE.mapService(serviceRepository.findAll());
	}

	@Override
	public ServiceResponse get(Long id) throws GlNotFoundException {

		Optional<Service> findService = serviceRepository.findById(id);

		if (!findService.isPresent()) {
			throw new GlNotFoundException(id.toString(), Service.class.getSimpleName());
		}

		return ServiceMapper.INSTANCE.entityToResponse(serviceRepository.findById(id).get());

	}

	@Override
	public void delete(Long id) throws GlNotFoundException {

		Optional<Service> findService = serviceRepository.findById(id);

		if (!findService.isPresent()) {
			throw new GlNotFoundException(id.toString(), Client.class.getSimpleName());
		}

		serviceRepository.deleteById(id);

	}

	@Override
	public ServiceResponse update(Long id, ServiceRequest serviceRequest) throws GlNotFoundException {

		Optional<Service> findService = serviceRepository.findById(id);
		Optional<ServiceType> findServiceType = typeRepository.findById(serviceRequest.getServiceTypeId());

		if (!findServiceType.isPresent()) {
			throw new GlNotFoundException(serviceRequest.getServiceName(), Service.class.getSimpleName());
		}

		if (!findService.isPresent()) {
			throw new GlNotFoundException(id.toString(), Service.class.getSimpleName());
		}

		Service service = serviceRepository.findById(id).get();

		service.setPoint(serviceRequest.getPoint());
		service.setServiceName(serviceRequest.getServiceName());
		service.setStatut(serviceRequest.getStatut());
		service.setServiceType(findServiceType.get());

		return ServiceMapper.INSTANCE.entityToResponse(serviceRepository.save(service));

	}

	@Override
	public Map<String, Object> getAllPaginations(String name, Pageable pageable) {

		List<ServiceResponse> serviceList = new ArrayList<>();
		Page<Service> services = (name.isBlank()) ? serviceRepository.findAll(pageable)
				: serviceRepository.findByServiceNameContainingIgnoreCase(name, pageable);
		services.getContent().forEach(service -> {
			ServiceResponse response = ServiceResponse.builder().id(service.getId())
					.serviceName(service.getServiceName()).point(service.getPoint()).statut(service.getStatut())
					.build();

			serviceList.add(response);
		});
		Map<String, Object> serviceMap = new HashMap<>();
		serviceMap.put("content", serviceList);
		serviceMap.put("currentPage", services.getNumber());
		serviceMap.put("totalElements", services.getTotalElements());
		serviceMap.put("totalPages", services.getTotalPages());

		return serviceMap;
	}

}
