package com.inventory.core.validation;

import com.inventory.core.model.repository.CityInfoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by dhiraj on 8/26/17.
 */
@Service
public class ClientInfoValidation {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CityInfoRepository cityInfoRepository;


}
