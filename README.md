# CV Processing and Validation Application

This project is a full-stack web application built with **Java Spring Boot** on the backend and **HTML, CSS, and JavaScript** on the frontend.
Its purpose is to automatically process uploaded CVs (PDF format), extract relevant information, validate the extracted data, and display the results in a structured form.

The application integrates a **Large Language Model (LLM)** via the OpenAI API to interpret and analyze natural language text.

---

## Features

* Upload CV files in PDF format
* Extract text from uploaded documents
* Automatically identify key information (skills, work experience, languages, profile)
* Validate extracted data
* Display results in a user-friendly web interface
* REST API communication between frontend and backend

---

## Technologies Used

### Backend

* Java
* Spring Boot
* Spring Web MVC
* Bean Validation
* Apache PDFBox
* Jackson (JSON processing)
* Lombok

### Frontend

* HTML
* CSS
* JavaScript (Fetch API)

### External Services

* OpenAI API for natural language processing

---

## Project Structure

```
cv-processing-app
│
├── src/main/java
│   ├── controller
│   ├── service
│   ├── model
│   └── config
│
├── src/main/resources
│   └── application.properties
│
├── frontend
│   ├── index.html
│   ├── style.css
│   └── script.js
│
└── pom.xml
```

---

## Requirements

To run the project, you need:

* Java 17 or newer
* Maven
* Internet connection (for OpenAI API calls)

---

## OpenAI API Key Configuration

The application requires an OpenAI API key to process CV content.

Add the key to `application.properties`:

```
openai.api.key=YOUR_API_KEY
```

---

## Running the Backend

Navigate to the project root directory and start the Spring Boot application:

```
mvn spring-boot:run
```

After startup, the backend will be available at:

```
http://localhost:8080
```

---

## Testing the Backend via Terminal

You can test the file upload endpoint using `curl`:

```
curl.exe -X POST http://localhost:8080/api/cv/upload -F "file=@C:\path\to\your\CV.pdf"
```

In PowerShell, a manually constructed multipart request may be required because of differences in file upload handling.

---

## Running the Frontend

The frontend is a static web page and can be opened directly in a browser:

```
frontend/index.html
```

Simply double-click the file or open it using:

```
file:///.../frontend/index.html
```

The frontend communicates with the backend using asynchronous Fetch API requests, so results are displayed without reloading the page.

---

## Application Workflow

1. The user uploads a CV through the web interface.
2. The frontend sends the file to the backend using a multipart/form-data POST request.
3. The backend:

   * extracts text from the PDF
   * sends the text to the OpenAI API
   * receives structured data
   * performs validation on extracted fields
4. The backend returns the results as JSON.
5. The frontend displays:

   * extracted information
   * validation results in a table
   * overall compliance percentage

---

## Validation Logic

The system validates:

* presence of work experience
* listed skills
* language knowledge
* existence of a profile/summary section

Each field produces a validation result and contributes to an overall score.

<img width="1920" height="1020" alt="Képernyőkép 2026-02-17 170616" src="https://github.com/user-attachments/assets/0ee17722-c393-4587-a4f4-9b075100cbbb" />

<img width="1920" height="1020" alt="Képernyőkép 2026-02-17 170701" src="https://github.com/user-attachments/assets/ae95e408-f55e-472f-be1c-e09077254e0b" />

---

## Main Dependencies

The project uses the following key Maven dependencies:

* `spring-boot-starter-webmvc` – REST API and HTTP handling
* `spring-boot-starter-validation` – data validation
* `apache pdfbox` – PDF text extraction
* `jackson-databind` – JSON serialization/deserialization
* `lombok` – boilerplate code reduction

---

## Possible Future Improvements

* Database integration for storing processed CVs
* User authentication
* Support for additional file formats (e.g., DOCX)
* Multi-language CV processing

---

## Author

- LinkedIn: www.linkedin.com/in/bence-kupecz-119701305
- GitHub: https://github.com/kupeczbence
- Portfolio: https://www.kupeczbence.com/
