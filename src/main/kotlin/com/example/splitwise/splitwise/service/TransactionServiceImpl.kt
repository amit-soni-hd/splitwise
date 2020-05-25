package com.example.splitwise.splitwise.service

import com.example.splitwise.splitwise.module.Payment
import com.example.splitwise.splitwise.repository.PaymentRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class TransactionServiceImpl(private val userService: UserService, private val billService: BillService, private val paymentRepository: PaymentRepository) : TransactionService {

    companion object {
        private var log = LoggerFactory.getLogger(TransactionServiceImpl::class.java)
    }

    override fun getAllTransaction(userId: Long): List<Payment> {
        log.info("get all transaction of user $userId")
        userService.userIdValidation(userId = userId)
        return paymentRepository.findByPayerId(userId = userId).toList()
    }

    override fun getAllTransactionOfBill(billId: Long): List<Payment> {
        log.info("get all transaction of bill $billId")
        billService.isBillExist(billId = billId)
        return paymentRepository.findByBillId(billId = billId).toList()
    }

}