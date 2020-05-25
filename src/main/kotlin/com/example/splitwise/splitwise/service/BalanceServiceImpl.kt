package com.example.splitwise.splitwise.service

import com.example.splitwise.splitwise.module.Bill
import com.example.splitwise.splitwise.module.Payment
import com.example.splitwise.splitwise.module.UserBill
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class BalanceServiceImpl(private val billService: BillService, private val userBillService: UserBillService, val userService: UserService, private val transactionService: TransactionService) : BalanceService {

    companion object {
        private var log: Logger = LoggerFactory.getLogger(BalanceServiceImpl::class.java)
    }

    override fun getTotalBalance(userId: Long): Map<String, Double> {
        log.info("Get total balance with user id $userId")
        val userBills = getAllBills(userBillService.getBillIdsByUserId(userId = userId))
        val debitBills = mutableListOf<Bill>()
        val creditBills = mutableListOf<Bill>()

        userBills.forEach { bill ->
            if (bill.ownerId == userId)
                debitBills.add(bill)
            else
                creditBills.add(bill)
        }

        val debit = getDebitBalance(debitBills, userId)
        val credit = getCreditBalance(creditBills, userId)

        return mapOf("Credit" to credit, "Debit" to debit)
    }

    private fun getAllBills(userBills: List<UserBill>): MutableList<Bill> {

        var billList: MutableList<Bill> = mutableListOf()
        userBills.forEach { userBill ->
            run {
                val bill = billService.getBill(userBill.billId)
                billList.add(bill)
            }
        }
        return billList
    }

    override fun getIndividualBalance(userId: Long, respectUserId: Long): Map<String, Double> {
        log.info("get individual balance for user $userId to respect user $respectUserId")
        userService.userIdValidation(userId = userId)
        userService.userIdValidation(userId = respectUserId)
        val billsByUserId = userBillService.getBillIdsByUserId(userId = userId)
        val userBills = findUserBills(getAllBills(billsByUserId), userId)
        val respectUserBills = findUserBills(getAllBills(billsByUserId), respectUserId)


        val debit = getCreditBalance(userBills, respectUserId)
        val credit = getCreditBalance(respectUserBills, userId)

        return mapOf("Credit" to credit, "Debit" to debit)

    }

    private fun getDebitBalance(bills: List<Bill>, receiverId: Long): Double {
        log.info("get debit balance with user id $receiverId")
        var balance = 0.0
        bills.forEach { bill ->
            val sum = transactionService
                    .getAllTransactionOfBill(billId = bill.billId)
                    .filter { payment: Payment -> payment.receiverId == receiverId }
                    .map { payment: Payment -> payment.amount }
                    .sum()
            balance += bill.amount - sum - bill.amount.div(bill.noOfUser)
        }
        return balance
    }

    private fun getCreditBalance(bills: List<Bill>, payerId: Long): Double {
        log.info("get credit balance with user id $payerId")
        var balance = 0.0
        bills.forEach { bill ->
            val sum = transactionService
                    .getAllTransactionOfBill(billId = bill.billId)
                    .filter { payment: Payment -> payment.payerId == payerId }
                    .map { payment: Payment -> payment.amount }
                    .sum()
            balance += bill.amount - sum - bill.amount.div(bill.noOfUser)
        }
        return balance
    }

    private fun findUserBills(bills: List<Bill>, billOwnerId: Long): List<Bill> {
        return bills.filter { bill: Bill ->
            bill.ownerId == billOwnerId
        }
    }
}