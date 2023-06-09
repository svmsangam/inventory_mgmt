package com.inventory.core.api.iapi;

import com.inventory.core.model.dto.ClientInfoDTO;
import com.inventory.core.model.enumconstant.ClientType;
import com.inventory.core.model.enumconstant.Status;

import java.util.List;

public interface IClientInfoApi {

    ClientInfoDTO save(ClientInfoDTO clientInfoDTO);

    ClientInfoDTO update(ClientInfoDTO clientInfoDTO);

    ClientInfoDTO show(Status status , long clientId , long storeId);

    long countByStatusAndClientType(Status status , ClientType clientType , long storeId);

    List<ClientInfoDTO> list(Status status , ClientType clientType , int page , int size , long storeId);

    List<ClientInfoDTO> search(Status status , ClientType clientType , String q , int page , int size , long storeId);

    long searchCount(Status status , ClientType clientType , String q , long storeId);

    List<ClientInfoDTO> search(Status status , String q , int page , int size , long storeId);
}
