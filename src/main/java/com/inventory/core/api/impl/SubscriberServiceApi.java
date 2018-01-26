package com.inventory.core.api.impl;

import com.inventory.core.api.iapi.ISubscriberServiceApi;
import com.inventory.core.model.converter.ServiceConverter;
import com.inventory.core.model.converter.SubscriberServiceConverter;
import com.inventory.core.model.dto.SubscriberServiceDTO;
import com.inventory.core.model.entity.SubscriberService;
import com.inventory.core.model.enumconstant.Status;
import com.inventory.core.model.repository.ServiceRepository;
import com.inventory.core.model.repository.SubscriberRepository;
import com.inventory.core.model.repository.SubscriberServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by dhiraj on 1/26/18.
 */
@Service
public class SubscriberServiceApi implements ISubscriberServiceApi{

    @Autowired
    private SubscriberServiceRepository subscriberServiceRepository;

    @Autowired
    private SubscriberServiceConverter subscriberServiceConverter;

    @Override
    public void save(long serviceId , long subscriberId) throws ParseException {

        SubscriberService subscriberService = subscriberServiceConverter.convertToEntity(serviceId , subscriberId);

        SubscriberService selected = getSelectedEntity(subscriberId);

        if (selected != null){
            selected.setSelected(false);
            subscriberServiceRepository.save(selected);
        }

        subscriberServiceRepository.save(subscriberService);
    }

    @Override
    public SubscriberServiceDTO getSelected(long subscriberId){

        List<SubscriberService>  entities = subscriberServiceRepository.findAllByStatusAndSubscriber_IdAndSelected(Status.ACTIVE , subscriberId , true);

        if (entities == null){
            return null;
        }

        if (entities.isEmpty()){
            return null;
        }

        SubscriberServiceDTO dto = null;

        for (SubscriberService entity : entities){

            dto = subscriberServiceConverter.convertToDto(entity);

            return dto;
        }

        return dto;
    }

    private SubscriberService getSelectedEntity(long subscriberId){

        List<SubscriberService>  entities = subscriberServiceRepository.findAllByStatusAndSubscriber_IdAndSelected(Status.ACTIVE , subscriberId , true);

        if (entities == null){
            return null;
        }

        if (entities.isEmpty()){
            return null;
        }

        for (SubscriberService entity : entities){

            return entity;
        }

        return null;
    }


    @Override
    public List<SubscriberServiceDTO> list(Status status, long subscriberId){
        return subscriberServiceConverter.convertToDtoList(subscriberServiceRepository.findAllByStatusAndSubscriber_Id(status , subscriberId));
    }

    private Date getExpireDate(int days) throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Calendar c = Calendar.getInstance();
        c.setTime(new Date()); // Now use today date.
        c.add(Calendar.DATE, 5); // Adding 5 days
        String output = sdf.format(c.getTime());

        Date expireDate = sdf.parse(output);

        return expireDate;
    }
}
