package com.giantlink.project.services.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

import com.giantlink.project.entities.Client;
import com.giantlink.project.exceptions.GlAlreadyExistException;
import com.giantlink.project.exceptions.GlNotFoundException;
import com.giantlink.project.mappers.ClientMapper;
import com.giantlink.project.models.requests.ClientRequest;
import com.giantlink.project.models.responses.ClientResponse;
import com.giantlink.project.repositories.ClientRepository;
import com.giantlink.project.services.ClientService;

public class ClientServiceImpl implements ClientService  {
	
	@Autowired
	private ClientRepository clientRepository;

	@Override
	public ClientResponse add(ClientRequest clientRequest) throws GlAlreadyExistException, GlNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ClientResponse> getAll() {
		return ClientMapper.INSTANCE.mapClient(clientRepository.findAll());
	}

	@Override
	public ClientResponse get(Long id) throws GlNotFoundException {
		Optional<Client> findClient = clientRepository.findById(id);

		if (!findClient.isPresent()) {
			throw new GlNotFoundException(id.toString(), Client.class.getSimpleName());
		}

		return ClientMapper.INSTANCE.entityToResponse(clientRepository.findById(id).get());
		
	}

	@Override
	public void delete(Long id) throws GlNotFoundException {
		Optional<Client> findClient = clientRepository.findById(id);

		if (!findClient.isPresent()) {
			throw new GlNotFoundException(id.toString(), Client.class.getSimpleName());
		} 
		
		clientRepository.deleteById(id);
		
	}

	@Override
	public ClientResponse update(Long id, ClientRequest clientRequest) throws GlNotFoundException {
		
		Optional<Client> findClient = clientRepository.findById(id);

		if (!findClient.isPresent()) {
			throw new GlNotFoundException(id.toString(), Client.class.getSimpleName());
		}
		
		Client client = clientRepository.findById(id).get();

		client.setFirstName(clientRequest.getFirstName());
		client.setLastName(clientRequest.getLastName());
		client.setEmail(clientRequest.getEmail());
		client.setAdress(clientRequest.getAdress());
		client.setCity(clientRequest.getCity());
		client.setCP(clientRequest.getCP());
		client.setGSM(clientRequest.getGSM());
		client.setTele(clientRequest.getTele());

		clientRepository.save(client);

		return ClientMapper.INSTANCE.entityToResponse(client);
		
	}

	@Override
	public Map<String, Object> getAllPaginations(String name, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

}
