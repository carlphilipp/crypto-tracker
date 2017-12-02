const TOKEN = 'token';

export function isLoggedIn(inc) {
    let token = localStorage.getItem(TOKEN)
    return token != null;
}

export function getToken() {
    return localStorage.getItem(TOKEN)
}

export function storeToken(token) {
    localStorage.setItem(TOKEN, token)
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
