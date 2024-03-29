package com.example.demo.model;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserSpringSecurity implements UserDetails {

	private static final long serialVersionUID = 1L;
	private User user;

	public UserSpringSecurity(User user) {
		this.user = user;
	}
	
	public String getEmail() {
		return user.getEmail();
	}
	
	public String getRole() {
		return user.getRole();
	}
	
	@Override
	public String getPassword() {
		return user.getPassword();
	}
	
	@Override
	public String getUsername() {
		return user.getId();
	}
	
	@Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collections = new ArrayList<>();
        
        for(String role : user.getRole().split(",")){ //계정당 권한을 여러개 가질 수 있다고 가정함 
        	collections.add(new SimpleGrantedAuthority(role));
        }
        return collections;
    }
	
	@Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
