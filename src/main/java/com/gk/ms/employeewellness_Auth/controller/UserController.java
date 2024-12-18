package com.gk.ms.employeewellness_Auth.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.gk.ms.employeewellness_Auth.service.JwtService;
import com.gk.ms.employeewellness_Auth.entity.User;
import com.gk.ms.employeewellness_Auth.service.*;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.SignatureException;
import jakarta.ws.rs.core.HttpHeaders;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/api/auth")
@RequiredArgsConstructor
@CrossOrigin("*")
public class UserController {

	private final UserServiceImpl userService;
	
	@Autowired
	JwtService jwtService;

	@PostMapping("/validate")
	public ResponseEntity<Void> validateToken(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
		try {
			String token = authorizationHeader.substring(7);
			Claims claims = Jwts.parser()
	                .verifyWith(jwtService.getKey())
	                .build()
	                .parseSignedClaims(token)
	                .getPayload();
			return ResponseEntity.ok().build();
		} catch (SignatureException e) {
			return ResponseEntity.status(401).build(); // Invalid token
		}
	}

	@PostMapping("/register")
	public User register(@RequestBody User user) {
		return userService.register(user);
	}

	@PostMapping("/login")
	public String login(@RequestParam("username") String username,@RequestParam("password") String password) {
		return userService.verifyAndGenerateToken(username,password);
	}

	@GetMapping("/users")
	public ResponseEntity<List<User>> getAllUsers() {
		try {
			List<User> userList = userService.getAllUsers();
			return new ResponseEntity<>(userList, HttpStatus.OK);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, e.getMessage());
		}
	}

	@GetMapping("/users/{id}")
	public ResponseEntity<User> getUserById(@PathVariable("id") Integer id) {
		try {
			User user = userService.getUserById(id);
			return new ResponseEntity<>(user, HttpStatus.OK);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, e.getMessage());
		}
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/ping")
	public ResponseEntity<String> test() {
		try {
			return new ResponseEntity<>("Welcome", HttpStatus.OK);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, e.getMessage());
		}
	}
}
