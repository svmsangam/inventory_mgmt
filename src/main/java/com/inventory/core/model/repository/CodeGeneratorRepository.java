package com.inventory.core.model.repository;

import com.inventory.core.model.entity.CodeGenerator;
import com.inventory.core.model.entity.StoreInfo;
import com.inventory.core.model.enumconstant.NumberStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by dhiraj on 8/27/17.
 */
@Transactional(readOnly = true)
@Repository
public interface CodeGeneratorRepository extends JpaRepository<CodeGenerator , Long> , JpaSpecificationExecutor<CodeGenerator>{

    @Query("select count (n) from CodeGenerator n where n.storeInfo.id=?1 and n.numberStatus=?2")
    Long findByStoreAndNumberStatus(long storeId , NumberStatus numberStatus);

    @Query("select max(n.id) from CodeGenerator n where n.storeInfo.id=?1 and n.numberStatus=?2")
    Long findLastRowByStoreAndNumberStatus(long storeId , NumberStatus numberStatus);

    CodeGenerator findFirstByStoreInfoAndNumberStatusOrderByIdDesc(StoreInfo storeInfo, NumberStatus numberStatus);
}
