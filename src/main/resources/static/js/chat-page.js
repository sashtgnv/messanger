const messageForm = document.getElementById('message-form');
const chatWindow = document.getElementById('chat-window');

let lastMessage = -1;
let recipient;

// получение сообщений
function getMessages() {
    
    const json = {
        lastMessageid: lastMessage
    }   

    fetch(`/messages/${recipient.id}`,{
        method:"POST",
        headers: {
            'Content-type': 'application/json'
        },
        body: JSON.stringify(json)
    })
    .then(response => {
            if (!response.ok) {
                throw new Error('Ошибка сети');
            }
            return response.json();
        })
        .then(messages => {
            messages.forEach(message => {
                lastMessage = message.id;
                if (message.sender.id == currentUser.id) {
                    const container = document.createElement('div');
                    container.className = 'message-right';
                    container.innerHTML = `<p>${message.messageText}</p>`;
                    chatWindow.appendChild(container);
                }
                console.log(message)
            });
        });
}

// сохранение сообщения
messageForm.addEventListener('submit', function (event) {
    event.preventDefault();
    const formData = new FormData(messageForm);
    
    fetch(`/messages/post/${recipient.id}`, {
            method:'POST',
            body:formData
        }).then(response => {
            if (!response.ok) {
                throw new Error('Ошибка сети');
            }
            // return response.text();
        });
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
fetch(currentPath + '/getUser',{
        method:"POST"
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Ошибка сети');
        }
        return response.json();
    })
    .then(user => {
        recipient = user;
        const recipientUsername = document.getElementById('recipientUsername');
        recipientUsername.textContent = recipient.username;
    });


const intervalId = setInterval(getMessages, 5000);