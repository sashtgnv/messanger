const username = document.getElementById("username");
const email = document.getElementById("email");
const form = document.getElementById('change-password-form');
const err = document.getElementById('err');
const success = document.getElementById('success');

let currentUser;

function changePassword(params) {
    console.log('смена пароля')
}

fetch('/current_user', {
        method: "GET",
        headers: {
            'api': 'true'
        }
    })
    .then(response=>response.json())
    .then(user => {
        username.textContent=user.username;
        email.textContent=user.email;
        currentUser=user.username;
    }).catch(error => console.error('Ошибка: ',error))

form.addEventListener('submit', function (event) {
    event.preventDefault();

    err.hidden=true;
    success.hidden=true;

    formData = new FormData(form);
    formData.append('username',currentUser);

    fetch('/change_password', {
        method: 'POST',
        body: formData
    }).then(response => response.text()).then(data => {
        console.log(data);

        if (data=='Пароль сменен успешно') {
            form.reset();
            success.hidden=false;
        }
        else {
            form.reset();
            err.hidden = false;

        }
    }).catch(error => {
        console.error('Ошибка:', error);
    });
});
