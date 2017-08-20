package com.inventory.web.session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dhiraj on 8/17/17.
 */
@Service
public class SessionInfo {

    @Autowired
    @Qualifier("sessionRegistry")
    private SessionRegistry sessionRegistry;

    public void list(String username){
        List<Object> principals = sessionRegistry.getAllPrincipals();

        List<String> usersNamesList = new ArrayList<String>();

        for (Object principal: principals) {
            if (principal instanceof User) {
                System.out.println("logined User : " + ((User) principal).getUsername());
                //usersNamesList.add(((User) principal).getUsername());
            }
        }
    }

    public void expireUserSessions(String username)  {
        for (Object principal : sessionRegistry.getAllPrincipals()) {
            if (principal instanceof User) {
                User user = (User) principal;
                if (user.getUsername().equals(username)) {
                    for (SessionInformation information : sessionRegistry.getAllSessions(user, true)) {

                        System.out.println("session id : " + information.getSessionId() + " expired now");
                        information.expireNow();
                    }
                }
            }
        }
    }
}
