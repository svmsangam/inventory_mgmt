package com.inventory.core.api.impl;

import com.inventory.core.api.iapi.IAccountInfoApi;
import com.inventory.core.api.iapi.IClientInfoApi;
import com.inventory.core.model.converter.ClientInfoConverter;
import com.inventory.core.model.dto.ClientInfoDTO;
import com.inventory.core.model.entity.ClientInfo;
import com.inventory.core.model.enumconstant.AccountAssociateType;
import com.inventory.core.model.enumconstant.ClientType;
import com.inventory.core.model.enumconstant.Status;
import com.inventory.core.model.repository.ClientInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class ClientInfoApi implements IClientInfoApi {

    @Autowired
    private ClientInfoRepository clientInfoRepository;

    @Autowired
    private ClientInfoConverter clientInfoConverter;

    @Autowired
    private IAccountInfoApi accountInfoApi;

    @Override
    public ClientInfoDTO save(ClientInfoDTO clientInfoDTO) {

        ClientInfo clientInfo = clientInfoConverter.convertToEntity(clientInfoDTO);

        clientInfo.setStatus(Status.ACTIVE);

        clientInfo = clientInfoRepository.save(clientInfo);

        AccountAssociateType accountAssociateType ;

        if (clientInfo.getClientType().equals(ClientType.CUSTOMER)){
            accountAssociateType = AccountAssociateType.CUSTOMER;
        }else {
            accountAssociateType = AccountAssociateType.VENDOR;
        }

        accountInfoApi.save(clientInfo.getId() , accountAssociateType , "CUS-");

        return clientInfoConverter.convertToDto(clientInfo);
    }

    @Override
    public ClientInfoDTO update(ClientInfoDTO clientInfoDTO) {

        ClientInfo clientInfo = clientInfoRepository.findById(clientInfoDTO.getClientId()).orElseThrow();

        clientInfo = clientInfoConverter.copyConvertToEntity(clientInfoDTO , clientInfo);

        return clientInfoConverter.convertToDto(clientInfoRepository.save(clientInfo));
    }

    @Override
    public ClientInfoDTO show(Status status, long clientId , long storeId) {
        return clientInfoConverter.convertToDto(clientInfoRepository.findByIdAndStatusAndStoreInfo_Id(clientId , status , storeId));
    }

    @Override
    public long countByStatusAndClientType(Status status, ClientType clientType , long storeId) {
        return clientInfoRepository.countAllByStatusAndAndClientTypeAndStoreInfo_Id(status , clientType , storeId);
    }

    private Pageable createPageRequest(int page , int size , String properties , Sort.Direction direction) {

        return PageRequest.of(page, size, Sort.by(direction, properties));
    }

    @Override
    public List<ClientInfoDTO> list(Status status, ClientType clientType, int page, int size , long storeId) {

        Pageable pageable = createPageRequest(page,size ,"name" , Sort.Direction.ASC);

        return clientInfoConverter.convertToDtoList(clientInfoRepository.findAllByStatusAndClientTypeAndStoreInfo_Id(status , clientType , storeId, pageable));
    }

    @Override
    public List<ClientInfoDTO> search(Status status, ClientType clientType, String q, int page, int size , long storeId) {

        Pageable pageable = createPageRequest(page, size ,"name" , Sort.Direction.ASC);

        return clientInfoConverter.convertToDtoList(clientInfoRepository.findAllByStatusAndClientTypeAndNameContainsOrCompanyNameContainsOrContactOrMobileNumberContainsAndStoreInfo(status , clientType , q ,storeId, pageable));

    }

    @Override
    public long searchCount(Status status, ClientType clientType, String q , long storeId) {

        Long count = clientInfoRepository.countAllByStatusAndClientTypeAndNameContainsOrCompanyNameContainsOrContactOrMobileNumberContainsAndStoreInfo(status , clientType , q ,storeId);

        if (count == null){
            return 0;
        }

        return count;
    }

    @Override
    public List<ClientInfoDTO> search(Status status, String q, int page, int size , long storeId) {

        Pageable pageable = createPageRequest(page, size ,"name" , Sort.Direction.ASC);

        return clientInfoConverter.convertToDtoList(clientInfoRepository.findAllByStatusAndNameContainsOrCompanyNameContainsOrContactOrMobileNumberContainsAndStoreInfo(status , q , storeId , pageable));

    }
}
