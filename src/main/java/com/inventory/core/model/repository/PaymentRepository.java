package com.inventory.core.model.repository;

import com.inventory.core.model.entity.Payment;
import com.inventory.core.model.enumconstant.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Created by dhiraj on 10/10/17.
 */
@Repository
public interface PaymentRepository extends JpaRepository<Payment , Long> , JpaSpecificationExecutor<Payment>{

    Payment findById(long paymentId);

    Payment findByIdAndStatus(long paymentId , Status status);
}
