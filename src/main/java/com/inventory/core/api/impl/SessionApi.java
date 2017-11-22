package com.inventory.core.api.impl;

import com.inventory.core.api.iapi.ISessionApi;
import com.inventory.core.model.dto.UserSessionDTO;
import com.inventory.core.model.entity.User;
import com.inventory.core.model.entity.UserSession;
import com.inventory.core.model.repository.UserRepository;
import com.inventory.core.model.repository.UserSessionRepository;
import com.inventory.core.util.ConvertUtil;
import com.inventory.web.session.UserDetailsWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class SessionApi implements ISessionApi, InitializingBean {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private UserSessionRepository userSessionRepository;
	
	@Autowired
	private UserRepository userRepository;

	//in minutes
	@Value("0")
	private int sessionExpirtyTime;

	/*public SessionApi(UserSessionRepository userSessionRepository, UserRepository userRepository, int sessionExpirtyTime) {
		this.userSessionRepository = userSessionRepository;
		this.userRepository = userRepository;
		this.sessionExpirtyTime = sessionExpirtyTime;

	}*/

	@Override
	public void registerNewSession(String sessionId, UserDetailsWrapper principal) {

		UserSession userSession = userSessionRepository.findBySessionId(sessionId);

		if (userSession == null) {
			userSession = new UserSession();
			userSession.setSessionId(sessionId);
		}
		
		userSession.setUser(userRepository.findByUsername(principal.getUsername()));
		userSession.setLastRequest(new Date());

		userSessionRepository.save(userSession);
	}

	@Override
	public void removeSession(String tokenKey) {

		UserSession userSession = userSessionRepository.findBySessionId(tokenKey);

		if (userSession != null) {
			userSessionRepository.delete(userSession);
		}
	}

	@Override
	public UserSession getUserSession(String sessionId) {
		return userSessionRepository.findBySessionId(sessionId);
	}

	@Override
	public void refreshSession(String sessionId) {
		userSessionRepository.refreshSession(sessionId);
	}

	@Override
	public List<UserSession> getAllUserSession(long userId, boolean includeExpiredSessions) {

		if (includeExpiredSessions) {
			return userSessionRepository.getUserSessionsIncludingExpired(userId);
		}
		return userSessionRepository.getUserSessions(userId);
	}

	@Override
	public void afterPropertiesSet() throws Exception {

		Calendar c = Calendar.getInstance();

		c.setTime(new Date());
		c.add(Calendar.MINUTE, -sessionExpirtyTime);

		userSessionRepository.deleteExpiredSessions(c.getTime());

	}

	@Override
	public void expireSession(String sessionId) {

		UserSession session = getUserSession(sessionId);

		session.setExpired(true);

		userSessionRepository.save(session);
	}

	@Override
	public long countActiveSessions() {

		return userSessionRepository.countActiveSessions();
	}

	@Override
	public Page<User> findOnlineUsers(Pageable page) {

		return null ; //userSessionRepository.findActiveUsers(page);
	}

	@Override
	public Page<UserSession> findActiveSessions(Pageable page) {

		return userSessionRepository.findActiveSessions(page);
	}

	@Override
	public void registerNewSession(String sessionId, User principal) {

		UserSession userSession = userSessionRepository.findBySessionId(sessionId);

		if (userSession == null) {
			userSession = new UserSession();
			userSession.setSessionId(sessionId);
		}
		userSession.setUser(userRepository.findByUsername(principal.getUsername()));
		userSession.setLastRequest(new Date());

		userSessionRepository.save(userSession);
	}
	
	@Override
	public List<UserSessionDTO> getAllActiveUser(){

		List<UserSession> userSession=userSessionRepository.findActiveUser();

		return ConvertUtil.convertSessionList(userSession);
		
	}

//	private String getRandomHexString(int numchars) {
//		Random r = new Random();
//		StringBuffer sb = new StringBuffer();
//		while (sb.length() < numchars) {
//			sb.append(Integer.toHexString(r.nextInt()));
//		}
//
//		String text = sb.toString().substring(0, numchars);
//
//		StringBuilder sb1 = new StringBuilder(text);
//		for (int index = 0; index < sb1.length(); index++) {
//			char c = sb1.charAt(index);
//			if (Character.isLowerCase(c)) {
//				sb1.setCharAt(index, Character.toUpperCase(c));
//			} else {
//				sb1.setCharAt(index, Character.toLowerCase(c));
//			}
//		}
//		return sb1.toString();
//
//	}


	//	public String nextSessionId() {
	//		
	//		String text=UUID.randomUUID().toString();
	////		String text=gen(36);
	//		StringBuilder sb = new StringBuilder(text);
	//		for (int index = 0; index < sb.length(); index++) {
	//		    char c = sb.charAt(index);
	//		    if (Character.isLowerCase(c)) {
	//		        sb.setCharAt(index, Character.toUpperCase(c));
	//		    } else {
	//		        sb.setCharAt(index, Character.toLowerCase(c)); 
	//		    }
	//		}
	//	    return sb.toString();
	//	  }

	//	public static String gen(int length) {
	//	    StringBuffer sb = new StringBuffer();
	//	    for (int i = length; i > 0; i -= 12) {
	//	      int n = min(36, abs(i));
	//	      sb.append(leftPad(Long.toString(round(random() * pow(36, n)), 36), n, '0'));
	//	    }
	//	    return sb.toString();
	//	  }

}
