package com.usbbog.proyectovocacional.backend.infrastructure.config.exception

//import org.springframework.security.access.AccessDeniedException
//import org.springframework.security.core.AuthenticationException
import jakarta.servlet.http.HttpServletRequest
import org.slf4j.LoggerFactory
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.core.AuthenticationException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.server.ResponseStatusException
import java.time.LocalDateTime

@RestControllerAdvice
class GlobalExceptionHandler {

    private val logger = LoggerFactory.getLogger(GlobalExceptionHandler::class.java)

    // 400
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleBadRequest(
        ex: MethodArgumentNotValidException,
        request: HttpServletRequest
    ): ResponseEntity<ErrorResponse> {

        val response = ErrorResponse(
            LocalDateTime.now(),
            400,
            "BAD_REQUEST",
            ex.bindingResult.fieldErrors.first().defaultMessage
                ?: "Solicitud inválida.",
            request.requestURI
        )

        return ResponseEntity(response, HttpStatus.BAD_REQUEST)
    }

    // 401
    @ExceptionHandler(AuthenticationException::class)
    fun handleUnauthorized(
        ex: AuthenticationException,
        request: HttpServletRequest
    ): ResponseEntity<ErrorResponse> {

        val response = ErrorResponse(
            LocalDateTime.now(),
            401,
            "UNAUTHORIZED",
            "Credenciales inválidas.",
            request.requestURI
        )

        return ResponseEntity(response, HttpStatus.UNAUTHORIZED)
    }

    // 403
    @ExceptionHandler(AccessDeniedException::class)
    fun handleForbidden(
        ex: AccessDeniedException,
        request: HttpServletRequest
    ): ResponseEntity<ErrorResponse> {

        val response = ErrorResponse(
            LocalDateTime.now(),
            403,
            "FORBIDDEN",
            "No tiene permisos para acceder a este recurso.",
            request.requestURI
        )

        return ResponseEntity(response, HttpStatus.FORBIDDEN)
    }

    // 404, 400, 409, etc. (ResponseStatusException)
    @ExceptionHandler(ResponseStatusException::class)
    fun handleResponseStatusException(
        ex: ResponseStatusException,
        request: HttpServletRequest
    ): ResponseEntity<ErrorResponse> {

        val response = ErrorResponse(
            timestamp = LocalDateTime.now(),
            status = ex.statusCode.value(),
            error = ex.statusCode.toString(),
            message = ex.reason ?: "Error.",
            path = request.requestURI
        )

        return ResponseEntity(response, ex.statusCode)
    }

    // 409
    @ExceptionHandler(DataIntegrityViolationException::class)
    fun handleConflict(
        ex: DataIntegrityViolationException,
        request: HttpServletRequest
    ): ResponseEntity<ErrorResponse> {

        val response = ErrorResponse(
            LocalDateTime.now(),
            409,
            "CONFLICT",
            "No fue posible completar la operación debido a una restricción de integridad.",
            request.requestURI
        )

        return ResponseEntity(response, HttpStatus.CONFLICT)
    }

    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleHttpMessageNotReadable(
        ex: HttpMessageNotReadableException,
        request: HttpServletRequest
    ): ResponseEntity<ErrorResponse> {

        return ResponseEntity(
            ErrorResponse(
                timestamp = LocalDateTime.now(),
                status = HttpStatus.BAD_REQUEST.value(),
                error = "Bad Request",
                message = "El cuerpo de la petición es inválido o faltan campos obligatorios.",
                path = request.requestURI
            ),
            HttpStatus.BAD_REQUEST
        )
    }

    // 500
    @ExceptionHandler(Exception::class)
    fun handleException(
        ex: Exception,
        request: HttpServletRequest
    ): ResponseEntity<ErrorResponse> {

        logger.error("Error inesperado.", ex)

        val response = ErrorResponse(
            timestamp = LocalDateTime.now(),
            status = 500,
            error = "INTERNAL_SERVER_ERROR",
            message = "Ha ocurrido un error inesperado.",
            path = request.requestURI
        )

        return ResponseEntity(
            response,
            HttpStatus.INTERNAL_SERVER_ERROR
        )
    }
}