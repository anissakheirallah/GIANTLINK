package com.giantlink.project.services.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.giantlink.project.entities.Role;
import com.giantlink.project.entities.Team;
import com.giantlink.project.entities.User;
import com.giantlink.project.exceptions.GlAlreadyExistException;
import com.giantlink.project.exceptions.GlNotFoundException;
import com.giantlink.project.mappers.UserMapper;
import com.giantlink.project.models.requests.UserRequest;
import com.giantlink.project.models.responses.UserResponse;
import com.giantlink.project.repositories.LeadRepository;
import com.giantlink.project.repositories.RoleRepository;
import com.giantlink.project.repositories.TeamRepository;
import com.giantlink.project.repositories.UserRepository;
import com.giantlink.project.services.UserService;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	TeamRepository teamRepository;

	@Autowired
	LeadRepository leadRepository;

	BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

	@Override
	public UserResponse addUser(UserRequest userRequest) throws GlAlreadyExistException, GlNotFoundException {
		Optional<User> userSearch = userRepository.findByuserName(userRequest.getUserName());
		if (userSearch.isPresent()) {
			throw new GlAlreadyExistException(userRequest.getUserName(), User.class.getSimpleName());
		} else {

			Optional<Role> role = roleRepository.findById(userRequest.getIdRole());
			Optional<Team> team = teamRepository.findById(userRequest.getIdTeam());
			if (role.isEmpty()) {
				throw new GlNotFoundException(userRequest.getIdRole().toString(), Role.class.getSimpleName());
			}
			if (team.isEmpty()) {
				throw new GlNotFoundException(userRequest.getIdTeam().toString(), Team.class.getSimpleName());
			}
			User us = User.builder().firstName(userRequest.getFirstName()).lastName(userRequest.getLastName())
					.password(encoder.encode(userRequest.getPassword())).language(userRequest.getLanguage())
					.userName(userRequest.getUserName()).leads(new HashSet<>()).role(role.get()).team(team.get())
					.build();
			return UserMapper.INSTANCE.mapEntity(userRepository.save(us));
		}
	}

	@Override
	public void deleteUser(Long id) throws GlNotFoundException {
		Optional<User> userSearch = userRepository.findById(id);
		if (userSearch.isEmpty()) {
			throw new GlNotFoundException(id.toString(), User.class.getSimpleName());
		} else {
			userRepository.delete(userSearch.get());
		}
	}

	@Override
	public UserResponse updateUser(Long id, UserRequest userRequest) throws GlNotFoundException {
		Optional<User> userSearch = userRepository.findById(id);
		if (userSearch.isEmpty()) {
			throw new GlNotFoundException(id.toString(), User.class.getSimpleName());
		} else {

			userSearch.get().setFirstName(userRequest.getFirstName());
			userSearch.get().setLastName(userRequest.getLastName());
			userSearch.get().setPassword(encoder.encode(userRequest.getPassword()));
			userSearch.get().setUserName(userRequest.getUserName());
			userSearch.get().setLanguage(userRequest.getLanguage());

			Optional<Role> role = roleRepository.findById(userRequest.getIdRole());
			Optional<Team> team = teamRepository.findById(userRequest.getIdTeam());

			if (role.isEmpty()) {
				throw new GlNotFoundException(userRequest.getIdRole().toString(), Role.class.getSimpleName());
			}
			if (team.isEmpty()) {
				throw new GlNotFoundException(userRequest.getIdTeam().toString(), Team.class.getSimpleName());
			}
			userSearch.get().setRole(role.get());
			userSearch.get().setTeam(team.get());
			return UserMapper.INSTANCE.mapEntity(userRepository.save(userSearch.get()));
		}
	}

	@Override
	public UserResponse getUser(Long id) throws GlNotFoundException {
		Optional<User> userSearch = userRepository.findById(id);
		if (userSearch.isEmpty()) {
			throw new GlNotFoundException(id.toString(), User.class.getSimpleName());
		} else {
			return UserMapper.INSTANCE.mapEntity(userSearch.get());
		}
	}

	@Override
	public List<UserResponse> getUsers() {
		return UserMapper.INSTANCE.mapResponses(userRepository.findAll());
	}

	@Override
	public void changeRole(Long userId, Long roleId) throws GlNotFoundException {
		Optional<User> userSearch = userRepository.findById(userId);
		if (userSearch.isEmpty()) {
			throw new GlNotFoundException(userId.toString(), User.class.getSimpleName());
		} else {
			Optional<Role> roleSearch = roleRepository.findById(roleId);
			if (roleSearch.isEmpty()) {
				throw new GlNotFoundException(roleId.toString(), Role.class.getSimpleName());
			} else {
				userSearch.get().setRole(roleSearch.get());
				userRepository.save(userSearch.get());
			}
		}
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByuserName(username).get();
		if (user == null) {
			throw new UsernameNotFoundException("user : " + username + "not found");
		} else {
			System.out.println("user exist");
		}
		Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();

		authorities.add(new SimpleGrantedAuthority(user.getRole().getName().toString()));

		return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(),
				authorities);
	}

	@Override
	public UserResponse getUser(String userName) throws GlNotFoundException {
		Optional<User> userSearch = userRepository.findByuserName(userName);
		if (userSearch.isEmpty()) {
			throw new GlNotFoundException(userName, User.class.getSimpleName());
		} else {
			return UserMapper.INSTANCE.mapEntity(userSearch.get());
		}
	}

	@Override
	public Map<String, Object> getAllUsersPaginations(Pageable pageable) {
		List<UserResponse> usersResponses = new ArrayList<>();
		Page<User> users = userRepository.findAll(pageable);

		users.getContent().forEach(user -> {
			usersResponses.add(UserMapper.INSTANCE.mapEntity(user));
		});

		Map<String, Object> requestResponse = new HashMap<>();
		requestResponse.put("content", usersResponses);
		requestResponse.put("currentPage", users.getNumber());
		requestResponse.put("totalElements", users.getTotalElements());
		requestResponse.put("totalPages", users.getTotalPages());
		return requestResponse;
	}

}
