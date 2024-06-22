package com.userlogin.Controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.userlogin.Helper.JwtUtil;
import com.userlogin.Model.JwtRequest;
import com.userlogin.Model.JwtResponse;
import com.userlogin.Services.CustomUserDetailsService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@CrossOrigin(origins = "*")
public class JwtController {

	private static final Logger logger = LoggerFactory.getLogger(JwtController.class);

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private CustomUserDetailsService customUserDetailsService;

	@Autowired
	private JwtUtil jwtUtil;

	@PostMapping("/token")
	public ResponseEntity<?> generateToken(@RequestBody @Valid JwtRequest jwtRequest, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResponseEntity.badRequest().body("Invalid request payload");
		}

		ValidationStatus validationStatus = validateCredentials(jwtRequest);
		if (validationStatus != ValidationStatus.VALID) {
			String errorMessage = getErrorMessage(validationStatus);
			logger.error(errorMessage);
			return ResponseEntity.badRequest().body(errorMessage);
		}

		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(), jwtRequest.getPassword()));
		} catch (UsernameNotFoundException | BadCredentialsException e) {
			logger.error("Invalid Username and Password", e);
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Username and Password");
		}

		UserDetails userDetails = customUserDetailsService.loadUserByUsername(jwtRequest.getUsername());
		String token = jwtUtil.generateToken(userDetails);

		logger.info("Login Successfully Done");
		return ResponseEntity.ok(new JwtResponse(token));
	}

	private enum ValidationStatus {
		BOTH_NULL,
		BOTH_EMPTY,
		PASSWORD_NULL_OR_EMPTY,
		USERNAME_NULL_OR_EMPTY,
		VALID
	}

	private ValidationStatus validateCredentials(JwtRequest jwtRequest) {
		String username = jwtRequest.getUsername();
		String password = jwtRequest.getPassword();

		if (username == null && password == null) {
			return ValidationStatus.BOTH_NULL;
		} else if (username != null && username.isEmpty() && (password != null && password.isEmpty())) {
			return ValidationStatus.BOTH_EMPTY;
		} else if (username == null || username.isEmpty()) {
			return ValidationStatus.USERNAME_NULL_OR_EMPTY;
		} else if (password == null || password.isEmpty()) {
			return ValidationStatus.PASSWORD_NULL_OR_EMPTY;
		} else {
			return ValidationStatus.VALID;
		}
	}

	private String getErrorMessage(ValidationStatus validationStatus) {
		switch (validationStatus) {
			case BOTH_NULL:
				return "Username and Password Required";
			case BOTH_EMPTY:
				return "Username and Password are Required";
			case PASSWORD_NULL_OR_EMPTY:
				return "Password is Required";
			case USERNAME_NULL_OR_EMPTY:
				return "Username is Required";
			default:
				return "Invalid request";
		}
	}
}
