/**
 * 
 */
package com.praful.kecloak.factory;

import org.keycloak.component.ComponentModel;
import org.keycloak.credential.CredentialInput;
import org.keycloak.credential.CredentialInputValidator;
import org.keycloak.credential.UserCredentialStore;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserCredentialModel;
import org.keycloak.models.UserModel;
import org.keycloak.models.credential.PasswordCredentialModel;
import org.keycloak.storage.UserStorageProvider;
import org.keycloak.storage.adapter.AbstractUserAdapter;
import org.keycloak.storage.user.UserLookupProvider;

import com.praful.kecloak.payload.response.User;
import com.praful.kecloak.payload.response.VerifyPasswordResponse;
import com.praful.kecloak.services.UsersApiService;

/**
 * @author jack
 *
 */

public class RemoteUserStorageProvider implements UserStorageProvider, UserLookupProvider, CredentialInputValidator {

	private KeycloakSession session;
	private ComponentModel model;
	private UsersApiService usersApiService;

	/**
	 * @param session
	 * @param model
	 * @param usersApiService
	 */
	public RemoteUserStorageProvider(KeycloakSession session, ComponentModel model, UsersApiService usersApiService) {
		this.session = session;
		this.model = model;
		this.usersApiService = usersApiService;
	}

	@Override
	public void close() {

	}

	@Override
	public boolean supportsCredentialType(String credentialType) {

		return PasswordCredentialModel.TYPE.equals(credentialType);
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean isConfiguredFor(RealmModel realm, UserModel user, String credentialType) {

		if (!supportsCredentialType(credentialType))
			return false;

		return !getCredentialStore().getStoredCredentialsByType(realm, user, credentialType).isEmpty();
	}

	@Override
	public boolean isValid(RealmModel realm, UserModel user, CredentialInput credentialInput) {
		VerifyPasswordResponse verifyPasswordResponse = usersApiService.verifyUserPassword(user.getUsername(),
				credentialInput.getChallengeResponse());
		if (verifyPasswordResponse == null)
			return false;
		else
			return verifyPasswordResponse.isResult();
	}

	@Override
	public UserModel getUserById(String id, RealmModel realm) {

		return null;
	}

	@Override
	public UserModel getUserByUsername(String username, RealmModel realm) {
		UserModel returnValue = null;
		User user = this.usersApiService.getUserDetails(username);
		if (user != null)
			returnValue = this.createUserModel(username, realm);

		return returnValue;
	}

	@Override
	public UserModel getUserByEmail(String email, RealmModel realm) {

		return null;
	}

	private UserModel createUserModel(String username, RealmModel realm) {

		return new AbstractUserAdapter(session, realm, model) {
			@Override
			public String getUsername() {
				return username;
			}
		};
	}

	private UserCredentialStore getCredentialStore() {
		return session.userCredentialManager();
	}

}
