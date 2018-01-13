package com.inventory.core.api.impl;

import com.inventory.core.api.iapi.IOAuthClientApi;
import com.inventory.core.model.entity.OAuthClient;
import com.inventory.core.model.repository.OAuthClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class OAuthClientApi implements IOAuthClientApi {

	private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

	@Autowired
	private OAuthClientRepository oauthClientRepository;

	@Override
	public OAuthClient registerClient(String web_server_redirect_uri) {

		OAuthClient oauthClient = this.getClientDetails();
		oauthClient.setWeb_server_redirect_uri(web_server_redirect_uri);
		return oauthClientRepository.save(oauthClient);
	}

	public OAuthClient getClientDetails() {

		OAuthClient oauthClient = new OAuthClient();
		oauthClient.setResource_ids("test");
		oauthClient.setAccess_token_validity("3600");
		oauthClient.setRefresh_token_validity("0");
		oauthClient.setAdditional_information("No additional info");
		oauthClient.setAuthorities("ROLE_CLIENT,ROLE_TRUSTED_CLIENT");
		oauthClient.setAuthorized_grant_types("client_credentials,password,authorization_code,refresh_token");
		oauthClient.setClient_id(getClientId(10));
		oauthClient.setClient_secret(getClientSecret());
		oauthClient.setAutoapprove(true);
		oauthClient.setScope("read,write,trust");
		return oauthClient;
	}

	public String getClientSecret() {
		Random random = new Random();
		int id = random.nextInt(555555 - 444444 + 1) + 555555 - 444444;
		return Integer.toString(id);
	}

	public static String getClientId(int count) {
		StringBuilder builder = new StringBuilder();
		while (count-- != 0) {
			int character = (int) (Math.random() * ALPHA_NUMERIC_STRING.length());
			builder.append(ALPHA_NUMERIC_STRING.charAt(character));
		}
		return builder.toString();
	}

}
