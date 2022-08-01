package com.giantlink.project.services.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.giantlink.project.entities.Role;
import com.giantlink.project.entities.User;
import com.giantlink.project.mappers.UserMapper;
import com.giantlink.project.models.requests.UserRequest;
import com.giantlink.project.models.responses.UserResponse;
import com.giantlink.project.repositories.RoleRepository;
import com.giantlink.project.repositories.UserRepository;
import com.giantlink.project.services.UserService;

@Service
public class UserServiceImpl implements UserService,UserDetailsService {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	RoleRepository roleRepository;
	
	BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

	@Override
	public UserResponse addUser(UserRequest user) {
		Optional<User> userSearch =  userRepository.findByuserName(user.getUserName());
		if(userSearch.isPresent()) {
		return null;
		}else {
			User us = User.builder().name(user.getName()).password(encoder.encode(user.getPassword())).userName(user.getUserName()).build();
			Optional<Role> role = roleRepository.findById(user.getIdRole());
			us.setRole(role.get());
			return UserMapper.INSTANCE.mapResponse(userRepository.save(us));
		}
	}

	@Override
	public void deleteUser(Long id) {
		Optional<User> userSearch =  userRepository.findById(id);
		if(userSearch.isEmpty()) {
		}else {
			userRepository.delete(userSearch.get());
		}
		
	}

	@Override
	public UserResponse updateUser(Long id, UserRequest user) {
		Optional<User> userSearch =  userRepository.findById(id);
		if(userSearch.isEmpty()) {
			return null;
		}else {
			Set<Role> roleList = new HashSet<>();
			userSearch.get().setName(user.getName());
			userSearch.get().setPassword(user.getPassword());
			userSearch.get().setUserName(user.getUserName());
			Optional<Role> role = roleRepository.findById(user.getIdRole());
			userSearch.get().setRole(role.get());
			
			return UserMapper.INSTANCE.mapResponse(userRepository.save(userSearch.get()));
		}
	}

	@Override
	public UserResponse getUser(Long id) {
		Optional<User> userSearch =  userRepository.findById(id);
		if(userSearch.isEmpty()) {
			return null;
		}else {
			return UserMapper.INSTANCE.mapResponse(userSearch.get());
		}
	}

	@Override
	public List<UserResponse> getUsers() {
		return UserMapper.INSTANCE.mapResponses(userRepository.findAll());
	}

	
	@Override
	public void changeRole(Long userId, Long roleId) {
		
		Optional<User> userSearch =  userRepository.findById(userId);
		if(userSearch.isEmpty()) {
			
		}else {
			Optional<Role> roleSearch =  roleRepository.findById(roleId);
			if(roleSearch.isEmpty()) {
				
			}else {
				userSearch.get().setRole(roleSearch.get());;
				userRepository.save(userSearch.get());
			}
		}
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByuserName(username).get();
		if(user == null) {
			throw new UsernameNotFoundException("user : " + username + "not found");
		}else {
			System.out.println("user exist");	
		}
		Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
		
		authorities.add(new SimpleGrantedAuthority(user.getRole().getName().toString()));

		return new org.springframework.security.core.userdetails.User(user.getUserName(),user.getPassword(),authorities);
	}



}









