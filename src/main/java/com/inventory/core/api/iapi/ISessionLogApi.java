package com.inventory.core.api.iapi;

import com.inventory.core.model.entity.SessionLog;
import com.inventory.core.model.entity.User;

import java.util.List;

public interface ISessionLogApi {

	public void logUserLoggedIn(long userId, String sessionId, String remoteAddress);

	public void logUserLoggedOut(long userId, String sessionId);

	public long getTotalOnlineUsers();

	public List<SessionLog> getUserHistory(long userId);

	public void endUserSession(long userId);

	public String getUserAccountActivity();

	public String getUserAccountActivity(User u);
}
