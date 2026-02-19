// Fájl feltöltése a backend felé
async function uploadCV() {

    // HTML input mező lekérése
    const fileInput = document.getElementById("fileInput");
    const file = fileInput.files[0];

    // Ha nincs fájl kiválasztva figyelmeztet
    if (!file) {
        alert("Válassz ki egy fájlt!");
        return;
    }

    // Betöltés animáció megjelenítése
    document.getElementById("loading").classList.remove("hidden");

    // FormData objektum létrehozása (multipart/form-data kéréshez kell)
    const formData = new FormData();
    formData.append("file", file);

    // POST kérés küldése a Spring Boot API-nak
    const response = await fetch("http://localhost:8080/api/cv/upload", {
        method: "POST",
        body: formData
    });

    // JSON válasz feldolgozása
    const data = await response.json();

    // Betöltés animáció elrejtése
    document.getElementById("loading").classList.add("hidden");

    // Eredmények megjelenítése
    renderExtracted(data);
    renderValidation(data.validations);
    updateProgress(data.validations);
}

// kilvasott adatok megjelenítése
function renderExtracted(data) {

    const resultDiv = document.getElementById("result");

    // Backendtől kapott struktúrált adatok kiírása
    resultDiv.innerHTML = `
        <p><b>Work experience:</b> ${data.extractedData.workExperienceYears} év</p>

        <p><b>Skills:</b><br>${data.extractedData.skills.join("<br>")}</p>

        <p><b>Languages:</b><br>${data.extractedData.languages.join("<br>")}</p>

        <p><b>Profile:</b><br>${data.extractedData.profile}</p>
    `;
}

// val. táblázat
function renderValidation(validations) {

    const tbody = document.querySelector("#validationTable tbody");
    tbody.innerHTML = "";

    validations.forEach(v => {

        const row = document.createElement("tr");

        const statusClass = v.valid ? "valid" : "invalid";
        const statusText = v.valid ? "✔" : "✖";

        row.innerHTML = `
            <td>${v.fieldName}</td>
            <td class="${statusClass}">${statusText}</td>
            <td>${v.message}</td>
            
        `;

        tbody.appendChild(row);
    });

}

// megfelelőségi százalék
function updateProgress(validations) {

    const total = validations.length; // összes szabály
    const passed = validations.filter(v => v.valid).length;// sikeres szabály

    const percent = Math.round((passed / total) * 100);

    // progress bar frissítése
    const bar = document.getElementById("progressBar");
    bar.style.width = percent + "%";
    bar.innerText = percent + "%";

    const status = document.getElementById("statusText");

    // szöveges kiértékelés
    if (percent === 100)
        status.innerHTML = "CV teljesen megfelel";
    else if (percent >= 50)
        status.innerHTML = "CV részben megfelelő";
    else
        status.innerHTML = "CV nem megfelelő";
}

//  Drag és drop feltöltsé 
const dropZone = document.getElementById("dropZone");
const fileInput = document.getElementById("fileInput");

// kattintás, fájlválasztó megnyitása
dropZone.addEventListener("click", () => fileInput.click());

// fájl fölé húzása
dropZone.addEventListener("dragover", e => {
    e.preventDefault();
    dropZone.classList.add("dragover");
});

// amikor elhagyja a zónát
dropZone.addEventListener("dragleave", () => {
    dropZone.classList.remove("dragover");
});

// fájl ledobása
dropZone.addEventListener("drop", e => {
    e.preventDefault();
    dropZone.classList.remove("dragover");

    // a ledobott fájl bekerül az input mezőbe
    const file = e.dataTransfer.files[0];
    fileInput.files = e.dataTransfer.files;

    // automatikus feltöltés
    uploadCV();
});