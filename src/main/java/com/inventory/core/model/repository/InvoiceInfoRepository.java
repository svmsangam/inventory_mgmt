package com.inventory.core.model.repository;

import com.inventory.core.model.entity.InvoiceInfo;
import com.inventory.core.model.enumconstant.Status;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
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

    @Query("select i from InvoiceInfo i where i.status = ?1 and i.storeInfo.id = ?2 and i.orderInfo.id = ?3")
    InvoiceInfo findByStatusAndStoreInfoAndAndOrderInfo(Status status , long storeInfoId , long orderInfoId);

    InvoiceInfo findByInvoiceNo(String invoiceNo);

    @Query("select i from InvoiceInfo i where i.status = ?1 and i.storeInfo.id = ?2")
    List<InvoiceInfo> findAllByStatusAndStoreInfo(Status status , long storeInfoId , Pageable pageable);

    @Query("select count (i) from InvoiceInfo i where i.status = ?1 and i.storeInfo.id = ?2")
    long countAllByStatusAndStoreInfo(Status status , long storeInfoId);

    @Query("select sum (i.totalAmount) from InvoiceInfo i where i.storeInfo.id = ?1 and i.status = ?2")
    Double findTotalAmountByStoreAndStatus(long storeInfoId , Status status);

    @Query("select sum (i.totalAmount) from InvoiceInfo i where i.storeInfo.id = ?1 and i.status = ?2 and i.invoiceDate = current_date")
    Double findToDayTotalAmountByStoreAndStatus(long storeInfoId , Status status);

    @Query("select i from InvoiceInfo i where i.status = ?1 and i.storeInfo.id = ?2")
    List<InvoiceInfo> findAllTopReceivableByStatusAndStoreInfo(Status status , long storeInfoId , Pageable pageable);

    @Query("select sum (i.receivableAmount) from InvoiceInfo i where i.storeInfo.id = ?1 and i.status = ?2")
    Double findTotalReceivableByStoreInfoAndStatus(long storeInfoId , Status status);

    @Query("select sum (i.totalAmount) , substring(i.invoiceDate , 6, 2) from InvoiceInfo i where i.storeInfo.id=?1 and substring(i.invoiceDate , 1, 4) = ?2 group by substring(i.invoiceDate , 6, 2)")
    List<Object[]> findTotalSellOfYearByStore(long storeId , String year );

    @Query("select i from InvoiceInfo i where i.status = ?1 and i.orderInfo.clientInfo.id = ?2 and i.storeInfo.id =?3")
    List<InvoiceInfo> findAllByStatusAndBuyerAndStoreInfo(Status status , long clientId , long storeId , Pageable pageable);

    @Query("select count (i) from InvoiceInfo i where i.status = ?1 and i.orderInfo.clientInfo.id = ?2 and i.storeInfo.id =?3")
    long countAllByStatusAndBuyerAndStoreInfo(Status status , long clientId , long storeId);

    @Query("select i from InvoiceInfo i where i.status = ?1 and i.storeInfo.id = ?2 and (i.invoiceDate between ?3 and ?4)")
    List<InvoiceInfo> findAllByStatusAndStoreInfoAndInvoiceDateBetween(Status status , long storeId , Date from , Date to , Pageable pageable);

    @Query("select count (i) from InvoiceInfo i where i.status = ?1 and i.storeInfo.id = ?2 and (i.invoiceDate between ?3 and ?4)")
    Long countAllByStatusAndStoreInfoAndInvoiceDateBetween(Status status , long storeId , Date from , Date to);

}
