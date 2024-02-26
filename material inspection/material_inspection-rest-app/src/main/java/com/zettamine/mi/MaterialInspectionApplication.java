package com.zettamine.mi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Material Inspection APP",version="2.0",contact = @Contact(email = "jagadeesh.s@zettamine.in")))
public class MaterialInspectionApplication {

	public static void main(String[] args) {
		SpringApplication.run(MaterialInspectionApplication.class, args);
	}

}
