package com.example.splitwise.splitwise.service

import com.example.splitwise.splitwise.module.UserBill
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class BalanceServiceImpl(private val userBillService: UserBillService, val userService: UserService) : BalanceService {

    companion object {
        private var log: Logger = LoggerFactory.getLogger(BalanceServiceImpl::class.java)
    }

    /**
     * function for getting the total balance of user
     * @param userId user id
     * @return map of debit and credit balance and total balance
     */
    override fun getTotalBalance(userId: Long): Map<String, Double> {
        log.info("Get total balance with user id $userId")
        userService.userIdValidation(userId = userId)
        val userBills = userBillService.getUserPendingAndUserOwnerBills(userId = userId)
        val debitBills = mutableListOf<UserBill>()
        val creditBills = mutableListOf<UserBill>()

        userBills.forEach { bill ->
            if (bill.ownerId == userId)
                debitBills.add(bill)
            else
                creditBills.add(bill)
        }

        val debit = getBalance(debitBills, userId)
        val credit = getBalance(creditBills, userId)

        return mapOf("Credit" to credit, "Debit" to debit, "Balance" to debit - credit)
    }


    /**
     * get the individual balance of user
     * @param userId user id
     * @param respectUserId second user id
     * @return map of debit and credit balance and total balance
     */
    override fun getIndividualBalance(userId: Long, respectUserId: Long): Map<String, Double> {
        log.info("get individual balance for user $userId to respect user $respectUserId")
        userService.userIdValidation(userId = userId)
        userService.userIdValidation(userId = respectUserId)
        val respectUserBills = userBillService.getCommonBill(userId = userId, ownerId = respectUserId)
        val userBills = userBillService.getCommonBill(userId = respectUserId, ownerId = userId)

        val debit = getBalance(userBills.toMutableList(), userId = userId)
        val credit = getBalance(respectUserBills.toMutableList(), userId = respectUserId)

        return mapOf("Credit" to credit, "Debit" to debit, "Balance" to debit - credit)

    }

    /**
     * for calculate the balance of bills
     * @param bills list of bill
     * @return balance
     */
    private fun getBalance(bills: MutableList<UserBill>, userId: Long): Double {
        log.info("get balance with user id $userId")
        var balance = 0.0
        bills.forEach { bill ->
            log.info("get bill balance with user id ${bill.userId} bill id : ${bill.billId} and due amount ${bill.dueAmount}")
            balance += bill.dueAmount
        }
        return balance
    }


}