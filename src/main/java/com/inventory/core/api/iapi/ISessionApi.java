package com.inventory.core.api.iapi;

import com.inventory.core.model.dto.UserSessionDTO;
import com.inventory.core.model.entity.User;
import com.inventory.core.model.entity.UserSession;
import com.inventory.web.session.UserDetailsWrapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ISessionApi {

	public void registerNewSession(String sessionId, UserDetailsWrapper principal);

	public void removeSession(String tokenKey);

	public UserSession getUserSession(String sessionId);

	public void refreshSession(String sessionId);

	public List<UserSession> getAllUserSession(long userId, boolean includeExpiredSessions);

	public void expireSession(String sessionId);

	public long countActiveSessions();

	public Page<User> findOnlineUsers(Pageable page);

	public Page<UserSession> findActiveSessions(Pageable page);

	public void registerNewSession(String sessionId, User principal);

	public List<UserSessionDTO> getAllActiveUser();

}
