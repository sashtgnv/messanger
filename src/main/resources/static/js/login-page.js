const form = document.getElementById('form');
const err = document.getElementById('err');

form.addEventListener('submit', function (event) {
    event.preventDefault();
    
    formData = new FormData(form);
    fetch('/authenticate', {
        method:'POST',
        body: formData
    }).then(response => response.text()).then(data => {
        if (data=='Вход выполнен успешно') 
            window.location.href = '/';
        else {
            form.reset();
            err.hidden = false;
        }
    }).catch(error => {
        console.error('Ошибка:', error);
    });
})