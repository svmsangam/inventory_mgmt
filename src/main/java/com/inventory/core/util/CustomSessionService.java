package com.inventory.core.util;

import com.inventory.core.model.dto.InvUserDTO;
import com.inventory.core.model.entity.User;
import com.inventory.core.model.repository.UserRepository;
import com.inventory.web.session.UserDetailsServiceImpl;
import com.inventory.web.session.UserDetailsWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by dhiraj on 2/6/18.
 */

@Service
public class CustomSessionService {

    @Autowired
    private UserRepository userRepository;

    public void authenticateUserAndSetSession(InvUserDTO user, HttpServletRequest request) {

        String username = user.getInventoryuser();
        String password = user.getUserrepassword();

        UserDetailsServiceImpl service = new UserDetailsServiceImpl(userRepository);

        UserDetailsWrapper wrapper  = (UserDetailsWrapper) service.loadUserByUsername(username);

        Authentication authentication = new UsernamePasswordAuthenticationToken(wrapper, password, AuthorityUtils.commaSeparatedStringToAuthorityList(user.getUserauthority()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
