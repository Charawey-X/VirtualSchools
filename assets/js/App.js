const BaseUrl = 'https://api.vschool.namani.co/api/v1';

const checkLogin = () => {
    const token = localStorage.getItem('token');
    if (token) {
        // if user is Admin
        window.location.href = './dashboard.html';

        //if user is Teacher
        //TODO: Implement this
    }
    window.location.href = './login.html';
}

const login = (email, password) => {
    fetch(`${BaseUrl}/users/login`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            email: email,
            password: password
        })
    })
        .then(res => res.json())
        .then(data => {
            if (data.status === 200) {
                localStorage.setItem('token', data.token);
                window.location.href = './dashboard.html';
            }
        }).catch(err => console.log(err));
}

const getUser = () => {
    const token = localStorage.getItem('token');
    if (token) {
        fetch(`${BaseUrl}/users`, {
            headers: {
                'Authorization': `Bearer ${token}`
            }
            ,
            method: 'GET'
        })
            .then(res => res.json())
            .then(data => console.log(data))
            .catch(err => console.log(err));
        //TODO: Do something with the retrieved data
    }
    return null;
}

const getUserById = (id) => {
    const token = localStorage.getItem('token');
    if (token) {
        fetch(`${BaseUrl}/users/${id}`, {
            headers: {
                'Authorization': `Bearer ${token}`
            }
            ,
            method: 'GET'
        })
            .then(res => res.json())
            .then(data => console.log(data))
            .catch(err => console.log(err));
    }
     //TODO: Do something with the retrieved data
}

