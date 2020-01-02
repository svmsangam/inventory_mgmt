package com.inventory.web.session;

import com.inventory.core.api.iapi.ISessionApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.authentication.session.ConcurrentSessionControlStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;

import java.util.List;

public class PersistingConcurrentSessionControlStrategy extends ConcurrentSessionControlStrategy {

	private ISessionApi sessionApi;


	public PersistingConcurrentSessionControlStrategy(SessionRegistry sessionRegistry, ISessionApi sessionApi) {
		super(sessionRegistry);
		this.sessionApi = sessionApi;
	}

	@Override
	protected void allowableSessionsExceeded(List<SessionInformation> sessions, int allowableSessions, SessionRegistry registry) throws SessionAuthenticationException {
		logger.debug("sessions==>"+sessions);
		logger.debug("allowableSessions==>"+allowableSessions);
		logger.debug("registry==>"+registry);
		if (sessions == null) {
			throw new SessionAuthenticationException(messages.getMessage("ConcurrentSessionControlStrategy.exceededAllowed",
					new Object[] { Integer.valueOf(allowableSessions) },
					"Maximum sessions of {0} for this principal exceeded"));
		}
		SessionInformation leastRecentlyUsed = null;
		for (SessionInformation session : sessions) {
			if ((leastRecentlyUsed == null)
					|| session.getLastRequest().before(leastRecentlyUsed.getLastRequest())) {
				leastRecentlyUsed = session;
			}
		}
		logger.debug("leastRecentlyUsed==>"+leastRecentlyUsed);
		sessionApi.expireSession(leastRecentlyUsed.getSessionId());
	}

}
