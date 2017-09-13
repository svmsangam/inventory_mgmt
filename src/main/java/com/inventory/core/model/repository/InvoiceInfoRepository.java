package com.inventory.core.model.repository;

import com.inventory.core.model.entity.InvoiceInfo;
import com.inventory.core.model.enumconstant.Status;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by dhiraj on 9/13/17.
 */

@Repository
public interface InvoiceInfoRepository extends JpaRepository<InvoiceInfo , Long> , JpaSpecificationExecutor<InvoiceInfo>{

    InvoiceInfo findById(long invoiceId);

    @Query("select i from InvoiceInfo i where i.id = ?1 and i.status = ?2 and i.storeInfo.id = ?3")
    InvoiceInfo findByIdAndStatusAndStoreInfo(long invoiceId , Status status , long storeInfoId);

    @Query("select i from InvoiceInfo i where i.id = ?1 and i.status = ?2 and i.storeInfo.id = ?3 and i.orderInfo.id = ?4")
    InvoiceInfo findByIdAndStatusAndStoreInfoAndAndOrderInfo(long invoiceId , Status status , long storeInfoId , long orderInfoId);

    InvoiceInfo findByInvoiceNo(String invoiceNo);

    @Query("select i from InvoiceInfo i where i.status = ?1 and i.storeInfo.id = ?2")
    List<InvoiceInfo> findAllByStatusAndStoreInfo(Status status , long storeInfoId , Pageable pageable);

}
