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
	public UserResponse addUser(UserRequest user) {
		Optional<User> userSearch = userRepository.findByuserName(user.getUserName());
		if (userSearch.isPresent()) {
			return null;
		} else {
			User us = User.builder().firstName(user.getFirstName()).lastName(user.getLastName())
					.password(encoder.encode(user.getPassword())).language(user.getLanguage())
					.userName(user.getUserName()).leads(new HashSet<>()).build();
			Optional<Role> role = roleRepository.findById(user.getIdRole());
			Optional<Team> team = teamRepository.findById(user.getIdTeam());
			us.setRole(role.get());
			us.setTeam(team.get());
			return UserMapper.INSTANCE.mapEntity(userRepository.save(us));
		}
	}

	@Override
	public void deleteUser(Long id) {
		Optional<User> userSearch = userRepository.findById(id);
		if (userSearch.isEmpty()) {
		} else {
			userRepository.delete(userSearch.get());
		}

	}

	@Override
	public UserResponse updateUser(Long id, UserRequest user) {
		Optional<User> userSearch = userRepository.findById(id);
		if (userSearch.isEmpty()) {
			return null;
		} else {

			userSearch.get().setFirstName(user.getFirstName());
			userSearch.get().setLastName(user.getLastName());
			userSearch.get().setPassword(encoder.encode(user.getPassword()));
			userSearch.get().setUserName(user.getUserName());
			userSearch.get().setLanguage(user.getLanguage());

			Optional<Role> role = roleRepository.findById(user.getIdRole());
			Optional<Team> team = teamRepository.findById(user.getIdTeam());

			if (role.isPresent()) {
				userSearch.get().setRole(role.get());
			}
			if (team.isPresent()) {
				userSearch.get().setTeam(team.get());
			}

			return UserMapper.INSTANCE.mapEntity(userRepository.save(userSearch.get()));
		}
	}

	@Override
	public UserResponse getUser(Long id) {
		Optional<User> userSearch = userRepository.findById(id);
		if (userSearch.isEmpty()) {
			return null;
		} else {
			return UserMapper.INSTANCE.mapEntity(userSearch.get());
		}
	}

	@Override
	public List<UserResponse> getUsers() {
		return UserMapper.INSTANCE.mapResponses(userRepository.findAll());
	}

	@Override
	public void changeRole(Long userId, Long roleId) {

		Optional<User> userSearch = userRepository.findById(userId);
		if (userSearch.isEmpty()) {

		} else {
			Optional<Role> roleSearch = roleRepository.findById(roleId);
			if (roleSearch.isEmpty()) {

			} else {
				userSearch.get().setRole(roleSearch.get());
				;
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
	public UserResponse getUser(String userName) {
		Optional<User> userSearch = userRepository.findByuserName(userName);
		if (userSearch.isEmpty()) {
			return null;
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
