package com.inventory.web.startup;

import com.inventory.core.validation.UserValidation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ValidationBean {

	@Bean(name = "userValidation")
	public UserValidation userValidation() {
		return new UserValidation();
	}
}
