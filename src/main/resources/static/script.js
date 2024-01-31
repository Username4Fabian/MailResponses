document.addEventListener("DOMContentLoaded", function () {
    getallUsers();
    receiveMails();

    const sendButton = document.getElementById('sendButton');
    sendButton.addEventListener('click', sendMail);
});

document.getElementById('gptHelp').addEventListener('click', function() {
    const emailContent = document.getElementById('message-list').textContent;
    const mood = 'helpful';

    fetch(`/chatGPT/getResponse?mood=${mood}&message=${encodeURIComponent(emailContent)}`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
    })
        .then(response => {
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status} ${response.statusText}`);
            }
            return response.text();
        })
        .then(data => {
            console.log('Response from GPT:', data);
            document.getElementById('input').value = data;
        })
        .catch(error => console.error('Error:', error));
});



function receiveMails() {
    const userId = 2;
    const url = new URL('/email/receiveEmails', window.location.origin);

    url.searchParams.append('userId', userId);

    fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({})
    })
        .then(response => {
            if (!response.ok) {
                console.log(response);
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            console.log('Email received successfully:', data);
            const mailList = document.getElementById('mail-list');
            mailList.innerHTML = ''; // Clear the list before adding new items
            data.forEach(mail => {
                const listItem = document.createElement('li');
                listItem.textContent = `${mail.subject}`; // Display subject
                listItem.addEventListener('click', function() {
                    document.getElementById('message-list').textContent = mail.content; // Load email content into message-list

                    const emailMatch = mail.receiver.match(/\<(.*?)\>/); // Extract email from receiver string
                    const email = emailMatch ? emailMatch[1] : mail.receiver; // If match found, use it. Otherwise, use the whole receiver string

                    document.getElementById('to').value = email; // Load sender email into 'to' input field
                    document.getElementById('subject').value = 'AW: ' + mail.subject; // Load subject into 'subject' input field with 'AW: ' prefix
                });
                mailList.appendChild(listItem);
            });
        })
        .catch(error => console.error('Error:', error));
}

function sendMail() {
    const userId = 2;
    const to = document.getElementById('to').value;
    const subject = document.getElementById('subject').value;
    const text = document.getElementById('input').value;

    const url = new URL('/email/sendEmail', window.location.origin);
    url.searchParams.append('userId', userId);
    url.searchParams.append('to', to);
    url.searchParams.append('subject', subject);
    url.searchParams.append('text', text);

    fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        }
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => console.log('Email sent successfully:', data))
        .catch(error => console.error('Error:', error));
}

function getallUsers(){
    fetch('/getUser')
        .then(response => response.json())
        .then(users => {
            const userList = document.getElementById('account-list');
            userList.innerHTML = ''; // Clear the list before adding new items

            users.forEach(user => {
                const listItem = document.createElement('li');
                listItem.textContent = user.email; // Assuming the email object has a 'subject' property
                userList.appendChild(listItem);
            });
        })
        .catch(error => console.error('Error:', error));
}
