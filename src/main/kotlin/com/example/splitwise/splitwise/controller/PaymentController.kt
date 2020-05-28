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

    /**
     * post api for pay the bill
     * @param paymentDto details for pay the bill
     * @return ResponseEntity<ResponseDto> which contain the details of status
     */
    @PostMapping("/")
    fun payBill(@RequestBody paymentDto: PaymentDto): ResponseEntity<ResponseDto> {
        val payBill = paymentService.payBill(paymentDto = paymentDto)
        var response = ResponseDto("Successfully paid", payBill, HttpStatus.ACCEPTED)
        return ResponseEntity.status(HttpStatus.OK).body(response)
    }

    /**
     * get api for getting all transaction of user
     * @param userId
     * @return ResponseEntity<ResponseDto> which contain the details of status
     */
    @GetMapping("/user/{userId}")
    fun getAllTransaction(@PathVariable("userId") userId: Long): ResponseEntity<ResponseDto> {
        val allTransaction = paymentService.getAllTransactionByUserId(userId = userId)
        var response = ResponseDto("Successfully fetch all transaction ", allTransaction, HttpStatus.ACCEPTED)
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response)
    }

    /**
     * get api for getting the all transaction of bill
     * @param billId
     * @return ResponseEntity<ResponseDto> which contain the details of status
     */
    @GetMapping("/bill/{billId}")
    fun getAllTransactionOfBill(@PathVariable("billId") billId: Long): ResponseEntity<ResponseDto> {
        val allTransactionOfBill = paymentService.getPaymentsByBillId(billId = billId)
        var response = ResponseDto("Successfully fetch all transaction of bill id $billId",
                allTransactionOfBill, HttpStatus.ACCEPTED)
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response)
    }
}