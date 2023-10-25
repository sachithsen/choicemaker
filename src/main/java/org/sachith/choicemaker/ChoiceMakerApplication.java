package org.sachith.choicemaker;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info=@Info(title="Choicemaker API", version = "1.0.0"))
public class ChoiceMakerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChoiceMakerApplication.class, args);
	}

}
