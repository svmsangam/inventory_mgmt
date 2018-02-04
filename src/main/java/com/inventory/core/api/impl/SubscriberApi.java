package com.inventory.core.api.impl;

import com.inventory.core.api.iapi.ISubscriberApi;
import com.inventory.core.api.iapi.ISubscriberServiceApi;
import com.inventory.core.api.iapi.IUserApi;
import com.inventory.core.model.converter.SubscriberConverter;
import com.inventory.core.model.dto.SubscriberDTO;
import com.inventory.core.model.entity.ServiceInfo;
import com.inventory.core.model.entity.Subscriber;
import com.inventory.core.model.entity.SubscriberService;
import com.inventory.core.model.enumconstant.Status;
import com.inventory.core.model.repository.ServiceRepository;
import com.inventory.core.model.repository.SubscriberRepository;
import com.inventory.core.model.repository.SubscriberServiceRepository;
import com.inventory.web.controller.SubscriberController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.crypto.Data;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by dhiraj on 1/25/18.
 */
@Service
public class SubscriberApi implements ISubscriberApi {

    @Autowired
    private SubscriberRepository subscriberRepository;

    @Autowired
    private SubscriberConverter subscriberConverter;

    @Autowired
    private IUserApi userApi;

    @Autowired
    private ISubscriberServiceApi subscriberServiceApi;

    @Autowired
    private ServiceRepository serviceRepository;

    @Override
    @Transactional
    public SubscriberDTO save(SubscriberDTO subscriberDTO) throws ParseException {

        subscriberDTO.setUserId(userApi.save(subscriberDTO.getUsername() , subscriberDTO.getPassword()));

        Subscriber subscriber = subscriberConverter.convertToEntity(subscriberDTO);

        subscriber = subscriberRepository.save(subscriber);

        subscriberServiceApi.save(subscriberDTO.getServiceId() , subscriber.getId());

        return subscriberConverter.convertToDto(subscriber);
    }

    @Override
    @Transactional
    public SubscriberDTO register(SubscriberDTO subscriberDTO) throws ParseException {

        subscriberDTO.setUserId(userApi.save(subscriberDTO.getUsername() , subscriberDTO.getPassword()));

        Subscriber subscriber = subscriberConverter.convertToEntity(subscriberDTO);

        subscriber = subscriberRepository.save(subscriber);

        ServiceInfo serviceInfo = serviceRepository.findByTitle("demo");

        if (serviceInfo != null) {
            subscriberServiceApi.save(subscriberDTO.getServiceId(), serviceInfo.getId());
        }

        return subscriberConverter.convertToDto(subscriber);
    }

    @Override
    public SubscriberDTO show(Status status, long subscribId) {
        return subscriberConverter.convertToDto(subscriberRepository.findByIdAndStatus(subscribId , status));
    }

    @Override
    public List<SubscriberDTO> list(Status status) {
        return subscriberConverter.convertToDtoList(subscriberRepository.findAllByStatus(status));
    }
}
