package com.inventory.core.validation;

import com.inventory.core.model.dto.ProductInfoDTO;
import com.inventory.core.model.enumconstant.Status;
import com.inventory.core.model.repository.ProductInfoRepository;
import com.inventory.core.model.repository.SubCategoryInfoRepository;
import com.inventory.core.model.repository.UnitInfoRepository;
import com.inventory.web.error.ProductInfoError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.Arrays;
import java.util.List;

/**
 * Created by dhiraj on 8/22/17.
 */
@Service
public class ProductInfoValidation extends GlobalValidation{

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ProductInfoRepository productInfoRepository;

    @Autowired
    private UnitInfoRepository unitInfoRepository;

    @Autowired
    private SubCategoryInfoRepository subCategoryInfoRepository;

    ProductInfoError error = new ProductInfoError();

    private boolean valid ;

    public ProductInfoError onSave(ProductInfoDTO productInfoDTO , BindingResult result){

        valid = true;

        if (result.hasErrors()) {

            valid = false;

            List<FieldError> errors = result.getFieldErrors();
            for (FieldError errorResult : errors) {

                if (errorResult.getField().equals("name")) {
                    error.setName("invalid store name");
                } else if (errorResult.getField().equals("code")) {
                    error.setCode("invalid code");
                } else if (errorResult.getField().equals("description")){
                    error.setDescription("invalid description");
                }else if (errorResult.getField().equals("trendingLevel")){
                    error.setTrendingLevel("invalid trendingLevel");
                }else if (errorResult.getField().equals("subCategoryId")){
                    error.setSubCategoryId("invalid subCategory");
                }else if (errorResult.getField().equals("unitId")){
                    error.setUnitId("invalid unit");
                }
            }

            error.setValid(valid);

            return error;
        }


        valid = valid & checkName(productInfoDTO.getName() , productInfoDTO.getStoreInfoId());

        error.setValid(valid);

        return error;
    }

    private boolean checkName(String value , long storeId){

        try {

            error.setName(checkString(value, 3, 20, "name", true));

            if (!("".equals(error.getName()))) {

                return false;

            } else if (productInfoRepository.findByNameAndStatusAndStoreInfo(value.trim(), Status.ACTIVE, storeId) != null) {

                error.setName("this name already in use");

                return false;
            }
        }catch (Exception e){
            logger.error("exception on product valivation : "  + Arrays.toString(e.getStackTrace()));
            error.setName("invalid name");
            return false;
        }

        return true;
    }

}
