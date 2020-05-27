package com.example.splitwise.splitwise.service

import com.example.splitwise.splitwise.module.Payment
import com.example.splitwise.splitwise.module.User

interface ReportGenerateService {
    fun generateReport(): MutableMap<User, MutableMap<MutableMap<String, Double>, List<Payment>>>
}