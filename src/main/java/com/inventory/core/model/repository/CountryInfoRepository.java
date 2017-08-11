package com.inventory.core.model.repository;

import com.inventory.core.model.entity.CountryInfo;
import com.inventory.core.model.enumconstant.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by dhiraj on 8/2/17.
 */
@Repository
@Transactional(readOnly = true)
public interface CountryInfoRepository extends JpaRepository<CountryInfo, Long> , JpaSpecificationExecutor<CountryInfo>{

    CountryInfo findByIdAndStatus(long countryId , Status status);

    CountryInfo findByNameAndStatus(String countryName , Status status);

    CountryInfo findByName(String countryName);

    List<CountryInfo> findAllByStatus(Status status);

}
