package com.inventory.web.session;

import com.inventory.core.api.iapi.ISubscriberServiceApi;
import com.inventory.core.model.entity.User;
import com.inventory.core.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.servlet.http.HttpServletRequest;

public class UserDetailsServiceImpl implements UserDetailsService {

	private final UserRepository userRepository;

	@Autowired
	private ISubscriberServiceApi subscriberServiceApi;

	public UserDetailsServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException , AccountExpiredException {

		User u = userRepository.findByUsername(username.toLowerCase());

		//System.out.println("login");

		HttpServletRequest curRequest = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		final StringBuilder msg = new StringBuilder();
		msg.append(curRequest.getRemoteAddr());
		final String forwardedFor = curRequest.getHeader("X-Forwarded-For");
		if (forwardedFor != null) {
			msg.append(", forwardedFor = ").append(forwardedFor);
		}
		if (u == null) {
			throw new UsernameNotFoundException("user doesnt exists");
		}

		UserDetailsWrapper userDetailsWrapper = new UserDetailsWrapper(u, AuthorityUtils.commaSeparatedStringToAuthorityList(u.getAuthority()), msg.toString() , userRepository , subscriberServiceApi);

		/*if (!userDetailsWrapper.isAccountNonLocked()){
			throw new AccountExpiredException("account is expired");
		}*/
		return userDetailsWrapper;
	}

}
