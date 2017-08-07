package com.inventory.core.api.iapi;

import com.inventory.core.model.dto.CountryInfoDTO;

import java.util.List;

public interface ICountryInfoApi {

    CountryInfoDTO save(CountryInfoDTO countryDTO);

    CountryInfoDTO update(CountryInfoDTO countryDTO);

    void delete(long countryId);

    CountryInfoDTO show(long countryId);

    CountryInfoDTO getByName(String name);

    List<CountryInfoDTO> list();

}
