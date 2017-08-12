package com.inventory.core.api.iapi;

import com.inventory.core.model.dto.ClientInfoDTO;
import com.inventory.core.model.enumconstant.Status;

import java.util.List;

public interface IClientInfoApi {

    ClientInfoDTO save(ClientInfoDTO clientInfoDTO);

    ClientInfoDTO update(ClientInfoDTO clientInfoDTO);

    void delete(long clientId);

    ClientInfoDTO show(long clientId, long storeId, Status status);

    List<ClientInfoDTO> list(Status status, long storeId);

    ClientInfoDTO getClientByNameAndStoreAndStatus(String clientName, long storeId, Status status);

    long ClientCount(Status status, long storeId);
}
