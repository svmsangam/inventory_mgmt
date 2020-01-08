package com.inventory.web.controller.ajax;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.inventory.core.api.iapi.ITagInfoApi;
import com.inventory.core.api.iapi.IUserApi;
import com.inventory.core.model.dto.InvUserDTO;
import com.inventory.core.model.dto.RestResponseDTO;
import com.inventory.core.model.dto.TagInfoDTO;
import com.inventory.core.model.enumconstant.Permission;
import com.inventory.core.model.enumconstant.ResponseStatus;
import com.inventory.core.model.enumconstant.Status;
import com.inventory.core.util.Authorities;
import com.inventory.core.validation.TagInfoValidation;
import com.inventory.web.error.TagInfoError;
import com.inventory.web.util.AuthenticationUtil;
import com.inventory.web.util.LoggerUtil;

@RestController
@RequestMapping("/api/tag")
public class TagAjaxController {

	@Autowired
	private ITagInfoApi tagInfoApi;

	@Autowired
	private TagInfoValidation tagInfoValidation;

	@Autowired
	private IUserApi userApi;

	@PostMapping("/save")
	@PreAuthorize("hasAnyRole('ROLE_SUPERADMINISTRATOR','ROLE_ADMINISTRATOR','ROLE_USER,ROLE_AUTHENTICATED')")
	public ResponseEntity<RestResponseDTO> save(@RequestParam("name") String name, @RequestParam("code") String code,
			HttpServletRequest request) {

		RestResponseDTO result = new RestResponseDTO();

		try {

			/* current user checking start */
			InvUserDTO currentUser = AuthenticationUtil.getCurrentUser(userApi);

			if (currentUser.getUserauthority().contains(Authorities.USER)
					& !AuthenticationUtil.checkPermission(currentUser, Permission.TAG_CREATE)) {
				request.getSession().invalidate();
				result.setStatus(ResponseStatus.FAILURE.getValue());
				result.setMessage("access denied");
				return new ResponseEntity<RestResponseDTO>(result, HttpStatus.OK);
			}

			if (currentUser.getStoreId() == null) {
				result.setStatus(ResponseStatus.FAILURE.getValue());
				result.setMessage("Store not assigned for you");
				return new ResponseEntity<RestResponseDTO>(result, HttpStatus.OK);
			}

			/* current user checking end */

			TagInfoDTO tagInfoDTO = new TagInfoDTO();

			tagInfoDTO.setCode(code);
			tagInfoDTO.setName(name);

			synchronized (this.getClass()) {
				tagInfoDTO.setStoreInfoId(currentUser.getStoreId());
				tagInfoDTO.setCreatedById(currentUser.getUserId());

				TagInfoError error = tagInfoValidation.onSave(tagInfoDTO, null);

				if (!error.isValid()) {
					result.setStatus(ResponseStatus.VALIDATION_FAILED.getValue());
					result.setMessage("Validation failure");
					result.setDetail(error);
					return new ResponseEntity<RestResponseDTO>(result, HttpStatus.OK);
				}

				tagInfoApi.save(tagInfoDTO);

				result.setStatus(ResponseStatus.SUCCESS.getValue());
				result.setMessage("tag successfully saved");

			}

		} catch (Exception e) {
			LoggerUtil.logException(this.getClass(), e);
			result.setStatus(ResponseStatus.FAILURE.getValue());
			result.setMessage("internal server error");
		}

		return new ResponseEntity<RestResponseDTO>(result, HttpStatus.OK);

	}

	@GetMapping(value = "/search")
	@PreAuthorize("hasAnyRole('ROLE_SUPERADMINISTRATOR','ROLE_ADMINISTRATOR','ROLE_USER,ROLE_AUTHENTICATED')")
	@ResponseBody
	public ResponseEntity<RestResponseDTO> search(@RequestParam("term") String term, HttpServletRequest request) {
		RestResponseDTO result = new RestResponseDTO();

		try {

			InvUserDTO currentUser = AuthenticationUtil.getCurrentUser(userApi);
			result.setStatus(ResponseStatus.SUCCESS.getValue());
			result.setMessage("store successfully saved");
			result.setDetail(tagInfoApi.search(Status.ACTIVE, term, 0, 50, currentUser.getStoreId()));

		} catch (Exception e) {
			LoggerUtil.logException(this.getClass(), e);
			result.setStatus(ResponseStatus.FAILURE.getValue());
			result.setMessage("internal server error");
		}

		return new ResponseEntity<RestResponseDTO>(result, HttpStatus.OK);
	}
}
