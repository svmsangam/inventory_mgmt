package com.inventory.core.api.iapi;

import com.inventory.core.model.dto.SubscriberServiceDTO;
import com.inventory.core.model.enumconstant.Status;

import java.text.ParseException;
import java.util.List;

/**
 * Created by dhiraj on 1/26/18.
 */
public interface ISubscriberServiceApi {

    void save(long serviceId , long subscriberId) throws ParseException;

    SubscriberServiceDTO getSelected(long subscriberId);

    SubscriberServiceDTO getSelectedByUserId(long userId);

    List<SubscriberServiceDTO> list(Status status, long subscriberId);
}
