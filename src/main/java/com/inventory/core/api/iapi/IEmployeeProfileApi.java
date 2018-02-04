package com.inventory.core.api.iapi;

import com.inventory.core.model.dto.EmployeeProfileDTO;
import com.inventory.core.model.enumconstant.Status;

import java.util.List;

/**
 * Created by dhiraj on 12/19/17.
 */
public interface IEmployeeProfileApi {

    EmployeeProfileDTO save(EmployeeProfileDTO employeeProfileDTO);

    EmployeeProfileDTO show(long employeeProfileId, Status status, long ownerId);

    EmployeeProfileDTO showForSuperAdmin(long employeeProfileId, Status status, long superAdminId);

    EmployeeProfileDTO update(EmployeeProfileDTO employeeProfileDTO);

    List<EmployeeProfileDTO> list(Status status, long ownerId);

    List<EmployeeProfileDTO> listForSuperAdmin(Status status, long superAdminId);

    EmployeeProfileDTO getByStatusAndUser(Status status , long userId);
}
