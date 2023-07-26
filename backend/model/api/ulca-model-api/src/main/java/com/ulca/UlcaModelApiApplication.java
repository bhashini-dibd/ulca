package com.ulca;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

// import org.springframework.context.annotation.Bean;
// import org.springframework.web.servlet.config.annotation.CorsRegistry;
// import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@EnableScheduling
public class UlcaModelApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(UlcaModelApiApplication.class, args);
	}
	// @Bean
	// public WebMvcConfigurer corsConfigurer() {
	// 	return new WebMvcConfigurer() {
	// 		@Override
	// 		public void addCorsMappings(CorsRegistry registry) {
	// 			registry.addMapping("/**")
	// 			.allowedOrigins("http://localhost:3000")
	// 			.allowedMethods("POST", "GET")
	// 			.allowedHeaders("userID", "ulcaApiKey").maxAge(3600);
	// 		//	registry.addMapping("/ulca/apis/v0/model").allowedOrigins("http://localhost:3000");
	// 		//	registry.addMapping("/ulca/apis/v0/model/getModelsPipeline").allowedOrigins("http://localhost:3000");
	// 		//	registry.addMapping(null)
	// 		}
	// 	};
	// }
}
