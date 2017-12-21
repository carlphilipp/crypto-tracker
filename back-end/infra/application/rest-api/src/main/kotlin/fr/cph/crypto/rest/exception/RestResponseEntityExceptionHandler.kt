package fr.cph.crypto.rest.exception

import com.mongodb.DuplicateKeyException
import org.slf4j.LoggerFactory
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler


@ControllerAdvice
class RestResponseEntityExceptionHandler : ResponseEntityExceptionHandler() {

    @ExceptionHandler(value = [DuplicateKeyException::class])
    protected fun duplicateKeyException(ex: RuntimeException, request: WebRequest): ResponseEntity<Any> {
        return handleExceptionInternal(ex, null, HttpHeaders(), HttpStatus.BAD_REQUEST, request)
    }

    @ExceptionHandler(value = [NotAllowedException::class])
    protected fun notAllowedException(ex: RuntimeException, request: WebRequest): ResponseEntity<Any> {
        return handleExceptionInternal(ex, null, HttpHeaders(), HttpStatus.UNAUTHORIZED, request)
    }

    @ExceptionHandler(value = [Exception::class])
    protected fun handleWebServerException(ex: Exception, request: WebRequest): ResponseEntity<Any> {
        LOGGER.error("Error: {}", ex.message, ex)
        return handleExceptionInternal(ex, null, HttpHeaders(), HttpStatus.BAD_REQUEST, request)
    }

    companion object {
        private val LOGGER = LoggerFactory.getLogger(RestResponseEntityExceptionHandler::class.java)
    }
}

class NotAllowedException : RuntimeException()