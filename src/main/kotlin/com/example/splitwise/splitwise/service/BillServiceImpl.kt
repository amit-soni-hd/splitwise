package com.example.splitwise.splitwise.service

import com.example.splitwise.splitwise.dto.request.BillGenerateDto
import com.example.splitwise.splitwise.dto.request.BillUpdateDto
import com.example.splitwise.splitwise.dto.request.IncludeUserOnBillDto
import com.example.splitwise.splitwise.enum.BillStatus
import com.example.splitwise.splitwise.enum.PaymentStatus
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
class BillServiceImpl(private val modelMapper: ModelMapper, private val billRepository: BillRepository, private val userService: UserService, private val userBillService: UserBillService) : BillService {

    /**
     * object of logger for print the logs
     */
    companion object {
        private val log: Logger = LoggerFactory.getLogger(BillServiceImpl::class.java)
    }

    /**
     * function to generate the bill
     * @param billGenerateDto object which contain the details of bill
     * @return Bill return the bill object after save
     */
    override fun generateBill(billGenerateDto: BillGenerateDto): Bill {
        log.info("generate bill with details {}", billGenerateDto)
        val bill = modelMapper.map(billGenerateDto, Bill::class.java)
        validateUser(billGenerateDto.involvedUserIds)
        bill.noOfUser = billGenerateDto.involvedUserIds?.size?.toLong()!!
        val save = billRepository.save(bill)
        addUserBills(billGenerateDto.involvedUserIds, save)
        return save;
    }

    /**
     * function for include the new user
     * @param includeUserOnBillDto details for adding new user of a bill
     * @return bill which we include the user
     */
    override fun includeNewUsers(includeUserOnBillDto: IncludeUserOnBillDto): Bill {
        isBillExist(billId = includeUserOnBillDto.billId)
        includeUserOnBillDto.usersId.forEach { userId -> userService.userIdValidation(userId = userId) }
        val bill = billRepository.findById(includeUserOnBillDto.billId).get()
        bill.noOfUser += includeUserOnBillDto.usersId.size
        updateOldUsersBill(bill = bill)
        addUserBills(userIds = includeUserOnBillDto.usersId, bill = bill)
        return bill
    }

    /**
     * fun for adding the bill
     * @param userIds ids of user which belong to bill
     * @param billId id of bill
     * @return Unit
     */
    private fun addUserBills(userIds: List<Long>?, bill: Bill) {

        val mutableList: MutableList<UserBill> = mutableListOf()
        userIds?.forEach { id ->
            if (bill.ownerId != id)
                run {
                    mutableList.add(UserBill(userId = id, billId = bill.billId,
                            userShare = bill.amount.div(bill.noOfUser), dueAmount = bill.amount.div(bill.noOfUser),
                            ownerId = bill.ownerId))
                }
        }
        userBillService.saveAllBill(mutableList)
    }

    /**
     * fun for validate the user
     * @param userIds list of user ids which we have to validate
     * @return Unit
     */
    private fun validateUser(userIds: List<Long>?) {
        log.info("validate the users $userIds")
        userIds?.forEach { userId ->
            userService.userIdValidation(userId)
        }
    }

    /**
     * fun for getting the bill
     * @param billId id of bill
     * @return object of required bill
     */
    override fun getBill(billId: Long): Bill {
        log.info("get bill with id $billId")
        isBillExist(billId = billId)
        return billRepository.findById(billId).get()
    }

    /**
     * fun for check the bill if exist or not
     * @param billId id of bill
     * @throws BillNotFoundException if bill does not exist
     * @return Unit
     */
    override fun isBillExist(billId: Long) {
        log.info("check bill validation with id $billId")
        val existsById = billRepository.existsById(billId)
        if (!existsById)
            throw BillNotFoundException("Bill does not exist with id $billId")
    }


    /**
     * function for update the bill
     * @param billUpdateDto details of updating the bill
     * @return Bill return the bill object after update
     */
    override fun updateBill(billUpdateDto: BillUpdateDto): Bill {
        log.info("update the bill with details $billUpdateDto")
        isBillExist(billId = billUpdateDto.billId)
        val bill = billRepository.findById(billUpdateDto.billId).get()

        if (billUpdateDto.amount != null)
            bill.amount = billUpdateDto.amount!!

        if (billUpdateDto.billName != null)
            bill.description = billUpdateDto.description!!

        if (billUpdateDto.date != null)
            bill.date = billUpdateDto.date!!

        if (billUpdateDto.description != null)
            bill.description = billUpdateDto.description!!

        updateOldUsersBill(bill = bill)
        return billRepository.save(bill)
    }

    /**
     * function for delete the bill
     * @param billId id of bill
     * @return true if successfully deleted otherwise false
     */
    override fun deleteBill(billId: Long): Bill {
        log.info("delete bill with id $billId")
        isBillExist(billId = billId)
        val bill = billRepository.findById(billId).get()
        bill.billStatus = BillStatus.DELETED
        return billRepository.save(bill)
    }

    /**
     * function for undo the bill
     * @param billId bill id
     * @return bill
     */
    override fun undoBill(billId: Long): Bill {
        isBillExist(billId = billId)
        val bill = billRepository.findById(billId).get()
        bill.billStatus = BillStatus.PRESENT
        return billRepository.save(bill)
    }

    /**
     * function for update old users bill after adding new user in same bill
     * @param bill object of bill
     * @return
     */
    private fun updateOldUsersBill(bill: Bill) {
        val usersBill = userBillService.getUserBillsByBillId(billId = bill.billId)
        val usersUpdateBill = mutableListOf<UserBill>()
        usersBill.forEach { userBill ->
            run {
                val paidBalance = userBill.userShare - userBill.dueAmount
                userBill.userShare = bill.amount.div(bill.noOfUser)
                userBill.dueAmount = userBill.userShare - paidBalance
                if (userBill.dueAmount <= 0)
                    userBill.paymentStatus = PaymentStatus.COMPLETE
                else
                    userBill.paymentStatus = PaymentStatus.PENDING
                usersUpdateBill.add(userBill)
            }
        }
        userBillService.saveAllBill(usersUpdateBill)
    }

}