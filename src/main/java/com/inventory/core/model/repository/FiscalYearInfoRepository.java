package com.inventory.core.model.repository;

import com.inventory.core.model.entity.FiscalYearInfo;
import com.inventory.core.model.enumconstant.Status;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by dhiraj on 12/24/17.
 */
@Repository
public interface FiscalYearInfoRepository extends JpaRepository<FiscalYearInfo , Long>{

    FiscalYearInfo findById(long fiscalYearInfoId);

    FiscalYearInfo findByIdAndStatus(long fiscalYearInfoId , Status status);

    @Query("select f from FiscalYearInfo f where f.id = ?1 and f.status = ?2 and f.storeInfo.id = ?3")
    FiscalYearInfo findByIdAndStatusAndStoreInfo(long fiscalYearInfoId , Status status , long storeInfoId);

    @Query("select f from FiscalYearInfo f where f.id = ?1 and f.status = ?2 and f.storeInfo.id = ?3 and f.selected = ?4")
    FiscalYearInfo findByIdAndStatusAndStoreInfoAndSelected(long fiscalYearInfoId , Status status , long storeInfoId , boolean selected);

    @Query("select f from FiscalYearInfo f where f.status = ?1 and f.storeInfo.id = ?2 and f.selected = ?3")
    FiscalYearInfo findByStatusAndStoreInfoAndSelected(Status status , long storeInfoId , boolean selected);

    @Query("select f from FiscalYearInfo f where f.status = ?1 and f.storeInfo.id = ?2")
    List<FiscalYearInfo> findAllByStatusAndStoreInfo(Status status , long storeInfoId , Pageable pageable);
}
