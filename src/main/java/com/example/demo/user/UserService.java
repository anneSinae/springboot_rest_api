package com.example.demo.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.model.User;

@Service
public class UserService implements UserDetailsService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	@Autowired
	public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    	User user = this.userRepository.getUserById(username);
        if (user == null) {
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
        }
        
    	UserSpringSecurity userSecurity = new UserSpringSecurity(user);
        return userSecurity;
    }
    
    public int createUser(User user) {
    	user.setPassword(passwordEncoder.encode(user.getPassword()));
    	System.out.println("============pw: " + user.getPassword());
		return this.userRepository.createUser(user);
	}
}
