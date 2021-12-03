/**
 * 
 */
package com.praful.resource.server.controller;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.praful.resource.server.payload.response.UserRest;

/**
 * @author jack
 *
 */

@RestController
@RequestMapping("/users")
public class UsersController {

	@GetMapping("/status/check")
	public String status() {
		return "Working";
	}

	// @PreAuthorize("hasRole('developer') or #id==#jwt.subject" ) Or
	// @PreAuthorize("hasAuthority('ROLE_DEVELOPER')")
	// @Secured("ROLE_DEVELOPER")
	@DeleteMapping("/{id}")
	public String deleteUser(@PathVariable int id, @AuthenticationPrincipal Jwt jwt) {
		return "User Deleted = " + id + " And Jwt Subject =  " + jwt.getSubject();
	}
	
	@PostAuthorize("returnObject.userId == #jwt.subject")
    @GetMapping(path = "/{id}")
    public UserRest getUser(@PathVariable String id, @AuthenticationPrincipal Jwt jwt) {
        return new UserRest("Sergey", "Kargopolov","5f3fb480-f86c-4514-8d23-ca88d66c6253");
    }

}
