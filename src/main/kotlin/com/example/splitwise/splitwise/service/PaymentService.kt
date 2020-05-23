package com.example.splitwise.splitwise.service

import com.example.splitwise.splitwise.dto.PaymentDto
import com.example.splitwise.splitwise.module.Payment

interface PaymentService {
    fun payBill(paymentDto: PaymentDto): Payment
}