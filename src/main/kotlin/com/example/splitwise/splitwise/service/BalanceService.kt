package code.service

import code.module.Bill

interface BalanceService {
    fun getTotalBalance(userId: Long): Map<String, Long>
    fun getIndividualBalance(userId: Long, individualUserId: Long): Map<String, Long>
    fun getDebtors(userId: Long): Map<Long, List<Bill>>?
    fun getCreditors(userId: Long): MutableMap<Long, List<Bill>>?
}