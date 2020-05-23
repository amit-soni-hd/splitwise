package com.example.splitwise.splitwise.controller

import com.example.splitwise.splitwise.dto.ResponseDto
import com.example.splitwise.splitwise.service.TransactionService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/transaction")
class TransactionController(val transactionService: TransactionService) {

    @GetMapping("/user/{userId}")
    fun getAllTransaction(@PathVariable("userId") userId: Long): ResponseEntity<ResponseDto> {
        val allTransaction = transactionService.getAllTransaction(userId = userId)
        var response = ResponseDto("Successfully fetch all transaction ", allTransaction, HttpStatus.ACCEPTED)
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response)
    }

    @GetMapping("/bill/{billId}")
    fun getAllTransactionOfBill(@PathVariable("billId") billId: Long): ResponseEntity<ResponseDto> {
        val allTransactionOfBill = transactionService.getAllTransactionOfBill(billId = billId)
        var response = ResponseDto("Successfully fetch all transaction of bill id $billId",
                allTransactionOfBill, HttpStatus.ACCEPTED)
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response)
    }
}