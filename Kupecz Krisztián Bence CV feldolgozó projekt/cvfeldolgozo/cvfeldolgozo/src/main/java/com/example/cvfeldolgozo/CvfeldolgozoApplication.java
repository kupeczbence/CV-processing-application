package com.example.cvfeldolgozo;// Meghatározza a csomagot, ahol a kód található

import org.springframework.boot.SpringApplication; // Importálja a Spring alkalmazás indításához szükséges osztályt
import org.springframework.boot.autoconfigure.SpringBootApplication; // Importálja az automatikus konfigurációt biztosító annotációt

// Bekapcsolja az automatikus konfigurációt, a komponenskeresést és jelzi, hogy ez egy Spring Boot konfigurációs osztály

@SpringBootApplication
public class CvfeldolgozoApplication {

	// Ez a metódus az alkalmazás tényleges indítópultja.
	// A run() metódus elindítja a beépített webszervert és felépíti a Spring alkalmazási környezetet
	public static void main(String[] args) {
		SpringApplication.run(CvfeldolgozoApplication.class, args);
	}

}
