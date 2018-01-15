package com.inventory.web.controller.ajax;

import com.inventory.core.api.iapi.IOAuthClientApi;
import com.inventory.core.api.iapi.IUserApi;
import com.inventory.core.model.dto.OauthRegisterResponseDTO;
import com.inventory.core.model.dto.RestResponseDTO;
import com.inventory.core.model.enumconstant.ResponseStatus;
import com.inventory.core.util.ConvertUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping(value = "/oauth")
public class OAuthClientController {

	private Logger logger = LoggerFactory.getLogger(OAuthClientController.class);

	@Autowired
	private IOAuthClientApi oauthClientApi;

	@Autowired
	private IUserApi userApi;

	@RequestMapping(value = "/registerClient", method = RequestMethod.GET)
	public ResponseEntity<RestResponseDTO> getClientDetailsForm(HttpServletRequest request,
																HttpServletResponse response, ModelMap modelMap) {
		RestResponseDTO restResponseDTO = new RestResponseDTO();

		OauthRegisterResponseDTO oauthClient = oauthClientApi.registerClient(ConvertUtil.getOrderNo(15));

		restResponseDTO.setStatus(ResponseStatus.SUCCESS.getValue());
		restResponseDTO.setDetail(oauthClient);
		return new ResponseEntity<RestResponseDTO>(restResponseDTO, HttpStatus.OK);

	}


	@RequestMapping(method = RequestMethod.POST, value = "/clientregister", produces = {
			MediaType.APPLICATION_JSON_VALUE })
	ResponseEntity<RestResponseDTO> registerOauthClient(HttpServletRequest request, HttpServletResponse response) {
		RestResponseDTO result = new RestResponseDTO();
		try {

			String redirectUri = request.getParameter("redirect_uri");
			OauthRegisterResponseDTO oauthClient = oauthClientApi.registerClient(redirectUri);

			if (oauthClient != null) {
				result.setStatus(ResponseStatus.SUCCESS.getValue());
				result.setMessage("Client Successfully Registered");
				result.setDetail(oauthClient);
			} else {
				result.setStatus(ResponseStatus.BAD_REQUEST.getValue());
				result.setMessage("Client Not Registered");
			}
			return new ResponseEntity<RestResponseDTO>(result, HttpStatus.OK);
		} catch (Exception e) {
			//BugMail.Bugmail(e);
			result.setStatus(ResponseStatus.INTERNAL_SERVER_ERROR.getValue());
			result.setMessage("Please contact your Administrator.");
			e.printStackTrace();
			return new ResponseEntity<RestResponseDTO>(result, HttpStatus.OK);
		}
	}
	
	

}
