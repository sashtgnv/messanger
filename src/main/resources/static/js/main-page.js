// Находим форму и контейнер для результата
const form = document.getElementById('find-user-form');
// const resultContainer = document.getElementById('result');
const chatList = document.getElementById('chat-list-id');
const chat = document.getElementById('chat');
const sender = document.getElementById('sender');
let chatWindow;
let currentUser;
let recipientId;
let lastMessage;

function getMessages() {
    fetch(`/messages/${recipientId}`)
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

function clickOnUserProfile(a) {
    a.addEventListener('click', function (event) {
        event.preventDefault();
        recipientId = a.getAttribute('href').slice(1);
        fetch(a.href)
            .then(response => {
                if (!response.ok) {
                    throw new Error('Ошибка сети');
                }
                return response.text();
            })
            .then(username => {
                const upper = document.createElement('div');
                upper.classList.add('upper');
                upper.innerHTML = ` <div class="profile-container">
                                        <img class="icon" src="/images/user_avatar_.webp" alt="profile">
                                        <span>${username}</span>
                                    </div>`;

                chat.innerHTML = '';
                if (chatWindow==null){
                    console.log('create chat-window');
                    chatWindow = document.createElement('div');
                    chatWindow.className = 'chat-window';
                    chatWindow.innerHTML = `<div class="text-input-conteiner">
                                                <form action="/messages/${recipientId}" method="post">
                                                    <input type="text" name="messageText" autocomplete="off">
                                                    <button>send</button>
                                                </form>
                                            </div>
                                            
                                            <div class="message-left">
                                                <p>Lorem ipsum dolor sit, amet consectetur adipisicing elit. Aut laboriosam quibusdam veritatis tempora
                                                    eum illum quia iure est nobis quaerat, aspernatur ex tempore magnam eos quo. Accusamus consequatur
                                                    expedita consectetur!</p>
                                            </div>`;
                    
                }
                chat.appendChild(upper);
                chat.appendChild(chatWindow);
            });

        getMessages
    });
}
// текущий пользователь
fetch('/current_user')
    .then(response => {
        if (!response.ok) {
            throw new Error('Ошибка сети');
        }
        return response.json(); // Преобразуем ответ в JSON
    })
    .then(user => {
        currentUser = user;
        sender.textContent = user.username;
    })

// поиск пользователя
form.addEventListener('submit', function (event) {
    event.preventDefault(); // Отменяем стандартное поведение формы

    // Собираем данные из формы
    const formData = new FormData(form);

    // Преобразуем FormData в URL-параметры
    const urlParams = new URLSearchParams(formData).toString();
    const url = `/find_user?${urlParams}`;

    fetch(url)
        .then(response => {
            if (!response.ok) {
                throw new Error('Ошибка сети');
            }
            return response.json(); // Преобразуем ответ в JSON
        })
        .then(users => {
            users.forEach(user => {
                const a = document.createElement('a');
                a.href = `/${user.id}`;
                a.innerHTML = `<div class="profile-container chat-profile-container">
                    <img class="icon" src="/images/user_avatar_.webp" alt="profile">
                    <span>${user.username}</span></div>`;
                chatList.innerHTML = '';
                chatList.appendChild(a);
                clickOnUserProfile(a);
            });
        });
});

// поиск друзей пользователя
fetch('/friends')
    .then(response => {
        if (!response.ok) {
            throw new Error('Ошибка сети');
        }
        return response.json();
    })
    .then(users => {
        users.forEach(user => {
            const a = document.createElement('a');
            a.href = `/${user.id}`;
            a.innerHTML = `<div class="profile-container chat-profile-container">
                                <img class="icon" src="/images/user_avatar_.webp" alt="profile">
                                <span>${user.username}</span>
                            </div>`;
            chatList.appendChild(a);
            clickOnUserProfile(a);
        });

    });

