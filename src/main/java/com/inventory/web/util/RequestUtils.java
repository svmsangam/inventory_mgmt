package com.inventory.web.util;

import javax.servlet.http.HttpServletRequest;

public class RequestUtils {

    public static String getServerUlr(HttpServletRequest request){

        if (request == null){
            return "";
        }

        return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
    }
}
