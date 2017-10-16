package com.inventory.core.validation;

import com.inventory.core.model.dto.OrderInfoDTO;
import com.inventory.core.model.dto.OrderItemInfoDTO;
import com.inventory.core.model.entity.ItemInfo;
import com.inventory.core.model.enumconstant.ClientType;
import com.inventory.core.model.enumconstant.Status;
import com.inventory.core.model.repository.ClientInfoRepository;
import com.inventory.core.model.repository.ItemInfoRepository;
import com.inventory.core.model.repository.OrderInfoRepository;
import com.inventory.web.error.OrderError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Created by dhiraj on 10/15/17.
 */

@Service
public class OrderValidation extends GlobalValidation {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    OrderError error;

    private boolean valid;

    @Autowired
    private OrderInfoRepository orderInfoRepository;

    @Autowired
    private ItemInfoRepository itemInfoRepository;

    @Autowired
    private ClientInfoRepository clientInfoRepository;

    public OrderError onSaleSave(OrderInfoDTO orderInfoDTO , BindingResult result){

        error = new OrderError();

        valid = true;


        if (result.hasErrors()) {

            valid = false;

            List<FieldError> errors = result.getFieldErrors();

            for (FieldError errorResult : errors) {

                if (errorResult.getField().equals("orderNo")) {
                    error.setOrderNo("invalid order number");
                }
            }

            error.setValid(valid);

            return error;
        }

        valid = valid && checkOrderNo(orderInfoDTO.getOrderNo() , orderInfoDTO.getStoreInfoId());

        valid = valid && checkItemList(orderInfoDTO.getOrderItemInfoDTOList() , orderInfoDTO.getStoreInfoId());

        valid = valid && checkRemark(orderInfoDTO.getDescription());

        valid = valid && checkCostumer(orderInfoDTO.getClientId() , orderInfoDTO.getStoreInfoId());

        error.setValid(valid);

        return error;
    }


    private boolean checkOrderNo(String value , long storeId){

        error.setOrderNo(checkString(value , 10 , 10 , "orderNo" , true));

        if (!"".equals(error.getOrderNo())){
            return false;
        } else if (orderInfoRepository.findByOrderNoAndStoreInfo(value , storeId) != null){
            error.setOrderNo("this order number is already in use");
            return false;
        }
        return true;
    }

    private boolean checkItemList(List<OrderItemInfoDTO> orderItemInfoDTOList , long storeId){

        if (orderItemInfoDTOList == null){
            error.setError("select any item to order");
            return false;
        }

        if (orderItemInfoDTOList.isEmpty()){
            error.setError("select any item to order");
            return false;
        }

        Set<Long> longSet = new HashSet<>();

        try {

            for (OrderItemInfoDTO orderItemInfoDTO : orderItemInfoDTOList) {

                longSet.add(orderItemInfoDTO.getItemInfoId());

                ItemInfo itemInfo = itemInfoRepository.findByIdAndStatusAndStoreInfo(orderItemInfoDTO.getItemInfoId() , Status.ACTIVE , storeId);

                if (itemInfo == null){
                    error.setError("item not found having id " + orderItemInfoDTO.getItemInfoId());
                    return false;
                }

                if (itemInfo.getInStock() < orderItemInfoDTO.getQuantity()){
                    error.setError(itemInfo.getProductInfo().getName() + " is avialable for only " + itemInfo.getInStock() + " " + itemInfo.getProductInfo().getUnitInfo().getName());
                    return false;
                }

                for (Long itemId : longSet){

                    if (Objects.equals(itemId, itemInfo.getId())){
                        error.setError(itemInfo.getProductInfo().getName() + " selected multiple times");
                        return false;
                    }
                }
            }
        } catch (Exception e){

            error.setError("their may be doublicate item selected check and make unique");

            return false;
        }

        return true;
    }

    private boolean checkRemark(String value){

        error.setDescription(checkString(value , 1 , 250 , "description" , false));

        if (!"".equals(error.getDescription())){
            return false;
        }

        return true;
    }

    private boolean checkCostumer(Long value , long storeId){

       error.setClientInfo(checkLong(value , 1, "client info" , true));

       if (!"".equals(error.getClientInfo())){
           return false;
       }

       if (clientInfoRepository.findByIdAndStatusAndClientType(value , Status.ACTIVE , ClientType.CUSTOMER) == null){
           error.setClientInfo("client not found");
           return false;
       }
        return true;
    }
}
