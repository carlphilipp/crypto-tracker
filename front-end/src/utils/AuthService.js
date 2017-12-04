const jwtDecode = require('jwt-decode');
const TOKEN = 'token';

export function isLoggedIn() {
    let token = localStorage.getItem(TOKEN)
    return token != null;
}

export function getToken() {
    return localStorage.getItem(TOKEN)
}

export function getUserId() {
    let accessToken = JSON.parse(localStorage.getItem(TOKEN)).access_token;
    return jwtDecode(accessToken).id;
}

export function getAccessToken() {
    return JSON.parse(localStorage.getItem(TOKEN)).access_token;
}

export function storeToken(token) {
    localStorage.setItem(TOKEN, JSON.stringify(token))
}

export function login() {
}

export function logout() {
    localStorage.removeItem(TOKEN)
}

export function setIdToken() {
}

export function setAccessToken() {
}
