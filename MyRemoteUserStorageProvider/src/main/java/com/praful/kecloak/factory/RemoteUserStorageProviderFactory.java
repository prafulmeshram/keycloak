/**
 * 
 */
package com.praful.kecloak.factory;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.keycloak.component.ComponentModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.storage.UserStorageProviderFactory;

import com.praful.kecloak.services.UsersApiService;

/**
 * @author jack
 *
 */
public class RemoteUserStorageProviderFactory implements UserStorageProviderFactory<RemoteUserStorageProvider> {

	public static final String PROVIDER_NAME = "my-remote-mysql-user-storage-provider";

	@Override
	public RemoteUserStorageProvider create(KeycloakSession session, ComponentModel model) {

		return new RemoteUserStorageProvider(session, model, this.buildHttpClient("http://localhost:8099/"));
	}

	@Override
	public String getId() {

		return PROVIDER_NAME;
	}

	private UsersApiService buildHttpClient(String baseUrl) {
		ResteasyClient client = new ResteasyClientBuilder().build();

		ResteasyWebTarget target = client.target(baseUrl);
		return target.proxyBuilder(UsersApiService.class).classloader(UsersApiService.class.getClassLoader()).build();
	}

}
