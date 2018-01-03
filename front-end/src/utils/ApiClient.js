/**
 * Copyright 2018 Carl-Philipp Harmant
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import axios from 'axios';

// FIXME pass that url through env variable
const BASE_URL = process.env.NODE_ENV === 'production'
    ? 'https://stocktracker.fr/gateway'
    : 'http://localhost:8180';

// Tickers
export function getAllTickers() {
    const url = `${BASE_URL}/api/ticker`;
    return axios.get(url);
}

export function refreshTickers() {
    const url = `${BASE_URL}/api/refresh`;
    return axios.get(url);
}

// Users
export function createUser(user) {
    const url = `${BASE_URL}/api/user`;
    return axios.post(url, user).then(response => response.data);
}

export function getOneUser(accessToken, userId) {
    const url = `${BASE_URL}/api/user/` + userId;
    const config = createConfig(accessToken);
    return axios.get(url, config);
}

export function addPosition(accessToken, id, position) {
  const url = `${BASE_URL}/api/user/` + id + `/position`;
  const config = createConfig(accessToken);
  return axios.post(url, position, config);
}

export function updatePosition(accessToken, id, transactionQuantity, transactionUnitCostPrice, position) {
  const url = `${BASE_URL}/api/user/` + id + `/position/` + position.id;
  const config = {headers: {'Authorization': 'Bearer ' + accessToken}, params: {transactionQuantity: transactionQuantity, transactionUnitCostPrice: transactionUnitCostPrice}};
  return axios.put(url, position, config);
}

export function deletePosition(accessToken, id, positionId, price) {
  const url = `${BASE_URL}/api/user/` + id + `/position/` + positionId + `/` + price;
  const config = createConfig(accessToken);
  return axios.delete(url, config);
}

export function addFeeToPosition(accessToken, id, positionId, feeAmount) {
  const url = `${BASE_URL}/api/user/` + id + `/position/` + positionId + `/fee/` + feeAmount;
  const config = createConfig(accessToken);
  return axios.post(url, null, config);
}

export function getAllShareValue(accessToken, userId) {
    const url = `${BASE_URL}/api/user/` + userId + `/sharevalue`;
    const config = createConfig(accessToken);
    return axios.get(url, config);
}

export function login(email, password) {
    const url = `${BASE_URL}/oauth/token?grant_type=password&username=` + email + `&password=` + password;
    return axios.post(url);
}

export function validateUser(userId, key) {
    const url = `${BASE_URL}/api/user/` + userId + `/validate/` + key;
    return axios.get(url);
}

function createConfig(accessToken) {
  return {headers: {'Authorization': 'Bearer ' + accessToken}};
}
