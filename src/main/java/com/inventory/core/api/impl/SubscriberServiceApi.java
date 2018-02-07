package com.inventory.core.api.impl;

import com.inventory.core.api.iapi.ISubscriberServiceApi;
import com.inventory.core.model.converter.ServiceConverter;
import com.inventory.core.model.converter.SubscriberServiceConverter;
import com.inventory.core.model.dto.ServiceDTO;
import com.inventory.core.model.dto.SubscriberServiceDTO;
import com.inventory.core.model.entity.StoreUserInfo;
import com.inventory.core.model.entity.Subscriber;
import com.inventory.core.model.entity.SubscriberService;
import com.inventory.core.model.entity.User;
import com.inventory.core.model.enumconstant.Status;
import com.inventory.core.model.enumconstant.UserType;
import com.inventory.core.model.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
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

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SubscriberRepository subscriberRepository;

    @Autowired
    private StoreUserInfoRepository storeUserInfoRepository;

    @Autowired
    private ServiceConverter serviceConverter;

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

    @Override
    public SubscriberServiceDTO getSelectedByUserId(long userId) {

        User user = userRepository.findById(userId);

        if (user.getUserType().equals(UserType.SYSTEM)){
            return null;
        }

        if (user.getUserType().equals(UserType.SUPERADMIN)){

            Subscriber subscriber = subscriberRepository.findByUser_Id(userId);

            if (subscriber == null){
                return null;
            }
            return getSelected(subscriber.getId());
        }

        if (user.getUserType().equals(UserType.ADMIN)){

            User superAdmin = storeUserInfoRepository.findSuperAdminByStoreInfo(user.getStoreInfo().getId());

            Subscriber subscriber = subscriberRepository.findByUser_Id(superAdmin.getId());

            return getSelected(subscriber.getId());
        }

        if (user.getUserType().equals(UserType.USER)){

            User superAdmin = storeUserInfoRepository.findSuperAdminByStoreInfo(user.getStoreInfo().getId());

            Subscriber subscriber = subscriberRepository.findByUser_Id(superAdmin.getId());

            return getSelected(subscriber.getId());
        }

        return null;
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

    @Override
    public List<ServiceDTO> listService(Status status, long subscriberId) {
        return serviceConverter.convertToDtoList(subscriberServiceRepository.findAllServiceByStatusAndSubscriber_Id(Status.ACTIVE , subscriberId));
    }

    private Date getExpireDate(int days) throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Calendar c = Calendar.getInstance();
        c.setTime(new Date()); // Now use today date.
        c.add(Calendar.DATE, days); // Adding 5 days
        String output = sdf.format(c.getTime());

        Date expireDate = sdf.parse(output);

        return expireDate;
    }

    private Date calculateExpiryDate(int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Timestamp(cal.getTime().getTime()));
        cal.add(Calendar.DATE, days);
        return new Date(cal.getTime().getTime());
    }
}
