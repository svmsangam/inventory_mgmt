package com.inventory.core.model.repository;

import com.inventory.core.model.entity.AccountInfo;
import com.inventory.core.model.enumconstant.AccountAssociateType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import java.util.List;

/**
 * Created by dhiraj on 8/12/17.
 */
@Repository
public interface AccountInfoRepository extends JpaRepository<AccountInfo , Long> , JpaSpecificationExecutor<AccountInfo>{

    @Lock(LockModeType.OPTIMISTIC)
    AccountInfo findById(long accountInfoId);

    @Lock(LockModeType.OPTIMISTIC)
    AccountInfo findByAcountNumber(String accountNumber);

    @Lock(LockModeType.OPTIMISTIC)
    AccountInfo findByAssociateIdAndAssociateType(long associateId , AccountAssociateType associateType);

    List<AccountInfo> findAllByAssociateType(AccountAssociateType accountAssociateType);

}
