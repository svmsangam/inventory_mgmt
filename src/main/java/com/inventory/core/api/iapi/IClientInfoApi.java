package com.inventory.core.api.iapi;

import com.inventory.core.model.dto.ClientInfoDTO;
import com.inventory.core.model.enumconstant.ClientType;
import com.inventory.core.model.enumconstant.Status;

import java.util.List;

public interface IClientInfoApi {

    ClientInfoDTO save(ClientInfoDTO clientInfoDTO);

    ClientInfoDTO update(ClientInfoDTO clientInfoDTO);

    long countByStatusAndClientType(Status status , ClientType clientType);

    List<ClientInfoDTO> list(Status status , ClientType clientType , int page , int size);

    List<ClientInfoDTO> search(Status status , ClientType clientType , String q , int page , int size);
}
