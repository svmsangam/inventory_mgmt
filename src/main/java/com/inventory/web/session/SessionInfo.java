package com.inventory.web.session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;


/**
 * Created by dhiraj on 8/17/17.
 */
@Service
public class SessionInfo {

    @Autowired
    private SessionRegistry sessionRegistry;

    public void expireUserSessions(String username) {
        for (Object principal : sessionRegistry.getAllPrincipals()) {
            if (principal instanceof User) {
                UserDetails userDetails = (UserDetails) principal;
                if (userDetails.getUsername().equals(username)) {
                    for (SessionInformation information : sessionRegistry.getAllSessions(userDetails, true)) {

                        System.out.println("session id : " + information.getSessionId());
                        information.expireNow();
                    }
                }
            }
        }
    }
}
