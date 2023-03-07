package com.inventory.web.controller;

import com.inventory.core.api.iapi.*;
import com.inventory.core.model.dto.InvUserDTO;
import com.inventory.core.model.enumconstant.SalesOrderStatus;
import com.inventory.core.model.enumconstant.Status;
import com.inventory.core.model.enumconstant.UserType;
import com.inventory.core.util.Authorities;
import com.inventory.web.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

@Controller
public class HomeController {

    @Autowired
    private IUserApi userApi;

    @Autowired
    private IStockInfoApi stockInfoApi;

    @Autowired
    private IInvoiceInfoApi invoiceInfoApi;

    @Autowired
    private IOrderInfoApi orderInfoApi;

    @Autowired
    private IPaymentInfoApi paymentInfoApi;

    @Autowired
    private IStoreUserInfoApi storeUserInfoApi;

    @Autowired
    private ISendMailSSL sendMailSSL;




    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String getMainPage(@RequestParam(value = "message" , required = false)String message , RedirectAttributes redirectAttributes) throws IOException {

        if (AuthenticationUtil.getCurrentUser() == null) {
            return "dashboard/login";
        }

        redirectAttributes.addFlashAttribute(ParameterConstants.PARAM_MESSAGE, message);
        return "redirect:/dashboard";

    }


    //for business owner

    @GetMapping(value = "/dashboard")
    public String getDashboard(RedirectAttributes redirectAttributes , ModelMap modelMap) throws IOException {

        InvUserDTO currentUser = AuthenticationUtil.getCurrentUser(userApi);

        if (currentUser == null) {
            redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Athentication failed");
            return "redirect:/";
        }

        if (!currentUser.getUserType().equals(UserType.SYSTEM) && currentUser.getStoreId() == null) {
            redirectAttributes.addFlashAttribute(StringConstants.INFO, UIUtil.addStoreMessage());
            return "redirect:/store/list";//store not assigned page
        }

        if ((currentUser.getUserauthority().contains(Authorities.SUPERADMIN) && currentUser.getUserauthority().contains(Authorities.AUTHENTICATED)) | (currentUser.getUserauthority().contains(Authorities.ADMINISTRATOR) && currentUser.getUserauthority().contains(Authorities.AUTHENTICATED))) {

            modelMap.put(StringConstants.TOTALSTOCK , stockInfoApi.getTotalStockByStoreInfoAndStatus(currentUser.getStoreId() , Status.ACTIVE));
            modelMap.put(StringConstants.TOTALSALE , invoiceInfoApi.getTotalAmountByStoreInfoAndStatus(currentUser.getStoreId() , Status.ACTIVE));
            modelMap.put(StringConstants.TOTALUSER , userApi.getTotalUserByStoreInfoAndStatus(currentUser.getStoreId() , Status.ACTIVE));

            modelMap.put(StringConstants.ORDER_LIST , orderInfoApi.listTopSale(Status.ACTIVE , currentUser.getStoreId() , 0 , 8));
            modelMap.put(StringConstants.INVOICE_LIST , invoiceInfoApi.listTopReceivable(Status.ACTIVE  , currentUser.getStoreId() , 0 , 5));

            modelMap.put(StringConstants.TOTALPENDINGSALE , orderInfoApi.countSaleByStatusAndStoreInfoAndSaleTrack(Status.ACTIVE , currentUser.getStoreId() , SalesOrderStatus.PENDDING));
            modelMap.put(StringConstants.TOTALACCEPTEDGSALE , orderInfoApi.countSaleByStatusAndStoreInfoAndSaleTrack(Status.ACTIVE , currentUser.getStoreId() , SalesOrderStatus.ACCEPTED));
            modelMap.put(StringConstants.TOTALPACKEDSALE , orderInfoApi.countSaleByStatusAndStoreInfoAndSaleTrack(Status.ACTIVE , currentUser.getStoreId() , SalesOrderStatus.PACKED));
            modelMap.put(StringConstants.TOTALSHIPEDSALE , orderInfoApi.countSaleByStatusAndStoreInfoAndSaleTrack(Status.ACTIVE , currentUser.getStoreId() , SalesOrderStatus.SHIPPED));
            modelMap.put(StringConstants.TOTALCANCELEDSALE , orderInfoApi.countSaleByStatusAndStoreInfoAndSaleTrack(Status.ACTIVE , currentUser.getStoreId() , SalesOrderStatus.CANCEL));

            modelMap.put(StringConstants.TOTALPAYMENT , paymentInfoApi.getTotalPaymentByStoreInfoAndStatus(currentUser.getStoreId() , Status.ACTIVE));
            modelMap.put(StringConstants.TODAYTOTALPAYMENT , paymentInfoApi.getToDayTotalPaymentByStoreInfoAndStatus(currentUser.getStoreId() , Status.ACTIVE));
            modelMap.put(StringConstants.TOTALRECEIVABLE , invoiceInfoApi.getTotalReceivableByStoreInfoAndStatus(currentUser.getStoreId() , Status.ACTIVE));
            modelMap.put(StringConstants.TODAYTOTALSALE , invoiceInfoApi.getToDayTotalAmountByStoreInfoAndStatus(currentUser.getStoreId() , Status.ACTIVE));


            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            int year = calendar.get(Calendar.YEAR);

            modelMap.put(StringConstants.CART_SALE_DATA , invoiceInfoApi.getTotalSellOfYearByStore(currentUser.getStoreId() , year));

            modelMap.put(StringConstants.STORE , currentUser.getStoreName());

            modelMap.put(StringConstants.STORE_LIST , storeUserInfoApi.getAllStoreByUser(currentUser.getUserId()));

        }


        return "dashboard/index";


    }

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String getLogin(@RequestParam(value = "error", required = false) Boolean error, HttpServletRequest request, ModelMap modelMap) throws IOException {

        if (error == null){
            error = false;
        }

        if (error) {
            modelMap.put(StringConstants.ERROR, "wrong username or password");
        }
        return "dashboard/login";
    }

    @RequestMapping(value = "/login/{error}", method = RequestMethod.GET)
    public String getLoginWithError(@PathVariable(value = "error") String error, HttpServletRequest request, ModelMap modelMap) throws IOException {

        error = error.replace("_" , " ");

        modelMap.put(StringConstants.ERROR, error);

        return "dashboard/login";
    }

    @RequestMapping(value = "/404", method = RequestMethod.GET)
    public String error(HttpServletRequest request) {

        return "static/404";
    }

    @RequestMapping(value = "/400", method = RequestMethod.GET)
    public String dataNotFound(HttpServletRequest request) {

        return "static/400";
    }

    @RequestMapping(value = "/401", method = RequestMethod.GET)
    public String accessDeniled(HttpServletRequest request) {

        return "static/401";
    }

    @RequestMapping(value = "/500", method = RequestMethod.GET)
    public String errorpage(HttpServletRequest request) {

        return "static/500";
    }

    @RequestMapping(value = "/testException", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_SYSTEM')")
    public String testExcetion(HttpServletRequest request) {

        LoggerUtil.logException(this.getClass(),  new Exception("test Excetion"));
        return "static/500";
    }

}
