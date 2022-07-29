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
import org.springframework.stereotype.Service;

import com.giantlink.project.entities.Role;
import com.giantlink.project.entities.User;
import com.giantlink.project.mappers.UserMapper;
import com.giantlink.project.models.requests.UserRequest;
import com.giantlink.project.models.responses.UserResponse;
import com.giantlink.project.repositories.RoleRepository;
import com.giantlink.project.repositories.UserRepository;
import com.giantlink.project.services.UserService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImpl implements UserService,UserDetailsService {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	RoleRepository roleRepository;

	@Override
	public UserResponse addUser(UserRequest user) {
		Optional<User> userSearch =  userRepository.findByuserName(user.getUserName());
		if(userSearch.isPresent()) {
		return null;
		}else {
			User us = new User();
			us.builder().name(user.getName()).password(user.getPassword()).userName(user.getUserName()).build();
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
			for (Long roleid : user.getRoles()) {
				Optional<Role> role = roleRepository.findById(roleid);
				roleList.add(role.get());
			}
			userSearch.get().setRoles(roleList);
			
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
	public void grantRole(Long userId,Long roleId) {
		Optional<User> userSearch =  userRepository.findById(userId);
		if(userSearch.isEmpty()) {
			
		}else {
			Optional<Role> roleSearch =  roleRepository.findById(roleId);
			if(roleSearch.isEmpty()) {
			}else {
				userSearch.get().getRoles().add(roleSearch.get());
				userRepository.save(userSearch.get());
			}
		}
	}

	@Override
	public void revokeRole(Long userId,Long roleId) {
		Optional<User> userSearch =  userRepository.findById(userId);
		if(userSearch.isEmpty()) {
			
		}else {
			Optional<Role> roleSearch =  roleRepository.findById(roleId);
			if(roleSearch.isEmpty()) {
				
			}else if(userSearch.get().getRoles().contains(roleSearch)) {
				userSearch.get().getRoles().remove(roleSearch);
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
		user.getRoles().forEach(role ->{
			authorities.add(new SimpleGrantedAuthority(role.getName().toString()));
		});
		return new org.springframework.security.core.userdetails.User(user.getUserName(),user.getPassword(),authorities);
	}

}









