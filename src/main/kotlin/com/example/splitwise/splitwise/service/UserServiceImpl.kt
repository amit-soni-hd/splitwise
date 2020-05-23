package com.example.splitwise.splitwise.service

import com.example.splitwise.splitwise.dto.UserCreationDto
import com.example.splitwise.splitwise.dto.UserUpdateDto
import com.example.splitwise.splitwise.exception.UserExistException
import com.example.splitwise.splitwise.exception.UserNotFoundException
import com.example.splitwise.splitwise.module.Bill
import com.example.splitwise.splitwise.module.User
import com.example.splitwise.splitwise.repository.UserRepository
import org.modelmapper.ModelMapper
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(private val userRepository: UserRepository, private val modelMapper: ModelMapper) : UserService {

    companion object {
        private val log: Logger = LoggerFactory.getLogger(UserServiceImpl::class.java)
    }

    override fun create(userCreationDto: UserCreationDto): User {
        log.info("Creating user with email id : ", userCreationDto.emailId)
        userValidation(userCreationDto)
        val newUser = modelMapper.map(userCreationDto, User::class.java)
        this.userValidation(userCreationDto);
        return userRepository.save(newUser)
    }

    private fun userValidation(userCreationDto: UserCreationDto) {
        log.info("Validation user with details {}", userCreationDto)
        userContactValidation(userCreationDto.contact)
        userEmailValidation(userCreationDto.emailId)
    }

    private fun userEmailValidation(emailId: String) {
        log.info("validate user email id {}", emailId)
        val present = userRepository.findByEmailId(emailId).isPresent
        if (present)
            throw UserExistException("user already exist with email $emailId")
    }

    private fun userContactValidation(contact: String) {
        log.info("validate user contact  {}", contact)
        val present = userRepository.findByContact(contact).isPresent
        if (present)
            throw UserExistException("user already exist with contact $contact")
    }

    override fun userIdValidation(userId: Long) {
        log.info("Validating user id {}", userId)
        val existsById = userRepository.existsById(userId)
        if (!existsById)
            throw UserNotFoundException("User does not exist with id $userId")
    }


    override fun updateDetails(userId: Long, requestUpdate: UserUpdateDto): User {

        val user = userRepository.findById(userId).orElseGet(null)
                ?: throw UserNotFoundException("User does not exist with id $userId")

        if (requestUpdate.emailId != null) {
            this.userEmailValidation(requestUpdate.emailId!!)
            user.emailId = requestUpdate.emailId!!
        }

        if (requestUpdate.contact != null) {
            this.userContactValidation(requestUpdate.contact!!)
            user.contact = requestUpdate.contact!!
        }

        if (requestUpdate.name != null) {
            user.name = requestUpdate.name!!
        }
        return userRepository.save(user)

    }

    override fun getUserById(userId: Long): User {
        log.info("Get user by id {}", userId)
        return userRepository.findById(userId).orElseGet(null)
                ?: throw UserNotFoundException("User does not exist with id $userId")
    }

    override fun getAllUser(): MutableIterator<User> {
        log.info("Get all user ")
        return userRepository.findAll().iterator()
    }

    fun deleteUser(userId: Long): Boolean {
        log.info("Deleting user with id {}", userId)
        if (userRepository.existsById(userId))
            userRepository.deleteById(userId)
        else throw UserNotFoundException("User doesn't  exist with id $userId")
        return true
    }

    override fun addUserBill(userId: Long, bill: Bill) {
        log.info("Add bill with user id $userId and bill $bill")
        userIdValidation(userId)
        val user = userRepository.findById(userId).get()
        user.bills.add(bill)
        userRepository.save(user)
    }

    override fun getUserBills(userId: Long): MutableList<Bill> {
        log.info("Get user bill with user id $userId")
        userIdValidation(userId = userId)
        return userRepository.findById(userId).get().bills
    }

    override fun getUserByEmail(emailId: String): User {
        return userRepository.findByEmailId(email = emailId).orElseGet(null)
                ?: throw UserNotFoundException("user does not exist with email $emailId")
    }

    override fun getUserByContact(contact: String): User {
        return userRepository.findByContact(contact = contact).orElseGet(null)
                ?: throw UserNotFoundException("user does not exist with contact no $contact")
    }

}