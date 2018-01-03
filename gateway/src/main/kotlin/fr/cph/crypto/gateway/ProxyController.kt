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
package fr.cph.crypto.gateway

import fr.cph.crypto.gateway.config.BackendProperties
import org.slf4j.LoggerFactory
import org.springframework.http.*
import org.springframework.web.bind.annotation.*
import org.springframework.web.client.RestTemplate
import java.net.URI
import javax.servlet.http.HttpServletRequest

@CrossOrigin
@RestController
class Controller(private val restTemplate: RestTemplate,
                 private val restTemplateAuth: RestTemplate,
                 private val backendProperties: BackendProperties) {

    private var currentToken: Token? = null

    @RequestMapping(
            value = ["/**"],
            produces = ["application/json"],
            method = [RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE]
    )
    fun proxy(@RequestHeader("Authorization", required = false) authorization: String?,
              @RequestBody(required = false) body: String?,
              method: HttpMethod,
              request: HttpServletRequest): ResponseEntity<String>? {
        LOGGER.debug("Request {}", request.requestURI)
        val uri = URI(backendProperties.scheme, null, backendProperties.host, backendProperties.port!!.toInt(), request.requestURI, request.queryString, null)
        val headers = buildHeaders(authorization)
        val entity = HttpEntity(body, headers)
        val result: ResponseEntity<String>? = restTemplate.exchange(uri, method, entity, String::class.java)
        return if (result!!.statusCode == HttpStatus.UNAUTHORIZED && authorization == null) {
            LOGGER.warn("Unauthorized code received, attempt to get new web token")
            val headers2 = HttpHeaders()
            currentToken = getNewToken()
            headers.add("Authorization", "Bearer " + currentToken!!.access_token)
            headers.add("Content-Type", "application/json")
            val entity2 = HttpEntity(null, headers2)
            return restTemplate.exchange(uri, method, entity2, String::class.java)
        } else {
            result
        }
    }

    @RequestMapping(value = ["/oauth/**"], produces = ["application/json"])
    fun proxyOAuth(method: HttpMethod, request: HttpServletRequest): ResponseEntity<String>? {
        LOGGER.debug("Request OAuth {}", request.requestURI)
        val uri = URI(backendProperties.scheme, null, backendProperties.host, backendProperties.port!!.toInt(), request.requestURI, request.queryString, null)
        val headers = HttpHeaders()
        headers.add("Content-Type", "application/json")
        val entity = HttpEntity(null, headers)
        return restTemplateAuth.exchange(uri, method, entity, String::class.java)
    }

    private fun buildHeaders(authorization: String?): HttpHeaders {
        val headers = HttpHeaders()
        if (authorization != null) {
            headers.add("Authorization", authorization)
        } else {
            if (currentToken == null) {
                currentToken = getNewToken()
            }
            headers.add("Authorization", "Bearer " + currentToken!!.access_token)
        }
        headers.add("Content-Type", "application/json")
        return headers
    }

    private fun getNewToken(): Token {
        val uri = URI(backendProperties.scheme, null, backendProperties.host, backendProperties.port!!.toInt(), "/oauth/token", "grant_type=client_credentials", null)
        return restTemplateAuth.postForObject(uri, null, Token::class.java)
    }

    companion object {
        private val LOGGER = LoggerFactory.getLogger(Controller::class.java)
    }
}

class Token(val access_token: String, val token_type: String, val expires_in: Int, val scope: String, val jti: String)
