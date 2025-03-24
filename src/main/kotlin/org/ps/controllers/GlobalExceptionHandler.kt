package org.ps.controllers

import org.ps.dto.responces.ApiResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import javax.validation.ConstraintViolationException

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationException(ex: MethodArgumentNotValidException) = ResponseEntity(
        ApiResponse.error(
        message = ex.bindingResult.fieldErrors.associate { it.field to (it.defaultMessage ?: "Invalid value") }
            .toString()
    ), HttpStatus.BAD_REQUEST)

    @ExceptionHandler(ConstraintViolationException::class)
    fun handleConstraintViolationException(ex: ConstraintViolationException) =
        ResponseEntity(
            ApiResponse.error(
                message = ex.constraintViolations.associate { it.propertyPath.toString() to it.message }.toString()
            ), HttpStatus.BAD_REQUEST
        )

    @ExceptionHandler(Exception::class)
    fun handleGeneralException(ex: Exception) = ResponseEntity(
        ApiResponse.error(message = ex.message ?: "Unknown error"),
        HttpStatus.INTERNAL_SERVER_ERROR
    )
}