package com.inventory.core.model.dto;

import com.inventory.core.model.enumconstant.Status;

/**
 * Created by dhiraj on 8/1/17.
 */
public class CountryInfoDTO {

    private Long countryId;

    private String countryName;

    private String countryISO;

    private Status countryStatus;

    public Long getCountryId() {
        return countryId;
    }

    public void setCountryId(Long countryId) {
        this.countryId = countryId;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCountryISO() {
        return countryISO;
    }

    public void setCountryISO(String countryISO) {
        this.countryISO = countryISO;
    }

    public Status getCountryStatus() {
        return countryStatus;
    }

    public void setCountryStatus(Status countryStatus) {
        this.countryStatus = countryStatus;
    }
}
