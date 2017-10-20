package com.inventory.core.model.repository;

import com.inventory.core.model.entity.LedgerInfo;
import com.inventory.core.model.enumconstant.AccountEntryType;
import com.inventory.core.model.enumconstant.Status;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by dhiraj on 9/16/17.
 */
@Repository
public interface LedgerInfoRepository extends JpaRepository<LedgerInfo , Long> , JpaSpecificationExecutor<LedgerInfo>{

    @Query("select count (l) from LedgerInfo l where l.status = ?1 and l.storeInfo.id = ?2")
    long countAllByStatusAndStoreInfo(Status status , long storeId);

    @Query("select l from LedgerInfo l where l.status = ?1 and l.storeInfo.id = ?2")
    List<LedgerInfo> findAllByStatusAndStoreInfo(Status status , long storeId , Pageable pageable);

    @Query("select l from LedgerInfo l where l.status = ?1 and l.storeInfo.id = ?2 and l.accountInfo.id = ?3 and l.created between ?4 and ?5")
    List<LedgerInfo> filter(Status status , long storeId , long accountId , Date from , Date to, Pageable pageable);

    @Query("select count (l) from LedgerInfo l where l.status = ?1 and l.storeInfo.id = ?2 and l.accountInfo.id = ?3 and l.created between ?4 and ?5")
    Long filterCount(Status status , long storeId , long accountId , Date from , Date to);

    @Query("select sum (l.amount) from LedgerInfo l where l.status = ?1 and l.storeInfo.id = ?2 and l.accountInfo.id = ?3 and l.created between ?4 and ?5 and l.accountEntryType = ?6")
    Double filterTotalAmount(Status status , long storeId , long accountId , Date from , Date to , AccountEntryType accountEntryType);

    @Query("select sum (l.amount) from LedgerInfo l where l.status = ?1 and l.storeInfo.id = ?2 and l.accountInfo.id = ?3 and l.accountEntryType = ?4")
    Double findTotalAmountByStatusAndStoreInfoIdAndAccountInfoAndAccountEntryType(Status status , long storeId , long accountId, AccountEntryType accountEntryType);
}
