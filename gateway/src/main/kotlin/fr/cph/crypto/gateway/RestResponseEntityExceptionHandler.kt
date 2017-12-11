package fr.cph.crypto.gateway

import org.slf4j.LoggerFactory
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.net.ConnectException

@ControllerAdvice
class RestResponseEntityExceptionHandler : ResponseEntityExceptionHandler() {

    @ExceptionHandler(value = [ConnectException::class])
    protected fun connectException(ex: ConnectException, request: WebRequest): ResponseEntity<Any> {
        LOGGER.warn("Connection exception {}", request)
        return handleExceptionInternal(ex, null, HttpHeaders(), HttpStatus.BAD_GATEWAY, request)
    }

    companion object {
        private val LOGGER = LoggerFactory.getLogger(RestResponseEntityExceptionHandler::class.java)
    }
}