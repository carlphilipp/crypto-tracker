import {createUser, getOneUser, addPosition, deletePosition, updatePosition, getAllShareValue, login} from '../utils/ApiClient';
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
        currency1: ticker,
        currency2: 'USD',
        quantity: quantity,
        unitCostPrice: unitCostPrice
      });
}

export function updateOnePosition(positionId, ticker, quantity, unitCostPrice, transactionQuantity, transactionUnitCostPrice) {
    const accessToken = getAccessToken();
    const userId = getUserId();
    return updatePosition(accessToken, userId, transactionQuantity, transactionUnitCostPrice,
      {
        id: positionId,
        currency1: ticker,
        currency2: 'USD',
        quantity: quantity,
        unitCostPrice: unitCostPrice
      });
}

export function deletePositionFromCurrentUser(positionId, price) {
    const accessToken = getAccessToken();
    const userId = getUserId();
    return deletePosition(accessToken, userId, positionId, price);
}

export function getCurrentUserShareValue() {
    const accessToken = getAccessToken();
    const userId = getUserId();
    return getAllShareValue(accessToken, userId).then(response => response.data);
}

export function loginUser(email, password) {
    return login(email, password).then(response => response.data)
}