package com.example.cvfeldolgozo.controller; // Meghatározza a csomagot, ahol a kód található

//Szükséges importok

import java.time.LocalDateTime;// A pontos időpont rögzítéshez
import java.util.List;// Lista adatszerkezet használathoz

import org.springframework.http.ResponseEntity;// szabályos HTTP válaszok küldése
import org.springframework.web.bind.annotation.PostMapping;// Lehetővé teszi, hogy a metódus adatküldést (POST) fogadjon
import org.springframework.web.bind.annotation.RequestMapping; // Meghatározza az útvonalat, ahol az API elérhető
import org.springframework.web.bind.annotation.RequestParam;// Segít kinyerni a kérésből a konkrét paramétert (pl. a fájlt)
import org.springframework.web.bind.annotation.RestController;// Jelzi, hogy ez egy webes végpont (Controller)
import org.springframework.web.multipart.MultipartFile;// Speciális típus a feltöltött fájlok (PDF, Word) kezelésére

import com.example.cvfeldolgozo.model.CvProcessingResponse;// A végső válaszobjektum szerkezete
import com.example.cvfeldolgozo.model.ExtractedCvData;// Az AI által kinyert adatok (név, skillek stb.) formátuma
import com.example.cvfeldolgozo.model.ValidationResult;// Egy-egy ellenőrzés eredményét írja le
import com.example.cvfeldolgozo.service.CvValidationService;// Az ellenőrzési logikát tartalmazó osztály beimportálása
import com.example.cvfeldolgozo.service.DocumentService;// A dokumentum-feldolgozó osztály beimportálása
import com.example.cvfeldolgozo.service.LlmService;// Az AI/LLM-mel kommunikáló osztály beimportálása

import lombok.RequiredArgsConstructor;// Automatikusan megírja a háttérben szükséges konstruktort

@RestController// Jelzi a Springnek, hogy ez egy REST API végpont, ami JSON választ küld vissza
@RequestMapping("/api/cv")// Minden itt lévő hívás a http://domain/api/cv útvonalról indul
@RequiredArgsConstructor// Lombok annotáció: automatikusan legenerálja a konstruktort a 'final' mezőkhöz
public class CvController {

    private final DocumentService documentService; // Dokumentum-olvasó
    private final LlmService llmService; // Mesterséges intelligencia kapcsolat
    private final CvValidationService validationService; // Ellenőrző logika

    @PostMapping("/upload")// HTTP POST kéréseket fogad az /api/cv/upload végponton
    public ResponseEntity<CvProcessingResponse> uploadCv(
            @RequestParam("file") MultipartFile file) throws Exception {

        // Ellenőrzés, ha üres a fájl, rögtön hibát küld vissza
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        // PDF-ből szöveg. A feltöltött fájlból kinyerjük a nyers szöveget
        String text = documentService.extractText(file);

        //Az AI (LLM) elemzi a szöveget és objektumba rendezi az adatokat
        ExtractedCvData extracted = llmService.extractFields(text);

        // Lefut a validáció, hogy meg van-e minden adat
        List<ValidationResult> validations =
                validationService.validateDetailed(extracted);

        // Megállapítja, hogy érvényes-e a CV (minden szabálynak megfelelt-e)        
        boolean overallValid =
                validations.stream().allMatch(ValidationResult::isValid);

        // Formázás
        String formatted =
            "Work experience years: " + extracted.getWorkExperienceYears() + "\n\n" +
            "Skills:\n" + String.join("\n", extracted.getSkills()) + "\n\n" +
            "Languages:\n" + String.join("\n", extracted.getLanguages()) + "\n\n" +
            "Profile:\n" + extracted.getProfile();

        // Validációs hibalista vagy sikeres jelentés fűzése a szöveghez
        String validationReport = validationService.buildValidationReport(validations);
        formatted += validationReport;

        // Becsomagolja az összes eredményt egy válasz objektumba
        CvProcessingResponse response = new CvProcessingResponse();
        response.setFilename(file.getOriginalFilename());
        response.setProcessedAt(LocalDateTime.now());
        response.setFormattedOutput(formatted);
        response.setExtractedData(extracted);
        response.setValidations(validations);
        response.setOverallValid(overallValid);

        // Visszaküldi a választ a kliensnek a kész adatokkal
        return ResponseEntity.ok(response);
    }
}
