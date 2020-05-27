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

    @Query(nativeQuery = true, value = "select * from user_bill where bill_id = :bill_id and bill_status = \"PRESENT\"")
    fun findAllByBillId(@Param(value = "bill_id") billId: Long): Iterable<UserBill>

    @Query(nativeQuery = true, value = "select * from user_bill where user_id = :user_id and bill_status = \"PRESENT\"")
    fun findAllByUserId(@Param("user_id") userId: Long): Iterable<UserBill>

    @Query(nativeQuery = true, value = "select * from user_bill where user_id = :user_id and bill_id = :bill_id and bill_status = \"PRESENT\"")
    fun findByUserIdAndBillId(@Param(value = "user_id") userId: Long, @Param(value = "bill_id") billId: Long): Optional<UserBill>

    @Query(nativeQuery = true, value = "select * from user_bill where user_id = :user_id and owner_id = :owner_id and bill_status = \"PRESENT\"")
    fun findAllByUserIdAndOwnerId(@Param(value = "user_id") userId: Long, @Param(value = "owner_id") ownerId:Long) : Iterable<UserBill>

    @Query(nativeQuery = true, value = "select * from user_bill where (userId = :user_id or owner_id = :user_id) and payment_status = :payment_status and bill_status = \"PRESENT\"")
    fun findUserPendingAndUserOwnerBills(@Param(value = "user_id") userId: Long, @Param(value = "payment_status") paymentStatus: PaymentStatus): Iterable<UserBill>
}
