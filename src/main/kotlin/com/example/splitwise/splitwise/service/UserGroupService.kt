package com.example.splitwise.splitwise.service

import com.example.splitwise.splitwise.dto.request.UserGroupDto
import com.example.splitwise.splitwise.module.Bill
import com.example.splitwise.splitwise.module.Group

interface UserGroupService {
    fun createGroup(userGroupDto: UserGroupDto) : Group
    fun addGroupBill(groupId: Long, billId: Long) : Bill
}