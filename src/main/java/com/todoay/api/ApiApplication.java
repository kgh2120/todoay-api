package com.todoay.api;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@EnableJpaAuditing // createdate 추가해주는 애
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@OpenAPIDefinition(info = @Info(title = "TODOAY Open API", version = "${springdoc.version}"))
public class ApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);
	}

	@GetMapping("/")
	public String index(Model model) { return "index"; }
}
