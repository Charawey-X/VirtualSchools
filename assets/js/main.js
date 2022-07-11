import * as App from './App.js';

let resource = {
    name: "",
    description: "",
    url: "",
    type: "",
    subjectId: 0,
    access: ""
}
let school = {
    name: "",
    description: "",
}

let subject = {
    name: "",
    description: "",
    school: 0,
    teacher: 0
}

const loginButton = document.getElementById('loginButton');

loginButton.addEventListener('click', (event) => {
    event.preventDefault();
    let email = document.getElementById('email').value;
    let password = document.getElementById('password').value;

    const data = {
        email: email,
        password: password
    } 
    App.login(data);
});



