package com.userlogin.Services;

import java.util.ArrayList;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService 
{
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException 
	{
		if (username.equals("Siddhesh"))
		{
			return new User("Siddhesh", "Siddhesh@456", new ArrayList<>());
		}
		else if(username.equals("siddheshgupta"))
		{
			return new User("siddheshgupta", "Siddhesh@786", new ArrayList<>());
		}
		else
		{
			throw new UsernameNotFoundException("User Not Found !!");
		}
	}
	
}
