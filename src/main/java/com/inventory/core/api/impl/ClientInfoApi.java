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

        ClientInfo clientInfo = clientInfoRepository.findById(clientInfoDTO.getClientId());

        clientInfo = clientInfoConverter.copyConvertToEntity(clientInfoDTO , clientInfo);

        return clientInfoConverter.convertToDto(clientInfoRepository.save(clientInfo));
    }

    @Override
    public ClientInfoDTO show(Status status, long clientId) {
        return clientInfoConverter.convertToDto(clientInfoRepository.findByIdAndStatus(clientId , status));
    }

    @Override
    public long countByStatusAndClientType(Status status, ClientType clientType) {
        return clientInfoRepository.countAllByStatusAndAndClientType(status , clientType);
    }

    private Pageable createPageRequest(int page , int size , String properties , Sort.Direction direction) {

        return new PageRequest(page, size, new Sort(direction, properties));
    }

    @Override
    public List<ClientInfoDTO> list(Status status, ClientType clientType, int page, int size) {

        Pageable pageable = createPageRequest(page,size ,"name" , Sort.Direction.ASC);

        return clientInfoConverter.convertToDtoList(clientInfoRepository.findAllByStatusAndClientType(status , clientType , pageable));
    }

    @Override
    public List<ClientInfoDTO> search(Status status, ClientType clientType, String q, int page, int size) {

        Pageable pageable = createPageRequest(page, size ,"name" , Sort.Direction.ASC);

        return clientInfoConverter.convertToDtoList(clientInfoRepository.findAllByStatusAndClientTypeAndNameContainsOrCompanyNameContainsOrContactOrMobileNumberContains(status , clientType , q , pageable));

    }

    @Override
    public long searchCount(Status status, ClientType clientType, String q) {
        return 0;
    }

    @Override
    public List<ClientInfoDTO> search(Status status, String q, int page, int size) {

        Pageable pageable = createPageRequest(page, size ,"name" , Sort.Direction.ASC);

        return clientInfoConverter.convertToDtoList(clientInfoRepository.findAllByStatusAndNameContainsOrCompanyNameContainsOrContactOrMobileNumberContains(status , q , pageable));

    }
}
