package com.inventory.core.api.iapi;

import com.inventory.core.model.dto.CityInfoDTO;
import com.inventory.core.model.dto.PageableDTO;
import com.inventory.core.model.enumconstant.Status;

import java.util.List;

public interface ICityInfoApi {

	CityInfoDTO save(CityInfoDTO cityDTO);

	CityInfoDTO update(CityInfoDTO cityDTO);

	void delete(long cityId);

	CityInfoDTO show(long cityId);

	List<CityInfoDTO> list(PageableDTO pageableDTO);

	List<CityInfoDTO> list();

	CityInfoDTO getCityByName(String cityName);
	
	List<CityInfoDTO> getCityByStateId(long id);

	long cityCount(Status status);

}

