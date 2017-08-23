package com.inventory.web.controller;

import com.inventory.core.api.iapi.IUserApi;
import com.inventory.core.model.dto.InvUserDTO;
import com.inventory.web.util.AuthenticationUtil;
import com.inventory.web.util.ParameterConstants;
import com.inventory.web.util.StringConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Controller
public class HomeController {

    @Autowired
    private IUserApi userApi;

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String toTestJspPage(Model model, HttpServletRequest request, RedirectAttributes redirectAttributes) throws IOException {

        return "tag/add";
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String getMainPage(HttpServletRequest request, @ModelAttribute("message") String message, RedirectAttributes redirectAttributes) throws IOException {

        if (AuthenticationUtil.getCurrentUser(userApi) == null) {
            //redirectAttributes.addFlashAttribute(StringConstants.ERROR , "Athentication failed");
            return "dashboard/login";
        }

        redirectAttributes.addFlashAttribute(ParameterConstants.PARAM_MESSAGE, message);
        return "redirect:/dashboard";

    }


    //for business owner

    @RequestMapping(value = "/dashboard", method = RequestMethod.GET)
    public String getDashboard(Model model, HttpServletRequest request, RedirectAttributes redirectAttributes) throws IOException {

        InvUserDTO currentUser = AuthenticationUtil.getCurrentUser(userApi);

        if (currentUser == null) {
            redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Athentication failed");
            return "redirect:/logout";
        }

        return "dashboard/index";


    }

    @RequestMapping(value = "admin/charts", method = RequestMethod.GET)
    public String getChart(HttpServletRequest request) throws IOException {

        return "dashboard/chart";
    }

    @RequestMapping(value = "admin/icons", method = RequestMethod.GET)
    public String getIcons(HttpServletRequest request) throws IOException {

        return "dashboard/icons";
    }

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String getLogin(@RequestParam(value = "error", required = false) Boolean error, HttpServletRequest request, ModelMap modelMap) throws IOException {

        if (error) {
            modelMap.put(StringConstants.ERROR, "wrong username or password");
        }
        return "dashboard/login";
    }

    @RequestMapping(value = "admin/panels", method = RequestMethod.GET)
    public String getPanel(HttpServletRequest request) throws IOException {

        return "dashboard/panels";
    }

    @RequestMapping(value = "admin/tables", method = RequestMethod.GET)
    public String getTable(HttpServletRequest request) throws IOException {

        return "dashboard/tables";
    }

    @RequestMapping(value = "admin/widgets", method = RequestMethod.GET)
    public String getWidget(HttpServletRequest request) throws IOException {

        return "dashboard/widgets";
    }

    @RequestMapping(value = "admin/forms", method = RequestMethod.GET)
    public String getForms(HttpServletRequest request) throws IOException {
        return "dashboard/forms";
    }

    @RequestMapping(value = "404")
    public String get404() {
        return "404";
    }

    @RequestMapping(value = "/404", method = RequestMethod.GET)
    public String error(HttpServletRequest request) {

        return "static/404";
    }

    @RequestMapping(value = "/500", method = RequestMethod.GET)
    public String errorpage(HttpServletRequest request) {

        return "static/500";
    }

}
