package com.inventory;

import com.inventory.web.startup.Startup;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@SpringBootApplication
@Slf4j
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}


	@Component
	class BootStrap implements CommandLineRunner {

		@Autowired
		private Startup startup;

		@Autowired
		private PasswordEncoder passwordEncoder;

		@Override
		public void run(String... args) throws Exception {

	//		log.info("---inside bootstrap----");
			startup.initialize();
		}



		@Component
		@Profile("dev")
		class BootStrapDev implements CommandLineRunner {

			public BootStrapDev() {
			}

			@Override
			public void run(String... args) {

				//log.info("---inside bootstrap dev----");


			}
		}

	}
}
