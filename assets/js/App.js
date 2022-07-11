const BaseUrl = 'http://api.vschool.namani.co/api/v1';

export const checkLogin = () => {
    const token = localStorage.getItem('token');
    if (token) {
        // if user is Admin
        window.location.href = './dashboard.html';
        return;

        //if user is Teacher
        //TODO: Implement this
    }

    window.location.href = './login.html';
    return;
}

export const login = (data) => {
    fetch(`${BaseUrl}/users/login`, {
        method: 'POST',
        mode: 'no-cors',
        headers: {
            'Content-Type': 'application/json',
            "Authorization": `Bearer ''`
        },
        body: JSON.stringify(data)
    })
        .then((res) => {
           console.log(res);
            return res.json();
        })
        .then(data => {
            if (data.status === 200) {
                localStorage.setItem('token', data.token);
                window.location.href = './dashboard.html';
            }
        }).catch(err => console.log(err));
}

export const getUser = () => {
    checkLogin();
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

export const getUserById = (id) => {
    checkLogin();
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

export const getResources = () => {
    checkLogin();
    const token = localStorage.getItem('token');
    if (token) {
        fetch(`${BaseUrl}/resources`, {
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
}

export const getResource = (id) => {
    checkLogin();
    const token = localStorage.getItem('token');
    if (token) {
        fetch(`${BaseUrl}/resources/${id}`, {
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
}

export const createResource = (resource) => {
    checkLogin();
    const token = localStorage.getItem('token');
    if (token) {
        fetch(`${BaseUrl}/resources`, {
            headers: {
                'Authorization': `Bearer ${token}`
            }
            ,
            method: 'POST',
            body: JSON.stringify(resource)
        })
            .then(res => res.json())
            .then(data => console.log(data))
            .catch(err => console.log(err));
    }
}


export const getSchools = () => {
    checkLogin();
    const token = localStorage.getItem('token');
    if (token) {
        fetch(`${BaseUrl}/schools`, {
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
}

export const getSchool = (id) => {
    checkLogin();
    const token = localStorage.getItem('token');
    if (token) {
        fetch(`${BaseUrl}/schools/${id}`, {
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
}

export const createSchool = (school) => {
    checkLogin();
    const token = localStorage.getItem('token');
    if (token) {
        fetch(`${BaseUrl}/schools`, {
            headers: {
                'Authorization': `Bearer ${token}`
            }
            ,
            method: 'POST',
            body: JSON.stringify(school)
        })
            .then(res => res.json())
            .then(data => console.log(data))
            .catch(err => console.log(err));
    }
}

export const getSubjects = () => {
    checkLogin();
    const token = localStorage.getItem('token');
    if (token) {
        fetch(`${BaseUrl}/courses`, {
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
}

export const getSubject = (id) => {
    checkLogin();
    const token = localStorage.getItem('token');
    if (token) {
        fetch(`${BaseUrl}/courses/${id}`, {
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
}

export const createSubject = (subject) => {
    checkLogin();
    const token = localStorage.getItem('token');
    if (token) {
        fetch(`${BaseUrl}/courses`, {
            headers: {
                'Authorization': `Bearer ${token}`
            }
            ,
            method: 'POST',
            body: JSON.stringify(subject)
        })
            .then(res => res.json())
            .then(data => console.log(data))
            .catch(err => console.log(err));
    }
}


export const getAttendaces = (date) => {
    checkLogin();
    //date must have a format of YYYY-MM-DD

    const token = localStorage.getItem('token');
    if (token) {
        fetch(`${BaseUrl}/attendances/${date}/all`, {
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
}

export const getAttendace = (id, date) => {
    //date must have a format of YYYY-MM-DD

    const token = localStorage.getItem('token');
    if (token) {
        fetch(`${BaseUrl}/attendances/${date}/${id}`, {
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
}