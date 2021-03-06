package com.example.splitwise.splitwise.service

import com.example.splitwise.splitwise.dto.request.PaymentDto
import com.example.splitwise.splitwise.enum.PaymentStatus
import com.example.splitwise.splitwise.exception.PaymentException
import com.example.splitwise.splitwise.module.Payment
import com.example.splitwise.splitwise.repository.PaymentRepository
import org.modelmapper.ModelMapper
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class PaymentServiceImpl(private val userBillService: UserBillService, private val modelMapper: ModelMapper,
                         private val billService: BillService, private val userService: UserService, private val paymentRepository: PaymentRepository) : PaymentService {

    companion object {
        private var log = LoggerFactory.getLogger(PaymentServiceImpl::class.java)
    }

    /**
     * function for pay the bill partial of full
     * @param paymentDto details of bill and user and amount etc..
     * @return payment receipt of payment
     */
    override fun payBill(paymentDto: PaymentDto): Payment {
        log.info("pay bill by user ${paymentDto.payerId} to user ${paymentDto.receiverId} for bill ${paymentDto.billId}")
        userService.userIdValidation(userId = paymentDto.payerId)
        userService.userIdValidation(userId = paymentDto.receiverId)
        billService.isBillExist(billId = paymentDto.billId)

        val payment = modelMapper.map(paymentDto, Payment::class.java)
        val userBill = userBillService.getUserBill(userId = paymentDto.payerId, billId = paymentDto.billId)

        if (userBill.paymentStatus == PaymentStatus.COMPLETE)
            throw PaymentException("The bill ${payment.billId} is not due for user id ${payment.payerId}")

        if (payment.amount > userBill.dueAmount)
            throw PaymentException("You are paying ${payment.amount} while due amount is : ${userBill.dueAmount}")

        userBill.dueAmount -= payment.amount
        if (userBill.dueAmount == 0.0)
            userBill.paymentStatus = PaymentStatus.COMPLETE

        payment.date = LocalDateTime.now()
        payment.paymentDue = userBill.dueAmount
        payment.paymentStatus = PaymentStatus.COMPLETE
        paymentRepository.save(payment)
        userBillService.saveBill(userBill = userBill)
        return payment
    }

    /**
     * function for getting the payments of a bill by user
     * @param userId user id of user
     * @param billId of bill which user want to take history
     * @return list of payments history
     */
    override fun getPaymentsOfBill(userId: Long, billId: Long): List<Payment> {
        log.info("get payments of bill $billId by user $userId")
        userService.userIdValidation(userId = userId)
        billService.isBillExist(billId = billId)
        return paymentRepository.findByPayerIdAndBillId(userId = userId, billId = billId).toList()
    }

    /**
     * fun for getting the all transaction of user
     *@param userId of user
     * @return list of payments history
     */
    override fun getAllTransactionByUserId(userId: Long): Iterable<Payment> {
        log.info("get all payment details by user id $userId")
        userService.userIdValidation(userId = userId)
        return paymentRepository.findAllByPayerIdOrReceiverId(payerId = userId,receiverId = userId)
    }

    /**
     * function for getting the all paid transaction of user
     * @param payerId payer id, who pay the bill
     * @return list of payment history
     */
    override fun getAllPaidTransactionByUserId(payerId:Long): List<Payment> {
        log.info("get all payments paid by user $payerId")
        userService.userIdValidation(userId = payerId)
        return paymentRepository.findAllByPayerId(payerId = payerId).toList()
    }

    /**
     * function for getting the all received transaction of user
     * @param receiverId receiver id, who pay the bill
     * @return list of payment history
     */
    override fun getAllReceivedTransactionByUserId(receiverId:Long): List<Payment> {
        log.info("get all payments paid by user $receiverId")
        userService.userIdValidation(userId = receiverId)
        return paymentRepository.findAllByReceiverId(receiverId = receiverId).toList()
    }

    /**
     * get all payments by bill id
     * @param billId bill id
     * @return list of payment history
     */
    override fun getPaymentsByBillId(billId: Long): Iterable<Payment> {
        log.info("get payment details by bill id $billId")
        billService.isBillExist(billId = billId)
        return paymentRepository.findAllByBillId(billId = billId)
    }

}