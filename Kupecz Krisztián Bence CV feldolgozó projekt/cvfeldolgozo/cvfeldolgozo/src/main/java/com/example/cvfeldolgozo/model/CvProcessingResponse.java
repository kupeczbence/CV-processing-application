package com.example.cvfeldolgozo.model; // Meghatározza a csomagot, ahol a kód található

import java.time.LocalDateTime;// A pontos időpont rögzítéshez
import java.util.List;// Lista adatszerkezet használathoz

import lombok.Data;// Automatikusan létrehozza a gettereket, settereket, a toString és az equals/hashCode metódusokat a háttérben.

@Data
public class CvProcessingResponse {

    private String filename;// A feltöltött eredeti fájl neve
    private LocalDateTime processedAt;// A feldolgozás pontos időpontja

    private String formattedOutput;// Egy előre formázott, olvasható szöveg

    private ExtractedCvData extractedData;// Az AI által kinyert részletes adatok (név, tapasztalat stb.)
    private List<ValidationResult> validations;// Az összes elvégzett validáció listája 

    private boolean overallValid;// Egyszerű igen/nem jelzés, hogy a CV minden kritériumnak megfelelt-e
}
