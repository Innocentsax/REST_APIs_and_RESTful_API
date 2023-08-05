package com.innocentCharles.HelloSwagger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "Hello Swagger Project",
				version = "1.0.0",
				description = "Am innocent charles Udo",
				termsOfService = "At runtime",
				contact = @Contact(
						name = "Mr. Innocent Udo",
						email = "innocentcharlesudo@gmail.com"
				),
				license = @License(
						name = "Udo_licence",
						url = "com.innocentCharlesUdo"
				)
		)
)
public class HelloSwaggerApplication {

	public static void main(String[] args) {
		SpringApplication.run(HelloSwaggerApplication.class, args);
	}

}
