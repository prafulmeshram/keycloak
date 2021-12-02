/**
 * 
 */
package com.praful.kecloak.services;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.praful.kecloak.payload.response.User;
import com.praful.kecloak.payload.response.VerifyPasswordResponse;

/**
 * @author jack
 *
 */
@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
public interface UsersApiService {

	@GET
	@Path("/{username}")
	User getUserDetails(@PathParam("username") String username);
	
	@POST
	@Path("/{username}/verify-password")
	@Produces(MediaType.APPLICATION_JSON)
	VerifyPasswordResponse verifyUserPassword(@PathParam("username") String username, String password);
	
}
