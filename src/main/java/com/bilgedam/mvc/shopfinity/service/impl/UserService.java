package com.bilgedam.mvc.shopfinity.service.impl;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bilgedam.mvc.shopfinity.enums.Role;
import com.bilgedam.mvc.shopfinity.model.UserEntity;
import com.bilgedam.mvc.shopfinity.repository.UserRepository;
import com.bilgedam.mvc.shopfinity.service.UserReadable;
import com.bilgedam.mvc.shopfinity.service.UserWriteable;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService, UserReadable, UserWriteable {

	private final UserRepository userRepository;
	private final PasswordEncoder encoder;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return userRepository.findByEmail(username).orElseThrow(()-> new UsernameNotFoundException(username));
	}

	@Override
	public List<UserEntity> getList() {
		return userRepository.findAll();
	}

	@Override
	public UserEntity getById(Integer id) {
		return userRepository.findById(id).orElseThrow(()-> new IllegalArgumentException());
	}

	@Override
	public List<UserEntity> getList(String sortBy, String direction) {
		Sort sort = null;

		if ("asc".equalsIgnoreCase(direction)) {
			sort = Sort.by(sortBy).ascending();
		} else {
			sort = Sort.by(sortBy).descending();
		}

		// return categoryRepository.findAll();
		return null; // userRepository.findByUsername(sort); 
	}

	@Override
	public void add(UserEntity entity) {
		
		userRepository.save(entity);
		
	}

	@Override
	public void change(Integer id, UserEntity entity) {
		
		UserEntity user = getById(id);
		
		user.setBirthDate(entity.getBirthDate());
		//user.setEmail(entity.getEmail());
		user.setName(entity.getName());
		user.setPassword(encoder.encode(entity.getPassword()));
		user.setRole(Role.ROLE_USER);
		user.setSurname(entity.getSurname());
	}

	@Override
	public void remove(Integer id) {
		
		userRepository.deleteById(id);
		
	}
	

}
