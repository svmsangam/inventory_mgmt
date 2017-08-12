package com.inventory.core.api.iapi;

import com.inventory.core.model.dto.AccountInfoDTO;
import com.inventory.core.model.enumconstant.Status;
import org.json.JSONException;

import java.io.IOException;
import java.util.List;

public interface IAccountInfoApi {

    AccountInfoDTO save(AccountInfoDTO accountInfoDTO) throws IOException, JSONException;

    AccountInfoDTO update(AccountInfoDTO accountInfoDTO);

    void delete(long accountId);

    AccountInfoDTO show(long accountId, long storeId, Status status);

    List<AccountInfoDTO> list(Status status, long storeId);

}
