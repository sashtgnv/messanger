const form = document.getElementById('form');

form.addEventListener('submit', function (event) {
    event.preventDefault();
    
    formData = new FormData(form);
    fetch('/authenticate', {
        method:'POST',
        credentials: 'include',
        body: formData
    }).then(response => response.json()).then(data => {
        console.log('Ответ от сервера:', data);
        console.log(document.cookie);
        console.log('твыфтомв');
    }).catch(error => {
        console.error('Ошибка:', error);
    });
})