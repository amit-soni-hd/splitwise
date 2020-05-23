package com.example.splitwise.splitwise.repository

import com.example.splitwise.splitwise.module.Bill
import com.example.splitwise.splitwise.module.Payment
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
@EnableJpaRepositories
interface PaymentRepository : CrudRepository<Payment, Long> {

    fun findByBillId(billId:Long) : Iterable<Payment>
    fun findByPayerId(userId:Long) : Iterable<Payment>
}