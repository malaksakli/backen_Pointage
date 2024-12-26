package com.Blacher.Blacher.Configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	  @Override
	    public void addCorsMappings(CorsRegistry registry) {
	        registry.addMapping("/**") // Appliquer CORS à toutes les routes
	                .allowedOrigins("http://localhost:4200") // Autoriser les requêtes venant de cette origine
	                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Méthodes autorisées
	                .allowedHeaders("*") // Tous les headers autorisés
	                .allowCredentials(true); // Permettre l'envoi de cookies/identifiants
	    }
}