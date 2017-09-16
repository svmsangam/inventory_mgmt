package com.inventory.web.session;

import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by dhiraj on 9/16/17.
 */
public class RequestCacheUtil {

    private static RequestCache requestCache = new HttpSessionRequestCache();

    public static void save(HttpServletRequest request , HttpServletResponse response){

        requestCache.saveRequest(request , response);
    }

    public static RequestCache get(){

        return requestCache;
    }

}
