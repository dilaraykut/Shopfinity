package com.bilgedam.mvc.shopfinity.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.bilgedam.mvc.shopfinity.enums.Role;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name="users")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserEntity implements UserDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Size(min=2, max=30)
	private String name;
	
	@Size(min=2, max=30)
	private String surname;
	
	@Size(min=2, max=30)
	@Email
	private String email;
	
	@Past
	private LocalDate birthDate;
	
	//@Size(min=8,max=30)
	private String password; 
	
	private Role role;
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority(role.name()));
		
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override// Spring Security username unique olmasını sağlar sağlamazsa kendimiz unique yapacağız
	public String getUsername() {
		return email;
	}

}
