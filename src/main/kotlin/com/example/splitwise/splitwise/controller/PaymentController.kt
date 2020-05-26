package com.example.splitwise.splitwise.controller

import com.example.splitwise.splitwise.dto.request.PaymentDto
import com.example.splitwise.splitwise.dto.response.ResponseDto
import com.example.splitwise.splitwise.service.PaymentService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/payment")
class PaymentController(val paymentService: PaymentService) {

    @PostMapping("/")
    fun payBill(@RequestBody paymentDto: PaymentDto): ResponseEntity<ResponseDto> {
        val payBill = paymentService.payBill(paymentDto = paymentDto)
        var response = ResponseDto("Successfully paiid", payBill, HttpStatus.ACCEPTED)
        return ResponseEntity.status(HttpStatus.OK).body(response)
    }

    @GetMapping("/user/{userId}")
    fun getAllTransaction(@PathVariable("userId") userId: Long): ResponseEntity<ResponseDto> {
        val allTransaction = paymentService.getAllTransactionByUserId(payerId = userId)
        var response = ResponseDto("Successfully fetch all transaction ", allTransaction, HttpStatus.ACCEPTED)
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response)
    }

    @GetMapping("/bill/{billId}")
    fun getAllTransactionOfBill(@PathVariable("billId") billId: Long): ResponseEntity<ResponseDto> {
        val allTransactionOfBill = paymentService.getPaymentsByBillId(billId = billId)
        var response = ResponseDto("Successfully fetch all transaction of bill id $billId",
                allTransactionOfBill, HttpStatus.ACCEPTED)
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response)
    }
}