package com.example.splitwise.splitwise.service

import com.example.splitwise.splitwise.module.Bill


interface BalanceService {
    fun getTotalBalance(userId: Long): Map<String, Double>
    fun getIndividualBalance(userId: Long, respectUserId: Long): Map<String, Double>
}