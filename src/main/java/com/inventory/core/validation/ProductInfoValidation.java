package com.inventory.core.validation;

import com.inventory.core.model.dto.ProductInfoDTO;
import com.inventory.core.model.entity.ProductInfo;
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
public class ProductInfoValidation extends GlobalValidation {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ProductInfoRepository productInfoRepository;

    @Autowired
    private UnitInfoRepository unitInfoRepository;

    @Autowired
    private SubCategoryInfoRepository subCategoryInfoRepository;

    ProductInfoError error;

    public ProductInfoError onSave(ProductInfoDTO productInfoDTO, BindingResult result) {

        error = new ProductInfoError();

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

    public ProductInfoError onUpdate(ProductInfoDTO productInfoDTO, BindingResult result) {

        error = new ProductInfoError();

        boolean valid = true;

        if (result.hasErrors()) {

            List<FieldError> errors = result.getFieldErrors();
            for (FieldError errorResult : errors) {

                if (errorResult.getField().equals("name")) {
                    error.setName("invalid store name");
                }else if (errorResult.getField().equals("description")) {
                    error.setDescription("invalid description");
                } else if (errorResult.getField().equals("trendingLevel")) {
                    error.setTrendingLevel("invalid trendingLevel");
                } else if (errorResult.getField().equals("subCategoryId")) {
                    error.setSubCategoryId("invalid subCategory");
                } else if (errorResult.getField().equals("unitId")) {
                    error.setUnitId("invalid unit");
                }else if (errorResult.getField().equals("productId")) {
                    error.setName("invalid product");
                }
            }

            error.setValid(false);

            return error;
        }

        valid = valid && checkProduct(productInfoDTO.getProductId() , productInfoDTO.getStoreInfoId());

        valid = valid && checkNameOnUpdate(productInfoDTO.getName(), productInfoDTO.getStoreInfoId() , productInfoDTO.getProductId());

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

    private boolean checkProduct(Long productId , long storeId){
        try {
            error.setName(checkLong(productId, 0, "name", true));

            if (!("".equals(error.getName()))) {

                return false;

            } else if (productInfoRepository.findByIdAndStatusAndStoreInfo(productId, Status.ACTIVE, storeId) == null) {

                error.setName("product not found to update");

                return false;
            }
        }catch (Exception e){
            logger.error("exception on product valivation : " + Arrays.toString(e.getStackTrace()));
            error.setName("invalid product");
            return false;
        }

        return true;
    }


    private boolean checkName(String value, long storeId) {

        try {

            error.setName(checkString(value, 3, 50, "name", true));

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

    private boolean checkNameOnUpdate(String value, long storeId , long productId) {

        try {

            error.setName(checkString(value, 3, 50, "name", true));

            if (!("".equals(error.getName()))) {

                return false;

            } else {

                ProductInfo productInfo = productInfoRepository.findByNameAndStatusAndStoreInfo(value.trim(), Status.ACTIVE, storeId);

                if (productInfo != null){
                    if (productInfo.getId() != productId){
                        error.setName("this name already in use");

                        return false;
                    }
                }

            }
        } catch (Exception e) {
            logger.error("exception on product valivation : " + Arrays.toString(e.getStackTrace()));
            error.setName("invalid name");
            return false;
        }

        return true;
    }

    private boolean checkDescription(String value) {

        error.setDescription(checkString(value, 1, 200, "description", false));

        return "".equals(error.getDescription());
    }

    private boolean checkSubCategory(Long subcategoryId, long storeId) {

        try {

            if (subcategoryId == null) {
                error.setSubCategoryId("subcategory required");
                return false;
            }

            error.setSubCategoryId(checkLong(subcategoryId, 1, "categoryId", true));

            if (!"".equals(error.getSubCategoryId())) {

                return false;

            } else if (subCategoryInfoRepository.findByIdAndStatusAndStoreInfo_Id(subcategoryId, Status.ACTIVE, storeId) == null) {

                error.setSubCategoryId("invalid category");

                return false;
            }

        } catch (Exception e) {
            logger.error("exception on product valivation : " + Arrays.toString(e.getStackTrace()));
            error.setSubCategoryId("invalid category");
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
