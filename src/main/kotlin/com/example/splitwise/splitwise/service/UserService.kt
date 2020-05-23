package com.example.splitwise.splitwise.service

import com.example.splitwise.splitwise.dto.UserCreationDto
import com.example.splitwise.splitwise.dto.UserUpdateDto
import com.example.splitwise.splitwise.module.Bill
import com.example.splitwise.splitwise.module.User

interface UserService {
    fun create(userCreationDto: UserCreationDto): User
    fun updateDetails(userId: Long, requestUpdate: UserUpdateDto): User
    fun getUser(userId: Long): User
    fun getAllUser(): MutableIterator<User>
    fun userIdValidation(userId: Long)
    fun addUserBill(userId: Long, bill: Bill)
    fun getUserBills(userId: Long): MutableList<Bill>
}