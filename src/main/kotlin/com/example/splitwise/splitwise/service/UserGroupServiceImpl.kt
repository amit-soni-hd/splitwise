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
class UserGroupServiceImpl(private val billService: BillService, private val groupRepository: GroupRepository, private val userService: UserService, private val userGroupRepository: UserGroupRepository) : UserGroupService {

    companion object {
        private var log: Logger = LoggerFactory.getLogger(UserGroupServiceImpl::class.java)
    }

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

    override fun addGroupBill(groupId: Long, billId: Long): Bill {
        isGroupExist(groupId = groupId)
        billService.isBillExist(billId = billId)
        val bill = billService.getBill(billId = billId)
        val usersGroupDetail = userGroupRepository.findAllByGroupId(groupId = groupId)
        usersGroupDetail.forEach { userGroup ->
            run {
                userGroup.involvedBills.add(bill)
            }
            userGroupRepository.save(userGroup)
        }
        return bill
    }

    private fun isGroupExist(groupId: Long) {
        val existsById = groupRepository.existsById(groupId)
        if (!existsById)
            throw GroupNotFoundException("Group not found with id $groupId")
    }
}