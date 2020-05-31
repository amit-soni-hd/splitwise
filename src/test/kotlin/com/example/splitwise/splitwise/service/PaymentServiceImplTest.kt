package com.example.splitwise.splitwise.service

import com.example.splitwise.splitwise.dto.request.PaymentDto
import com.example.splitwise.splitwise.enum.PaymentStatus
import com.example.splitwise.splitwise.enum.PaymentType
import com.example.splitwise.splitwise.exception.PaymentException
import com.example.splitwise.splitwise.module.Bill
import com.example.splitwise.splitwise.module.Payment
import com.example.splitwise.splitwise.module.UserBill
import com.example.splitwise.splitwise.repository.PaymentRepository
import org.junit.jupiter.api.*
import org.mockito.ArgumentMatchers.any
import org.mockito.ArgumentMatchers.anyLong
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.modelmapper.ModelMapper
import java.time.LocalDateTime
import kotlin.test.assertEquals

@DisplayName("test payment service")
internal class PaymentServiceImplTest {

    @InjectMocks
    private lateinit var paymentService: PaymentServiceImpl
    @Mock
    private lateinit var modelMapper: ModelMapper
    @Mock
    private lateinit var userService: UserServiceImpl
    @Mock
    private lateinit var userBillService: UserBillServiceImpl
    @Mock
    private lateinit var paymentRepository: PaymentRepository
    @Mock
    private lateinit var billService: BillServiceImpl

    companion object {
        private var payment1 = Payment(paymentId = 1, billId = 1, payerId = 1, receiverId = 2,
                amount = 100.0, paymentType = PaymentType.G_PAY, paymentStatus = PaymentStatus.PENDING)
        private var payment2 = Payment(paymentId = 2, billId = 2, payerId = 2, receiverId = 1,
                amount = 2000.0, paymentType = PaymentType.G_PAY, paymentStatus = PaymentStatus.COMPLETE)
        private var userBill = UserBill(id = 1, userId = 1, billId = 1, ownerId = 2, userShare = 100.0, paymentStatus = PaymentStatus.PENDING,dueAmount = 100.0)


        private var paymentDto = PaymentDto(payerId = 1, billId = 1, receiverId = 2, amount = 1000.0, paymentType = PaymentType.G_PAY)

    }


    @BeforeEach
    fun setUp() = MockitoAnnotations.initMocks(this)

    @Nested
    @DisplayName("settle the bill fully of partial")
    inner class Settle {

        @Test
        @DisplayName("pay the will partial or full")
        fun `settle the bill`() {
            userBill.paymentStatus = PaymentStatus.PENDING
            payment1.amount = 100.0
            `when`(modelMapper.map(paymentDto, Payment::class.java)).thenReturn(payment1)
            `when`(userBillService.getUserBill(anyLong(), anyLong())).thenReturn(userBill)
            `when`(paymentRepository.save(any(Payment::class.java))).thenReturn(payment1)
            val payBill = paymentService.payBill(paymentDto)

            assertAll("check payment details",
                    { assertEquals(payBill.paymentStatus, PaymentStatus.COMPLETE) },
                    { assertEquals(payBill.amount, payment1.amount) },
                    { assertEquals(payBill.receiverId, payment1.receiverId) }
            )
        }

        @Test
        @DisplayName("pay bill more the share")
        fun `pay more then share`() {
            payment1.amount += 100.0
            `when`(modelMapper.map(paymentDto, Payment::class.java)).thenReturn(payment1)
            `when`(userBillService.getUserBill(anyLong(), anyLong())).thenReturn(userBill)

            val expectedException = assertThrows<PaymentException>("Should throw an exception") {
                paymentService.payBill(paymentDto)
            }
            assertAll("check exception",
                    {
                        assertEquals("You are paying ${payment1.amount} while due amount is : ${userBill.dueAmount}"
                                , expectedException.message)
                    },
                    { assertEquals(payment1.paymentStatus, PaymentStatus.PENDING) }
            )
        }

        @Test
        @DisplayName("bill already paid")
        fun `bill already paid exception`() {
            userBill.paymentStatus = PaymentStatus.COMPLETE
            `when`(modelMapper.map(paymentDto, Payment::class.java)).thenReturn(payment1)
            `when`(userBillService.getUserBill(anyLong(), anyLong())).thenReturn(userBill)

            val expectedException = assertThrows<PaymentException>("Should throw an exception") {
                paymentService.payBill(paymentDto)
            }
            assertAll("check exception",
                    {
                        assertEquals("The bill ${payment1.billId} is not due for user id ${payment1.payerId}"
                                , expectedException.message)
                    },
                    { assertEquals(payment1.paymentStatus, PaymentStatus.PENDING) }
            )
        }
    }

