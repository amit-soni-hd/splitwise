package com.example.splitwise.splitwise.service

import com.example.splitwise.splitwise.dto.request.UserCreationDto
import com.example.splitwise.splitwise.dto.request.UserUpdateDto
import com.example.splitwise.splitwise.module.User

interface UserService {
    fun create(userCreationDto: UserCreationDto): User
    fun updateDetails(userId: Long, requestUpdate: UserUpdateDto): User
    fun getUserById(userId: Long): User
    fun getAllUser(): MutableIterator<User>
    fun userIdValidation(userId: Long)
    fun getUserByEmail(emailId: String): User
    fun getUserByContact(contact: String): User
    fun deleteUser(userId: Long): Boolean
    fun userContactValidation(contact: String)
    fun userEmailValidation(emailId: String)
    fun validateUsers(users:List<Long>)
}