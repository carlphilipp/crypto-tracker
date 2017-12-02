const TOKEN = 'token';

export function isLoggedIn(inc) {
    let token = localStorage.getItem(TOKEN)
    console.log("isLoggedIn " + token)
    return token != null;
}

export function getToken() {
    return localStorage.getItem(TOKEN)
}

export function storeToken(token) {
    console.log("Store token:   " + token)
    localStorage.setItem(TOKEN, token)
}

export function login() {
}

export function logout() {
}

export function setIdToken() {
}

export function setAccessToken() {
}
