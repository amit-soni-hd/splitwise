package com.example.splitwise.splitwise.service

import com.example.splitwise.splitwise.dto.PaymentDto
import com.example.splitwise.splitwise.enum.PaymentStatus
import com.example.splitwise.splitwise.enum.PaymentType
import com.example.splitwise.splitwise.exception.PaymentException
import com.example.splitwise.splitwise.module.Bill
import com.example.splitwise.splitwise.module.Payment
import com.example.splitwise.splitwise.module.User
import com.example.splitwise.splitwise.repository.PaymentRepository
import org.junit.jupiter.api.*
import org.mockito.ArgumentMatchers.any
import org.mockito.ArgumentMatchers.anyLong
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.modelmapper.ModelMapper
import java.time.LocalDateTime
import kotlin.test.assertEquals

@DisplayName("test payment service")
internal class PaymentServiceImplTest {

    @InjectMocks
    private lateinit var paymentService: PaymentServiceImpl

    @Mock
    private lateinit var transactionService: TransactionServiceImpl

    @Mock
    private lateinit var billService: BillServiceImpl

    @Mock
    private lateinit var modelMapper: ModelMapper

    @Mock
    private lateinit var userService: UserServiceImpl

    @Mock
    private lateinit var paymentRepository: PaymentRepository

    companion object {
        private var payment = Payment(paymentId = 1, billId = 1, payerId = 1, receiverId = 2,
                amount = 1000.0, paymentType = PaymentType.G_PAY, paymentStatus = PaymentStatus.PENDING)
        private var paymentDto = PaymentDto(payerId = 1, billId = 1, receiverId = 2, amount = 1000.0, paymentType = PaymentType.G_PAY)
        private var user = User(userId = 1, emailId = "verma@gmail.com", name = "Amit Verma", contact = "8979710512")
        private var user1 = User(userId = 2, emailId = "amit@gmail.com", name = "Rajat Verma", contact = "8979710514")
        private var bill = Bill(billId = 1, ownerId = 1, billName = "party", description = "tour of goa", amount = 10000.0, date = LocalDateTime.now(), involvedUser = mutableListOf(user, user1))

    }


    @BeforeEach
    fun setUp() = MockitoAnnotations.initMocks(this)

    @Nested
    @DisplayName("settle the bill fully of partial")
    inner class Settle {

        @Test
        @DisplayName("pay the will partial or full")
        fun `settle the bill`() {
            `when`(modelMapper.map(paymentDto, Payment::class.java)).thenReturn(payment)
            `when`(billService.getBill(anyLong())).thenReturn(bill)
            `when`(transactionService.getAllTransactionOfBill(anyLong())).thenReturn(emptyList())
            `when`(paymentRepository.save(any(Payment::class.java))).thenReturn(payment)
            val payBill = paymentService.payBill(paymentDto)

            assertAll("check payment details",
                    { assertEquals(payBill.paymentStatus, PaymentStatus.COMPLETE) },
                    { assertEquals(payBill.amount, payment.amount) },
                    { assertEquals(payBill.receiverId, payment.receiverId) }
            )
        }

        @Test
        @DisplayName("pay bill more the share")
        fun `pay more then share`() {
            val payment1 = Payment(paymentId = 2, billId = 1, payerId = 1, receiverId = 2,
                    amount = 6000.0, paymentType = PaymentType.G_PAY, paymentStatus = PaymentStatus.PENDING)
            `when`(modelMapper.map(paymentDto, Payment::class.java)).thenReturn(payment1)
            `when`(transactionService.getAllTransactionOfBill(anyLong())).thenReturn(emptyList())
            `when`(billService.getBill(anyLong())).thenReturn(bill)

            val expectedException = assertThrows<PaymentException>("Should throw an exception") {
                paymentService.payBill(paymentDto)
            }
            assertAll("check exception",
                    {
                        assertEquals("You are paying amount more then due amount, due amount is : 5000.0"
                                , expectedException.message)
                    },
                    { assertEquals(payment1.paymentStatus, PaymentStatus.PENDING) }
            )
        }

        @Test
        @DisplayName("pay bill more the share")
        fun `settle bill more then one time`() {
            val previousPayment = Payment(paymentId = 2, billId = 1, payerId = 1, receiverId = 2,
                    amount = 2000.0, paymentType = PaymentType.G_PAY, paymentStatus = PaymentStatus.PENDING)
            `when`(modelMapper.map(paymentDto, Payment::class.java)).thenReturn(payment)
            `when`(transactionService.getAllTransactionOfBill(anyLong())).thenReturn(listOf(previousPayment))
            `when`(billService.getBill(anyLong())).thenReturn(bill)
            `when`(paymentRepository.save(any(Payment::class.java))).thenReturn(payment)
            val payBill = paymentService.payBill(paymentDto)
            assertAll("check payment details",
                    { assertEquals(PaymentStatus.COMPLETE, payBill.paymentStatus) },
                    { assertEquals(payment.amount, payBill.amount) },
                    { assertEquals(payment.receiverId, payBill.receiverId) },
                    { assertEquals(2000.0, payBill.paymentDue) }
            )
        }

        @Test
        @DisplayName("bill already paid")
        fun `bill already paid exception`() {
            val previousPayment = Payment(paymentId = 2, billId = 1, payerId = 1, receiverId = 2,
                    amount = 5000.0, paymentType = PaymentType.G_PAY, paymentStatus = PaymentStatus.PENDING)
            `when`(modelMapper.map(paymentDto, Payment::class.java)).thenReturn(payment)
            `when`(transactionService.getAllTransactionOfBill(anyLong())).thenReturn(listOf(previousPayment))
            `when`(billService.getBill(anyLong())).thenReturn(bill)
            `when`(paymentRepository.save(any(Payment::class.java))).thenReturn(payment)

            val expectedException = assertThrows<PaymentException>("Should throw an exception") {
                paymentService.payBill(paymentDto)
            }
            assertAll("check exception",
                    {assertEquals("You have already paid bill with billId ${bill.billId}"
                                , expectedException.message) },
                    { assertEquals(payment.paymentStatus, PaymentStatus.PENDING)}
            )
        }
    }

    @Test
    @DisplayName("get payments by payer id")
    fun getPaymentsByPayerId() {
        `when`(paymentRepository.findByPayerId(1)).thenReturn(listOf(payment))
        val paymentsByPayerId = paymentService.getPaymentsByPayerId(1)
        assertAll("check payments by payer id",
                { assertEquals(paymentsByPayerId.iterator().next().paymentId, payment.paymentId) },
                { assertEquals(paymentsByPayerId.toList().size, 1) }
        )
    }

    @Test
    @DisplayName("get payments by bill id")
    fun getPaymentsByBillId() {
        `when`(paymentRepository.findByBillId(1)).thenReturn(listOf(payment))
        val paymentsByPayerId = paymentService.getPaymentsByBillId(1)
        assertAll("check payments by payer id",
                { assertEquals(paymentsByPayerId.iterator().next().paymentId, payment.paymentId) },
                { assertEquals(paymentsByPayerId.toList().size, 1) },
                { assertEquals(paymentsByPayerId.toList().get(0).billId, payment.billId) }
        )
    }
}