import axios from 'axios';

const BASE_URL = 'http://localhost:8180';

export {getAllTickers, getOneUser, createUser};

function getAllTickers() {
    const url = `${BASE_URL}/api/ticker`;
    return axios.get(url).then(response => response.data);
}

function getOneUser() {
    const url = `${BASE_URL}/api/user/1`;
    return axios.get(url).then(response => response.data);
}

function createUser() {
    const url = `${BASE_URL}/api/user`;
    return axios.post(url, {
        email: 'cp.derp2@gmail.com',
        password: 'password',
    }).then(response => response.data)
    .catch(function (error) {
    console.log(error);
  });
}
