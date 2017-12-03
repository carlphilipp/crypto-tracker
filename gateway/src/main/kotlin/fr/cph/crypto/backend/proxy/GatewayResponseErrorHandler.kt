package fr.cph.crypto.backend.proxy

import org.springframework.http.client.ClientHttpResponse
import org.springframework.web.client.ResponseErrorHandler

/**
 * We do not want to handle upstream errors here, just proxy the request as is.
 */
class GatewayResponseErrorHandler : ResponseErrorHandler {

    override fun hasError(response: ClientHttpResponse?): Boolean {
        return false
    }

    override fun handleError(response: ClientHttpResponse?) {
    }
}