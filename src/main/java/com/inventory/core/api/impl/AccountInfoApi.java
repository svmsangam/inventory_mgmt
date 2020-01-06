package com.inventory.core.api.impl;

import com.inventory.core.api.iapi.IAccountInfoApi;
import com.inventory.core.model.converter.AccountInfoConverter;
import com.inventory.core.model.dto.AccountInfoDTO;
import com.inventory.core.model.entity.AccountInfo;
import com.inventory.core.model.enumconstant.AccountAssociateType;
import com.inventory.core.model.enumconstant.Status;
import com.inventory.core.model.repository.AccountInfoRepository;
import com.inventory.web.util.LoggerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
public class AccountInfoApi implements IAccountInfoApi {

    @Autowired
    private AccountInfoConverter accountInfoConverter;

    @Autowired
    private AccountInfoRepository accountInfoRepository;

    @Override
    public AccountInfoDTO save(long associateId, AccountAssociateType associateType, String prefix) {

        AccountInfo accountInfo = new AccountInfo();

        accountInfo.setAssociateType(associateType);
        accountInfo.setAssociateId(associateId);

        String accountNo = prefix + System.currentTimeMillis();

        while (accountInfoRepository.findByAcountNumber(accountNo) != null) {
            accountNo = prefix + System.currentTimeMillis();
        }

        accountInfo.setAcountNumber(accountNo);

        return accountInfoConverter.convertToDto(accountInfoRepository.save(accountInfo));
    }

    @Override
    public AccountInfoDTO update(AccountInfoDTO accountInfoDTO) {

        AccountInfo accountInfo = accountInfoRepository.findById(accountInfoDTO.getAccountId());
        accountInfo = accountInfoConverter.copyConvertToEntity(accountInfoDTO, accountInfo);

        return accountInfoConverter.convertToDto(accountInfoRepository.save(accountInfo));
    }

    @Override
    public void delete(long accountId) {

        AccountInfo accountInfo = accountInfoRepository.findById(accountId);

        accountInfoRepository.save(accountInfo);
    }

    @Override
    public void addDebitAmount(long associateId, AccountAssociateType associateType, BigDecimal debitAmount) {

        try {

            AccountInfo accountInfo = accountInfoRepository.findByAssociateIdAndAssociateType(associateId,  associateType);

            BigDecimal prevDebitAmount = accountInfo.getDebitAmount() == null ? BigDecimal.valueOf(0) : accountInfo.getDebitAmount();

            accountInfo.setDebitAmount(prevDebitAmount.add(debitAmount));

            accountInfoRepository.save(accountInfo);
        }catch (Exception e){
            LoggerUtil.logException(this.getClass(),  e);
            throw e;
        }

    }

    @Override
    public void addCreditAmount(long associateId, AccountAssociateType associateType, BigDecimal creditAmount) {

        try {

            AccountInfo accountInfo = accountInfoRepository.findByAssociateIdAndAssociateType(associateId,  associateType);

            BigDecimal prevCreditAmount = accountInfo.getCreditAmount() == null ? BigDecimal.valueOf(0) : accountInfo.getCreditAmount();

            accountInfo.setDebitAmount(prevCreditAmount.add(creditAmount));

            accountInfoRepository.save(accountInfo);
        }catch (Exception e){
            LoggerUtil.logException(this.getClass(),  e);
            throw e;
        }



    }

    @Override
    public AccountInfoDTO show(long accountId, long storeId, Status status) {
        return null;
    }

    @Override
    public AccountInfoDTO getByAssociateIdAndAccountAssociateType(long associateId, AccountAssociateType associateType) {
        return accountInfoConverter.convertToDto(accountInfoRepository.findByAssociateIdAndAssociateType(associateId , associateType));
    }

    @Override
    public List<AccountInfoDTO> list(Status status, long storeId) {
        return null;
    }

    @Override
    public BigDecimal totalCreditAmountOfStore(AccountAssociateType associateType, long storeId){
        BigDecimal totalCreditAmount = accountInfoRepository.findTotalCrAmountByAssociateIdAndAssociateType(associateType , storeId , Status.ACTIVE);
        if (totalCreditAmount == null){
            totalCreditAmount = BigDecimal.valueOf(0);
        }
        return totalCreditAmount;
    }
}
