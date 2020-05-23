package com.example.splitwise.splitwise.repository

import com.example.splitwise.splitwise.module.Payment
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.stereotype.Repository

@Repository
@EnableJpaRepositories
interface PaymentRepository : JpaRepository<Payment, Long> {

    fun findByBillId(billId:Long) : Iterable<Payment>
    fun findByPayerId(userId:Long) : Iterable<Payment>
}