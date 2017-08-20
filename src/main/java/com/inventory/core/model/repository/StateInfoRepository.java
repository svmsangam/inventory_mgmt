package com.inventory.core.model.repository;

import com.inventory.core.model.entity.CountryInfo;
import com.inventory.core.model.entity.StateInfo;
import com.inventory.core.model.enumconstant.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.LockModeType;
import java.util.List;

/**
 * Created by dhiraj on 8/1/17.
 */
@Repository
@Transactional(readOnly = true)
public interface StateInfoRepository extends JpaRepository<StateInfo, Long> , JpaSpecificationExecutor<StateInfo> {

    @Lock(LockModeType.OPTIMISTIC)
    StateInfo findByIdAndStatus(long stateId , Status status);

    @Lock(LockModeType.OPTIMISTIC)
    StateInfo findByNameAndStatus(String stateName , Status status);

    @Lock(LockModeType.OPTIMISTIC)
    StateInfo findByName(String stateName);

    List<StateInfo> findAllByCountryInfoAndStatus(CountryInfo countryInfo , Status status);

    @Query("select s from StateInfo s where s.countryInfo.id = ?1 and s.status = ?3")
    List<StateInfo> findAllByCountryIdAndStatus(long countryId , Status status);

    List<StateInfo> findAllByStatus(Status status);
}
