package com.security.learn.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.security.learn.model.ApiResponse;
import com.security.learn.service.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
public class MySecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
	  @Override 
	  protected void configure(AuthenticationManagerBuilder auth) throws  Exception { 
		  // In memory authentication
		 /* auth.inMemoryAuthentication() 
		  	.withUser("ambadas")
		  	.password("{noop}password") // {noop} to skip PasswordEncoder
		  	.roles("OFFICE_ADMIN");
	  
		  auth.inMemoryAuthentication() 
		  	.withUser("anitha") 
		  	.password("{noop}password")
		  	.roles("STUDENT"); */
		  
		  // You can also configure DB to read user,password and role. 
		  auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
	  }
	 

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http			
			.csrf().disable()			
			.sessionManagement(cust -> cust.sessionCreationPolicy(SessionCreationPolicy.STATELESS))			
			.authorizeRequests()	
			.antMatchers("/h2-console/**").permitAll()
			.antMatchers(HttpMethod.GET ).permitAll()
			.antMatchers(HttpMethod.POST, "/student").hasAnyRole("STUDENT","OFFICE_ADMIN")
			.antMatchers(HttpMethod.POST, "/course").hasRole("OFFICE_ADMIN")			
			.anyRequest()
			.authenticated()				
			.and() // Will give http to continue with HttpSecurity methods
			.httpBasic()
			.authenticationEntryPoint(new AuthenticationEntryPoint() {  // inline implmentation
				
				@Override
				public void commence(HttpServletRequest request, HttpServletResponse response,
						AuthenticationException authException) throws IOException, ServletException {
					System.out.println(authException);
					ObjectMapper mapper = new ObjectMapper();
					ApiResponse apiResponse = new ApiResponse(
							HttpStatus.UNAUTHORIZED.value(), 
							"Authentication Failure-Invalid combination of user name and password");
					
					response.setStatus(HttpStatus.UNAUTHORIZED.value());
					response.addHeader("content-type", "application/json");
					response.getWriter().write(mapper.writeValueAsString(apiResponse));
					response.getWriter().flush();
					
				}
				
				
			})
			.and()
			.exceptionHandling().accessDeniedHandler(customAccessDeniedHandler()); // calling from the external methods
			
			
		
		/*
		 * http .exceptionHandling().accessDeniedHandler(customAccessDeniedHandler())
		 */
		
			
			
	}
	
	@Bean("customAccessDeniedHandler")
	public AccessDeniedHandler customAccessDeniedHandler() {
		return new AccessDeniedHandler() {

			@Override
			public void handle(HttpServletRequest request, HttpServletResponse response,
					AccessDeniedException accessDeniedException) throws IOException, ServletException {
				ObjectMapper mapper = new ObjectMapper();
				ApiResponse apiResponse = new ApiResponse(
						HttpStatus.FORBIDDEN.value(), 
						"Authroization Failure-The user is not allowed to access this resource");
				
				response.setStatus(HttpStatus.FORBIDDEN.value());
				response.addHeader("content-type", "application/json");
				response.getWriter().write(mapper.writeValueAsString(apiResponse));
				response.getWriter().flush();
				
			}
			
		};
	}
	
	
	
	  @Bean 
	  public BCryptPasswordEncoder passwordEncoder() { 
		  return new BCryptPasswordEncoder();
	  }
	 
	 
	
}
