package com.inventory.core.validation;

import com.inventory.core.model.dto.OrderReturnInfoDTO;
import com.inventory.core.model.entity.OrderInfo;
import com.inventory.core.model.entity.OrderItemInfo;
import com.inventory.core.model.entity.ReturnItemInfo;
import com.inventory.core.model.enumconstant.SalesOrderStatus;
import com.inventory.core.model.enumconstant.Status;
import com.inventory.core.model.repository.OrderInfoRepository;
import com.inventory.core.model.repository.OrderItemInfoRepository;
import com.inventory.web.error.OrderError;
import com.inventory.web.error.OrderReturnError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.Date;
import java.util.List;

/**
 * Created by dhiraj on 1/30/18.
 */
@Service
public class OrderReturnValidation extends GlobalValidation {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    OrderReturnError error;

    private boolean valid;

    @Autowired
    private OrderInfoRepository orderInfoRepository;

    @Autowired
    private OrderItemInfoRepository orderItemInfoRepository;

    public OrderReturnError onsave(OrderReturnInfoDTO dto, BindingResult result) {

        error = new OrderReturnError();

        valid = true;


        if (result.hasErrors()) {

            valid = false;

            List<FieldError> errors = result.getFieldErrors();

            for (FieldError errorResult : errors) {

                if (errorResult.getField().equals("returnDate")) {
                    error.setReturnDate("invalid Return Date");
                }
            }

            error.setValid(valid);

            return error;
        }

        valid = valid && checkReturnDate(dto.getReturnDate());
        valid = valid && checkOrder(dto.getOrderInfoId(), dto.getStoreId());
        valid = valid && checkNote(dto.getNote());
        valid = valid & checkItem(dto.getOrderItemInfoIdList() , dto.getReturnQuantityList() , dto.getOrderInfoId());

        error.setValid(valid);

        return error;

    }

    private boolean checkReturnDate(Date returnDate) {
        if (returnDate == null) {
            error.setReturnDate("return date required");
        }

        return true;
    }

    private boolean checkOrder(Long orderInfoId, long storeId) {

        error.setError(checkLong(orderInfoId, 1, "order", true));

        if (!"".equals(error.getError())) {
            return false;
        }

        OrderInfo orderInfo = orderInfoRepository.findByIdAndStatusAndStoreInfo(orderInfoId, Status.ACTIVE, storeId);

        if (orderInfo == null) {
            error.setError("provided order not found");
            return false;
        }

        if (!orderInfo.getSaleTrack().equals(SalesOrderStatus.DELIVERED)) {
            error.setError("provided order not delivered yet");
            return false;
        }

        return true;
    }

    private boolean checkNote(String note) {

        error.setNote(checkString(note.trim(), 1, 200, "note", false));

        if (!"".equals(error.getNote())) {
            return false;
        }

        return true;
    }

    private boolean checkItem(List<Long> orderItemList, List<Integer> quanitytList, long orderId) {

        if (orderItemList == null) {
            error.setError("no item to return");
            return false;
        }

        if (quanitytList == null) {
            error.setError("no quantity to return");
            return false;
        }

        if (orderItemList.size() != quanitytList.size()) {
            error.setError("every item must have its quantity");
            return false;
        }

        for (int i = 0; i < orderItemList.size(); i++) {

            error.setError(checkLong(orderItemList.get(i) , 1 , "item" , true));

            if (!"".equals(error.getError())){
                return false;
            }

            error.setError(checkInteger(quanitytList.get(i) , 1 , Integer.MAX_VALUE , "quantity" , true));

            if (!"".equals(error.getError())){
                return false;
            }

            OrderItemInfo orderItemInfo = orderItemInfoRepository.findByIdAndStatusAndOrderInfo(orderItemList.get(i), Status.ACTIVE, orderId);

            if (orderItemInfo == null) {
                error.setError("one of the item is not found");
                return false;
            }

            int remainingQuantity = orderItemInfo.getQuantity() - orderItemInfo.getReturnQuantity();

            if (remainingQuantity == 0){
                error.setError("item : " + orderItemInfo.getItemInfo().getProductInfo().getName() + " fully returned already");
                return false;
            }

            if (quanitytList.get(i) > remainingQuantity) {
                error.setError("item : " + orderItemInfo.getItemInfo().getProductInfo().getName() + " only sold " + remainingQuantity);
                return false;
            }


        }

        return true;
    }
}
