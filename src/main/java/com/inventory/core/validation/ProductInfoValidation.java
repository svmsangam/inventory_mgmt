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
import org.springframework.validation.Validator;

import java.util.Arrays;
import java.util.List;

/**
 * Created by dhiraj on 8/22/17.
 */
@Service
public class ProductInfoValidation extends GlobalValidation {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ProductInfoRepository productInfoRepository;

    @Autowired
    private UnitInfoRepository unitInfoRepository;

    @Autowired
    private SubCategoryInfoRepository subCategoryInfoRepository;

    @Autowired
    private Validator validator;

    ProductInfoError error = new ProductInfoError();

    public ProductInfoError onSave(ProductInfoDTO productInfoDTO, BindingResult result) {

        boolean valid = true;

        if (result.hasErrors()) {

            List<FieldError> errors = result.getFieldErrors();
            for (FieldError errorResult : errors) {

                if (errorResult.getField().equals("name")) {
                    error.setName("invalid store name");
                } else if (errorResult.getField().equals("code")) {
                    error.setCode("invalid code");
                } else if (errorResult.getField().equals("description")) {
                    error.setDescription("invalid description");
                } else if (errorResult.getField().equals("trendingLevel")) {
                    error.setTrendingLevel("invalid trendingLevel");
                } else if (errorResult.getField().equals("subCategoryId")) {
                    error.setSubCategoryId("invalid subCategory");
                } else if (errorResult.getField().equals("unitId")) {
                    error.setUnitId("invalid unit");
                }
            }

            error.setValid(false);

            return error;
        }


        valid = valid && checkName(productInfoDTO.getName(), productInfoDTO.getStoreInfoId());

        valid = valid && checkCode(productInfoDTO.getCode(), productInfoDTO.getStoreInfoId());

        valid = valid && checkDescription(productInfoDTO.getDescription());

        valid = valid && checkSubCategory(productInfoDTO.getSubCategoryId(), productInfoDTO.getStoreInfoId());

        valid = valid && checkUnit(productInfoDTO.getUnitId(), productInfoDTO.getStoreInfoId());

        if (productInfoDTO.getTrendingLevel() == null){
            valid = false;
            error.setTrendingLevel("trending level required");
        }

        error.setValid(valid);

        return error;
    }

    private boolean checkName(String value, long storeId) {

        try {

            error.setName(checkString(value, 3, 20, "name", true));

            if (!("".equals(error.getName()))) {

                return false;

            } else if (productInfoRepository.findByNameAndStatusAndStoreInfo(value.trim(), Status.ACTIVE, storeId) != null) {

                error.setName("this name already in use");

                return false;
            }
        } catch (Exception e) {
            logger.error("exception on product valivation : " + Arrays.toString(e.getStackTrace()));
            error.setName("invalid name");
            return false;
        }

        return true;
    }

    private boolean checkCode(String value, long storeId) {

        try {

            error.setCode(checkString(value, 1, 10, "code", true));

            if (!("".equals(error.getCode()))) {

                return false;

            } else if (productInfoRepository.findByCodeAndStatusAndStoreInfo(value.trim(), Status.ACTIVE, storeId) != null) {

                error.setName("this code already in use");

                return false;
            }
        } catch (Exception e) {
            logger.error("exception on product valivation : " + Arrays.toString(e.getStackTrace()));
            error.setName("invalid code");
            return false;
        }

        return true;
    }

    private boolean checkDescription(String value) {

        error.setDescription(checkString(value, 1, 200, "description", false));

        return "".equals(error.getCode());
    }

    private boolean checkSubCategory(Long subcategoryId, long storeId) {

        try {

            if (subcategoryId == null) {
                error.setSubCategoryId("subcategory required");
                return false;
            }

            error.setSubCategoryId(checkLong(subcategoryId, 1, "subcategoryId", true));

            if (!"".equals(error.getSubCategoryId())) {

                return false;

            } else if (subCategoryInfoRepository.findByIdAndStatusAndStoreInfo(subcategoryId, Status.ACTIVE, storeId) == null) {

                error.setSubCategoryId("invalid subcategory");

                return false;
            }

        } catch (Exception e) {
            logger.error("exception on product valivation : " + Arrays.toString(e.getStackTrace()));
            error.setSubCategoryId("invalid subcategory");
            return false;
        }

        return true;
    }


    private boolean checkUnit(Long unitId, long storeId) {

        try {

            if (unitId == null) {
                error.setUnitId("unit required");
                return false;
            }

            error.setUnitId(checkLong(unitId, 1, "unitId", true));

            if (!"".equals(error.getUnitId())) {

                return false;

            } else if (unitInfoRepository.findByIdAndStatusAndStoreInfo(unitId, Status.ACTIVE, storeId) == null) {

                error.setSubCategoryId("invalid unit");

                return false;
            }

        } catch (Exception e) {
            logger.error("exception on product valivation : " + Arrays.toString(e.getStackTrace()));
            error.setSubCategoryId("invalid unit");
            return false;
        }

        return true;
    }

}
