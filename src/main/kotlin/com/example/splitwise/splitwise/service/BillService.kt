package com.example.splitwise.splitwise.service

import com.example.splitwise.splitwise.dto.BillGenerateDto
import com.example.splitwise.splitwise.dto.BillUpdateDto
import com.example.splitwise.splitwise.module.Bill

interface BillService {
    fun generateBill(billGenerateDto: BillGenerateDto): Bill
    fun getBill(billId: Long): Bill
    fun updateBill(billUpdateDto: BillUpdateDto): Bill
}