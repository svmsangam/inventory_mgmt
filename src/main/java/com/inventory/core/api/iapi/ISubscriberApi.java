package com.inventory.core.api.iapi;

import com.inventory.core.model.dto.SubscriberDTO;
import com.inventory.core.model.enumconstant.Status;

import java.text.ParseException;
import java.util.List;

/**
 * Created by dhiraj on 1/25/18.
 */
public interface ISubscriberApi {

    SubscriberDTO save(SubscriberDTO subscriberDTO) throws ParseException;

    String register(SubscriberDTO subscriberDTO) throws ParseException;

    SubscriberDTO show(Status status , long subscribId);

    List<SubscriberDTO> list(Status status);
}
