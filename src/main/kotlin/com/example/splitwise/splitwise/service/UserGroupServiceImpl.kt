package com.example.splitwise.splitwise.service

import com.example.splitwise.splitwise.dto.request.UserGroupDto
import com.example.splitwise.splitwise.exception.GroupNotFoundException
import com.example.splitwise.splitwise.module.Bill
import com.example.splitwise.splitwise.module.Group
import com.example.splitwise.splitwise.module.UserGroup
import com.example.splitwise.splitwise.repository.GroupRepository
import com.example.splitwise.splitwise.repository.UserGroupRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class UserGroupServiceImpl(private val userBillService: UserBillService, private val billService: BillService, private val groupRepository: GroupRepository, private val userService: UserService, private val userGroupRepository: UserGroupRepository) : UserGroupService {

    companion object {
        private var log: Logger = LoggerFactory.getLogger(UserGroupServiceImpl::class.java)
    }

    /**
     * function for creating the group
     * @param userGroupDto details for creating the group
     * @return object of created group
     */
    override fun createGroup(userGroupDto: UserGroupDto): Group {
        userService.validateUsers(users = userGroupDto.users)
        val group = groupRepository.save(Group(groupName = userGroupDto.groupName, date = LocalDateTime.now()))

        userGroupDto.users.forEach { usersId ->
            run {
                val userGroup = UserGroup(groupId = group.groupId, userId = usersId)
                userGroupRepository.save(userGroup)
            }
        }
        return group
    }

    /**
     * fun for adding the bill in group
     * @param groupId id of group
     * @param billId id of bill
     */
    override fun addGroupBill(groupId: Long, billId: Long): Bill {
        isGroupExist(groupId = groupId)
        billService.isBillExist(billId = billId)
        val bill = billService.getBill(billId = billId)
        val group = groupRepository.findById(groupId).get()
        group.involvedBills.add(bill)
        bill.group = group
        billService.saveBill(bill = bill)
        groupRepository.save(group)
        return bill
    }

    /**
     * private function for checking the bill is available or not
     * @param groupId id of group
     * @throws throw GroupNotFoundException if group does not exist
     */
    private fun isGroupExist(groupId: Long) {
        val existsById = groupRepository.existsById(groupId)
        if (!existsById)
            throw GroupNotFoundException("Group not found with id $groupId")
    }

    /**
     * fun for get debts of a user
     * @param groupId id of group
     * @return mutable map of details of debts of a group
     */
    override fun getDebts(groupId: Long): MutableMap<Long, Double> {
        log.info("getting the group debts with group id $groupId")
        isGroupExist(groupId = groupId)
        val balances: MutableMap<Long, Double> = mutableMapOf()
        val groupBills = groupRepository.findById(groupId).get().involvedBills.toList()

        groupBills.forEach { bill ->
            val userBillsByBillId = userBillService.getUserBillsByBillId(billId = bill.billId)
            userBillsByBillId.forEach { userBill ->
                run {
                    if(!balances.containsKey(userBill.ownerId))
                        balances[userBill.ownerId] = 0.0
                    if(!balances.containsKey(userBill.userId))
                        balances[userBill.userId] = 0.0
                    balances[userBill.ownerId] = balances[userBill.ownerId]!! + userBill.dueAmount
                    balances[userBill.userId] = balances[userBill.userId]!! - userBill.dueAmount
                }
            }
        }
        return balances
    }
}