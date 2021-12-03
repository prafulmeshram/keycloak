/**
 * 
 */
package com.praful.resource.server.security;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;

/**
 * @author jack
 *
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true, jsr250Enabled = true)
public class WebSecurity extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
		jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new KeycloakRoleConverter());
		/*
		 * http.authorizeRequests((requests) ->
		 * requests.anyRequest().authenticated().antMatchers(HttpMethod.GET, "/users")
		 * .hasAuthority("SCOPE_profile")).oauth2ResourceServer().jwt();
		 */

		http.authorizeRequests((requests) -> requests.antMatchers(HttpMethod.GET, "/users").hasRole("ADMIN")
				.anyRequest().authenticated()).oauth2ResourceServer().jwt()
				.jwtAuthenticationConverter(jwtAuthenticationConverter);
	}

}
