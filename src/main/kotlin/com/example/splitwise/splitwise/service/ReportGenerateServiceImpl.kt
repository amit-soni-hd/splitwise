package com.example.splitwise.splitwise.service

import com.example.splitwise.splitwise.module.Payment
import com.example.splitwise.splitwise.module.User
import org.springframework.stereotype.Service

@Service
class ReportGenerateServiceImpl(private val userService: UserService, private val balanceService: BalanceService, private val paymentService: PaymentService) : ReportGenerateService {

    /**
     * function for generate the report
     * @param
     * @return history of transaction and balance of user with user details
     */
    override fun generateReport(): MutableMap<User, MutableMap<MutableMap<String, Double>, List<Payment>>> {
        val allUser = userService.getAllUser()
        val reports: MutableMap<User, MutableMap<MutableMap<String, Double>, List<Payment>>> = mutableMapOf()
        allUser.forEachRemaining { user ->
            run {
                val totalBalance = balanceService.getTotalBalance(userId = user.userId)
                val allTransactionByUserId = paymentService.getAllTransactionByUserId(userId = user.userId)
                val details: MutableMap<MutableMap<String, Double>, List<Payment>> = mutableMapOf()
                details.put(totalBalance.toMutableMap(), allTransactionByUserId.toList())
                reports[user] = details
            }
        }
        return reports
    }
}