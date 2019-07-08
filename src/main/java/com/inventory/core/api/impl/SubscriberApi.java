package com.inventory.core.api.impl;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inventory.core.api.iapi.ISubscriberApi;
import com.inventory.core.api.iapi.ISubscriberServiceApi;
import com.inventory.core.api.iapi.IUserApi;
import com.inventory.core.model.converter.SubscriberConverter;
import com.inventory.core.model.dto.SubscriberDTO;
import com.inventory.core.model.entity.ServiceInfo;
import com.inventory.core.model.entity.Subscriber;
import com.inventory.core.model.entity.User;
import com.inventory.core.model.enumconstant.Status;
import com.inventory.core.model.repository.ServiceRepository;
import com.inventory.core.model.repository.SubscriberRepository;
import com.inventory.core.model.repository.UserRepository;

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
    
    @Autowired
    private UserRepository userRepository;

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
    public String register(SubscriberDTO subscriberDTO) throws ParseException {

        subscriberDTO.setUserId(userApi.save(subscriberDTO.getEmail().trim().toLowerCase() , subscriberDTO.getPassword()));

        Subscriber subscriber = subscriberConverter.convertToEntity(subscriberDTO);

        subscriber = subscriberRepository.save(subscriber);

        ServiceInfo serviceInfo = serviceRepository.findByTitle("demo");

        if (serviceInfo != null) {
            subscriberServiceApi.save(serviceInfo.getId() , subscriber.getId());
        }

        return userApi.saveVerificationToken(subscriberDTO.getUserId());
    }

    @Override
    public SubscriberDTO show(Status status, long subscribId) {
        return subscriberConverter.convertToDto(subscriberRepository.findByIdAndStatus(subscribId , status));
    }
    
    @Override
    public void activate(long subscribId) {
    	 Subscriber subscriber= subscriberRepository.findOne(subscribId);
    	 User u = userRepository.findOne(subscriber.getUser().getId());
    	 u.setStatus(Status.ACTIVE);
    	 userRepository.save(u);
    }

    @Override
    public List<SubscriberDTO> list(Status status) {
        return subscriberConverter.convertToDtoList(subscriberRepository.findAllByStatus(status));
    }
}
