package com.example.cvfeldolgozo.service;// Meghatározza a csomagot, ahol a kód található

import java.io.InputStream;// Az adatfolyam kezeléséhez szükséges (fájl beolvasása)

import org.apache.pdfbox.pdmodel.PDDocument; // Az Apache PDFBox könyvtár osztálya a PDF dokumentum reprezentálásához
import org.apache.pdfbox.text.PDFTextStripper;// Az eszköz, amely "leszedi" a szöveget a PDF oldalakról
import org.springframework.stereotype.Service;// Jelzi a Springnek, hogy ez egy szolgáltatás osztály (Bean)
import org.springframework.web.multipart.MultipartFile;// A webesen feltöltött fájl típusa

import lombok.SneakyThrows;// Lombok annotáció, ami elrejti a kötelező kivételkezelést (try-catch), tisztábbá téve a kódot

@Service// Ez az osztály egy Service, amit a Controller bármikor használhat
public class DocumentService {

    // Exception, ha valami baj történne a fájl megnyitásakor
    @SneakyThrows
    public String extractText(MultipartFile file) {

        try (InputStream inputStream = file.getInputStream();
             PDDocument document = PDDocument.load(inputStream)) {

            PDFTextStripper stripper = new PDFTextStripper();// szövegkinyerő eszköz létrehozása
            return stripper.getText(document);// összes szöveg kinyerése a dokumentumból és visszaadja String formátumban
        }
    }
}