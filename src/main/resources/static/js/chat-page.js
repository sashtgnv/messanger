const messageForm = document.getElementById('message-form');

let lastMessage;
let recipient;
let chatWindow;


function getMessages() {
    fetch(`/messages/${recipient.id}`)
        .then(response => {
            if (!response.ok) {
                throw new Error('Ошибка сети');
            }
            return response.json();
        })
        .then(messages => {
            messages.forEach(message => {
                if (message.sender == currentUser.id) {
                    const container = document.createElement('div');
                    container.className = 'message-right';
                    container.innerHTML = `<p>${message.messageText}</p>`;
                    chatWindow.appendChild(container);
                }
            });
        });
}

messageForm.addEventListener('submit', function (event) {
    event.preventDefault();
    const formData = new FormData(messageForm);
    
    fetch(`/messages/${recipient.id}`, {
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


const currentPath = window.location.pathname;
fetch(currentPath + '/getUser')
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
