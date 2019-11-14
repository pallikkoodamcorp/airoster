package com.ai.rostering;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * The type Application.
 *
 * @author karthickumarvp
 */
@SpringBootApplication
@EnableWebMvc
public class Application extends SpringBootServletInitializer {

	/**
	 * The entry point of application.
	 *
	 * @param args
	 *            the input arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }
}
