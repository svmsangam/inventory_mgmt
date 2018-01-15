package com.inventory.core.api.iapi;

import com.inventory.core.model.dto.OauthRegisterResponseDTO;

public interface IOAuthClientApi {
	OauthRegisterResponseDTO registerClient(String web_server_redirect_uri);
}
