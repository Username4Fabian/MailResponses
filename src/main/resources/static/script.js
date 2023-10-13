
document.addEventListener("DOMContentLoaded", function () {
    // Simuliere das Hinzufügen von neuen E-Mails
    setTimeout(function () {
        addNewEmail("Neue E-Mail 1", "Inhalt der E-Mail 1");
    }, 3000);

    setTimeout(function () {
        addNewEmail("Neue E-Mail 2", "Inhalt der E-Mail 2");
    }, 5000);

    // Funktion zum Hinzufügen einer neuen E-Mail zur Sidebar
    function addNewEmail(emailSubject, emailContent) {
        const emailList = document.getElementById("email-list");
        const listItem = document.createElement("li");
        listItem.textContent = emailSubject;
        emailList.appendChild(listItem);

        // Füge einen Event-Listener hinzu, um die E-Mail zu öffnen, wenn darauf geklickt wird
        listItem.addEventListener("click", function () {
            openEmail(emailSubject, emailContent);
        });
    }

    // Funktion zum Öffnen einer E-Mail im Hauptbereich
    function openEmail(emailSubject, emailContent) {
        const messageList = document.getElementById("message-list");
        messageList.innerHTML = ""; // Lösche den aktuellen Inhalt

        const emailTitle = document.createElement("h2");
        emailTitle.textContent = emailSubject;

        const emailText = document.createElement("p");
        emailText.textContent = emailContent;

        messageList.appendChild(emailTitle);
        messageList.appendChild(emailText);
    }



});

function uploadFile() {
    var fileInput = document.getElementById('myFile');
    var file = fileInput.files[0];
    var formData = new FormData();
    formData.append('file', file);

}
