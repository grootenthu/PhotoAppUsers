package com.photoapp.users.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {
	
	private Environment environment;
	private final String GATEWAY_IP = "gateway.ip";
	
	public WebSecurity(Environment environment) {
		this.environment = environment;
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.authorizeRequests().antMatchers("/**").hasIpAddress(environment.getProperty(GATEWAY_IP));
		http.csrf().disable();
		http.headers().frameOptions().disable();
	}

}
