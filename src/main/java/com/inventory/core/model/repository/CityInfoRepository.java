package com.inventory.core.model.repository;

import com.inventory.core.model.entity.CityInfo;
import com.inventory.core.model.entity.StateInfo;
import com.inventory.core.model.enumconstant.Status;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by dhiraj on 8/1/17.
 */
@Repository
@Transactional(readOnly = true)
public interface CityInfoRepository extends JpaRepository<CityInfo, Long> , JpaSpecificationExecutor<CityInfo> {

    CityInfo findByIdAndStatus(long cityId , Status status);

    CityInfo findByNameAndStatus(String cityName , Status status);

    CityInfo findByName(String name);

    List<CityInfo> findAllByStatusAndStateInfo(Status status , StateInfo stateInfo);

    @Query("select c from CityInfo c where c.status = ?1 and c.stateInfo.id = ?2")
    List<CityInfo> findAllByStatusAndStateInfoId(Status status , long stateId);

    long countAllByStatus(Status status);

    List<CityInfo> findAllByStatus(Status status , Pageable pageable);

    List<CityInfo> findAllByStatus(Status status);
}
