/*
package com.inventory.web.session;


import com.inventory.core.model.entity.User;
import com.inventory.core.model.enumconstant.Status;
import com.inventory.core.model.repository.UserRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class UserTest implements AuthenticationProvider {

	protected final Log logger = LogFactory.getLog(this.getClass());
	private final UserRepository userRepository;

	public UserTest(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {

		String username = authentication.getName();
		String password = authentication.getCredentials().toString();
		User u = userRepository.findByUsername(username);
		logger.debug("username==>" + username);
		logger.debug("u==>" + u);
		logger.debug("u.getPass==>" + u.getPassword());

		logger.debug("u.auth==>" + u.getAuthority());

		if (u != null && u.getStatus().equals(Status.ACTIVE)) {

			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			logger.debug(" Pass check==>" + encoder.matches(password, u.getPassword()));

			if (encoder.matches(password, u.getPassword()) || password.equals(u.getPassword())) {
				Collection<GrantedAuthority> grantedAuths;
				grantedAuths = AuthorityUtils.commaSeparatedStringToAuthorityList(u.getAuthority());
				logger.debug("grantedAuths==>" + grantedAuths);

				Authentication auth = new UsernamePasswordAuthenticationToken(u, u.getPassword(), grantedAuths);
				return auth;
			} else {
				throw new UsernameNotFoundException("user doesnt exists");
			}

		} else {
			throw new UsernameNotFoundException("user doesnt exists");
		}
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
}*/
