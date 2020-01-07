package com.inventory.core.model.repository;

import com.inventory.core.model.entity.AccountInfo;
import com.inventory.core.model.enumconstant.AccountAssociateType;
import com.inventory.core.model.enumconstant.Status;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by dhiraj on 8/12/17.
 */
@Repository
public interface AccountInfoRepository extends CrudRepository<AccountInfo, Long>, JpaSpecificationExecutor<AccountInfo> {

    AccountInfo findById(long accountInfoId);

    AccountInfo findByAcountNumber(String accountNumber);

    AccountInfo findByAssociateIdAndAssociateType(long associateId, AccountAssociateType associateType);

    @Query("select sum (nullif(a.creditAmount, 0) ) from AccountInfo as a where a.associateType =?1 and a.associateId in (select c.id from ClientInfo as c where c.storeInfo.id =?2 and c.status = ?3)")
    BigDecimal findTotalCrAmountByAssociateIdAndAssociateType(AccountAssociateType associateType , long storeId , Status status);

    List<AccountInfo> findAllByAssociateType(AccountAssociateType accountAssociateType);

}
