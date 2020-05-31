package com.example.splitwise.splitwise.service

import com.example.splitwise.splitwise.module.Payment
import com.example.splitwise.splitwise.module.User
import com.google.gson.Gson
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.io.File
import java.nio.charset.Charset
import java.time.LocalDate

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

    @Scheduled(cron = "0 0 12 1 * * ?")
    fun getReport() {
        val json = Gson()
        var jsonString:String = json.toJson(generateReport())
        val file = File("src/main/resources/report-" + LocalDate.now())
        file.writeText(jsonString, charset =  Charset.forName("UTF_8"))
    }
}