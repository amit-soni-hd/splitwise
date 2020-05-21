package code.service

import code.enums.BillStatus
import code.module.Balance
import code.module.Bill

class BalanceServiceImpl : BalanceService {

    var usersBalance: MutableMap<Long, Balance>? = null

    init {
        usersBalance = mutableMapOf()
    }

    override fun getTotalBalance(userId: Long): Map<String, Long> {

        val debtors = usersBalance?.get(userId)?.debtors
        val creditors = usersBalance?.get(userId)?.creditors

        var debitBalance: Long = 0
        var creditBalance: Long = 0

        debtors?.values?.forEach { bills ->
            run {
                debitBalance += getBalance(bills)
            }
        }

        creditors?.values?.forEach { bills ->
            run {
                creditBalance += getBalance(bills)
            }
        }
        return mapOf<String, Long>("User_Ows" to debitBalance, "You_Ows" to creditBalance)
    }

    override fun getIndividualBalance(userId: Long, individualUserId: Long): Map<String, Long> {

        var balance: MutableMap<String, Long> = mutableMapOf()

        val debtors = getDebtors(userId)
        val creditors = getCreditors(userId)

        val debtorsBills = debtors?.get(individualUserId)
        val creditorsBills = creditors?.get(individualUserId)

        val debitBalance = getBalance(debtorsBills)
        val creditBalance = getBalance(creditorsBills)

        balance.put("He_Ows", debitBalance)
        balance.put("You_Ows", creditBalance)

        return balance
    }

    private fun getBalance(bills: List<Bill>?): Long {
        var total: Long = 0
        bills?.forEach { bill ->
            total += bill.amount!!
        }
        return total
    }

    override fun getDebtors(userId: Long): Map<Long, List<Bill>>? {
        var debtors:MutableMap<Long, List<Bill>>? = mutableMapOf()
        usersBalance
            ?.get(userId)
            ?.debtors?.forEach { userId, bills ->
                run {
                    debtors?.put(userId, bills.filter { bill -> bill.status == BillStatus.DUE } )
                }
            }
        return debtors
    }

    override fun getCreditors(userId: Long): MutableMap<Long, List<Bill>>? {
        var creditors:MutableMap<Long, List<Bill>>? = mutableMapOf()
        usersBalance
            ?.get(userId)
            ?.creditors?.forEach { userId, bills ->
                run {
                    creditors?.put(userId, bills.filter { bill -> bill.status == BillStatus.DUE })
                }
            }
        return creditors
    }
}