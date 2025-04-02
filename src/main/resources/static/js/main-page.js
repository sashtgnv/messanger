const form = document.getElementById('find-user-form');
const chatList = document.getElementById('chat-list-id');
const chat = document.getElementById('chat');
const sender = document.getElementById('sender');

let currentUser;

// текущий пользователь
fetch('/current_user',{
        method:"GET",
        headers: {
            'api':'true'
        }
    })
    .then(response => response.json())
    .then(user => {
        currentUser = user;
        sender.textContent = user.username;
    }).catch(error => {
        console.error('Ошибка:', error);
    });

// поиск пользователя
form.addEventListener('submit', function (event) {
    event.preventDefault(); // Отменяем стандартное поведение формы

    // Собираем данные из формы
    const formData = new FormData(form);
    const params = new URLSearchParams(formData).toString();

    fetch(`/find_user?${params}`, {
        method: "GET",
        headers: {
            'api':'true'
        }
    })
        .then(response => response.json())
        .then(users => {
            chatList.innerHTML = '';
            users.forEach(user => {
                const a = document.createElement('a');
                a.href = `/chat/${user.id}`;
                a.innerHTML = `<div class="profile-container chat-profile-container">
                    <img class="icon" src="/images/user_avatar_.webp" alt="profile">
                    <span>${user.username}</span></div>`;
                chatList.appendChild(a);
            });
        }).catch(error => {
            console.error('Ошибка:', error);
        });
});

// поиск друзей пользователя
fetch('/friends',{
        method:"GET",
        headers: {
            'api':'true'
        }
    })
    .then(response =>response.json())
    .then(users => {
        users.forEach(user => {
            const a = document.createElement('a');
            a.href = `/chat/${user.id}`;
            a.innerHTML = `<div class="profile-container chat-profile-container">
                                <img class="icon" src="/images/user_avatar_.webp" alt="profile">
                                <span>${user.username}</span>
                            </div>`;
            chatList.appendChild(a);
        });

    }).catch(error => {
        console.error('Ошибка:', error);
    });


