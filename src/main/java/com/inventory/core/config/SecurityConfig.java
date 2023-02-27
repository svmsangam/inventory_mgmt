package com.inventory.core.config;

import com.inventory.web.session.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.ExceptionMappingAuthenticationFailureHandler;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    //https://docs.spring.io/spring-security/reference/servlet/oauth2/client/index.html
    @Autowired
    private UserDetailsServiceImpl userDetailsService;

   /* private static final ClearSiteDataHeaderWriter.Directive[] SOURCE = {
                ClearSiteDataHeaderWriter.Directive.CACHE,
            ClearSiteDataHeaderWriter.Directive.COOKIES,
            ClearSiteDataHeaderWriter.Directive.STORAGE,
            ClearSiteDataHeaderWriter.Directive.EXECUTION_CONTEXTS
    };*/

    private ExceptionMappingAuthenticationFailureHandler authenticationFailureHandler(){
        ExceptionMappingAuthenticationFailureHandler failureHandler = new ExceptionMappingAuthenticationFailureHandler();

        Map<String, String> error = new HashMap<String, String>();

        error.put("org.springframework.security.authentication.BadCredentialsException", "/login/wrong_username_or_password");
        error.put("org.springframework.security.authentication.CredentialsExpiredException", "/login/credentialsExpired");
        error.put("org.springframework.security.authentication.LockedException", "/login/account_is_not_activated_check_your_email");
        error.put("org.springframework.security.authentication.DisabledException", "/login/account_expired");

        failureHandler.setExceptionMappings(error);
        return failureHandler;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/img/**", "/css/**", "/js/**", "/auth/**")
                .permitAll()
                .and()
                .authorizeRequests().anyRequest().authenticated()
                .and()
                .userDetailsService(userDetailsService)
                .formLogin(login ->
                        login
                                .defaultSuccessUrl("/dashboard")
                                .failureHandler(
                                        (request, response, exception) ->
                                                response.sendRedirect("/auth/login?error=" + exception.getMessage())
                                )
                                .usernameParameter("username")
                                .passwordParameter("password")
                                .failureHandler(authenticationFailureHandler())
                                .loginPage("/login")
                                .permitAll()
                )
                .logout(logout ->
                        logout.
                                logoutUrl("/logout").
                                invalidateHttpSession(true).
                                clearAuthentication(true).
                                permitAll()
                );

        return http.build();
    }

   /* @Bean
    public UserDetailsService userDetailsService() {
        return userDetailsService;
    }*/

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
