package com.example.splitwise.splitwise.service

import com.example.splitwise.splitwise.dto.request.UserGroupDto
import com.example.splitwise.splitwise.enum.PaymentStatus
import com.example.splitwise.splitwise.exception.GroupNotFoundException
import com.example.splitwise.splitwise.exception.PaymentException
import com.example.splitwise.splitwise.module.Bill
import com.example.splitwise.splitwise.module.Group
import com.example.splitwise.splitwise.module.UserBill
import com.example.splitwise.splitwise.module.UserGroup
import com.example.splitwise.splitwise.repository.GroupRepository
import com.example.splitwise.splitwise.repository.UserGroupRepository
import org.junit.jupiter.api.*

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Assertions.assertThrows
import org.mockito.ArgumentMatchers
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import java.time.LocalDateTime

@DisplayName("testing the user group service")
internal class UserGroupServiceImplTest {

    @InjectMocks
    private lateinit var userGroupService: UserGroupServiceImpl

    @Mock
    private lateinit var groupRepository: GroupRepository

    @Mock
    private lateinit var userGroupRepository: UserGroupRepository

    @Mock
    private lateinit var userService: UserServiceImpl

    @Mock
    private lateinit var billService: BillServiceImpl

    @Mock
    private lateinit var userBillService: UserBillServiceImpl

    companion object {
        private var userGroupDto = UserGroupDto(groupName = "party boy", users = listOf(1, 2, 3))
        private var group = Group(groupId = 1, groupName = "party boy", date = LocalDateTime.now())
        private var bill1 = Bill(billId = 1, ownerId = 1, billName = "party", description = "tour of goa", amount = 400.0, date = LocalDateTime.now())
        private var bill2 = Bill(billId = 2, ownerId = 2, billName = "party", description = "tour of goa", amount = 200.0, date = LocalDateTime.now())
        private var userGroup = UserGroup(groupId = 1, userId = 1)
        private var userGroup1 = UserGroup(groupId = 1, userId = 1, involvedBills = mutableListOf(bill1, bill2))
        private var userGroup2 = UserGroup(groupId = 1, userId = 2, involvedBills = mutableListOf(bill2, bill2))
        private var userBill1 = UserBill(id = 2, userId = 2, billId = 1, ownerId = 1, userShare = 200.0,dueAmount = 200.0, paymentStatus = PaymentStatus.PENDING)
        private var userBill2 = UserBill(id = 1, userId = 1, billId = 2, ownerId = 2, userShare = 100.0,dueAmount = 100.0, paymentStatus = PaymentStatus.PENDING)


    }

    @BeforeEach
    fun setUp() = MockitoAnnotations.initMocks(this)

    @Test
    @DisplayName("create the group")
    fun createGroup() {
        `when`(groupRepository.save(any(Group::class.java))).thenReturn(group)
        val createGroup = userGroupService.createGroup(userGroupDto = userGroupDto)
        assertAll("verify group",
                { assertEquals(group.groupId, createGroup.groupId) }
        )
    }

    @Test
    @DisplayName("add bill in group")
    fun addGroupBill() {
        `when`(groupRepository.existsById(anyLong())).thenReturn(true)
        `when`(billService.getBill(anyLong())).thenReturn(bill1)
        `when`(userGroupRepository.findAllByGroupId(anyLong())).thenReturn(listOf(userGroup))
        val addGroupBill = userGroupService.addGroupBill(groupId = 1, billId = 1)
        assertAll("verify bill",
                { assertEquals(bill1.billId, addGroupBill.billId) }
        )
    }

    @Test
    @DisplayName("group does not found")
    fun groupNotFoundException() {
        val expectedException = assertThrows<GroupNotFoundException>("Should throw an exception") {
            val addGroupBill = userGroupService.addGroupBill(groupId = 1, billId = 1)
        }

        assertAll("test exception message",
                { assertEquals("Group not found with id 1", expectedException.message) }
        )
    }

    @Test
    @DisplayName("get debts of a group")
    fun getDebts() {
        `when`(groupRepository.existsById(anyLong())).thenReturn(true)
        `when`(userGroupRepository.findAllByGroupId(anyLong())).thenReturn(listOf(userGroup1, userGroup2))
        `when`(userBillService.getUserBill(1, 1)).thenReturn(userBill1)
        `when`(userBillService.getUserBill(1, 2)).thenReturn(userBill2)
        `when`(userBillService.getUserBill(2, 1)).thenReturn(userBill1)
        `when`(userBillService.getUserBill(2, 2)).thenReturn(userBill2)
        val debts = userGroupService.getDebts(1)

        assertAll("check balance",
                { assertEquals(100.0, debts[1]) },
                {assertEquals(200.0, debts[2])}
        )
    }
}
