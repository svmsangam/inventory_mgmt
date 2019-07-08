package com.inventory.core.api.iapi;

import com.inventory.core.model.dto.ServiceDTO;
import com.inventory.core.model.enumconstant.Status;

import java.util.List;

/**
 * Created by dhiraj on 1/25/18.
 */
public interface IServiceInfoApi {

    ServiceDTO save(ServiceDTO serviceDTO);

    ServiceDTO show(long serviceId , Status status);

    List<ServiceDTO> list(Status status);
}
