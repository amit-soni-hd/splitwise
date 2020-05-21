package com.example.splitwise.splitwise.service

import com.example.splitwise.splitwise.enum.BillStatus
import com.example.splitwise.splitwise.module.Bill

class BalanceServiceImpl : BalanceService {

    var userService: UserService? = null

    init {
        userService = UserServiceImpl()
    }

    override fun getTotalBalance(userEmail: String): Map<String, Long> {

        var debtorsBill = userService?.getDebtors(userEmail)
        var creditorsBill = userService?.getCreditors(userEmail)
        var debtorsBalance = getTotalBalance(debtorsBill)
        var creditorsBalance = getTotalBalance(creditorsBill)

        return mapOf<String, Long>("Debtors balance : " to debtorsBalance, "Creditors balance : " to creditorsBalance)
    }

    override fun getIndividualBalance(userEmail: String, respectUserEmail: String): Map<String, Long> {
        var debtorsBill = userService?.getDebtors(userEmail)
        var creditorsBill = userService?.getCreditors(userEmail)

        var debitBalance = findBalance(respectUserEmail, debtorsBill)
        var creditBalance = findBalance(respectUserEmail, creditorsBill)

        return mapOf<String, Long>("Debit balance : " to debitBalance, "Creditors balance : " to creditBalance)

    }

    private fun getTotalBalance(bills: List<Bill>?): Long {
        var total: Long = 0
        bills?.forEach { bill ->
            bill.involvedUser?.forEach { (userEmail, status) ->
                run {
                    if (status == BillStatus.DUE) {
                        total += bill.amount?.div(bill.involvedUser!!.size + 1)!!
                    }
                }
            }
        }
        return total
    }

    private fun findBalance(respectUserEmail: String, bills: List<Bill>?): Long {

        var total:Long = 0
        bills?.forEach { bill ->
            bill.involvedUser?.forEach { (userEmail, status) ->
                run {
                    if (userEmail == respectUserEmail && status == BillStatus.DUE) {
                        total += bill.amount?.div(bill.involvedUser!!.size + 1)!!
                    }
                }
            }
        }
        return total
    }

}