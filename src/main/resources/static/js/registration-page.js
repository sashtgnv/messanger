const form = document.getElementById('form');
const err = document.getElementById('err');

form.addEventListener('submit', function (event) {
    event.preventDefault();
    formData = new FormData(form);
    fetch('/registration', {
        method:'POST',
        body:formData
    }).then(response=> response.text())
    .then(data => {
        if (data=="Успех") window.location.href = '/login';
        else {
            err.hidden = false;
            form.reset();
        };
    });
});
