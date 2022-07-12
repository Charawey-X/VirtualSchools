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


//Create a new school

const createSchoolButton = document.getElementById('createSchool');

createSchoolButton.addEventListener('submit', (event) => {
    event.preventDefault();

    let name = document.getElementById('name').value;
    let description = document.getElementById('description').value;

    school.name = name;
    school.description = description;

    App.createSchool(school);
    
});

// Get schools

const schoolsSection = document.querySelector('.schools');
schoolsSection.addEventListener('onload', (event) => {
    App.getSchools();
});
