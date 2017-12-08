import axios from 'axios';
const BASE_URL = 'http://localhost:8180';
export {getAllTickers, getOneUser, createUser, login, refreshTickers, addPosition, deletePosition, updatePosition, getAllShareValue};

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

function addPosition(accessToken, id, ticker, quantity, unitCostPrice) {
  const url = `${BASE_URL}/api/user/` + id + `/position`;
  const config = {headers: {'Authorization': 'Bearer ' + accessToken}};
  return axios.post(url, {
      currency1: ticker,
      currency2: 'USD',
      quantity: quantity,
      unitCostPrice: unitCostPrice
    }, config);
}

function updatePosition(accessToken, id, positionId, ticker, quantity, unitCostPrice) {
  const url = `${BASE_URL}/api/user/` + id + `/position/` + positionId;
  const config = {headers: {'Authorization': 'Bearer ' + accessToken}};
  return axios.put(url, {
      id: positionId,
      currency1: ticker,
      currency2: 'USD',
      quantity: quantity,
      unitCostPrice: unitCostPrice
    }, config);
}

function deletePosition(accessToken, id, positionId, price) {
  console.log("delete position " + positionId)
  const url = `${BASE_URL}/api/user/` + id + `/position/` + positionId + `/` + price;
  const config = {headers: {'Authorization': 'Bearer ' + accessToken}};
  return axios.delete(url, config)
}

function getAllShareValue(accessToken, userId) {
    const url = `${BASE_URL}/api/user/` + userId + `/sharevalue`;
    const config = {headers: {'Authorization': 'Bearer ' + accessToken}};
    return axios.get(url, config).then(response => response.data);
}
