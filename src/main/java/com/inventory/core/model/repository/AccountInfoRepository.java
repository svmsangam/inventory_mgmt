package com.inventory.core.model.repository;

import com.inventory.core.model.entity.AccountInfo;
import com.inventory.core.model.enumconstant.AccountAssociateType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by dhiraj on 8/12/17.
 */
@Repository
public interface AccountInfoRepository extends JpaRepository<AccountInfo , Long> , JpaSpecificationExecutor<AccountInfo>{

    AccountInfo findById(long accountInfoId);

    AccountInfo findByAcountNumber(String accountNumber);

    List<AccountInfo> findAllByAssociateType(AccountAssociateType accountAssociateType);

}
