package com.example.splitwise.splitwise.service

import com.example.splitwise.splitwise.module.Bill
import com.example.splitwise.splitwise.module.Group
import com.example.splitwise.splitwise.module.User
import com.example.splitwise.splitwise.dto.UserDto
import com.example.splitwise.splitwise.module.Response
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service

@Service
class UserServiceImpl() : UserService {

    var userList: MutableMap<String, User> = mutableMapOf()

    override fun create(userDto: UserDto): Response {

        if (!userList.containsKey(userDto.email)) {
            var user = User()
            user.email = userDto.email
            user.contact = userDto.contact
            user.lName = userDto.lName
            user.fName = userDto.fName
            userList.put(userDto.email, user)
            return Response("Successfully created", user, HttpStatus.CREATED)
        }
        return Response("User already exist", userList.get(userDto.email)!!, HttpStatus.NOT_ACCEPTABLE)
    }


    override fun updateDetails(userEmail: String, requestUser: UserDto): Response {

            val user = userList[userEmail]
            user?.fName = requestUser.fName
            user?.lName = requestUser.lName
            user?.contact = requestUser.contact

        return if(user!=null)
            Response("Successfully created", user, HttpStatus.ACCEPTED)
        else
            Response("Successfully created", UserDto::class, HttpStatus.ACCEPTED)

    }

    override fun getUser(userEmail: String): User? {
        return userList.get(userEmail)
    }

    override fun getAllUser(): MutableCollection<User>? {
        return userList.values
    }

    fun deleteUser(emailId: String): User? {
        return userList.remove(emailId)
    }

    override fun getGroupList(userEmail: String): MutableList<Group>? {
        return userList.get(userEmail)?.userGroup
    }

    override fun getDebtors(userEmail: String): MutableList<Bill>? {
        return userList.get(userEmail)?.debtorsBill
    }

    override fun getCreditors(userEmail: String): MutableList<Bill>? {
        return userList.get(userEmail)?.creditorsBill
    }

    override fun addDebtorBill(userEmail: String, bill: Bill): Boolean? {
        return userList.get(userEmail)?.debtorsBill?.add(bill)
    }

    override fun addCreditorBill(userEmail: String, bill: Bill): Boolean? {
        return userList.get(userEmail)?.creditorsBill?.add(bill)
    }

}