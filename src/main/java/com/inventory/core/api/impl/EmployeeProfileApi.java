package com.inventory.core.api.impl;

import com.inventory.core.api.iapi.IEmployeeProfileApi;
import com.inventory.core.model.converter.EmployeeProfileConverter;
import com.inventory.core.model.dto.EmployeeProfileDTO;
import com.inventory.core.model.entity.EmployeeProfile;
import com.inventory.core.model.entity.StoreEmployee;
import com.inventory.core.model.entity.StoreUserInfo;
import com.inventory.core.model.enumconstant.EmployeeStatus;
import com.inventory.core.model.enumconstant.Status;
import com.inventory.core.model.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by dhiraj on 12/19/17.
 */
@Service
@Transactional
public class EmployeeProfileApi implements IEmployeeProfileApi{

    @Autowired
    private EmployeeProfileRepository employeeProfileRepository;

    @Autowired
    private EmployeeProfileConverter employeeProfileConverter;

    @Autowired
    private StoreEmployeeRepository storeEmployeeRepository;

    @Autowired
    private DesignationRepository designationRepository;

    @Autowired
    private StoreInfoRepository storeInfoRepository;

    @Autowired
    private StoreUserInfoRepository storeUserInfoRepository;

    @Override
    public EmployeeProfileDTO save(EmployeeProfileDTO employeeProfileDTO) {

        EmployeeProfile employeeProfile = employeeProfileConverter.convertToEntity(employeeProfileDTO);

        employeeProfile.setStatus(Status.ACTIVE);

        employeeProfile = employeeProfileRepository.save(employeeProfile);

        employeeProfileDTO.setEmployeeProfileId(employeeProfile.getId());

        saveStoreEmployee(employeeProfileDTO);

        return employeeProfileConverter.convertToDto(employeeProfile);
    }

    private void saveStoreEmployee(EmployeeProfileDTO employeeProfileDTO){

        StoreEmployee storeEmployee = new StoreEmployee();

        if (employeeProfileDTO.getJoinDate() != null && EmployeeStatus.CONFIRM.equals(employeeProfileDTO.getEmployeeStatus())) {
            storeEmployee.setDate(employeeProfileDTO.getJoinDate());
        }

        storeEmployee.setDesignation(designationRepository.findById(employeeProfileDTO.getDesignationId()).orElse(null));
        storeEmployee.setEmployeeProfile(employeeProfileRepository.findById(employeeProfileDTO.getEmployeeProfileId()).orElse(null));
        storeEmployee.setEmployeeStatus(employeeProfileDTO.getEmployeeStatus());
        storeEmployee.setOwner(storeInfoRepository.findById(employeeProfileDTO.getOwnerId()));
        storeEmployee.setStatus(Status.ACTIVE);

        storeEmployeeRepository.save(storeEmployee);
    }

    @Override
    public EmployeeProfileDTO show(long employeeProfileId, Status status, long ownerId) {
        return employeeProfileConverter.convertToDto(storeEmployeeRepository.findEmployeeProfileByIdAndStatusAndOwner(employeeProfileId , status , ownerId));
    }

    @Override
    public EmployeeProfileDTO showForSuperAdmin(long employeeProfileId, Status status, long superAdminId) {
        return employeeProfileConverter.convertToDto(storeEmployeeRepository.findEmployeeProfileByIdAndStatusAndOwnerIn(employeeProfileId , status , storeUserInfoRepository.findAllStoreIdByUserAndStatus(superAdminId , Status.ACTIVE)));
    }

    @Override
    public EmployeeProfileDTO update(EmployeeProfileDTO employeeProfileDTO) {
        return null;
    }

    @Override
    public List<EmployeeProfileDTO> list(Status status, long ownerId) {
        return employeeProfileConverter.convertToDtoList(storeEmployeeRepository.findAllEmployeeProfileByStatusAndOwner(status , ownerId));
    }

    @Override
    public List<EmployeeProfileDTO> listForSuperAdmin(Status status, long superAdminId) {
        return employeeProfileConverter.convertToDtoList(storeEmployeeRepository.findAllEmployeeProfileByStatusAndOwnerIn(status , storeUserInfoRepository.findAllStoreIdByUserAndStatus(superAdminId , Status.ACTIVE)));
    }

    @Override
    public EmployeeProfileDTO getByStatusAndUser(Status status, long userId) {
        return employeeProfileConverter.convertToDto(employeeProfileRepository.findByStatusAndUser(status , userId));
    }
}
