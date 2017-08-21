package com.inventory.core.api.impl;

import com.inventory.core.api.iapi.IClientInfoApi;
import com.inventory.core.model.dto.ClientInfoDTO;
import com.inventory.core.model.enumconstant.Status;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ClientInfoApi implements IClientInfoApi {


    @Override
    public ClientInfoDTO save(ClientInfoDTO clientInfoDTO) {
        return null;
    }

    @Override
    public ClientInfoDTO update(ClientInfoDTO clientInfoDTO) {
        return null;
    }

    @Override
    public void delete(long clientId) {

    }

    @Override
    public ClientInfoDTO show(long clientId, long storeId, Status status) {
        return null;
    }

    @Override
    public List<ClientInfoDTO> list(Status status, long storeId) {
        return null;
    }

    @Override
    public ClientInfoDTO getClientByNameAndStoreAndStatus(String clientName, long storeId, Status status) {
        return null;
    }

    @Override
    public long ClientCount(Status status, long storeId) {
        return 0;
    }
}
