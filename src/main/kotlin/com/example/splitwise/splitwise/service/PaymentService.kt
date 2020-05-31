package com.example.splitwise.splitwise.service

import com.example.splitwise.splitwise.dto.request.PaymentDto
import com.example.splitwise.splitwise.module.Payment

interface PaymentService {
    fun payBill(paymentDto: PaymentDto): Payment
    fun getPaymentsByBillId(billId: Long): Iterable<Payment>
    fun getAllTransactionByUserId(userId: Long): Iterable<Payment>
    fun getPaymentsOfBill(userId: Long, billId: Long): List<Payment>
    fun getAllPaidTransactionByUserId(payerId:Long): List<Payment>
    fun getAllReceivedTransactionByUserId(receiverId:Long): List<Payment>
}