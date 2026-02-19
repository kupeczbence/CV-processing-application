package com.example.cvfeldolgozo.model;// Meghatározza a csomagot, ahol a kód található

public class ValidationResult {

    private String fieldName;// Az ellenőrzött mező neve
    private boolean valid;// Az ellenőrzés eredménye: true v. false
    private String message;// Szöveges üzenet a hiba okáról
    private String validationMethod;// A módszer, amivel ellenőrizve lett

    // Üres konstruktor: a keretrendszereknek gyakran szükségük van erre
    public ValidationResult() {}

    // Paraméteres konstruktor: megkönnyíti az új eredmények létrehozását egyetlen sorban
    public ValidationResult(String fieldName, boolean valid, String message, String validationMethod) {
        this.fieldName = fieldName;
        this.valid = valid;
        this.message = message;
        this.validationMethod = validationMethod;
    }

    // getterek és setterek
    // Lehetővé teszik az adatok kiolvasását és módosítását más osztályok számára
    public String getFieldName() { return fieldName; }
    public void setFieldName(String fieldName) { this.fieldName = fieldName; }

    public boolean isValid() { return valid; }
    public void setValid(boolean valid) { this.valid = valid; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getValidationMethod() { return validationMethod; }
    public void setValidationMethod(String validationMethod) { this.validationMethod = validationMethod; }
}
