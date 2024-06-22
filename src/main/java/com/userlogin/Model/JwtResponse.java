package com.userlogin.Model;

public class JwtResponse 
{
	String token;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public JwtResponse(String token) {
		super();
		this.token = token;
	}

	public JwtResponse() {
		
	}

	@Override
	public String toString() {
		return "JwtResponse [token=" + token + "]";
	}
	
	
}
