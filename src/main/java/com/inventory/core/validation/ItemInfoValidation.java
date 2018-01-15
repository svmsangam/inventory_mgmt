package com.inventory.core.validation;

import com.inventory.core.model.dto.ItemInfoDTO;
import com.inventory.core.model.enumconstant.Status;
import com.inventory.core.model.repository.ItemInfoRepository;
import com.inventory.core.model.repository.LotInfoRepository;
import com.inventory.core.model.repository.ProductInfoRepository;
import com.inventory.core.model.repository.TagInfoRepository;
import com.inventory.web.error.ItemInfoError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by dhiraj on 8/22/17.
 */
@Service
public class ItemInfoValidation extends GlobalValidation{

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ItemInfoRepository itemInfoRepository;

    @Autowired
    private ProductInfoRepository productInfoRepository;

    @Autowired
    private LotInfoRepository lotInfoRepository;

    @Autowired
    private TagInfoRepository tagInfoRepository;

    ItemInfoError error ;

    public ItemInfoError onSave(ItemInfoDTO itemInfoDTO , long storeId , BindingResult result){

        error = new ItemInfoError();

        boolean valid = true;

        if (result.hasErrors()) {

            List<FieldError> errors = result.getFieldErrors();
            for (FieldError errorResult : errors) {

                if (errorResult.getField().equals("productId")) {
                    error.setProductId("invalid product");
                } else if (errorResult.getField().equals("costPrice")) {
                    error.setCostPrice("invalid costPrice");
                } else if (errorResult.getField().equals("sellingPrice")) {
                    error.setSellingPrice("invalid sellingPrice");
                } else if (errorResult.getField().equals("tagId")) {
                    error.setTagId("invalid tag");
                } else if (errorResult.getField().equals("lotId")) {
                    error.setLotId("invalid lot");
                } else if (errorResult.getField().equals("expireDate")) {
                    error.setExpireDate("invalid expiryDate");
                }else if (errorResult.getField().equals("quantity")) {
                    error.setQuantity("invalid quantity");
                } else if (errorResult.getField().equals("inStock")) {
                    error.setInStock("invalid inStock");
                }else if (errorResult.getField().equals("threshold")) {
                    error.setThreshold("invalid threshold");
                }
            }

            error.setValid(false);

            return error;
        }

        valid = valid && checkProduct(itemInfoDTO.getProductId() , storeId);

        valid = valid && checkCostPrice(itemInfoDTO.getCostPrice() );

        valid = valid && checkSellingPrice(itemInfoDTO.getSellingPrice() , itemInfoDTO.getCostPrice());

        valid = valid && checkTagId(itemInfoDTO.getTagId() , storeId);

        valid = valid && checkLotId(itemInfoDTO.getLotId());

        valid = valid && checkThreshold(itemInfoDTO.getThreshold());

        valid = valid && checkQuantity(itemInfoDTO.getQuantity() , itemInfoDTO.getThreshold());

        valid = valid && checkExpirydate(itemInfoDTO.getExpireDate());

        valid = valid && checkExpirydate(itemInfoDTO.getExpireDate());

        error.setValid(valid);

        return error;
    }

    private boolean checkProduct(Long productId , long storeId){

        error.setProductId(checkLong(productId , 1 , "productId" , true));

        if (! "".equals(error.getProductId())){
            return false;
        }else if (productInfoRepository.findByIdAndStatusAndStoreInfo(productId , Status.ACTIVE , storeId) == null){
            error.setProductId("invalid product");
            return false;
        }

        return true;
    }

    private boolean checkCostPrice(Double value){

        error.setCostPrice(checkDouble(value , 1 , 3 , "costPrice" , true));

        if (! "".equals(error.getCostPrice())){
            return false;
        }

        return true;
    }

    private boolean checkSellingPrice(Double value , Double costPrice){

        error.setSellingPrice(checkDouble(value , 1 , 3 , "sellingPrice" , true));

        if (! "".equals(error.getSellingPrice())){
            return false;
        }else {

            error.setSellingPrice(checkDoubleGreaterThan(value , costPrice , "sellingPrice" , "costPrice" , false));

            if (! "".equals(error.getSellingPrice())){
                return false;
            }
        }

        return true;
    }

    private boolean checkTagId(Long tagId , long storeId){

        try {

            error.setTagId(checkLong(tagId, 1, "tagId", true));

            if (!"".equals(error.getTagId())) {
                return false;
            } else if (tagInfoRepository.findByIdAndStatusAndStoreInfo(tagId, Status.ACTIVE, storeId) == null) {

                error.setTagId("invalid tag");
                return false;
            }
        }catch (Exception e){
            logger.error("exception on item valivation : " + Arrays.toString(e.getStackTrace()));
            error.setTagId("invalid tag");
            return false;
        }

        return true;
    }

    private boolean checkLotId(Long lotId ){

        try {

            error.setLotId(checkLong(lotId, 1, "lotId", true));

            if (!"".equals(error.getLotId())) {
                return false;
            } else if (lotInfoRepository.findByIdAndStatus(lotId, Status.ACTIVE) == null) {

                error.setTagId("invalid lot");
                return false;
            }
        }catch (Exception e){
            logger.error("exception on item valivation : " + Arrays.toString(e.getStackTrace()));
            error.setTagId("invalid lot");
            return false;
        }

        return true;
    }

    private boolean checkThreshold(Integer threshold){

        error.setThreshold(checkInteger(threshold , 1 , Integer.MAX_VALUE , "threshold" , true));

        if (! "".equals(error.getThreshold())){
            return false;
        }

        return true;
    }

    private boolean checkQuantity(Long quantity , Integer threshold){

        error.setQuantity(checkLong(quantity , 1 , "quantity" , true));

        if (! "".equals(error.getQuantity())){
            return false;
        }else if (threshold != null){
            if (quantity <= threshold){
                error.setQuantity("quantity must be less than threshold");
                return false;
            }
        }

        return true;
    }

    private boolean checkExpirydate(Date expiryDate){

        if (expiryDate == null){
           // error.setExpireDate("expiry date required");
            return true;
        }else if (expiryDate.before(new Date())){
            error.setExpireDate("expiry date must be before current date");
            return false;
        }

        return true;
    }

}
