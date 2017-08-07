package com.inventory.core.model.dto;

import com.inventory.core.model.enumconstant.Status;

/**
 * Created by dhiraj on 8/1/17.
 */
public class StateInfoDTO {

    private Long stateId;

    private String stateName;

    private Long countryId;

    private String countryName;

    private Status stateStatus;

    public Long getStateId() {
        return stateId;
    }

    public void setStateId(Long stateId) {
        this.stateId = stateId;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

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

    public Status getStateStatus() {
        return stateStatus;
    }

    public void setStateStatus(Status stateStatus) {
        this.stateStatus = stateStatus;
    }
}
