package com.inventory.core.model.repository;

import com.inventory.core.model.entity.StoreInvoice;
import com.inventory.core.model.enumconstant.Status;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface StoreInvoiceRepository extends JpaRepository<StoreInvoice , Long>{

    StoreInvoice findById(long id);

    StoreInvoice findByIdAndStatus(long id , Status status);

    StoreInvoice findByIdAndStatusAndStoreInfo_Id(long id , Status status , long storeId);

    StoreInvoice findByStoreInfo_IdAndMonth(long storeId , Date month);

    List<StoreInvoice> findAllByStoreInfo_Id(long storeId , Pageable pageable);

}
