package com.example.splitwise.splitwise.exception

import com.example.splitwise.splitwise.dto.ResponseDto
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import javax.servlet.http.HttpServletRequest

@RestControllerAdvice
class ExceptionHandlerController {

    companion object {
        var log = LoggerFactory.getLogger(ExceptionHandlerController::class.java)
    }

    @ExceptionHandler(BillNotFoundException::class)
    fun handleBillNotFoundException(request: HttpServletRequest, e: Exception): ResponseEntity<ResponseDto> {
        log.info("catch bill exception with local message ${e.localizedMessage} and message ${e.message}")

        var response = ResponseDto(e.localizedMessage, null, HttpStatus.NOT_FOUND)
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(response)
    }

    @ExceptionHandler(UserNotFoundException::class)
    fun handleUserNotFoundException(request: HttpServletRequest, e: Exception): ResponseEntity<ResponseDto> {
        log.info("catch user not found exception with local message ${e.localizedMessage} and message ${e.message}")

        var response = ResponseDto(e.localizedMessage, null, HttpStatus.NOT_FOUND)
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(response)
    }

    @ExceptionHandler(PaymentException::class)
    fun handlePaymentException(request: HttpServletRequest, e: Exception): ResponseEntity<ResponseDto> {
        log.info("Catch payment exception with local message ${e.localizedMessage} and message ${e.message}")

        var response = ResponseDto(e.localizedMessage, null, HttpStatus.NOT_FOUND)
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(response)
    }

    @ExceptionHandler(UserExistException::class)
    fun handleUserExistException(request: HttpServletRequest, e: Exception): ResponseEntity<ResponseDto> {
        log.info("Catch user exist exception with local message ${e.localizedMessage} and message ${e.message}")

        var response = ResponseDto(e.localizedMessage, null, HttpStatus.NOT_ACCEPTABLE)
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(response)
    }
}