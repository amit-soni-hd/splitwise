package com.example.splitwise.splitwise.service

import com.example.splitwise.splitwise.module.Bill
import com.example.splitwise.splitwise.module.Group
import com.example.splitwise.splitwise.module.User
import com.example.splitwise.splitwise.dto.UserDto
import com.example.splitwise.splitwise.module.Response

interface UserService {
    fun create(userDto: UserDto): Response
    fun updateDetails(userEmail: String, requestUser: UserDto): Response
    fun getUser(userEmail: String): User?
    fun getAllUser(): MutableCollection<User>?
    fun getGroupList(userEmail: String): MutableList<Group>?
    fun getDebtors(userEmail: String): MutableList<Bill>?
    fun getCreditors(userEmail: String): MutableList<Bill>?
    fun addDebtorBill(userEmail: String, bill: Bill): Boolean?
    fun addCreditorBill(userEmail: String, bill: Bill): Boolean?
}