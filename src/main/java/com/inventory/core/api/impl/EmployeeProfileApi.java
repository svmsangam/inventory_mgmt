package com.inventory.core.api.impl;

import com.inventory.core.api.iapi.IEmployeeProfileApi;
import com.inventory.core.model.converter.EmployeeProfileConverter;
import com.inventory.core.model.dto.EmployeeProfileDTO;
import com.inventory.core.model.entity.EmployeeProfile;
import com.inventory.core.model.enumconstant.Status;
import com.inventory.core.model.repository.EmployeeProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Override
    public EmployeeProfileDTO save(EmployeeProfileDTO employeeProfileDTO) {

        EmployeeProfile employeeProfile = employeeProfileConverter.convertToEntity(employeeProfileDTO);

        employeeProfile.setStatus(Status.ACTIVE);

        employeeProfile = employeeProfileRepository.save(employeeProfile);

        return employeeProfileConverter.convertToDto(employeeProfile);
    }

    @Override
    public EmployeeProfileDTO show(long employeeProfileId, Status status) {
        return employeeProfileConverter.convertToDto(employeeProfileRepository.findByStatusAndId(status , employeeProfileId));
    }

    @Override
    public EmployeeProfileDTO update(EmployeeProfileDTO employeeProfileDTO) {
        return null;
    }

    @Override
    public List<EmployeeProfileDTO> list(Status status) {
        return employeeProfileConverter.convertToDtoList(employeeProfileRepository.findAllByStatus(status));
    }

    @Override
    public EmployeeProfileDTO getByStatusAndUser(Status status, long userId) {
        return employeeProfileConverter.convertToDto(employeeProfileRepository.findByStatusAndUser(status , userId));
    }
}
