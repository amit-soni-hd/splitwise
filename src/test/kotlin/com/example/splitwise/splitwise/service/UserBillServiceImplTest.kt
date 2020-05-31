package com.example.splitwise.splitwise.service

import com.example.splitwise.splitwise.enum.PaymentStatus
import com.example.splitwise.splitwise.module.UserBill
import com.example.splitwise.splitwise.repository.UserBillRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

@DisplayName("testing user bill service")
internal class UserBillServiceImplTest {

    @InjectMocks
    private lateinit var userBillService: UserBillServiceImpl

    @Mock
    private lateinit var userBillRepository: UserBillRepository

    companion object {
        private var userBill1 = UserBill(id = 1, userId = 1, billId = 1, ownerId = 2, userShare = 100.0, paymentStatus = PaymentStatus.PENDING)
        private var userBill2 = UserBill(id = 2, userId = 2, billId = 2, ownerId = 1, userShare = 200.0, paymentStatus = PaymentStatus.PENDING)
        private var userBill3 = UserBill(id = 3, userId = 3, billId = 1, ownerId = 2, userShare = 300.0, paymentStatus = PaymentStatus.PENDING)
        private var userBill4 = UserBill(id = 4, userId = 1, billId = 3, ownerId = 3, userShare = 400.0, paymentStatus = PaymentStatus.COMPLETE)

    }

    @BeforeEach
    fun setUp() = MockitoAnnotations.initMocks(this)

    @Test
    @DisplayName("getting user bills by bill id")
    fun getUserBillsByBillId() {
        `when`(userBillRepository.findAllByBillId(anyLong())).thenReturn(listOf(userBill1, userBill3))
        val billIdsByUserId = userBillService.getUserBillsByBillId(billId = 1)
        assertAll("check user bills",
                { assertEquals(2, billIdsByUserId.size) },
                { assertEquals(userBill1.billId, billIdsByUserId[0].billId) },
                { assertEquals(userBill3.userShare, billIdsByUserId[1].userShare) }
        )
    }

    @Test
    @DisplayName("getting users bill by user id")
    fun getUserBillsByUserId() {
        `when`(userBillRepository.findAllByUserId(anyLong())).thenReturn(listOf(userBill1, userBill4))
        val billIdsByUserId = userBillService.getUserBillsByUserId(userId = 1)
        assertAll("check user bills",
                { assertEquals(2, billIdsByUserId.size) },
                { assertEquals(userBill1.billId, billIdsByUserId[0].billId) },
                { assertEquals(userBill4.userShare, billIdsByUserId[1].userShare) },
                { assertEquals(userBill4.paymentStatus, billIdsByUserId[1].paymentStatus) }
        )
    }

    @Test
    @DisplayName("save the user bill")
    fun saveBill() {
        `when`(userBillRepository.save(any(UserBill::class.java))).thenReturn(userBill1)
        val saveBill = userBillService.saveBill(userBill1)
        assertAll("verify save bill",
                { assertEquals(userBill1.billId, saveBill.billId) },
                { assertEquals(userBill1.userShare, saveBill.userShare) },
                { assertEquals(userBill1.paymentStatus, saveBill.paymentStatus) }
        )
    }

    @Test
    @DisplayName("fetch the user pending and user generate bill")
    fun getUserPendingAndUserOwnerBills() {
        `when`(userBillRepository.findUserPendingAndUserOwnerBills(userId = 1)).thenReturn(mutableListOf(userBill1, userBill3, userBill2))
        val userBills = userBillService.getUserPendingAndUserOwnerBills(1)
        assertAll("verify userBills bill",
                { assertEquals(3, userBills.size) },
                { assertEquals(userBill1.ownerId, userBills[0].ownerId) },
                { assertEquals(userBill2.userShare, userBills[2].userShare) },
                { assertEquals(userBill3.ownerId, userBills[1].ownerId) }
        )
    }

    @Test
    @DisplayName("getting the user common bill")
    fun getCommonBill() {
        `when`(userBillRepository.findAllByUserIdAndOwnerId(1, 2)).thenReturn(mutableListOf(userBill1))
        `when`(userBillRepository.findAllByUserIdAndOwnerId(2, 1)).thenReturn(mutableListOf(userBill2))
        val user1Bills = userBillService.getCommonBill(userId = 1, ownerId = 2)
        val user2Bills = userBillService.getCommonBill(userId = 2, ownerId = 1)

        assertAll("verify userBills bill",
                { assertEquals(userBill1.ownerId, user1Bills[0].ownerId) },
                { assertEquals(userBill2.userId, user2Bills[0].userId) },
                { assertEquals(user1Bills[0].ownerId, user2Bills[0].userId) }
        )

    }
}