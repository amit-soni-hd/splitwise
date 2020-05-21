package code.service

import code.module.User
import com.example.splitwise.splitwise.dto.UserDto

interface UserService {
    fun create(userDto: UserDto): User?
    fun updateDetails(requestUser: UserDto) : User?
    fun getUser(userEmail:String):User?
    fun getAllUser(): MutableCollection<User>?
}