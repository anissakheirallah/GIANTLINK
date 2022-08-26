package com.giantlink.project.services.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

import com.giantlink.project.entities.Commercial;
import com.giantlink.project.entities.Lead;
import com.giantlink.project.entities.Product;
import com.giantlink.project.entities.Service;
import com.giantlink.project.entities.User;
import com.giantlink.project.exceptions.GlAlreadyExistException;
import com.giantlink.project.exceptions.GlNotFoundException;
import com.giantlink.project.mappers.ClientMapper;
import com.giantlink.project.mappers.LeadMapper;
import com.giantlink.project.models.requests.LeadRequest;
import com.giantlink.project.models.requests.ServiceRequest;
import com.giantlink.project.models.responses.LeadResponse;
import com.giantlink.project.repositories.ClientRepository;
import com.giantlink.project.repositories.CommercialRepository;
import com.giantlink.project.repositories.LeadRepository;
import com.giantlink.project.repositories.ProductRepository;
import com.giantlink.project.repositories.ServiceRepository;
import com.giantlink.project.repositories.UserRepository;
import com.giantlink.project.services.ClientService;
import com.giantlink.project.services.LeadService;

@org.springframework.stereotype.Service
public class LeadServiceImpl implements LeadService {

	@Autowired
	private LeadRepository leadRepository;

	@Autowired
	private ServiceRepository serviceRepository;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ClientRepository clientRepository;

	@Autowired
	private CommercialRepository commercialRepository;


	@Transactional
	@Override
	public LeadResponse add(LeadRequest leadRequest) throws GlAlreadyExistException, GlNotFoundException {

		// can the client have the same lead twice ??

		// check user
		Optional<User> findUser = userRepository.findById(leadRequest.getUserId());
		if (findUser.isEmpty()) {
			throw new GlNotFoundException(leadRequest.getUserId().toString(), User.class.getSimpleName());
		}

		// check client
		// Optional<Client> findClient = clientRepository.findById(leadRequest.getClientId());
		// if (!findClient.isPresent()) {
		// throw new GlNotFoundException(leadRequest.getClientId().toString(),Client.class.getSimpleName());
		// }


		// check commercial
		Optional<Commercial> findCommercial = commercialRepository.findById(leadRequest.getCommercialId());
		if (!findCommercial.isPresent()) {
			throw new GlNotFoundException(leadRequest.getCommercialId().toString(), Commercial.class.getSimpleName());
		}
		// check product
		Optional<Product> findProduct = productRepository.findById(leadRequest.getProductId());
		if (!findProduct.isPresent()) {
			throw new GlNotFoundException(leadRequest.getProductId().toString(), Product.class.getSimpleName());
		}

		

		Lead lead = LeadMapper.INSTANCE.requestToEntity(leadRequest);
		lead.setCommercial(findCommercial.get());
		lead.setProduct(findProduct.get());
		// lead.setClient(findClient.get());
		lead.setClient(clientRepository.save(ClientMapper.INSTANCE.requestToEntity(leadRequest.getClient())));
		lead.setUser(findUser.get());

		// check service
		Set<Service> services = new HashSet<>();
		Set<ServiceRequest> serviceRequests = leadRequest.getServices();
		if (serviceRequests == null || serviceRequests.isEmpty()) {
			throw new GlNotFoundException("list is empty", Service.class.getSimpleName());
		}
		for (ServiceRequest service : serviceRequests) {
			Optional<Service> findService = serviceRepository.findByServiceName(service.getServiceName());
			if (findService.isPresent()) {
				services.add(findService.get());
			}
		}

		lead.setServices(services);

		return LeadMapper.INSTANCE.entityToResponse(leadRepository.save(lead));
		
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

		return null;

	}

}
