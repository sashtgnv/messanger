const form = document.getElementById('form');

form.addEventListener('submit', function (event) {
    event.preventDefault();
    
    formData = new FormData(form);
    fetch('/authenticate', {
        method:'POST',
        body: formData
    }).then(response => response.text()).then(data => {
        console.log('Ответ от сервера:', data);
        window.location.href = '/';
    }).catch(error => {
        console.error('Ошибка:', error);
    });
})