import {save, get, remove} from './LocalStorageService';
const jwtDecode = require('jwt-decode');
const TOKEN = 'token';

export function isLoggedIn() {
    let token = get(TOKEN)
    return token != null;
}

export function getToken() {
    return get(TOKEN)
}

export function getUserId() {
    let accessToken = JSON.parse(get(TOKEN)).access_token;
    return jwtDecode(accessToken).id;
}

export function getAccessToken() {
    return JSON.parse(get(TOKEN)).access_token;
}

export function storeToken(token) {
    save(TOKEN, JSON.stringify(token))
}

export function logout() {
    remove(TOKEN)
}
