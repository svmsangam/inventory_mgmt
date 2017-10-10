package com.inventory.core.model.repository;

import com.inventory.core.model.entity.PaymentInfo;
import com.inventory.core.model.enumconstant.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by dhiraj on 10/10/17.
 */
@Repository
public interface PaymentInfoRepository extends JpaRepository<PaymentInfo , Long> , JpaSpecificationExecutor<PaymentInfo> {

    PaymentInfo findById(long paymentInfoId);

    @Query("select p from PaymentInfo p where p.id = ?1 and p.receivedPayment.status = ?2")
    PaymentInfo findByIdAndStatus(long paymentInfoId , Status status);

    @Query("select p from PaymentInfo p where p.id = ?1 and p.receivedPayment.status = ?2 and p.storeInfo.id = ?3")
    PaymentInfo findByIdAndStatusAndStore(long paymentInfoId , Status status , long storeId);

    @Query("select p from PaymentInfo p where p.id = ?1 and p.receivedPayment.status = ?2 and p.storeInfo.id = ?3 and p.invoiceInfo.id = ?4")
    PaymentInfo findByIdAndStatusAndStoreAndInvoiceInfo(long paymentInfoId , Status status , long storeId , long invoiceInfoId);

    @Query("select p from PaymentInfo p where p.receivedPayment.status = ?1 and p.storeInfo.id = ?2 and p.invoiceInfo.id = ?3")
    List<PaymentInfo> findByStatusAndStoreAndInvoiceInfo(Status status , long storeId , long invoiceInfoId);
}
