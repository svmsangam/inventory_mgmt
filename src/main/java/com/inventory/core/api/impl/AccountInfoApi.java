package com.inventory.core.api.impl;

import com.inventory.core.api.iapi.IAccountInfoApi;
import com.inventory.core.model.converter.AccountInfoConverter;
import com.inventory.core.model.dto.AccountInfoDTO;
import com.inventory.core.model.entity.AccountInfo;
import com.inventory.core.model.enumconstant.Status;
import com.inventory.core.model.repository.AccountInfoRepository;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

@Service
@Transactional
public class AccountInfoApi implements IAccountInfoApi{

    @Autowired
    private AccountInfoConverter accountInfoConverter;

    @Autowired
    private AccountInfoRepository accountInfoRepository;

    @Override
    public AccountInfoDTO save(AccountInfoDTO accountInfoDTO) throws IOException, JSONException {

        AccountInfo accountInfo = accountInfoConverter.convertToEntity(accountInfoDTO);

        return accountInfoConverter.convertToDto(accountInfoRepository.save(accountInfo));
    }

    @Override
    public AccountInfoDTO update(AccountInfoDTO accountInfoDTO) {

        AccountInfo accountInfo = accountInfoRepository.findById(accountInfoDTO.getAccountId());
        accountInfo = accountInfoConverter.copyConvertToEntity(accountInfoDTO,accountInfo);

        return accountInfoConverter.convertToDto(accountInfoRepository.save(accountInfo));
    }

    @Override
    public void delete(long accountId) {

        AccountInfo accountInfo = accountInfoRepository.findById(accountId);

        accountInfoRepository.save(accountInfo);
    }

    @Override
    public AccountInfoDTO show(long accountId, long storeId, Status status) {
        return null;
    }

    @Override
    public List<AccountInfoDTO> list(Status status, long storeId) {
        return null;
    }
}
