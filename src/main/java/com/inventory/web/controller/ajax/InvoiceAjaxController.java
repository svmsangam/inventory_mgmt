package com.inventory.web.controller.ajax;

import com.inventory.core.api.iapi.IAuthenticationService;
import com.inventory.core.api.iapi.IFiscalYearInfoApi;
import com.inventory.core.api.iapi.IInvoiceInfoApi;
import com.inventory.core.api.iapi.IUserApi;
import com.inventory.core.model.dto.InvUserDTO;
import com.inventory.core.model.dto.InvoiceListDTO;
import com.inventory.core.model.dto.RestResponseDTO;
import com.inventory.core.model.enumconstant.Permission;
import com.inventory.core.model.enumconstant.ResponseStatus;
import com.inventory.core.model.enumconstant.Status;
import com.inventory.web.util.AuthenticationUtil;
import com.inventory.web.util.LoggerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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



    @Autowired
    private IInvoiceInfoApi invoiceInfoApi;

    @Autowired
    private IUserApi userApi;

    @Autowired
    private IAuthenticationService authenticationService;

    @Autowired
    private IFiscalYearInfoApi fiscalYearInfoApi;
    
    @GetMapping(value = "list")
    @PreAuthorize("hasAnyRole('ROLE_SUPERADMINISTRATOR','ROLE_ADMINISTRATOR','ROLE_USER,ROLE_AUTHENTICATED')")
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
            LoggerUtil.logException(this.getClass() , e);
            result.setStatus(ResponseStatus.FAILURE.getValue());
            result.setMessage("internal server error");
            result.setLength(0);
        }

        return null;
    }

}
