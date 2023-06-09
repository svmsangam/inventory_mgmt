package com.inventory.core.api.iapi;

import com.inventory.core.model.dto.PaymentDTO;
import com.inventory.core.model.entity.Payment;
import com.inventory.core.model.enumconstant.Status;

/**
 * Created by dhiraj on 10/10/17.
 */
public interface IPaymentApi {

    PaymentDTO save(PaymentDTO paymentDTO);

    Payment save(double amount);

    PaymentDTO show(long paymentId , Status status);

    PaymentDTO getById(long paymentId);
}
