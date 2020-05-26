package com.example.splitwise.splitwise.repository

import com.example.splitwise.splitwise.enum.PaymentStatus
import com.example.splitwise.splitwise.module.UserBill
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
@EnableJpaRepositories
interface UserBillRepository : JpaRepository<UserBill, Long> {
    fun findAllByBillId(billId: Long): Iterable<UserBill>
    fun findAllByUserId(userId: Long): Iterable<UserBill>
    fun findByUserIdAndBillId(userId: Long, billId: Long): Optional<UserBill>
    fun findAllByUserIdAndOwnerId(userId: Long,ownerId:Long) : Iterable<UserBill>

    @Query(nativeQuery = true, value = "select * from user_bill where (userId = :user_id or owner_id = :user_id) and payment_status = :payment_status")
    fun findUserPendingAndUserOwnerBills(@Param(value = "user_id") userId: Long, @Param(value = "payment_status") paymentStatus: PaymentStatus): Iterable<UserBill>
}
