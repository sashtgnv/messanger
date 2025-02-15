// Находим форму и контейнер для результата
const form = document.getElementById('find-user-form');
// const resultContainer = document.getElementById('result');
const chatList = document.getElementById('chat-list-id');
const chat = document.getElementById('chat');

// поиск пользователя
form.addEventListener('submit', function(event) {
    event.preventDefault(); // Отменяем стандартное поведение формы

    // Собираем данные из формы
    const formData = new FormData(form);

    // Преобразуем FormData в URL-параметры
    const urlParams = new URLSearchParams(formData).toString();

    // URL для запроса (замени на свой)
    const url = `/find_user?${urlParams}`;

    // Отправляем GET-запрос с помощью fetch
    fetch(url)
        .then(response => {
            if (!response.ok) {
                throw new Error('Ошибка сети');
            }
            return response.json(); // Преобразуем ответ в JSON
        })
        .then(users => {
            for (const key in users) {
                const a = document.createElement('a');
                a.href = `/${key}`;
                a.innerHTML = `<div class="profile-container chat-profile-container">
                    <img class="icon" src="/images/user_avatar_.webp" alt="profile">
                    <span>${users[key]}</span></div>`;
                chatList.innerHTML = '';
                chatList.appendChild(a);
            }
        });
});

// поиск друзей пользователя
fetch('/friends')
    .then(response => {
        if (!response.ok){
            throw new Error('Ошибка сети');
        }
        return response.json();
    })
    .then(users => {
        for (const key in users) {
            const a = document.createElement('a');
            a.href = `/${key}`;
            a.innerHTML = `<div class="profile-container chat-profile-container">
                <img class="icon" src="/images/user_avatar_.webp" alt="profile">
                <span>${users[key]}</span></div>`;
            chatList.appendChild(a);
            a.addEventListener('click',function (event) {
                event.preventDefault();

                fetch(`/${key}`)
                    .then(response => {
                        if (!response.ok){
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
                        chat.appendChild(upper);
                    });
                
                
            });
        }
});

