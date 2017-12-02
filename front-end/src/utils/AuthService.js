const TOKEN = 'token';

export function isLoggedIn() {
    return localStorage.getItem(TOKEN) != null;
}

export function storeToken(token){
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
