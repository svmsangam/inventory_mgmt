package com.inventory.core.api.iapi;

import com.inventory.core.model.entity.OAuthClient;

public interface IOAuthClientApi {
	public OAuthClient registerClient(String web_server_redirect_uri);
}
