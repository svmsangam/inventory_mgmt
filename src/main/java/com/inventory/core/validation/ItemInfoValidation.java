package com.inventory.core.validation;

import com.inventory.core.model.dto.ItemInfoDTO;
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

    ItemInfoError error = new ItemInfoError();

    public ItemInfoError onSave(ItemInfoDTO itemInfoDTO , BindingResult result){

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

        error.setValid(valid);

        return error;
    }
}
