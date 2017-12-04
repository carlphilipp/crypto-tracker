import axios from 'axios';
const BASE_URL = 'http://localhost:8180';
export {getAllTickers, getOneUser, createUser, login, refreshTickers};

function getAllTickers() {
    const url = `${BASE_URL}/api/ticker`;
    return axios.get(url).then(response => response.data);
}

function getOneUser(accessToken, userId) {
    const url = `${BASE_URL}/api/user/` + userId;
    const config = {headers: {'Authorization': 'Bearer ' + accessToken}};
    return axios.get(url, config).then(response => response.data);
}

function createUser(email, password) {
    const url = `${BASE_URL}/api/user`;
    return axios.post(url, {
        email: email,
        password: password,
    })
        .then(response => response.data);
}

function refreshTickers() {
    const url = `${BASE_URL}/api/refresh`;
    return axios.get(url);
}

function login(email, password) {
    const url = `${BASE_URL}/oauth/token?grant_type=password&username=` + email + `&password=` + password;
    return axios.post(url).then(response => response.data);
}