const messageForm = document.getElementById('message-form');
const chatWindow = document.getElementById('chat-window');
const messagesContainer = document.getElementById('messages-container');

let lastMessage = -1;
let recipient;

// получение сообщений
function getMessages() {
    const params = new URLSearchParams({ lastMessageid: lastMessage }).toString();

    fetch(`/messages/${recipient.id}?${params}`, {
        method: "GET",
        headers: {
            'api': 'true'
        }
    })
        .then(response =>response.json())
        .then(messages => {
            if (Array.isArray(messages) && messages.length > 0) {
                messagesContainer.innerHTML = '';
            }

            messages.forEach(message => {
                lastMessage = message.id;
                const container = document.createElement('div');
                if (message.sender.id == currentUser.id) {
                    container.className = 'message-right';
                } else {
                    container.className = 'message-left';
                }
                container.innerHTML = `<p>${message.messageText}</p>`;
                messagesContainer.appendChild(container);
            });
        })
        .catch(error => {
            console.error('Ошибка:', error);
        });
}

// сохранение сообщения
messageForm.addEventListener('submit', function (event) {
    event.preventDefault();
    const formData = new FormData(messageForm);

    fetch(`/messages/${recipient.id}`, {
        method: 'POST',
        body: formData
    // }).then(response => {
        // return response.text();
    }).catch(error => {
        console.error('Ошибка:', error);
    });;
    // .then(result=>{
    //     if(result=='error'){
    //         throw new Error('Ошибка сохранения данных');
    //     }
    // })
    messageForm.reset();
})

// 

// текущий собеседник
const currentPath = window.location.pathname;
fetch(currentPath + '/getUser', {
    method: "GET",
    headers: {
        'api': 'true'
    }
})
    .then(response => response.json())
    .then(user => {
        recipient = user;
        const recipientUsername = document.getElementById('recipientUsername');
        recipientUsername.textContent = recipient.username;
    }).catch(error => {
        console.error('Ошибка:', error);
    });


const intervalId = setInterval(getMessages, 200);