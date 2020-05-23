package com.example.splitwise.splitwise.service

import com.example.splitwise.splitwise.module.Bill
import com.example.splitwise.splitwise.module.Payment
import com.example.splitwise.splitwise.module.User
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class BalanceServiceImpl(val userService: UserService, val transactionService: TransactionService) : BalanceService {

    companion object {
        var log: Logger = LoggerFactory.getLogger(BalanceServiceImpl::class.java)
    }

    override fun getTotalBalance(userId: Long): Map<String, Long> {
        log.info("Get total balance with id $userId")
        val userBills = userService.getUserBills(userId = userId)
        var debitBills = mutableListOf<Bill>()
        var creditBills = mutableListOf<Bill>()

        userBills.forEach { bill ->
            if (bill.ownerId == userId)
                debitBills.add(bill)
            else
                creditBills.add(bill)
        }

        var debit = getDebitBalance(debitBills, userId)
        var credit = getCreditBalance(creditBills, userId)

        return mapOf("UserId:" to userId, "Credit : " to credit.toLong(), "Debit : " to debit.toLong())
    }

    override fun getIndividualBalance(userId: Long, respectUserId: Long): Map<String, Long> {
        log.info("get individual balance for user $userId to respect user $respectUserId")
        userService.userIdValidation(userId = userId)
        userService.userIdValidation(userId = respectUserId)
        val userBills = findUserBills(userService.getUserBills(userId = userId), respectUserId)
        val respectUserBills = findUserBills(userService.getUserBills(userId = respectUserId), userId)


        var debit = getCreditBalance(userBills, respectUserId)
        var credit = getCreditBalance(respectUserBills, userId)

        return mapOf("UserId:" to userId, "Individual User id " to respectUserId, "Credit : " to credit.toLong(), "Debit : " to debit.toLong())

    }

    private fun getDebitBalance(bills: List<Bill>, receiverId:Long): Double {
        var balance = 0.0
        bills.forEach { bill ->
            val sum = transactionService
                    .getAllTransactionOfBill(billId = bill.billId)
                    .filter { payment: Payment -> payment.receiverId == receiverId }
                    .map { payment: Payment -> payment.amount }
                    .sum()
            balance += bill.amount - sum
        }
        return balance
    }

    private fun getCreditBalance(bills: List<Bill>, payerId: Long): Double {
        var balance = 0.0
        bills.forEach { bill ->
            val sum = transactionService
                    .getAllTransactionOfBill(billId = bill.billId)
                    .filter { payment: Payment -> payment.payerId == payerId }
                    .map { payment: Payment -> payment.amount }
                    .sum()
            balance += bill.amount - sum
        }
        return balance
    }

    private fun findUserBills(bills: List<Bill>, userId:Long): List<Bill> {
        return bills.filter { bill: Bill ->
            val user = userService.getUser(userId)
            bill.involvedUser.contains(user)
        }
    }
}