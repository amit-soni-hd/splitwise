package com.example.splitwise.splitwise.repository

import com.example.splitwise.splitwise.module.Payment
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.stereotype.Repository

@Repository
@EnableJpaRepositories
interface PaymentRepository : JpaRepository<Payment, Long> {

    fun findAllByBillId(billId: Long): Iterable<Payment>
    fun findAllByPayerIdOrReceiverId(payerId: Long, receiverId: Long): Iterable<Payment>
    fun findAllByPayerId(payerId: Long): Iterable<Payment>
    fun findAllByReceiverId(receiverId: Long): Iterable<Payment>
    fun findByPayerIdAndBillId(userId: Long, billId: Long): Iterable<Payment>
}