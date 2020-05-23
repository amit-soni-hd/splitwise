package com.example.splitwise.splitwise.controller

import com.example.splitwise.splitwise.dto.PaymentDto
import com.example.splitwise.splitwise.dto.ResponseDto
import com.example.splitwise.splitwise.service.PaymentService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/payment")
class PaymentController(val paymentService: PaymentService) {

    @PostMapping("/")
    fun payBill(@RequestBody paymentDto: PaymentDto): ResponseEntity<ResponseDto> {
        val payBill = paymentService.payBill(paymentDto = paymentDto)
        var response = ResponseDto("Successfully paiid", payBill, HttpStatus.ACCEPTED)
        return ResponseEntity.status(HttpStatus.OK).body(response)
    }
}