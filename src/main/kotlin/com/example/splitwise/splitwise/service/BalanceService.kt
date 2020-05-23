package com.example.splitwise.splitwise.service

import com.example.splitwise.splitwise.module.Bill


interface BalanceService {
    fun getTotalBalance(userId: Long): Map<String, Long>
    fun getIndividualBalance(userEmail: String, respectUserEmail: String): Map<String, Long>
}