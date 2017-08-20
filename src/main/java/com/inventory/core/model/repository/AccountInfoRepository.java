package com.inventory.core.model.repository;

import com.inventory.core.model.entity.AccountInfo;
import com.inventory.core.model.enumconstant.AccountAssociateType;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by dhiraj on 8/12/17.
 */
@Repository
public interface AccountInfoRepository extends CrudRepository<AccountInfo , Long>, JpaSpecificationExecutor<AccountInfo>{

    AccountInfo findById(long accountInfoId);

    AccountInfo findByAcountNumber(String accountNumber);

    AccountInfo findByAssociateIdAndAssociateType(long associateId , AccountAssociateType associateType);

    List<AccountInfo> findAllByAssociateType(AccountAssociateType accountAssociateType);

}
