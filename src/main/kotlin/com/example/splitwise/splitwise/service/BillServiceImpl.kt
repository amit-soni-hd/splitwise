package com.example.splitwise.splitwise.service

import com.example.splitwise.splitwise.dto.BillGenerateDto
import com.example.splitwise.splitwise.dto.BillUpdateDto
import com.example.splitwise.splitwise.exception.BillNotFoundException
import com.example.splitwise.splitwise.module.Bill
import com.example.splitwise.splitwise.module.User
import com.example.splitwise.splitwise.repository.BillRepository
import org.modelmapper.ModelMapper
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class BillServiceImpl(val modelMapper: ModelMapper, val billRepository: BillRepository, val userService: UserService) : BillService {

    companion object {
        private val log: Logger = LoggerFactory.getLogger(BillServiceImpl::class.java)
    }

    override fun generateBill(billGenerateDto: BillGenerateDto): Bill {
        log.info("generate bill with details $billGenerateDto")
        val bill = modelMapper.map(billGenerateDto, Bill::class.java)
        validateUser(billGenerateDto.involvedUser)
        billRepository.save(bill)
        splitBill(bill = bill)
        return bill
    }

    private fun validateUser(users: List<User>) {
        log.info("validate the users $users")
        users.forEach { user ->
            userService.userIdValidation(user.userId)
        }
    }

    override fun getBill(billId: Long): Bill {
        log.info("get bill with id $billId")
        isBillExist(billId = billId)
        return billRepository.findById(billId).get()
    }

    override fun isBillExist(billId: Long) {
        log.info("check bill validation with id $billId")
        val existsById = billRepository.existsById(billId)
        if(!existsById)
            throw BillNotFoundException("Bill does not exist with id $billId")
    }

    private fun splitBill(bill: Bill) {
        log.info("Split the bill in users ${bill.involvedUser}")
        bill.involvedUser
                .forEach { user ->
                    userService.addUserBill(userId = user.userId, bill = bill)
                }
    }

    override fun updateBill(billUpdateDto: BillUpdateDto): Bill {
        log.info("update the bill with details $billUpdateDto")
        val bill = billRepository.findById(billUpdateDto.billId).orElse(null)
                ?: throw BillNotFoundException("Bill doesn't exist with id ${billUpdateDto.billId}")

        if (billUpdateDto.amount != null) {
            bill.amount = billUpdateDto.amount!!
        }
        if (billUpdateDto.billName != null) {
            bill.description = billUpdateDto.description!!
        }
        if (billUpdateDto.date != null) {
            bill.date = billUpdateDto.date!!
        }
        if (billUpdateDto.description != null) {
            bill.description = billUpdateDto.description!!
        }
        if (billUpdateDto.involvedUser != null) {
            bill.involvedUser = billUpdateDto.involvedUser!!
        }
        if (billUpdateDto.group != null) {
            bill.group = billUpdateDto.group
        }
        billRepository.save(bill)
        return bill

    }


}