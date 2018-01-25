package com.inventory.core.api.impl;

import com.inventory.core.api.iapi.IServiceInfoApi;
import com.inventory.core.model.converter.ServiceConverter;
import com.inventory.core.model.dto.ServiceDTO;
import com.inventory.core.model.entity.ServiceInfo;
import com.inventory.core.model.enumconstant.Status;
import com.inventory.core.model.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by dhiraj on 1/25/18.
 */
@Service
@Transactional
public class ServiceInfoApi implements IServiceInfoApi {

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private ServiceConverter serviceConverter;

    @Override
    public ServiceDTO save(ServiceDTO serviceDTO) {

        ServiceInfo serviceInfo = serviceConverter.convertToEntity(serviceDTO);

        return serviceConverter.convertToDto(serviceRepository.save(serviceInfo));
    }

    @Override
    public ServiceDTO show(long serviceId, Status status) {
        return serviceConverter.convertToDto(serviceRepository.findByIdAndStatus(serviceId , status));
    }

    @Override
    public List<ServiceDTO> list(Status status) {
        return serviceConverter.convertToDtoList(serviceRepository.findAllByStatus(status));
    }
}
