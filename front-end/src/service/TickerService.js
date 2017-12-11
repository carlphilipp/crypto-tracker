import {refreshTickers, getAllTickers} from '../utils/ApiClient';

export function getCurrentTickers() {
    return getAllTickers().then(response => response.data);
}

export function refreshCurrentTickers() {
    return refreshTickers().then(response => response.data);
}
