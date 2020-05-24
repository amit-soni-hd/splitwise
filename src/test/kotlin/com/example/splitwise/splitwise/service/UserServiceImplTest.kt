package com.example.splitwise.splitwise.service

import com.example.splitwise.splitwise.dto.UserCreationDto
import com.example.splitwise.splitwise.dto.UserUpdateDto
import com.example.splitwise.splitwise.module.Bill
import com.example.splitwise.splitwise.module.User
import com.example.splitwise.splitwise.repository.UserRepository
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.anyLong
import org.mockito.ArgumentMatchers.anyString
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.modelmapper.ModelMapper
import java.time.LocalDateTime
import java.util.*

@ExtendWith
@DisplayName("Testing user service functionality..............")
internal class UserServiceImplTest {


    @InjectMocks
    private lateinit var userService: UserServiceImpl

    @Mock
    private lateinit var userRepository: UserRepository

    @Mock
    private lateinit var modelMapper: ModelMapper

    companion object {
        var bill = Bill(billId = 1, ownerId = 1, billName = "party", description = "tour of goa", amount = 10000.0, date = LocalDateTime.now())
        var userCreationDto = UserCreationDto(emailId = "verma@gmail.com", contact = "8979710512", name = "Amit Verma")
        var userUpdateDto = UserUpdateDto(emailId = "amit@gmail.com", contact = "9045621234", name = "Amit Soni")
        var user = User(userId = 1, emailId = "verma@gmail.com", name = "Amit Verma", contact = "8979710512")
    }


    @BeforeEach
    fun setUp() = MockitoAnnotations.initMocks(this)

    @Test
    @DisplayName("New User creation")
    fun create() {
        `when`(userRepository.findByContact(anyString())).thenReturn(Optional.empty())
        `when`(userRepository.findByEmailId(anyString())).thenReturn(Optional.empty())
        `when`(modelMapper.map(userCreationDto, User::class.java)).thenReturn(user)
        `when`(userRepository.save(user)).thenReturn(user)
        val newUser = userService.create(userCreationDto)
        assertAll("For create new User",
                { assertEquals(userCreationDto.emailId, newUser.emailId) },
                { assertEquals(user.userId, newUser.userId) }
        )

    }

    @Test
    @DisplayName("Validate id of user")
    fun userIdValidation() {
        `when`(userRepository.existsById(anyLong())).thenReturn(true)
        userService.userIdValidation(1)
        verify(userRepository, times(1)).existsById(anyLong())
    }

    @Test
    @DisplayName("Update the user details")
    fun updateDetails() {
        `when`(userRepository.existsById(anyLong())).thenReturn(true)
        `when`(userRepository.findById(anyLong())).thenReturn(Optional.of(user))
        `when`(userRepository.save(user)).thenReturn(user)
        val updateDetails = userService.updateDetails(userId = 1, requestUpdate = userUpdateDto)
        assertAll("user updation check",
                { assertEquals(updateDetails.emailId, userUpdateDto.emailId) },
                { assertEquals(updateDetails.userId, user.userId) }
        )
    }

    @Test
    @DisplayName("Get all user")
    fun getAllUser() {
        `when`(userRepository.findAll()).thenReturn(listOf(user))
        val allUser = userService.getAllUser()
        assertAll("check all user",
                { assertEquals(allUser.next().emailId, user.emailId) },
                { assertTrue { listOf(allUser).size == 1 } }
        )
    }

    @Test
    @DisplayName("Delete user by id")
    fun deleteUser() {
        `when`(userRepository.existsById(anyLong())).thenReturn(true)
        val deleteUser = userService.deleteUser(1)
        assertTrue { deleteUser }
    }

    @Test
    @DisplayName("add the new bill for the user")
    fun addUserBill() {

        `when`(userRepository.existsById(anyLong())).thenReturn(true)
        `when`(userRepository.findById(anyLong())).thenReturn(Optional.of(user))
        `when`(userRepository.save(user)).thenReturn(user)
        val addUserBill = userService.addUserBill(userId = 1, bill = bill)

        assertAll("add new bill",
                { assertEquals(addUserBill.bills.get(0).amount, bill.amount) },
                { assertEquals(addUserBill.emailId, user.emailId) }
        )

    }

    @Test
    @DisplayName("Get user bills")
    fun getUserBills() {
        `when`(userRepository.existsById(anyLong())).thenReturn(true)
        `when`(userRepository.findById(anyLong())).thenReturn(Optional.of(user))
        val userBills = userService.getUserBills(1)
        assertAll("get user bills",
                { assertTrue(userBills.size == 1) },
                { assertEquals(userBills.get(0).billName, bill.billName) }
        )

    }

    @Test
    @DisplayName("validate the user email")
    fun userEmailValidation() {
        `when`(userRepository.findByEmailId(anyString())).thenReturn(Optional.empty())
        userService.userEmailValidation(user.emailId)
        verify(userRepository, times(1)).findByEmailId(anyString())
    }

    @Test
    @DisplayName("validate the user contact")
    fun userContactValidation() {
        `when`(userRepository.findByContact(anyString())).thenReturn(Optional.empty())
        userService.userContactValidation(user.contact)
        verify(userRepository, times(1)).findByContact(anyString())
    }

    @Test
    @DisplayName("get user by id")
    fun getUserById() {
        `when`(userRepository.existsById(anyLong())).thenReturn(true)
        `when`(userRepository.findById(anyLong())).thenReturn(Optional.of(user))
        val (_, emailId) = userService.getUserById(1)
        assertEquals(user.emailId, emailId)
    }

    @Test
    @DisplayName("get user by email")
    fun getUserByEmail() {
        `when`(userRepository.findByEmailId(anyString())).thenReturn(Optional.of(user))
        val response = userService.getUserByEmail("verma@gmail.com")
        assertAll("Check user get by email",
                { assertEquals(response.contact, user.contact) },
                { assertEquals(response.userId, user.userId) },
                { assertEquals(response.emailId, user.emailId) }
        )
    }

    @Test
    @DisplayName("get user by contact no")
    fun getUserByContact() {
        `when`(userRepository.findByContact(anyString())).thenReturn(Optional.of(user))
        val response = userService.getUserByContact("1")
        assertAll("Check user get by email",
                { assertEquals(response.contact, user.contact) },
                { assertEquals(response.userId, user.userId) },
                { assertEquals(response.emailId, user.emailId) }
        )
    }
}

