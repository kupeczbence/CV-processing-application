package com.example.cvfeldolgozo.config;

// Spring konfigurációs annotációk és webes beállítások importálása
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration// Jelzi, hogy ez egy konfigurációs osztály (nem logika, hanem beállítás)
public class CorsConfig {

    @Bean// Bean létrehozása: a Spring induláskor automatikusan betölti

    // Web konfiguráció felülírása
    public WebMvcConfigurer corsConfigurer() {

        return new WebMvcConfigurer() {
            @Override
            
            // CORS szabály: minden /api/** végpont hívható külső domainről is
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**")
                        .allowedOrigins("*")
                        .allowedMethods("*")
                        .allowedHeaders("*");
            }
        };
    }
}
