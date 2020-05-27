package com.example.splitwise.splitwise.service

import com.example.splitwise.splitwise.enum.PaymentStatus
import com.example.splitwise.splitwise.module.UserBill
import com.example.splitwise.splitwise.repository.UserBillRepository
import org.springframework.stereotype.Service

@Service
class UserBillServiceImpl(private val userBillRepository: UserBillRepository) : UserBillService {

    /**
     * function of get user bills  by  bill id
     * means getting all users who involved in this bill
     * @param billId bill id
     * @return list of user bill Object
     */
    override fun getUserBillsByBillId(billId: Long): List<UserBill> {
        return userBillRepository.findAllByBillId(billId = billId).toList()
    }

    /**
     * getting of users bill by user id
     * @param userId user id
     * @return list of user bills
     */
    override fun getUserBillsByUserId(userId: Long): List<UserBill> {
        return userBillRepository.findAllByUserId(userId = userId).toList()
    }

    /**
     * get user bill by user id and bill id
     * @param userId
     * @param billId
     * @return user's user bill
     */
    override fun getUserBill(userId: Long, billId: Long): UserBill {
        return userBillRepository.findByUserIdAndBillId(userId = userId, billId = billId).get()
    }

    /**
     * function for save the user bill
     * @param userBill
     * @return saved bill
     *
     */
    override fun saveBill(userBill: UserBill): UserBill {
        return userBillRepository.save(userBill)
    }

    /**
     * function of save all bills
     * @param usersBill list of user bill
     * @return
     */
    override fun saveAllBill(usersBill: MutableList<UserBill>) {
        userBillRepository.saveAll(usersBill)
    }

    /**
     * fun for getting the user's pending and user's generate bills
     * @param userId user id
     * @return list of user bill
     */
    override fun getUserPendingAndUserOwnerBills(userId: Long): List<UserBill> {
        return userBillRepository
                .findUserPendingAndUserOwnerBills(userId = userId, paymentStatus = PaymentStatus.PENDING).toList()
    }

    /**
     * fun for getting the common bill
     * @param userId user id
     * @param ownerId bill owner id
     */
    override fun getCommonBill(userId: Long, ownerId: Long): List<UserBill> {
        return userBillRepository.findAllByUserIdAndOwnerId(userId = userId, ownerId = ownerId).toList()
    }


}