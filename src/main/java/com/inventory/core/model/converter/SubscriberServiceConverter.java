package com.inventory.core.model.converter;

import com.inventory.core.model.dto.SubscriberServiceDTO;
import com.inventory.core.model.entity.SubscriberService;
import com.inventory.core.model.enumconstant.Status;
import com.inventory.core.model.repository.ServiceRepository;
import com.inventory.core.model.repository.SubscriberRepository;
import com.inventory.core.util.IConvertable;
import com.inventory.core.util.IListConvertable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by dhiraj on 1/26/18.
 */

@Service
public class SubscriberServiceConverter implements IListConvertable<SubscriberService , SubscriberServiceDTO> , IConvertable<SubscriberService , SubscriberServiceDTO>{

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private SubscriberRepository subscriberRepository;

    @Autowired
    private ServiceConverter serviceConverter;

    @Override
    public SubscriberService convertToEntity(SubscriberServiceDTO dto) {
        return null;
    }

    public SubscriberService convertToEntity(long serviceId , long subscriberId) throws ParseException {

        SubscriberService subscriberService = new SubscriberService();

        subscriberService.setSelected(true);
        subscriberService.setServiceInfo(serviceRepository.findById(serviceId));
        subscriberService.setExpireOn(calculateExpiryDate(subscriberService.getServiceInfo().getExpireDays()));
        subscriberService.setStatus(Status.ACTIVE);
        subscriberService.setSubscriber(subscriberRepository.findById(subscriberId));

        return subscriberService;

    }

    private Date calculateExpiryDate(int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Timestamp(cal.getTime().getTime()));
        cal.add(Calendar.DATE, days);
        return new Date(cal.getTime().getTime());
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

    @Override
    public SubscriberServiceDTO convertToDto(SubscriberService entity) {

        if (entity == null) {
            return null;
        }

        SubscriberServiceDTO dto = new SubscriberServiceDTO();

        dto.setSelected(entity.isSelected());
        dto.setServiceInfo(serviceConverter.convertToDto(entity.getServiceInfo()));
        dto.setExpireOn(entity.getExpireOn());
        dto.setStatus(entity.getStatus());

        if (new Date().equals(entity.getExpireOn())){
            dto.setExpired(true);
        }else if (new Date().before(entity.getExpireOn())){
            dto.setExpired(true);
        }else {
            dto.setExpired(false);
        }

        return dto;
    }

    @Override
    public SubscriberService copyConvertToEntity(SubscriberServiceDTO dto, SubscriberService entity) {
        return null;
    }

    @Override
    public List<SubscriberServiceDTO> convertToDtoList(List<SubscriberService> entities) {
        return entities.parallelStream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public List<SubscriberService> convertToEntityList(List<SubscriberServiceDTO> dtoList) {
        return null;
    }
}
