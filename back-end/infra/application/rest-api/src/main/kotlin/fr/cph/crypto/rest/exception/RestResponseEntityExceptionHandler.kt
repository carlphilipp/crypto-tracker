/**
 * Copyright 2017 Carl-Philipp Harmant
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