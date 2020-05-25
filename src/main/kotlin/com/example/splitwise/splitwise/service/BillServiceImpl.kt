package com.example.splitwise.splitwise.service

import com.example.splitwise.splitwise.dto.BillGenerateDto
import com.example.splitwise.splitwise.dto.BillUpdateDto
import com.example.splitwise.splitwise.exception.BillNotFoundException
import com.example.splitwise.splitwise.module.Bill
import com.example.splitwise.splitwise.module.UserBill
import com.example.splitwise.splitwise.repository.BillRepository
import com.example.splitwise.splitwise.repository.UserBillRepository
import org.modelmapper.ModelMapper
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class BillServiceImpl(private val modelMapper: ModelMapper, private val billRepository: BillRepository, private val userService: UserService, private val userBillRepository: UserBillRepository) : BillService {

    companion object {
        private val log: Logger = LoggerFactory.getLogger(BillServiceImpl::class.java)
    }

    override fun generateBill(billGenerateDto: BillGenerateDto): Bill {
        log.info("generate bill with details {}", billGenerateDto)
        var bill = modelMapper.map(billGenerateDto, Bill::class.java)
        validateUser(billGenerateDto.involvedUserIds)
        bill.noOfUser = billGenerateDto.involvedUserIds?.size?.toLong()!!
        val save = billRepository.save(bill)
        addUserBills(billGenerateDto.involvedUserIds, billId = save.billId)
        return save;
    }

    private fun addUserBills(userIds: List<Long>?, billId: Long) {

        var mutableList: MutableList<UserBill> = mutableListOf()
        userIds?.forEach { id ->
            run {
                mutableList.add(UserBill(0, id, billId))
            }
        }
        userBillRepository.saveAll(mutableList);
    }

    private fun validateUser(userIds: List<Long>?) {
        log.info("validate the users $userIds")
        userIds?.forEach { userId ->
            userService.userIdValidation(userId)
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
        billRepository.save(bill)
        return bill

    }


}