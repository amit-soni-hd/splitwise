package code.service

import code.module.Bill
import code.module.Group
import code.module.User
import com.example.splitwise.splitwise.dto.UserDto

interface UserService {
    fun create(userDto: UserDto): User?
    fun updateDetails(requestUser: UserDto) : User?
    fun getUser(userEmail:String):User?
    fun getAllUser(): MutableCollection<User>?
    fun getGroupList(userEmail: String): MutableList<Group>?
    fun getDebtors(userEmail:String): MutableList<Bill>?
    fun getCreditors(userEmail:String): MutableList<Bill>?
    fun addDebtorBill(userEmail: String, bill:Bill): Boolean?
    fun addCreditorBill(userEmail: String, bill: Bill): Boolean?
}