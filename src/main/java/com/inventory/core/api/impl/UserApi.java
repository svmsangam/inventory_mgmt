package com.inventory.core.api.impl;

import com.inventory.core.api.iapi.IUserApi;
import com.inventory.core.model.converter.UserConverter;
import com.inventory.core.model.dto.InvUserDTO;
import com.inventory.core.model.entity.User;
import com.inventory.core.model.enumconstant.Status;
import com.inventory.core.model.enumconstant.UserType;
import com.inventory.core.model.repository.StoreInfoRepository;
import com.inventory.core.model.repository.UserRepository;
import com.inventory.web.session.SessionInfo;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;


@Service
@Transactional
public class UserApi implements IUserApi {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private UserConverter userConverter;

	@Autowired
	private StoreInfoRepository storeInfoRepository;

	@Autowired
	private SessionInfo sessionInfo;

	@Override
	public InvUserDTO save(InvUserDTO userDTO) throws IOException, JSONException {

		userDTO.setEnable(false);

		User user = userConverter.convertToEntity(userDTO);

		user.setPassword(passwordEncoder.encode(user.getPassword()));

		user = userRepository.save(user);

		return userConverter.convertToDto(user);
	}

	public void changePassword(long userId, String newPassword) throws IOException, JSONException {
		User user = userRepository.findOne(userId);

		user.setPassword(passwordEncoder.encode(newPassword));

		userRepository.save(user);
	}

	@Override
	public InvUserDTO getUserWithId(long userId) {
		return userConverter.convertToDto(userRepository.findByIdAndStatus(userId , Status.ACTIVE));

	}

	@Override
	public InvUserDTO updateEnable(long userId) {

		User user = userRepository.findById(userId);

		if (user.getEnabled()){
			user.setEnabled(false);

			sessionInfo.expireUserSessions(user.getUsername());

		}else {
			user.setEnabled(true);
		}

		return userConverter.convertToDto(userRepository.save(user));
	}

	@Override
	public InvUserDTO getUserByUserName(String userName) {

		User user = userRepository.findByUsername(userName);

		return userConverter.convertToDto(user);
	}

	@Override
	public boolean nameExists(String userName) {
		return userRepository.findByUsername(userName) != null;
	}

	@Override
	public InvUserDTO changeStore(long userId, long storeId) {

		User user = userRepository.findById(userId);

		user.setStoreInfo(storeInfoRepository.findById(storeId));

		return userConverter.convertToDto(userRepository.save(user));
	}

	@Override
	public List<InvUserDTO> getAllByStatusAndUserTypeIn(Status status , List<UserType> userTypeList) {
		return userConverter.convertToDtoList(userRepository.findAllByStatusAndUserTypeIn(status , userTypeList));
	}

	@Override
	public List<InvUserDTO> getAllByStatusAndUserTypeInAndStoreInfo(Status status ,List<UserType> userTypeList, long storeInfoId) {
		return userConverter.convertToDtoList(userRepository.findAllByStatusAndUserTypeInAndStoreInfo(status , userTypeList , storeInfoId));
	}

}