    @Test
    @DisplayName("get all received transaction by user ")
    fun getAllReceivedTransactionByUserId() {
        `when`(paymentRepository.findAllByReceiverId(receiverId = anyLong())).thenReturn(listOf(payment2))
        val transactionByUserId = paymentService.getAllReceivedTransactionByUserId(receiverId =1).toList()
        assertAll("check payments by payer id",
                { assertEquals(transactionByUserId[0].paymentId, payment2.paymentId) },
                { assertEquals(transactionByUserId.size, 1) },
                { assertEquals(transactionByUserId[0].receiverId, payment2.receiverId) }
        )
    }
    @Test
    @DisplayName("get all transaction of bill ")
    fun getPaymentsOfBill() {
        `when`(paymentRepository.findByPayerIdAndBillId(userId = anyLong(),billId = anyLong())).thenReturn(listOf(payment1))
        val payments = paymentService.getPaymentsOfBill(userId = 1, billId = 1).toList()
        assertAll("check payments by payer id",
                { assertEquals(payments[0].paymentId, payment1.paymentId) },
                { assertEquals(payments.size, 1) },
                { assertEquals(payments[0].receiverId, payment1.receiverId) }
        )
    }
    @Test
    @DisplayName("get all paid transaction by user ")
    fun getAllPaidTransactionByUserId() {
        `when`(paymentRepository.findAllByPayerId(payerId = anyLong())).thenReturn(listOf(payment1))
        val transactionByUserId = paymentService.getAllPaidTransactionByUserId(payerId = 1).toList()
        assertAll("check payments by payer id",
                { assertEquals(transactionByUserId[0].paymentId, payment1.paymentId) },
                { assertEquals(transactionByUserId.size, 1) },
                { assertEquals(transactionByUserId[0].receiverId, payment1.receiverId) }
        )
    }

    @Test
    @DisplayName("get all transaction by user id")
    fun getAllTransactionByUserId() {
        `when`(paymentRepository.findAllByPayerIdOrReceiverId(payerId = anyLong(), receiverId = anyLong())).thenReturn(listOf(payment1, payment2))
        val transactionByUserId = paymentService.getAllTransactionByUserId(1).toList()
        assertAll("check payments by payer id",
                { assertEquals(transactionByUserId[0].paymentId, payment1.paymentId) },
                { assertEquals(transactionByUserId.size, 2) },
                { assertEquals(transactionByUserId[1].receiverId, payment2.receiverId) }
        )
    }

    @Test
    @DisplayName("get payments by bill id")
    fun getPaymentsByBillId() {
        `when`(paymentRepository.findAllByBillId(1)).thenReturn(listOf(payment1, payment2))
        val paymentsByPayerId = paymentService.getPaymentsByBillId(1).toList()
        assertAll("check payments by payer id",
                { assertEquals(paymentsByPayerId[0].paymentId, payment1.paymentId) },
                { assertEquals(paymentsByPayerId.toList().size, 2) },
                { assertEquals(paymentsByPayerId.toList().get(0).billId, payment1.billId) }
        )
    }
}