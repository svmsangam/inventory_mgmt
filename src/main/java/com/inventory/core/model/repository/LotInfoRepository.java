package com.inventory.core.model.repository;

import com.inventory.core.model.entity.LotInfo;
import com.inventory.core.model.enumconstant.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by dhiraj on 8/11/17.
 */
@Repository
public interface LotInfoRepository extends JpaRepository<LotInfo, Long>, JpaSpecificationExecutor<LotInfo> {

    LotInfo findById(long lotInfoId);

    LotInfo findByIdAndStatus(long lotInfoId, Status status);

    List<LotInfo> findAllByStatus(Status status);
}
