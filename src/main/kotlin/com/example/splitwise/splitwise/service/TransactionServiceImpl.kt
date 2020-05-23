package com.example.splitwise.splitwise.service

import com.example.splitwise.splitwise.enum.BillStatus
import com.example.splitwise.splitwise.module.Payment
import com.example.splitwise.splitwise.repository.PaymentRepository
import org.springframework.stereotype.Service

@Service
class TransactionServiceImpl(val userService: UserService, val billService: BillService, val paymentRepository: PaymentRepository) : TransactionService {

    override fun getAllTransaction(userId: Long): List<Payment> {
        userService.userIdValidation(userId = userId)
        return paymentRepository.findByPayerId(userId = userId).toList()
    }

    override fun getAllTransactionOfBill(billId: Long): List<Payment> {
        billService.isBillExist(billId = billId)
        return paymentRepository.findByBillId(billId = billId).toList()
    }

}