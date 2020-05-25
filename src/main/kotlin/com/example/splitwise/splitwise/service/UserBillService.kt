package com.example.splitwise.splitwise.service

import com.example.splitwise.splitwise.module.UserBill

interface UserBillService {
    fun getUserIdsByBillId(billId:Long): List<UserBill>
    fun getBillIdsByUserId(userId:Long): List<UserBill>
}