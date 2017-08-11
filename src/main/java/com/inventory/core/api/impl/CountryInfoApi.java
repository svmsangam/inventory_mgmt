package com.inventory.core.api.impl;

import com.inventory.core.api.iapi.ICountryInfoApi;
import com.inventory.core.model.converter.CountryInfoConverter;
import com.inventory.core.model.dto.CountryInfoDTO;
import com.inventory.core.model.entity.CountryInfo;
import com.inventory.core.model.enumconstant.Status;
import com.inventory.core.model.repository.CountryInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by dhiraj on 4/18/17.
 */
@Service
@Transactional
public class CountryInfoApi implements ICountryInfoApi {

    @Autowired
    private CountryInfoConverter countryConverter;

    @Autowired
    private CountryInfoRepository countryRepository;


    @Override
    public CountryInfoDTO save(CountryInfoDTO countryDTO) {

        return countryConverter.convertToDto(countryRepository.save(countryConverter.convertToEntity(countryDTO)));
    }

    @Override
    public CountryInfoDTO update(CountryInfoDTO countryDTO) {

        return countryConverter.convertToDto(countryRepository.save(countryConverter.copyConvertToEntity(countryDTO , countryRepository.findByIdAndStatus(countryDTO.getCountryId() , Status.ACTIVE))));
    }

    @Override
    public void delete(long countryId) {

        CountryInfo country = countryRepository.findByIdAndStatus(countryId, Status.ACTIVE);

        country.setStatus(Status.DELETED);

        countryRepository.save(country);

    }

    @Override
    public CountryInfoDTO show(long countryId) {

        return countryConverter.convertToDto(countryRepository.findByIdAndStatus(countryId , Status.ACTIVE));
    }

    @Override
    public CountryInfoDTO getByName(String name) {
        return countryConverter.convertToDto(countryRepository.findByName(name));
    }

    @Override
    public List<CountryInfoDTO> list() {

        return countryConverter.convertToDtoList(countryRepository.findAllByStatus(Status.ACTIVE));
    }
}
