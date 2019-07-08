package com.inventory.core.api.iapi;

import com.inventory.core.model.dto.AccountInfoDTO;
import com.inventory.core.model.enumconstant.AccountAssociateType;
import com.inventory.core.model.enumconstant.Status;

import java.util.List;

public interface IAccountInfoApi {

    AccountInfoDTO save(long associateId, AccountAssociateType associateType, String prefix);

    AccountInfoDTO update(AccountInfoDTO accountInfoDTO);

    void delete(long accountId);

    AccountInfoDTO show(long accountId, long storeId, Status status);

    AccountInfoDTO getByAssociateIdAndAccountAssociateType(long associateId , AccountAssociateType associateType);

    List<AccountInfoDTO> list(Status status, long storeId);
}
