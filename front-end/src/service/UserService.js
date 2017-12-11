import {getOneUser} from '../utils/ApiClient';
import {getUserId, getAccessToken} from './AuthService';

export function getCurrentUser() {
    const accessToken = getAccessToken();
    const userId = getUserId();
    return getOneUser(accessToken, userId)
                .then(response => response.data)
                .catch((error) => {
                  if (error.response.status === 401 && error.response.data.error_description.includes("expired")) {
                    console.log("Token expired, logging out...")
                    this.logout()
                  } else {
                    console.log("Unhandled error: " + error)
                  }
                })
}
