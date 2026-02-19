package com.example.cvfeldolgozo.service;// Meghatározza a csomagot, ahol a kód található

import java.util.List;// lista kezeléshez, amiben az AI válaszai érkeznek
import java.util.Map;// Kulcs-érték párok tárolása (pl. a JSON válasz szerkezete a Java-ban)

import org.springframework.http.HttpEntity;// Ebbe tesszük bele az adatot és a fejléceket a küldéshez
import org.springframework.http.HttpHeaders;// A fejléc, ahol megadjuk pl. az API kulcsot és a típusokat
import org.springframework.http.MediaType;// Meghatározza, hogy milyen típusú adatot küldünk
import org.springframework.http.ResponseEntity;// A külső szerver válaszát tartalmazó csomag
import org.springframework.stereotype.Service;// Megjelöli ezt az osztályt, mint a Spring rendszer egyik szolgáltatását
import org.springframework.web.client.RestTemplate;// Hívást indítunk a külső weboldal felé

import com.example.cvfeldolgozo.model.ExtractedCvData;// A célosztály, amibe az AI-tól kapott adatokat szeretnénk menteni
import com.fasterxml.jackson.databind.ObjectMapper;// Nyers JSON szöveget Java objektummá alakítja

// Spring szolgáltatásként regisztráljuk
@Service
public class LlmService {

    private final String apiKey = System.getenv("OPENAI_API_KEY");// Az OpenAI API kulcsot a környezeti változókból olvassa ki 
    private final ObjectMapper objectMapper = new ObjectMapper();// A Jackson könyvtár eszköze a JSON szöveg és a Java objektumok közötti oda-vissza alakításhoz

    public ExtractedCvData extractFields(String cvText) throws Exception {

        //A Spring eszköze HTTP kérések (pl. API hívások) küldésére
        RestTemplate restTemplate = new RestTemplate();

        // Az OpenAI API végpontja
        String url = "https://api.openai.com/v1/responses";

        // itt utasítjuk az AI-t, hogy mit keressen és milyen formátumban válaszoljon
        String prompt = """
                Extract the following fields from this CV text and return ONLY valid JSON:

                {
                  "workExperienceYears": number,
                  "skills": [],
                  "languages": [],
                  "profile": ""
                }

                CV:
                """ + cvText;
        // megadjuk a modellt és a promptot
        Map<String, Object> body = Map.of(
                "model", "gpt-4.1-mini",
                "input", prompt
        );
        // HTTP fejlécek beállítása, JSON formátum és az API kulcs
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        // Összeállítjuk a teljes kérést
        HttpEntity<Map<String, Object>> request =
                new HttpEntity<>(body, headers);

        // Elküldjük a POST kérést az OpenAI szerverének
        ResponseEntity<Map> response =
                restTemplate.postForEntity(url, request, Map.class);

        Map responseBody = response.getBody();

        
        //A válasz kibontása 
        // Az AI válasza egy mélyen beágyazott JSON struktúra:
        Map firstOutput = (Map) ((List) responseBody.get("output")).get(0);

        // content[0]
        Map firstContent = (Map) ((List) firstOutput.get("content")).get(0);

        // text mező
        String rawText = (String) firstContent.get("text");

        // Tisztítás, Az AI gyakran ```json ... ``` blokkba teszi a választ, ezt le kell vágni
        String cleanedJson = rawText
                .replace("```json", "")
                .replace("```", "")
                .trim();

        // JSON -> Java objektum. A megtisztított szöveget beleöntjük az ExtractedCvData osztályba
        return objectMapper.readValue(cleanedJson, ExtractedCvData.class);
    }
}