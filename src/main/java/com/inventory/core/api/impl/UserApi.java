package com.inventory.core.api.impl;

import com.inventory.core.api.iapi.IUserApi;
import com.inventory.core.model.converter.UserConverter;
import com.inventory.core.model.dto.InvUserDTO;
import com.inventory.core.model.entity.User;
import com.inventory.core.model.enumconstant.UserType;
import com.inventory.web.util.ClientException;
import com.inventory.core.model.repository.UserRepository;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Service
public class UserApi implements IUserApi {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private UserConverter userConverter;
	
	@Override
	public User saveUserABC(User user) {
		return userRepository.save(user);
	}

	

	@Override
	public InvUserDTO save(InvUserDTO userDTO) throws IOException, JSONException {

		userDTO.setEnable(false);

		User user = userConverter.convertToEntity(userDTO);

		user.setPassword(passwordEncoder.encode(user.getPassword()));

		user = userRepository.save(user);

		return userConverter.convertToDto(user);
	}

	@Override
	public void editUser(InvUserDTO userDto , String status) throws IOException, JSONException {
		User user = userRepository.findOne(userDto.getUserId());

		userRepository.save(user);

	}

	public void changePassword(long userId, String newPassword) throws IOException, JSONException {
		User user = userRepository.findOne(userId);

		user.setPassword(passwordEncoder.encode(newPassword));

		userRepository.save(user);
	}

	@Override
	public List<InvUserDTO> getAllUser() {
		return null;
	}

	@Override
	public InvUserDTO getUserWithId(long userId) {
		return null;// ConvertUtil.convertUser(userRepository.findOne(userId));

	}
	
	@Override
	public User getUser(long userId) {
		return userRepository.findOne(userId);

	}

	@Override
	public List<InvUserDTO> findAllUserExceptAdmin() {
		return null;

	}

	public List<InvUserDTO> findUser() {
		return null;
	}

	@Override
	public List<InvUserDTO> findAllUserExceptDefaultAdmin() {
		return null;
	}

	@Override
	public void deleteUser(Long userId) {
		userRepository.delete(userId);
	}

	@Override
	public String generateSecretKey(String clientId, String accessKey) throws ClientException {

		return null;
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

}
