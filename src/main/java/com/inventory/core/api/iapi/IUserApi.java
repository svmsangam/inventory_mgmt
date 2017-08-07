package com.inventory.core.api.iapi;

import com.inventory.core.model.dto.InvUserDTO;
import com.inventory.core.model.entity.User;
import com.inventory.web.util.ClientException;
import org.json.JSONException;

import java.io.IOException;
import java.util.List;


public interface IUserApi {

	InvUserDTO save(InvUserDTO userDTO) throws IOException, JSONException;

	List<InvUserDTO> getAllUser();

	InvUserDTO getUserWithId(long userId);
	
	User getUser(long userId);

	List<InvUserDTO> findUser();

	List<InvUserDTO> findAllUserExceptAdmin();

	List<InvUserDTO> findAllUserExceptDefaultAdmin();

	void editUser(InvUserDTO userDTO, String status) throws IOException, JSONException;

	void deleteUser(Long parseLong);

	String generateSecretKey(String clientId, String accessKey) throws ClientException;

	void changePassword(long userId,String newPassword) throws IOException , JSONException;
	
	InvUserDTO getUserByUserName(String userName);

	User saveUserABC(User user);

	boolean nameExists(String userName);
	
}
