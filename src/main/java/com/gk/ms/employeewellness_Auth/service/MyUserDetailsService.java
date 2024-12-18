package com.gk.ms.employeewellness_Auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.gk.ms.employeewellness_Auth.dao.UserDao;
import com.gk.ms.employeewellness_Auth.entity.User;
import com.gk.ms.employeewellness_Auth.model.UserPrincipal;

@Service
public class MyUserDetailsService implements UserDetailsService{

	@Autowired
	UserDao userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            System.out.println("user not found");
            throw new UsernameNotFoundException(username);
        }

        return new UserPrincipal(user);
    }
}
