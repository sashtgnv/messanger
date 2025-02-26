const form = document.getElementById('find-user-form');
const chatList = document.getElementById('chat-list-id');
const chat = document.getElementById('chat');
const sender = document.getElementById('sender');
let currentUser;

// текущий пользователь
fetch('/current_user',{
        method:"POST"
    })
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

    fetch("/find_user", {
        method: "POST",
        body: formData
    })
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
            });
        });
});

// поиск друзей пользователя
fetch('/friends',{
        method:"POST"
    })
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
        });

    });

