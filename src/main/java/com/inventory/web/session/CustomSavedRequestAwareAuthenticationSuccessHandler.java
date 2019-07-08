package com.inventory.web.session;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomSavedRequestAwareAuthenticationSuccessHandler extends
        SimpleUrlAuthenticationSuccessHandler {

    protected final Log logger = LogFactory.getLog(this.getClass());

    private RequestCache requestCache = null;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {

        logger.debug("success handler");
        requestCache = RequestCacheUtil.get();

        String defaultURL = request.getContextPath()  + "/dashboard";

        SavedRequest savedRequest = requestCache.getRequest(request, response);

        if (savedRequest == null) {

            clearAuthenticationAttributes(request);

            logger.debug("success handler redirects defaulturl " + defaultURL);

            getRedirectStrategy().sendRedirect(request, response, defaultURL);

            return;
        }

        String targetUrlParameter = getTargetUrlParameter();

        if (isAlwaysUseDefaultTargetUrl()
                || (targetUrlParameter != null && StringUtils.hasText(request
                .getParameter(targetUrlParameter)))) {

            RequestCacheUtil.removeRequest(request, response);

            clearAuthenticationAttributes(request);

            getRedirectStrategy().sendRedirect(request, response, defaultURL);

            return;
        }

        String targetUrl = savedRequest.getRedirectUrl();

        RequestCacheUtil.removeRequest(request, response);

        logger.debug("success handler redirects cached url  " + targetUrl);

        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    public void setRequestCache(RequestCache requestCache) {
        this.requestCache = requestCache;
    }

}
