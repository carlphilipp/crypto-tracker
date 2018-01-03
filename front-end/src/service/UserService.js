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
import {createUser, getOneUser, addPosition, deletePosition, updatePosition, getAllShareValue, login, addFeeToPosition, validateUser} from '../utils/ApiClient';
import {getUserId, getAccessToken} from './AuthService';


// FIXME handle expired token in all functions of that file
export function createNewUser(email, password) {
  return createUser({ email: email, password: password }).then(response => response.data);
}

export function getCurrentUser(callback) {
    const accessToken = getAccessToken();
    const userId = getUserId();
    return getOneUser(accessToken, userId)
                .then(response => response.data)
                .catch((error) => {
                  if (error.response.status === 401 && error.response.data.error_description.includes("expired")) {
                    console.log("Token expired, logging out...")
                    callback()
                  } else {
                    console.log("Unhandled error: " + error)
                  }
                });
}

export function addPositionToCurrentUser(ticker, quantity, unitCostPrice) {
    const accessToken = getAccessToken();
    const userId = getUserId();
    return addPosition(accessToken, userId, {
        currency1: ticker.currency1,
        currency2: ticker.currency2,
        quantity: quantity,
        unitCostPrice: unitCostPrice
      });
}

export function updateOnePosition(positionId, currency1, currency2, quantity, unitCostPrice, transactionQuantity, transactionUnitCostPrice) {
    // FIXME currency symbols are not properly encoded
    const accessToken = getAccessToken();
    const userId = getUserId();
    return updatePosition(accessToken, userId, transactionQuantity, transactionUnitCostPrice,
      {
        id: positionId,
        currency1: currency1,
        currency2: currency2,
        quantity: quantity,
        unitCostPrice: unitCostPrice
      });
}

export function deletePositionFromCurrentUser(positionId, price) {
    const accessToken = getAccessToken();
    const userId = getUserId();
    return deletePosition(accessToken, userId, positionId, price);
}

export function addFeePositionToCurrentUser(positionId, feeAmount) {
    const accessToken = getAccessToken();
    const userId = getUserId();
    return addFeeToPosition(accessToken, userId, positionId, feeAmount);
}

export function getCurrentUserShareValue() {
    const accessToken = getAccessToken();
    const userId = getUserId();
    return getAllShareValue(accessToken, userId).then(response => response.data);
}

export function loginUser(email, password) {
    return login(email, password).then(response => response.data)
}

export function validateGivenUser(userId, key) {
    return validateUser(userId, key)
}
