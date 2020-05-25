package com.example.splitwise.splitwise.service

import com.example.splitwise.splitwise.module.Bill
import com.example.splitwise.splitwise.module.User
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.anyLong
import org.mockito.MockitoAnnotations
import java.time.LocalDateTime
import kotlin.test.assertEquals

internal class BalanceServiceImplTest {

    companion object {
        private var user = User(userId = 1, emailId = "amit@gmail.com", name = "Rajat Verma", contact = "8979710514")
        private var user1 = User(userId = 2, emailId = "verma@gmail.com", name = "Amit Verma", contact = "8979790514")
        private var bill1 = Bill(billId = 1, ownerId = 1, billName = "party", description = "tour of goa", amount = 5000.0, date = LocalDateTime.now(),involvedUser = mutableListOf(user, user1))
        private var bill2 = Bill(billId = 2, ownerId = 2, billName = "party", description = "tour of goa", amount = 4000.0, date = LocalDateTime.now(),involvedUser = mutableListOf(user, user1))
    }

    @InjectMocks
    private lateinit var balanceService: BalanceServiceImpl

    @Mock
    private lateinit var userService: UserServiceImpl

    @Mock
    private lateinit var transactionService: TransactionServiceImpl

    @BeforeEach
    fun setUp() = MockitoAnnotations.initMocks(this)

    @Test
    fun getTotalBalance() {
        `when`(userService.getUserBills(anyLong())).thenReturn(mutableListOf(bill1, bill2))
        `when`(transactionService.getAllTransactionOfBill(anyLong())).thenReturn(emptyList())
        val totalBalance = balanceService.getTotalBalance(1)

        assertAll("check total balance",
                { assertEquals(2000.0, totalBalance.get("Credit")) },
                { assertEquals(2500.0, totalBalance.get("Debit")) }
        )
    }

    @Test
    fun getIndividualBalance() {
        user.bills.addAll(listOf(bill1, bill2))
        `when`(userService.getUserBills(anyLong())).thenReturn(mutableListOf(bill1, bill2))
        `when`(transactionService.getAllTransactionOfBill(anyLong())).thenReturn(emptyList())
        `when`(userService.getUserById(anyLong())).thenReturn(user)
        val totalBalance = balanceService.getIndividualBalance(userId = 1,respectUserId = 2)

        assertAll("check total balance",
                { assertEquals(2000.0, totalBalance.get("Credit")) },
                { assertEquals(2500.0, totalBalance.get("Debit")) }
        )
    }
}