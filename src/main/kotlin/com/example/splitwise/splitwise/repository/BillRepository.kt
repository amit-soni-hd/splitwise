package com.example.splitwise.splitwise.repository

import com.example.splitwise.splitwise.enum.BillStatus
import com.example.splitwise.splitwise.enum.PaymentStatus
import com.example.splitwise.splitwise.module.Bill
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
@EnableJpaRepositories
interface BillRepository : JpaRepository<Bill, Long> {

    @Query(nativeQuery = true,value = "select * from bill where bill_id = :id and bill_status = 'PRESENT' ")
    fun findByIdAndBillStatus(@Param(value = "id") id: Long) : Optional<Bill>
}