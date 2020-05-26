package com.example.splitwise.splitwise.service

import com.example.splitwise.splitwise.enum.PaymentStatus
import com.example.splitwise.splitwise.module.UserBill
import com.example.splitwise.splitwise.repository.UserBillRepository
import org.springframework.stereotype.Service

@Service
class UserBillServiceImpl(private val userBillRepository: UserBillRepository) : UserBillService {

    override fun getUserBiillsByBillId(billId:Long): List<UserBill> {
        return userBillRepository.findAllByBillId(billId = billId).toList()
    }

    override fun getUserBillsByUserId(userId:Long): List<UserBill> {
        return userBillRepository.findAllByUserId(userId = userId).toList()
    }

    override fun getUserBill(userId: Long, billId: Long): UserBill {
        return userBillRepository.findByUserIdAndBillId(userId = userId, billId = billId).get()
    }

    override fun saveBill(userBill: UserBill): UserBill {
        return userBillRepository.save(userBill)
    }

    override fun getUserPendingAndUserOwnerBills(userId: Long): List<UserBill> {
        return userBillRepository
                .findUserPendingAndUserOwnerBills(userId = userId,paymentStatus = PaymentStatus.PENDING).toList()
    }

    override fun getCommonBill(userId: Long,ownerId:Long): List<UserBill> {
        return userBillRepository.findAllByUserIdAndOwnerId(userId = userId,ownerId = ownerId).toList()
    }


}