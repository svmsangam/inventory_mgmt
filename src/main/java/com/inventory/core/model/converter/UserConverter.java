package com.inventory.core.model.converter;

import com.inventory.core.model.dto.InvUserDTO;
import com.inventory.core.model.entity.User;
import com.inventory.core.model.enumconstant.Status;
import com.inventory.core.model.enumconstant.UserType;
import com.inventory.core.model.repository.StoreInfoRepository;
import com.inventory.core.util.Authorities;
import com.inventory.core.util.IConvertable;
import com.inventory.core.util.IListConvertable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author manohar
 *
 */
@Service
public class UserConverter implements IConvertable<User, InvUserDTO>, IListConvertable<User, InvUserDTO> {

	@Autowired
	private StoreInfoRepository storeInfoRepository;

	@Override
	public User convertToEntity(InvUserDTO dto) {

		return copyConvertToEntity(dto , new User());
	}

	@Override
	public InvUserDTO convertToDto(User entity) {

		if (entity == null) {
			return null;
		}

		InvUserDTO dto = new InvUserDTO();

		dto.setInventoryuser(entity.getUsername());
		dto.setUserauthority(entity.getAuthority());
		dto.setUserstatus(entity.getStatus());
		dto.setUserType(entity.getUserType());
		dto.setUserId(entity.getId());
		dto.setEnable(entity.getEnabled());

		return dto;
	}

	@Override
	public User copyConvertToEntity(InvUserDTO dto , User entity) {

		if (dto == null | entity == null){
			return null;
		}

		entity.setUsername(dto.getInventoryuser());
		entity.setStatus(Status.ACTIVE);
		entity.setEnabled(dto.getEnable());
		if (dto.getStoreId() != null) {
			entity.setStoreInfo(storeInfoRepository.findOne(dto.getStoreId()));
		}
		entity.setPassword(dto.getUserpassword());
		entity.setUserType(dto.getUserType());
		entity.setEnabled(true);

		if (dto.getUserType().equals(UserType.ADMIN)) {
			entity.setAuthority(Authorities.ADMINISTRATOR + "," + Authorities.AUTHENTICATED);
		}else if (dto.getUserType().equals(UserType.SUPERADMIN)) {
			entity.setAuthority(Authorities.SUPERADMIN + "," + Authorities.AUTHENTICATED);
		} else if (dto.getUserType().equals(UserType.SYSTEM)) {
			entity.setAuthority(Authorities.SYSTEM + "," + Authorities.AUTHENTICATED);
		}else if (dto.getUserType().equals(UserType.USER)) {
			entity.setAuthority(Authorities.USER + "," + Authorities.AUTHENTICATED);
		}else if (dto.getUserType().equals(UserType.GUEST)) {
			entity.setAuthority(Authorities.GUEST + "," + Authorities.AUTHENTICATED);
		}


		return entity;
	}

	@Override
	public List<InvUserDTO> convertToDtoList(List<User> entities) {
		return entities.parallelStream().map(this::convertToDto).collect(Collectors.toList());
	}

	@Override
	public List<User> convertToEntityList(List<InvUserDTO> dtoList) {
		return dtoList.parallelStream().map(this::convertToEntity).collect(Collectors.toList());
	}

}
