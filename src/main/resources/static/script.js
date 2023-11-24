
document.addEventListener("DOMContentLoaded", function () {
    // Simuliere das Hinzufügen von neuen E-Mails
    // Funktion zum Hinzufügen einer neuen E-Mail zur Sidebar
    function addNewEmail(emailSubject, emailContent, emailSender) {
        const emailList = document.getElementById("email-list");
        const listItem = document.createElement("li");
        listItem.textContent = emailSubject;
        emailList.appendChild(listItem);

        // Füge einen Event-Listener hinzu, um die E-Mail zu öffnen, wenn darauf geklickt wird
        listItem.addEventListener("click", function () {
            openEmail(emailSubject, emailContent, emailSender);
        });
    }


    // const subject =
    // const content =
    // const sender =
    // addNewEmail(subject, content, sender);

// Schleife zum Generieren von 10 E-Mails
    for (let i = 1; i <= 10; i++) {
        setTimeout(function () {
            const subject = "Neue E-Mail " + i;
            const content = "Inhalt der E-Mail " + i;
            const sender = "Name " + i;
            addNewEmail(subject, content, sender);
        }, i * 2000); // Verzögerung von 2 Sekunden zwischen den E-Mails (kann nach Bedarf angepasst werden)
    }


    // Funktion zum Öffnen einer E-Mail im Hauptbereich
    function openEmail(emailSubject, emailContent, emailSender) {
        const messageList = document.getElementById("message-list");
        messageList.innerHTML = ""; // Lösche den aktuellen Inhalt

        const emailTitle = document.createElement("h2");
        emailTitle.textContent = "Subject: " + emailSubject;

        const emailSendername = document.createElement("h3");
        emailSendername.textContent = "Sender: " + emailSender;

        const emailText = document.createElement("p");
        emailText.textContent = emailContent;

        messageList.appendChild(emailTitle);
        messageList.appendChild(emailSendername);
        messageList.appendChild(emailText);
    }



});

function uploadFile() {
    var fileInput = document.getElementById('myFile');
    var file = fileInput.files[0];
    var formData = new FormData();
    formData.append('file', file);

}
