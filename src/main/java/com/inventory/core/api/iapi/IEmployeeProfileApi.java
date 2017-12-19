package com.inventory.core.api.iapi;

import com.inventory.core.model.dto.EmployeeProfileDTO;
import com.inventory.core.model.enumconstant.Status;

import java.util.List;

/**
 * Created by dhiraj on 12/19/17.
 */
public interface IEmployeeProfileApi {

    EmployeeProfileDTO save(EmployeeProfileDTO employeeProfileDTO);

    EmployeeProfileDTO show(long employeeProfileId , Status status);

    EmployeeProfileDTO update(EmployeeProfileDTO employeeProfileDTO);

    List<EmployeeProfileDTO> list(Status status);

    EmployeeProfileDTO getByStatusAndUser(Status status , long userId);
}
