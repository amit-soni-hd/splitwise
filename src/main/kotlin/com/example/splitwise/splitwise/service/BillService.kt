package com.example.splitwise.splitwise.service

import com.example.splitwise.splitwise.dto.BillDto
import com.example.splitwise.splitwise.module.Bill

interface BillService {
    fun generateBill(billDto: BillDto): Bill
    fun getBill(billId:Long): Bill?
}