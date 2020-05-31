package com.example.splitwise.splitwise.service

import com.example.splitwise.splitwise.dto.request.BillGenerateDto
import com.example.splitwise.splitwise.dto.request.BillUpdateDto
import com.example.splitwise.splitwise.dto.request.IncludeUserOnBillDto
import com.example.splitwise.splitwise.module.Bill
import com.example.splitwise.splitwise.module.UserBill

interface BillService {
    fun generateBill(billGenerateDto: BillGenerateDto): Bill
    fun getBill(billId: Long): Bill
    fun updateBill(billUpdateDto: BillUpdateDto): Bill
    fun isBillExist(billId: Long)
    fun isBillPresent(billId: Long)
    fun deleteBill(billId: Long): Bill
    fun undoBill(billId: Long): Bill
    fun includeNewUsers(includeUserOnBillDto: IncludeUserOnBillDto) : Bill
    fun saveBill(bill: Bill)
}