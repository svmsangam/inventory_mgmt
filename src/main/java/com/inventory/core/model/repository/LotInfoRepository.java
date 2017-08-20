package com.inventory.core.model.repository;

import com.inventory.core.model.entity.LotInfo;
import com.inventory.core.model.enumconstant.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.LockModeType;
import java.util.List;

/**
 * Created by dhiraj on 8/11/17.
 */
@Repository
@Transactional(readOnly = true)
public interface LotInfoRepository extends JpaRepository<LotInfo , Long> , JpaSpecificationExecutor<LotInfo>{

    @Lock(LockModeType.OPTIMISTIC)
    LotInfo findById(long lotInfoId);

    @Lock(LockModeType.OPTIMISTIC)
    LotInfo findByIdAndStatus(long lotInfoId , Status status);

    List<LotInfo> findAllByStatus(Status status);
}
