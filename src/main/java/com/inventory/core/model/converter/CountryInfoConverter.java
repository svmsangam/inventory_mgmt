package com.inventory.core.model.converter;

import com.inventory.core.model.dto.CountryInfoDTO;
import com.inventory.core.model.entity.CountryInfo;
import com.inventory.core.util.IConvertable;
import com.inventory.core.util.IListConvertable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CountryInfoConverter implements IConvertable<CountryInfo, CountryInfoDTO>, IListConvertable<CountryInfo, CountryInfoDTO> {

	@Override
	public CountryInfo convertToEntity(CountryInfoDTO dto) {

		return copyConvertToEntity(dto , new CountryInfo());
	}

	@Override
	public CountryInfoDTO convertToDto(CountryInfo entity) {

		if (entity == null) {
			return null;
		}

		CountryInfoDTO dto = new CountryInfoDTO();

		dto.setCountryId(entity.getId());
		dto.setCountryName(entity.getName());
		dto.setCountryStatus(entity.getStatus());
		dto.setCountryISO(entity.getISO());

		return dto;
	}

	@Override
	public CountryInfo copyConvertToEntity(CountryInfoDTO dto , CountryInfo entity) {

		if (entity == null || dto == null){

			return null;
		}

		entity.setName(dto.getCountryName());
		entity.setISO(dto.getCountryISO());
		entity.setStatus(dto.getCountryStatus());

		return entity;
	}

	@Override
	public List<CountryInfoDTO> convertToDtoList(List<CountryInfo> entities) {
		return entities.parallelStream().map(this::convertToDto).collect(Collectors.toList());
	}

	@Override
	public List<CountryInfo> convertToEntityList(List<CountryInfoDTO> dtoList) {
		return dtoList.parallelStream().filter(Objects::nonNull).map(this::convertToEntity).collect(Collectors.toList());
	}

}
