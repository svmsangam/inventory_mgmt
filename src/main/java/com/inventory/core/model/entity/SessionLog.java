package com.inventory.core.model.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "sessionLog")
public class SessionLog extends AbstractEntity<Long> {

	private static final long serialVersionUID = 1L;

	private long userId;

	private String sessionId;

	private Date loggedIn;

	private Date loggedOut;

	private String remoteAddress;

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public Date getLoggedIn() {
		return loggedIn;
	}

	public void setLoggedIn(Date loggedIn) {
		this.loggedIn = loggedIn;
	}

	public Date getLoggedOut() {
		return loggedOut;
	}

	public void setLoggedOut(Date loggedOut) {
		this.loggedOut = loggedOut;
	}

	public String getRemoteAddress() {
		return remoteAddress;
	}

	public void setRemoteAddress(String remoteAddress) {
		this.remoteAddress = remoteAddress;
	}

}
