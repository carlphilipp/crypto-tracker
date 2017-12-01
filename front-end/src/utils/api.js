import axios from 'axios';

const BASE_URL = 'http://localhost:8180';

export {getAllTickers, getOneUser};

function getAllTickers() {
    const url = `${BASE_URL}/api/ticker`;
    return axios.get(url).then(response => response.data);
}

function getOneUser() {
    const url = `${BASE_URL}/api/user/1`;
    return axios.get(url).then(response => response.data);
}
