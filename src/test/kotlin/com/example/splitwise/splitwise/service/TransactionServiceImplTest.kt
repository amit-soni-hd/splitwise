package com.example.splitwise.splitwise.service

import com.example.splitwise.splitwise.enum.PaymentStatus
import com.example.splitwise.splitwise.enum.PaymentType
import com.example.splitwise.splitwise.module.Payment
import com.example.splitwise.splitwise.repository.PaymentRepository
import com.nhaarman.mockito_kotlin.doNothing
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.mockito.ArgumentMatchers.anyLong
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import kotlin.test.assertEquals

@DisplayName("test transaction service")
class TransactionServiceImplTest {

    @InjectMocks
    private lateinit var transactionService: TransactionServiceImpl

    @Mock
    private lateinit var userService: UserServiceImpl
    @Mock
    private lateinit var billService: BillServiceImpl
    @Mock
    private lateinit var paymentRepository: PaymentRepository

    companion object {
        var payment = Payment(paymentId = 1, billId = 1, payerId = 1, receiverId = 1,
                amount = 500.0, paymentType = PaymentType.PAY_TM, paymentStatus = PaymentStatus.COMPLETE)
    }

    @BeforeEach
    fun setUp() = MockitoAnnotations.initMocks(this)


    @Test
    @DisplayName("get all transaction of user")
    fun getAllTransaction() {
        `when`(paymentRepository.findByPayerId(anyLong())).thenReturn(listOf(payment))
        val allTransaction = transactionService.getAllTransaction(1)
        assertAll("check user transactions",
                { assertEquals(allTransaction.get(0).amount, payment.amount)},
                { assertEquals(allTransaction.size, 1)}
        )

    }

    @Test
    @DisplayName("get all transaction of bill")
    fun getAllTransactionOfBill() {
        doNothing().`when`(billService).isBillExist(anyLong())
        `when`(paymentRepository.findByBillId(anyLong())).thenReturn(listOf(payment))
        val allTransactionOfBill = transactionService.getAllTransactionOfBill(1)
        assertAll("check all transaction of bill",
                { assertEquals(allTransactionOfBill.get(0).paymentId, payment.paymentId)},
                { assertEquals(allTransactionOfBill.size, 1)}
        )
    }
}