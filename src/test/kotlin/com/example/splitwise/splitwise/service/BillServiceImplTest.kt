package com.example.splitwise.splitwise.service

import com.example.splitwise.splitwise.dto.BillDto
import com.example.splitwise.splitwise.dto.UserDto
import com.example.splitwise.splitwise.enum.BillStatus
import com.example.splitwise.splitwise.module.Bill
import com.example.splitwise.splitwise.module.User
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import java.util.*

@DisplayName("Bill service testing...........")
internal class BillServiceImplTest {

    var billService:BillServiceImpl? = null
    var userService:UserServiceImpl? = null

    @BeforeEach
    fun setUp() {
        billService = BillServiceImpl()
        userService = UserServiceImpl()
        var user1 = User("Amit", "Verma", "amit@gmail.com", "8979710512")
        var user2 = User("Rajat", "Verma", "rajat@gmail.com", "8979710512")
        var user3 = User("Tushar", "Verma", "tushar@gmail.com", "8979710512")
        var user4 = User("Ankur", "Verma", "ankur@gmail.com", "8979710512")
        userService?.userList?.putAll(mapOf(Pair(user1.email!!, user1), Pair(user2.email!!, user2),
                Pair(user3.email!!, user3), Pair(user4.email!!, user4)))

    }

    @Test
    @DisplayName("Generate new bill")
    fun generateBill() {
        var billDto = BillDto(1, "Party bill", 5000, Date(),
                mutableMapOf("rajat@gmail.com" to BillStatus.DUE))
        val generateBill = billService?.generateBill("amit@gmail.com", billDto)
        assertEquals(billDto.billId, generateBill?.billId)
    }

    @Test
    @DisplayName("Get bill by bill id")
    fun getBill() {
        var billDto = BillDto(1, "Party bill", 5000, Date(),
                mutableMapOf("kumar@gmail.com" to BillStatus.DUE))
        billService?.generateBill("verma@gmail.com", billDto)
        val bill = billService?.getBill(1)
        assertEquals(bill?.description, billDto.description)
    }
}