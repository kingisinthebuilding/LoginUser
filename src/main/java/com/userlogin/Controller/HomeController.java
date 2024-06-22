package com.userlogin.Controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
public class HomeController {
	@GetMapping("/welcome")
	public String welcome() {
		String text = "This is private Page ";
		text += "This Page is Not Allowed to Unauthenticated Users";
		return text;
	}

	@GetMapping("/getusers")
	@CrossOrigin(origins = "http://localhost:4200")
	public String getUser() 
	{
		return "{\"username\":\"Siddhesh\":\"siddheshgupta\"}";
	}
}
