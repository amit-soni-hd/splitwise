package com.example.splitwise.splitwise.service

import com.example.splitwise.splitwise.module.UserBill
import com.example.splitwise.splitwise.repository.UserBillRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.assertAll
import org.mockito.ArgumentMatchers.anyLong
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

internal class UserBillServiceImplTest {

    @InjectMocks
    private lateinit var userBillService: UserBillServiceImpl

    @Mock
    private lateinit var userBillRepository: UserBillRepository

    companion object {
        private var userBill1 = UserBill(id = 1,userId = 1,billId =  1)
        private var userBill2 = UserBill(id = 2,userId = 2,billId =  2)

    }

    @BeforeEach
    fun setUp() = MockitoAnnotations.initMocks(this)

    @Test
    fun getUserIdsByBillId() {
        `when`(userBillRepository.findAllByBillId(anyLong())).thenReturn(listOf(userBill1, userBill2))
        val billIdsByUserId = userBillService.getUserIdsByBillId(billId = 1)
        assertAll("check user ids",
                { assertEquals(2, billIdsByUserId.size)},
                { assertEquals(userBill1.billId, billIdsByUserId.get(0).billId)}
        )
    }

    @Test
    fun getBillIdsByUserId() {
        `when`(userBillRepository.findAllByUserId(anyLong())).thenReturn(listOf(userBill1, userBill2))
        val billIdsByUserId = userBillService.getBillIdsByUserId(userId = 1)
        assertAll("check user ids",
                { assertEquals(2, billIdsByUserId.size)},
                { assertEquals(userBill1.billId, billIdsByUserId.get(0).billId)}
        )
    }
}