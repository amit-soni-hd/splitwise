package com.example.splitwise.splitwise.service

import com.example.splitwise.splitwise.module.Payment

interface TransactionService {
    fun getAllTransaction(userId:Long): List<Payment>
    fun getAllTransactionOfBill(billId: Long): List<Payment>
}