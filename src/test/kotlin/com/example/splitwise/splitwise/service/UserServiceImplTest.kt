package com.example.splitwise.splitwise.service

import com.example.splitwise.splitwise.dto.UserDto
import com.example.splitwise.splitwise.enum.BillStatus
import com.example.splitwise.splitwise.module.Bill
import com.example.splitwise.splitwise.module.Group
import com.example.splitwise.splitwise.module.User
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@DisplayName("User service testing...........")
internal class UserServiceImplTest {

    var userService: UserServiceImpl? = null

    @BeforeEach
    fun setUp() {
        userService = UserServiceImpl()
        var userDto: UserDto = UserDto("Amit", "Verma", "verma@gmail.com", "8979710512")
        userService?.create(userDto)
    }

    @Test
    @DisplayName("Create the new user")
    fun create() {
        var userDto: UserDto = UserDto("Amit", "Verma", "amit@gmail.com", "8979710512")
        val response = userService?.create(userDto)
        assertTrue { userService?.userList?.size == 2 }
        assertEquals(userDto.email, (response?.objects as User).email)
    }

    @Test
    @DisplayName("Update the user details")
    fun updateDetails() {
        var userDto: UserDto = UserDto("Amit", "Kumar", "verma@gmail.com", "8979710512")
        val response = userService?.updateDetails("verma@gmail.com", userDto)
        assertTrue { userService?.userList?.size == 1 }
        assertEquals(userDto.lName, (response?.objects as User).lName)
    }

    @Test
    @DisplayName("Get the user details")
    fun getUser() {
        val user = userService?.getUser("verma@gmail.com")
        assertEquals(user?.fName, "Amit")
    }

    @Test
    @DisplayName("Get all users")
    fun getAllUser() {
        val allUser = userService?.getAllUser()
        assertTrue { allUser?.size == 1 }
        assertEquals(allUser?.elementAt(0)?.fName, "Amit")
    }

    @Test
    @DisplayName("Delete the users")
    fun deleteUser() {
        var userDto: UserDto = UserDto("Amit", "Kumar", "amit@gmail.com", "8979710512")
        userService?.create(userDto)
        val deleteUser = userService?.deleteUser("verma@gmail.com")
        assertTrue { userService?.userList?.size == 1 }
        assertEquals(deleteUser?.email, "verma@gmail.com")
    }

    @Test
    @DisplayName("Get all the group which user belong")
    fun getGroupList() {
        var user = User("Amit", "Verma", "kumar@gmail.com", "8979710512")
        var group = Group("Birthday Party", mutableListOf(user))
        userService?.userList?.get("verma@gmail.com")?.userGroup?.add(group)
        val groupList = userService?.getGroupList("verma@gmail.com")
        assertTrue { groupList?.size == 1 }
        assertEquals(groupList?.get(0)?.userList?.get(0)?.email, user.email)
    }

    @Test
    @DisplayName("Get debtors functionality")
    fun getDebtors() {
        var bill = Bill(1, "Party bill", 5000, Date(), mutableMapOf("kumar@gmail.com" to BillStatus.DUE))
        userService?.addDebtorBill("verma@gmail.com", bill)
        val debtors = userService?.getDebtors("verma@gmail.com")
        assertTrue { debtors?.size == 1 }
        assertEquals(debtors?.get(0)?.amount, 5000)
    }

    @Test
    @DisplayName("Get creditors functionality")
    fun getCreditors() {
        var bill = Bill(1, "Party bill", 5000, Date(), mutableMapOf("kumar@gmail.com" to BillStatus.DUE))
        userService?.addCreditorBill("verma@gmail.com", bill)
        val creditors = userService?.getCreditors("verma@gmail.com")
        assertTrue { creditors?.size == 1 }
        assertEquals(creditors?.get(0)?.amount, 5000)
    }

    @Test
    @DisplayName("Add new debtors bill")
    fun addDebtorBill() {
        var bill = Bill(1, "Party bill", 5000, Date(), mutableMapOf("kumar@gmail.com" to BillStatus.DUE))
        userService?.addDebtorBill("verma@gmail.com", bill)
        val debtors = userService?.getDebtors("verma@gmail.com")
        assertEquals(debtors?.get(0)?.amount, 5000)
    }

    @Test
    @DisplayName("Add new creditors bill")
    fun addCreditorBill() {
        var bill = Bill(1, "Party bill", 5000, Date(), mutableMapOf("kumar@gmail.com" to BillStatus.DUE))
        userService?.addCreditorBill("verma@gmail.com", bill)
        val creditors = userService?.getCreditors("verma@gmail.com")
        assertEquals(creditors?.get(0)?.amount, 5000)
    }
}