package com.example.splitwise.splitwise.service

import com.example.splitwise.splitwise.enum.PaymentStatus
import com.example.splitwise.splitwise.module.UserBill
import org.junit.jupiter.api.*
import org.mockito.ArgumentMatchers
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

@DisplayName("testing balance of user (total or individual)")
internal class BalanceServiceImplTest {

    companion object {
        private var userBill1 = UserBill(id = 1, userId = 2, billId = 1, ownerId = 1, userShare = 100.0, dueAmount = 100.0)
        private var userBill2 = UserBill(id = 2, userId = 3, billId = 1, ownerId = 1, userShare = 100.0, dueAmount = 100.0)
        private var userBill3 = UserBill(id = 3, userId = 1, billId = 2, ownerId = 2, userShare = 200.0, dueAmount = 200.0)
        private var userBill4 = UserBill(id = 4, userId = 3, billId = 2, ownerId = 1, userShare = 200.0, dueAmount = 200.0)
        private var userBill5 = UserBill(id = 5, userId = 1, billId = 3, ownerId = 3, userShare = 300.0, dueAmount = 300.0)

    }

    @InjectMocks
    private lateinit var balanceService: BalanceServiceImpl

    @Mock
    private lateinit var userService: UserServiceImpl

    @Mock
    private lateinit var userBillService: UserBillServiceImpl


    @BeforeEach
    fun setUp() = MockitoAnnotations.initMocks(this)

    @Test
    @DisplayName("get total balance of user")
    fun getTotalBalance() {
        `when`(userBillService.getUserPendingAndUserOwnerBills(userId = anyLong())).thenReturn(mutableListOf(userBill1, userBill2, userBill4, userBill3, userBill5))
        val userPendingAndUserOwnerBills = balanceService.getTotalBalance(userId = 1)
        assertAll("verify userBills bill",
                { Assertions.assertEquals(400.0, userPendingAndUserOwnerBills["Debit"]) },
                { Assertions.assertEquals(500.0, userPendingAndUserOwnerBills["Credit"]) },
                { Assertions.assertEquals(-100.0, userPendingAndUserOwnerBills["Balance"]) }
        )

    }

    @Test
    @DisplayName("get individual balance of user")
    fun getIndividualBalance() {
        `when`(userBillService.getCommonBill(userId = 2, ownerId = 1)).thenReturn(mutableListOf(userBill1))
        `when`(userBillService.getCommonBill(userId = 1, ownerId = 2)).thenReturn(mutableListOf( userBill3))
        val individualBalance = balanceService.getIndividualBalance(userId = 1, respectUserId = 2)
        assertAll("verify userBills bill",
                { Assertions.assertEquals(100.0, individualBalance["Debit"]) },
                { Assertions.assertEquals(200.0, individualBalance["Credit"]) },
                { Assertions.assertEquals(-100.0, individualBalance["Balance"]) }
        )
    }
}