package code.service

import code.module.Bill
import code.module.Group
import code.module.User
import com.example.splitwise.splitwise.dto.UserDto

class UserServiceImpl() : UserService {

    var userList: MutableMap<String, User>? = null

    init {
        userList = mutableMapOf()
    }

    override fun create(userDto: UserDto): User? {

        if (!userList?.containsKey(userDto.email)!!) {
            var user = User()
            user.email = userDto.email
            user.contact = userDto.phone
            user.lName = userDto.lName
            user.fName = userDto.fName
            userList?.put(userDto.email!!, user)
            return user
        }
        return userList?.get(userDto.email)
    }


    override fun updateDetails(requestUser: UserDto): User? {

        val user = userList?.get(requestUser.email)
        if (user != null) {
            user.fName = requestUser.fName
            user.lName = requestUser.lName
            user.contact = requestUser.phone
            return user
        }
        return null
    }

    override fun getUser(userEmail: String): User? {
        return userList?.get(userEmail)
    }

    override fun getAllUser(): MutableCollection<User>? {
        return userList?.values
    }

    fun deleteUser(emailId: String): User? {
        val remove = userList?.remove(emailId)
        return remove
    }

    fun getGroupList(userEmail: String): MutableList<Group>? {
        return userList?.get(userEmail)?.userGroup
    }

    fun getDebtors(userEmail:String): MutableList<Bill>? {
        return userList?.get(userEmail)?.debtorsBill
    }

    fun getCreditors(userEmail:String): MutableList<Bill>? {
        return userList?.get(userEmail)?.creditorsBill
    }

    fun addDebtorBill(userEmail: String, bill:Bill): Boolean? {
        return userList?.get(userEmail)?.debtorsBill?.add(bill)
    }

    fun addCreditorBill(userEmail: String, bill: Bill): Boolean? {
        return userList?.get(userEmail)?.creditorsBill?.add(bill)
    }

}