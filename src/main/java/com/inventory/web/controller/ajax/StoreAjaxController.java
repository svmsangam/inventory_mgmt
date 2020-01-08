package com.inventory.web.controller.ajax;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.inventory.core.api.iapi.IStoreInfoApi;
import com.inventory.core.api.iapi.IStoreUserInfoApi;
import com.inventory.core.api.iapi.IUserApi;
import com.inventory.core.model.dto.InvUserDTO;
import com.inventory.core.model.dto.RestResponseDTO;
import com.inventory.core.model.dto.StoreInfoDTO;
import com.inventory.core.model.dto.StoreUserInfoDTO;
import com.inventory.core.model.enumconstant.ResponseStatus;
import com.inventory.core.model.enumconstant.Status;
import com.inventory.core.validation.StoreInfoValidation;
import com.inventory.web.error.StoreInfoError;
import com.inventory.web.util.AuthenticationUtil;
import com.inventory.web.util.LoggerUtil;

/**
 * Created by dhiraj on 8/14/17.
 */
@Controller
@RequestMapping("store")
@ResponseBody
public class StoreAjaxController {

	@Autowired
	private IStoreInfoApi storeInfoApi;

	@Autowired
	private StoreInfoValidation storeInfoValidation;

	@Autowired
	private IUserApi userApi;

	@Autowired
	private IStoreUserInfoApi storeUserInfoApi;

	@PostMapping(value = "save", produces = { MediaType.APPLICATION_JSON_VALUE })
	@PreAuthorize("hasRole('ROLE_SUPERADMINISTRATOR')")
	public ResponseEntity<RestResponseDTO> save(@RequestAttribute("store") StoreInfoDTO storeInfoDTO,
			BindingResult bindingResult, HttpServletRequest request) {
		RestResponseDTO result = new RestResponseDTO();

		try {

			InvUserDTO currentUser = AuthenticationUtil.getCurrentUser(userApi);

			synchronized (this.getClass()) {
				StoreInfoError error = new StoreInfoError();

				error = storeInfoValidation.onSave(storeInfoDTO, bindingResult, currentUser.getUserId());

				if (error.isValid()) {

					storeInfoDTO = storeInfoApi.save(storeInfoDTO, currentUser.getUserId());

					if (currentUser.getStoreId() == null) {
						userApi.changeStore(currentUser.getUserId(), storeInfoDTO.getStoreId());
					}

					result.setStatus(ResponseStatus.SUCCESS.getValue());
					result.setMessage("store successfully saved");
					result.setDetail(storeInfoDTO);

				} else {
					result.setStatus(ResponseStatus.VALIDATION_FAILED.getValue());
					result.setMessage("store validation failed");
					result.setDetail(error);
				}
			}

		} catch (Exception e) {
			LoggerUtil.logException(this.getClass(), e);
			e.getStackTrace();
			result.setStatus(ResponseStatus.FAILURE.getValue());
			result.setMessage("internal server error");
		}

		return new ResponseEntity<RestResponseDTO>(result, HttpStatus.OK);
	}

	@GetMapping(value = "/show/{storeId}", produces = { MediaType.APPLICATION_JSON_VALUE })
	@PreAuthorize("hasAnyRole('ROLE_SUPERADMINISTRATOR','ROLE_ADMINISTRATOR')")
	public ResponseEntity<RestResponseDTO> save(@PathVariable("storeId") Long storeId, HttpServletRequest request) {
		RestResponseDTO result = new RestResponseDTO();

		try {

			if (storeId == null) {
				result.setStatus(ResponseStatus.FAILURE.getValue());
				result.setMessage("store not found");
				return new ResponseEntity<RestResponseDTO>(result, HttpStatus.OK);
			}

			if (storeId < 0) {
				result.setStatus(ResponseStatus.FAILURE.getValue());
				result.setMessage("store not found");
				return new ResponseEntity<RestResponseDTO>(result, HttpStatus.OK);
			}

			StoreInfoDTO storeInfoDTO = storeInfoApi.show(storeId, Status.ACTIVE);

			if (storeInfoDTO == null) {
				result.setStatus(ResponseStatus.FAILURE.getValue());
				result.setMessage("store not found");
				return new ResponseEntity<RestResponseDTO>(result, HttpStatus.OK);
			}

			result.setStatus(ResponseStatus.SUCCESS.getValue());
			result.setMessage("store successfully saved");
			result.setDetail(storeInfoDTO);

		} catch (Exception e) {
			LoggerUtil.logException(this.getClass(), e);
			result.setStatus(ResponseStatus.FAILURE.getValue());
			result.setMessage("internal server error");
		}

		return new ResponseEntity<RestResponseDTO>(result, HttpStatus.OK);
	}

