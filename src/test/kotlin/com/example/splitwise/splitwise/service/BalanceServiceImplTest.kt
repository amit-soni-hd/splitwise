package com.example.splitwise.splitwise.service

import com.example.splitwise.splitwise.module.Bill
import com.example.splitwise.splitwise.module.UserBill
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
        private var userBill1 = UserBill(id = 1,userId = 1,billId =  1)
        private var userBill2 = UserBill(id = 2,userId = 2,billId =  2)
        private var userBill3 = UserBill(id = 3,userId = 2,billId =  3)
        private var bill1 = Bill(billId = 1, ownerId = 1, billName = "party", description = "tour of goa", amount = 5000.0, date = LocalDateTime.now(),noOfUser = 2)
        private var bill2 = Bill(billId = 2, ownerId = 2, billName = "party", description = "tour of rishikash", amount = 4000.0, date = LocalDateTime.now(), noOfUser = 2)
        private var bill3 = Bill(billId = 3, ownerId = 2, billName = "party", description = "tour of delhi", amount = 9000.0, date = LocalDateTime.now(), noOfUser = 2)

    }

    @InjectMocks
    private lateinit var balanceService: BalanceServiceImpl
    @Mock
    private lateinit var userService: UserServiceImpl
    @Mock
    private lateinit var transactionService: TransactionServiceImpl
    @Mock
    private lateinit var userBillService: UserBillService
    @Mock
    private lateinit var billService: BillService

    @BeforeEach
    fun setUp() = MockitoAnnotations.initMocks(this)

    @Test
    fun getTotalBalance() {
        `when`(userBillService.getBillIdsByUserId(userId = anyLong())).thenReturn(mutableListOf(userBill1, userBill2, userBill3))
        `when`(billService.getBill(billId = 1)).thenReturn(bill1)
        `when`(billService.getBill(billId = 2)).thenReturn(bill2)
        `when`(billService.getBill(billId = 3)).thenReturn(bill3)
        `when`(transactionService.getAllTransactionOfBill(anyLong())).thenReturn(emptyList())
        val totalBalance = balanceService.getTotalBalance(1)

        assertAll("check total balance",
                { assertEquals(6500.0, totalBalance.get("Credit")) },
                { assertEquals(2500.0, totalBalance.get("Debit")) }
        )
    }

    @Test
    fun getIndividualBalance() {
        `when`(userBillService.getBillIdsByUserId(userId = anyLong())).thenReturn(mutableListOf(userBill1, userBill2))
        `when`(billService.getBill(billId = 1)).thenReturn(bill1)
        `when`(billService.getBill(billId = 2)).thenReturn(bill2)
        `when`(transactionService.getAllTransactionOfBill(anyLong())).thenReturn(emptyList())
        val totalBalance = balanceService.getIndividualBalance(userId = 1,respectUserId = 2)

        assertAll("check total balance",
                { assertEquals(2000.0, totalBalance.get("Credit")) },
                { assertEquals(2500.0, totalBalance.get("Debit")) }
        )
    }
}