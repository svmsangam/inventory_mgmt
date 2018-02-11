package com.inventory.web.session;

import com.inventory.core.api.iapi.ISubscriberServiceApi;
import com.inventory.core.model.entity.User;
import com.inventory.core.model.enumconstant.Permission;
import com.inventory.core.model.enumconstant.UserType;
import com.inventory.core.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

	private final UserRepository userRepository;

	@Autowired
	private ISubscriberServiceApi subscriberServiceApi;

	public UserDetailsServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException , AccountExpiredException {

		User u = userRepository.findByUsername(username.trim().toLowerCase());

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

		if (u.getUserType().equals(UserType.USER)) {
			userDetailsWrapper = new UserDetailsWrapper(u, getAuthorities(u.getUsername()), msg.toString(), userRepository, subscriberServiceApi);
		}

		/*if (!userDetailsWrapper.isAccountNonLocked()){
			throw new AccountExpiredException("account is expired");
		}*/
		return userDetailsWrapper;
	}

	private List<GrantedAuthority> getAuthorities(String username){

		User user = userRepository.findByUsernameJoinPermission(username);

		if (user == null){

			List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>(2);

			authorities.add(new SimpleGrantedAuthority(user.getAuthority()));

			authorities.add(new SimpleGrantedAuthority("ROLE_DASHBOARD"));

			return authorities;
		}

		if (user.getPermission() == null){

			List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>(2);

			authorities.add(new SimpleGrantedAuthority(user.getAuthority()));

			authorities.add(new SimpleGrantedAuthority("ROLE_DASHBOARD"));

			return authorities;
		}


		Set<String> authoritySet = new HashSet<>();

		for (Permission role : user.getPermission().getPermissionList()){

				try {

					authoritySet.add(role.toString());

				}catch (Exception e){
					continue;
				}
		}

		List<GrantedAuthority> authorities = createAuthorityList(authoritySet);

		authorities.add(new SimpleGrantedAuthority(user.getAuthority()));

		authorities.add(new SimpleGrantedAuthority("ROLE_DASHBOARD"));

		return authorities;
	}

	private List<GrantedAuthority> createAuthorityList(Set<String>roles) {

		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>(roles.size());

		for (String role : roles) {

			authorities.add(new SimpleGrantedAuthority(role));
		}

		return authorities;
	}


}
