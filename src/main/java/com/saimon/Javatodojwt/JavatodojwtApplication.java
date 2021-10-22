package com.saimon.Javatodojwt;

import com.saimon.Javatodojwt.domain.AppUser;
import com.saimon.Javatodojwt.domain.Roles;
import com.saimon.Javatodojwt.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

@SpringBootApplication
public class JavatodojwtApplication {

	public static void main(String[] args) {
		SpringApplication.run(JavatodojwtApplication.class, args);
	}

	@Bean
	PasswordEncoder passwordEncoder(){return new BCryptPasswordEncoder();}
}
