package com.example.splitwise.splitwise.service

import com.example.splitwise.splitwise.module.UserBill

interface UserBillService {
    fun getBillsByBillId(billId:Long): List<UserBill>
    fun getBillsByUserId(userId:Long): List<UserBill>
}