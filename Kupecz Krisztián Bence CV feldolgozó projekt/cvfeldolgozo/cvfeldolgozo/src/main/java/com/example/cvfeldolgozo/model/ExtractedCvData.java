package com.example.cvfeldolgozo.model;// Meghatározza a csomagot, ahol a kód található

import java.util.List;// a skillek és nyelvek listázásához kell

public class ExtractedCvData {

    // Adattárolók
    private double workExperienceYears; // A munkatapasztalat években
    private List<String> skills;// A CV-ből kinyert szakmai készségek listája
    private List<String> languages;// A beszélt nyelvek listája
    private String profile;// Egy rövid szöveges összefoglaló a jelöltről

    // Getterek és setterek (hozzáférési pontok) ezek lehetővé teszik, hogy kívülről (pl.  service-ből) biztonságosan lekérdezzük vagy módosítsuk a fenti privát adatokat.

    public double getWorkExperienceYears() {
        return workExperienceYears;
    }

    public void setWorkExperienceYears(double workExperienceYears) {
        this.workExperienceYears = workExperienceYears;
    }

    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }

    public List<String> getLanguages() {
        return languages;
    }

    public void setLanguages(List<String> languages) {
        this.languages = languages;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }
}