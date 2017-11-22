package com.inventory.web.session;

import com.inventory.core.api.iapi.ISessionLogApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.web.authentication.session.ConcurrentSessionControlStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.security.web.session.HttpSessionDestroyedEvent;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SessionLoggingStrategy
		implements SessionAuthenticationStrategy, ApplicationListener<HttpSessionDestroyedEvent> {

	private final ConcurrentSessionControlStrategy concurrentSessionControlStrategy;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private final ISessionLogApi sessionLogApi;

	public SessionLoggingStrategy(ConcurrentSessionControlStrategy concurrentSessionControlStrategy,
			ISessionLogApi sessionLogApi) {
		this.concurrentSessionControlStrategy = concurrentSessionControlStrategy;
		this.sessionLogApi = sessionLogApi;
	}

	@Override
	public void onAuthentication(Authentication authentication, HttpServletRequest request,
                                 HttpServletResponse response) throws SessionAuthenticationException {
		concurrentSessionControlStrategy.onAuthentication(authentication, request, response);
		logger.debug("authentication==>" + authentication);
		logger.debug("user logged in from: " + request.getRemoteAddr());
		Object principal = authentication.getPrincipal();
		// if (principal instanceof User) {
		// User user = (User) principal;
		// sessionLogApi.logUserLoggedIn(user.getId(),
		// request.getSession(false).getId(), request.getRemoteAddr());
		// }
		if (principal instanceof UserDetailsWrapper) {
			sessionLogApi.logUserLoggedIn(((UserDetailsWrapper) principal).getUser().getId(),
					request.getSession(false).getId(), request.getRemoteAddr());
		}
	}

	@Override
	public void onApplicationEvent(HttpSessionDestroyedEvent event) {
		HttpSession session = event.getSession();
		Object context = session.getAttribute("SPRING_SECURITY_CONTEXT");
		if (context instanceof SecurityContext) {
			SecurityContext securityContext = (SecurityContext) context;
			Authentication authentication = securityContext.getAuthentication();
			Object principal = authentication.getPrincipal();
			// if (principal instanceof User) {
			// sessionLogApi.logUserLoggedOut(((User) principal).getId(),
			// event.getSession().getId());
			// }
			if (principal instanceof UserDetailsWrapper) {
				sessionLogApi.logUserLoggedOut(((UserDetailsWrapper) principal).getUser().getId(),
						event.getSession().getId());
			}
		}
	}

}
