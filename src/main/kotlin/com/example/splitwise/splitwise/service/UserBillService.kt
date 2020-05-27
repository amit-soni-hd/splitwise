package com.example.splitwise.splitwise.service

import com.example.splitwise.splitwise.module.UserBill

interface UserBillService {
    fun getUserBillsByBillId(billId: Long): List<UserBill>
    fun getUserBillsByUserId(userId: Long): List<UserBill>
    fun getUserBill(userId: Long, billId: Long): UserBill
    fun saveBill(userBill: UserBill): UserBill
    fun getUserPendingAndUserOwnerBills(userId: Long): List<UserBill>
    fun getCommonBill(userId: Long,ownerId:Long): List<UserBill>
    fun saveAllBill(usersBill: MutableList<UserBill>)
}