package com.userlogin.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.userlogin.Services.CustomUserDetailsService;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
public class MySecurityConfig extends WebSecurityConfigurerAdapter
{
	@Autowired
	private JwtAuthenticationEntryPoint entryPoint;
	
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
	@Autowired
	private JwtAuthenticationFilter jwtFilter;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.csrf().disable()
				.cors().and()
				.authorizeRequests()
				.antMatchers("/token").permitAll()
				.antMatchers("/h2-console/**").hasRole("ADMIN")
				.antMatchers("/product/upload").permitAll()
				.antMatchers("/product/**").permitAll()  // Temporarily allow public access for testing
				.anyRequest().authenticated()
				.and()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
				.exceptionHandling().authenticationEntryPoint(entryPoint);

		http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
	}


	@Override
    public void configure(WebSecurity web) throws Exception 
	{
        web.ignoring().antMatchers("/h2-console/**");
		web.ignoring().antMatchers("/http://localhost:8080/product/**");
    }
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth)throws Exception
	{
//		auth.userDetailsService(customUserDetailsService);
		auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
	}
	
	@Bean
	public PasswordEncoder passwordEncoder()
	{
		return NoOpPasswordEncoder.getInstance();
	}
	
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception
	{
		return super.authenticationManagerBean();
	}
}
