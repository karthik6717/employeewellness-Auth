package com.gk.ms.employeewellness_Auth.service;

import java.util.List;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.gk.ms.employeewellness_Auth.dao.*;
import com.gk.ms.employeewellness_Auth.entity.*;
import com.gk.ms.employeewellness_Auth.model.*;


import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl {
	
	
	private final UserDao userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(12);

    public User register(User user) {
    	user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public String verifyAndGenerateToken(String username, String password) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(username,password));

        if (authentication.isAuthenticated()) {
        	User user = userRepository.findByUsername(username);
            return jwtService.generateToken(username, user.getRole(),user.getEmployeeId());
        }

        return "fail";
    }
    
    
    public List<User> getAllUsers(){
    	return userRepository.findAll();
    }
    
    public User getUserById(Integer id) throws Exception{
    	return userRepository.findById(id)
				.orElseThrow(() -> new Exception("Invalid Id"));
    }
}