	@PostMapping(value = "update", produces = { MediaType.APPLICATION_JSON_VALUE })
	@PreAuthorize("hasRole('ROLE_SUPERADMINISTRATOR')")
	public ResponseEntity<RestResponseDTO> update(@RequestAttribute("store") StoreInfoDTO storeInfoDTO,
			BindingResult bindingResult, HttpServletRequest request) {
		RestResponseDTO result = new RestResponseDTO();

		try {

			InvUserDTO currentUser = AuthenticationUtil.getCurrentUser(userApi);

			StoreInfoError error = new StoreInfoError();

			error = storeInfoValidation.onUpdate(storeInfoDTO, bindingResult);

			if (error.isValid()) {

				storeInfoDTO = storeInfoApi.update(storeInfoDTO);

				if (currentUser.getStoreId() == null) {
					userApi.changeStore(currentUser.getUserId(), storeInfoDTO.getStoreId());
				}

				result.setStatus(ResponseStatus.SUCCESS.getValue());
				result.setMessage("store successfully updated");
				result.setDetail(storeInfoDTO);

			} else {
				result.setStatus(ResponseStatus.VALIDATION_FAILED.getValue());
				result.setMessage("store validation failed");
				result.setDetail(error);
			}

		} catch (Exception e) {
			LoggerUtil.logException(this.getClass(), e);
			result.setStatus(ResponseStatus.FAILURE.getValue());
			result.setMessage("internal server error");
		}

		return new ResponseEntity<RestResponseDTO>(result, HttpStatus.OK);
	}

	@GetMapping(value = "/select", produces = { MediaType.APPLICATION_JSON_VALUE })
	@PreAuthorize("hasRole('ROLE_SUPERADMINISTRATOR')")
	public ResponseEntity<RestResponseDTO> select(@RequestParam("storeId") Long storeId, HttpServletRequest request) {
		RestResponseDTO result = new RestResponseDTO();

		try {

			InvUserDTO currentUser = AuthenticationUtil.getCurrentUser(userApi);
			if (storeId == null) {
				result.setStatus(ResponseStatus.FAILURE.getValue());
				result.setMessage("store not found");
				return new ResponseEntity<RestResponseDTO>(result, HttpStatus.OK);
			}

			if (storeId < 0) {
				result.setStatus(ResponseStatus.FAILURE.getValue());
				result.setMessage("store not found");
				return new ResponseEntity<RestResponseDTO>(result, HttpStatus.OK);
			}

			StoreUserInfoDTO storeUserInfoDTO = storeUserInfoApi.getByUserAndStore(currentUser.getUserId(), storeId);

			if (storeUserInfoDTO == null) {
				result.setStatus(ResponseStatus.FAILURE.getValue());
				result.setMessage("store not found");
				return new ResponseEntity<RestResponseDTO>(result, HttpStatus.OK);
			}

			result.setStatus(ResponseStatus.SUCCESS.getValue());
			result.setMessage("store successfully saved");

			userApi.changeStore(currentUser.getUserId(), storeId);

		} catch (Exception e) {
			e.getStackTrace();
			LoggerUtil.logException(this.getClass(), e);
			result.setStatus(ResponseStatus.FAILURE.getValue());
			result.setMessage("internal server error");
		}

		return new ResponseEntity<RestResponseDTO>(result, HttpStatus.OK);
	}

}
