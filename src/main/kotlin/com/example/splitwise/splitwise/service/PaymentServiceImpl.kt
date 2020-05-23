package com.example.splitwise.splitwise.service

import com.example.splitwise.splitwise.dto.PaymentDto
import com.example.splitwise.splitwise.enum.PaymentStatus
import com.example.splitwise.splitwise.module.Payment
import com.example.splitwise.splitwise.repository.PaymentRepository
import org.modelmapper.ModelMapper
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.lang.RuntimeException

@Service
class PaymentServiceImpl(private val transactionService: TransactionService, private val modelMapper: ModelMapper,
                         private val billService: BillService, val userService: UserService, private val paymentRepository: PaymentRepository) : PaymentService {

    companion object {
        private var log = LoggerFactory.getLogger(PaymentServiceImpl::class.java)
    }
    override fun payBill(paymentDto: PaymentDto): Payment {
        log.info("pay bill by user ${paymentDto.payerId} to user ${paymentDto.receiverId} for bill ${paymentDto.billId}")
        userService.userIdValidation(userId = paymentDto.payerId)
        userService.userIdValidation(userId = paymentDto.receiverId)
        billService.isBillExist(billId = paymentDto.billId)

        var payment = modelMapper.map(paymentDto, Payment::class.java)
        val bill = billService.getBill(billId = paymentDto.billId)
        var userShare = bill.amount.div(bill.involvedUser.size)
        var paid = findPaidAmount(bill.billId, paymentDto.payerId)
        var dueAmount = userShare.minus(paid)

        if (payment.amount - dueAmount > 0)
            throw RuntimeException("You are paying amount more then due amount, due amount is : ${dueAmount}")

        if (dueAmount > 0) {
            payment.paymentStatus = PaymentStatus.COMPLETE
            return paymentRepository.save(payment)
        }
        throw RuntimeException("You have already paid bill with billId ${bill.billId}")

    }

    private fun findPaidAmount(billId: Long, payerId: Long): Double {
        log.info("get the paid balance for user $payerId for bill $billId")
        val allTransactionOfBill = transactionService.getAllTransactionOfBill(billId = billId)
        return allTransactionOfBill
                .filter { payment: Payment -> payment.payerId == payerId }
                .map { payment: Payment -> payment.amount }
                .sum()

    }

}