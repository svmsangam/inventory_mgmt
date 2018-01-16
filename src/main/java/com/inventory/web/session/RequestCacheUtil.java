package com.inventory.web.session;

import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by dhiraj on 9/16/17.
 */
public class RequestCacheUtil {

    private static RequestCache requestCache = null;

    public static void save(HttpServletRequest request , HttpServletResponse response){

        if (requestCache == null){

            requestCache = new HttpSessionRequestCache();
        }

        requestCache.saveRequest(request , response);
    }

    public static RequestCache get(){

        if (requestCache == null){
            return new HttpSessionRequestCache();
        }
        return requestCache;
    }

    public static void removeRequest(HttpServletRequest request , HttpServletResponse response){
        requestCache.removeRequest(request, response);
    }

}
