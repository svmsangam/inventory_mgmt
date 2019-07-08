package com.inventory.web.controller.ajax;

import com.inventory.core.api.iapi.*;
import com.inventory.core.model.dto.InvUserDTO;
import com.inventory.core.model.dto.InvoiceInfoDTO;
import com.inventory.core.model.dto.InvoiceListDTO;
import com.inventory.core.model.dto.RestResponseDTO;
import com.inventory.core.model.enumconstant.Permission;
import com.inventory.core.model.enumconstant.ResponseStatus;
import com.inventory.core.model.enumconstant.Status;
import com.inventory.web.util.AuthenticationUtil;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dhiraj on 1/31/18.
 */
@Controller
@RequestMapping("invoice/ajax")
public class InvoiceAjaxController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private IInvoiceInfoApi invoiceInfoApi;

    @Autowired
    private IUserApi userApi;

    @Autowired
    private IAuthenticationService authenticationService;

    @Autowired
    private IFiscalYearInfoApi fiscalYearInfoApi;
    
    @GetMapping(value = "list")
    public ResponseEntity<Map<String, List<InvoiceListDTO>>> list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "length", required = false) int length, @RequestParam(value = "direction", required = false) String direction , HttpServletRequest request , HttpServletResponse response) {

        RestResponseDTO result = new RestResponseDTO();

        try {

            InvUserDTO currentUser = AuthenticationUtil.getCurrentUser(userApi);

            result = authenticationService.auth_Rest_Controller_Store_All_User(currentUser, result);

            if (!result.getStatus().equals(ResponseStatus.SUCCESS.getValue())) {

                result.setLength(0);
                return null;
            }

            result = authenticationService.auth_Rest_Controller_Check_Permission(currentUser, result, Permission.INVOICE_VIEW);

            if (!result.getStatus().equals(ResponseStatus.SUCCESS.getValue())) {

                result.setLength(0);
                return null;
            }

            if (page == null) {
                page = 1;
            }

            if (page < 1) {
                page = 1;
            }

            int currentpage = page - 1;

            result.setStatus(ResponseStatus.SUCCESS.getValue());
            result.setDetail(invoiceInfoApi.list(Status.ACTIVE, currentUser.getStoreId(), currentpage, 10));
            result.setLength(50);

            List<InvoiceListDTO>  jsonData = invoiceInfoApi.listToJson(Status.ACTIVE, currentUser.getStoreId(), currentpage, 10);

            Map<String, List<InvoiceListDTO>> json = new HashMap<String, List<InvoiceListDTO>>();
            json.put("data", jsonData);

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", "application/json; charset=UTF-8");
          /*  headers.add("X-Fsl-Location", "/");
            headers.add("X-Fsl-Response-Code", "302");*/

            return new ResponseEntity<Map<String, List<InvoiceListDTO>>>( json, headers, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Stack trace: " + e.getStackTrace());
            result.setStatus(ResponseStatus.FAILURE.getValue());
            result.setMessage("internal server error");
            result.setLength(0);
        }

        return null;
    }

}
