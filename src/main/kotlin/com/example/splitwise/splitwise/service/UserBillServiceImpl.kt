package com.example.splitwise.splitwise.service

import com.example.splitwise.splitwise.module.UserBill
import com.example.splitwise.splitwise.repository.UserBillRepository
import org.springframework.stereotype.Service

@Service
class UserBillServiceImpl(private val userBillRepository: UserBillRepository) : UserBillService {

    override fun getUserIdsByBillId(billId:Long): List<UserBill> {
        return userBillRepository.findAllByBillId(billId = billId).toList()
    }

    override fun getBillIdsByUserId(userId:Long): List<UserBill> {
        return userBillRepository.findAllByUserId(userId = userId).toList()
    }

}