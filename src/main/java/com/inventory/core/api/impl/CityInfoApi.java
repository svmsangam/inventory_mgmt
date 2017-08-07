package com.inventory.core.api.impl;

import com.inventory.core.api.iapi.ICityInfoApi;
import com.inventory.core.model.dto.PageableDTO;
import com.inventory.core.model.entity.CityInfo;
import com.inventory.core.model.repository.CityInfoRepository;
import com.inventory.core.model.converter.CityInfoConverter;
import com.inventory.core.model.dto.CityInfoDTO;
import com.inventory.core.model.enumconstant.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityInfoApi implements ICityInfoApi {

	@Autowired
	private CityInfoRepository cityRepository;

	@Autowired
	private CityInfoConverter cityConverter;

	@Override
	public long cityCount(Status status){

		return cityRepository.countAllByStatus(Status.ACTIVE);
	}

	@Override
	public CityInfoDTO save(CityInfoDTO cityDTO) {

		return cityConverter.convertToDto(cityRepository.save(cityConverter.convertToEntity(cityDTO)));
	}

	@Override
	public CityInfoDTO update(CityInfoDTO cityDTO) {

		CityInfo city = cityRepository.findByIdAndStatus(cityDTO.getCityId() , Status.ACTIVE);

		city = cityConverter.copyConvertToEntity(cityDTO , city);

		return cityConverter.convertToDto(cityRepository.save(city));
	}

	@Override
	public void delete(long cityId) {

		CityInfo city = cityRepository.findByIdAndStatus(cityId , Status.ACTIVE);

		city.setStatus(Status.DELETED);

		cityRepository.save(city);

	}


	public CityInfoDTO show(long cityId) {

		return cityConverter.convertToDto(cityRepository.findByIdAndStatus(cityId , Status.ACTIVE));
	}

	private Pageable createPageRequest(PageableDTO pageableDTO) {
		return new PageRequest(pageableDTO.getPage(), pageableDTO.getSize(), new Sort(pageableDTO.getDirection(), pageableDTO.getProperties()));
	}

	@Override
	public List<CityInfoDTO> list(PageableDTO pageableDTO) {

		Pageable pageable = createPageRequest(pageableDTO);

		return cityConverter.convertToDtoList(cityRepository.findAllByStatus(Status.ACTIVE , pageable));
	}

	@Override
	public List<CityInfoDTO> list() {
		return cityConverter.convertToDtoList(cityRepository.findAllByStatus(Status.ACTIVE));
	}


	@Override
	public CityInfoDTO getCityByName(String cityName) {
		return cityConverter.convertToDto(cityRepository.findByNameAndStatus(cityName , Status.ACTIVE));
	}

	@Override
	public List<CityInfoDTO> getCityByStateId(long stateId) {

		return cityConverter.convertToDtoList(cityRepository.findAllByStatusAndStateInfoId(Status.ACTIVE , stateId));
	}
}
