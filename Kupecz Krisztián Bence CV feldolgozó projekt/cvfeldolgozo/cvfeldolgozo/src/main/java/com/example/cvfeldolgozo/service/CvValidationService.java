package com.example.cvfeldolgozo.service;// Meghatározza a csomagot, ahol a kód található

import java.util.ArrayList;// Egy rugalmas, dinamikusan bővíthető lista megvalósítás (ebbe gyűlik a hibalista)
import java.util.List;// Az általános lista típus, amely meghatározza, hogyan viselkedjenek a listák

import org.springframework.stereotype.Service;// Jelzi a Springnek, hogy ez az osztály egy üzleti logikát tartalmazó service

import com.example.cvfeldolgozo.model.ExtractedCvData;// Beemeli az AI által kinyert adatokat, amiket le kell ellenőrizni
import com.example.cvfeldolgozo.model.ValidationResult;// Beemeli azt a típust, amibe az ellenőrzés eredményeit rögzítjük

@Service// Spring komponensként regisztráljuk, hogy injektálható legyen
public class CvValidationService {

        //Részletes ellenőrzést végez a kinyert adatokon.
    public List<ValidationResult> validateDetailed(ExtractedCvData data) {

        List<ValidationResult> results = new ArrayList<>();

        // Null-check, Ha nincs adat, egyből egy rendszerhibát add vissza
        if (data == null) {
            results.add(new ValidationResult(
                    "CV",
                    false,
                    "No data extracted from CV",
                    "SYSTEM"
            ));
            return results;
        }

        //Munkatapasztalat, Max 2 év tapasztalatot engedélyez 
        Double workYearsObj = data.getWorkExperienceYears();
        double workYears = workYearsObj != null ? workYearsObj : -1;

        boolean workValid = workYears >= 0 && workYears <= 2;

        results.add(new ValidationResult(
                "Work Experience",
                workValid,
                workValid ? "Validation passed" : "Must be between 0 and 2 years",
                "JAVA"
        ));

        // Skillek, Kötelező a "Java" és az "LLM" kulcsszavak megléte
        List<String> skills = data.getSkills() != null ? data.getSkills() : new ArrayList<>();

        boolean hasJava = skills.stream()
                .filter(s -> s != null)
                .anyMatch(s -> s.toLowerCase().contains("java"));

        boolean hasLLM = skills.stream()
                .filter(s -> s != null)
                .anyMatch(s -> s.toLowerCase().contains("llm"));

        boolean skillsValid = hasJava && hasLLM;

        results.add(new ValidationResult(
                "Skills",
                skillsValid,
                skillsValid ? "Validation passed" : "Must include Java and LLMs",
                "JAVA"
        ));

        // Nyelvek, magyar és  angol 
        List<String> languages = data.getLanguages() != null ? data.getLanguages() : new ArrayList<>();

        boolean hasHungarian = languages.stream()
                .filter(l -> l != null)
                .anyMatch(l -> l.toLowerCase().contains("hungarian"));

        boolean hasEnglish = languages.stream()
                .filter(l -> l != null)
                .anyMatch(l -> l.toLowerCase().contains("english"));

        boolean langValid = hasHungarian && hasEnglish;

        results.add(new ValidationResult(
                "Languages",
                langValid,
                langValid ? "Validation passed" : "Must include Hungarian and English",
                "JAVA"
        ));

        // Profil, Tartalmaznia kell mesterséges intelligenciát, Javát és fejlődést
        String profile = data.getProfile() != null ? data.getProfile().toLowerCase() : "";

        boolean mentionsGenAI =
                profile.contains("genai") ||
                profile.contains("generative ai") ||
                profile.contains("ai");

        boolean mentionsJava =
                profile.contains("java");

        boolean mentionsExpertGoal =
                profile.contains("expert") ||
                profile.contains("master") ||
                profile.contains("specialist") ||
                profile.contains("become good at");

        boolean profileValid = mentionsGenAI && mentionsJava && mentionsExpertGoal;

        results.add(new ValidationResult(
                "Profile",
                profileValid,
                profileValid
                        ? "Validation passed"
                        : "Must mention interest in GenAI and becoming a Java expert",
                "LLM"
        ));

        return results;
    }
    //Szöveges beszámolót készít a validációs eredményekből.
    public String buildValidationReport(List<ValidationResult> results) {

        boolean overallValid = results.stream().allMatch(ValidationResult::isValid);

        StringBuilder sb = new StringBuilder();
        sb.append("\n-----------------------------------\n");

        if (overallValid) {
            sb.append("VALIDATION RESULT: PASSED\n");
            return sb.toString();
        }

        sb.append("VALIDATION RESULT: FAILED\n\n");
        sb.append("FAILED RULES:\n");

        for (ValidationResult r : results) {
            if (!r.isValid()) {
                sb.append("- ")
                  .append(r.getFieldName())
                  .append(": ")
                  .append(r.getMessage())
                  .append("\n");
            }
        }

        return sb.toString();
    }
}
