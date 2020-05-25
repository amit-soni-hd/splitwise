package com.example.splitwise.splitwise.repository

import com.example.splitwise.splitwise.module.UserBill
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.stereotype.Repository

@Repository
@EnableJpaRepositories
interface UserBillRepository:JpaRepository<UserBill,Long>  {
    fun findAllByBillId(billId: Long) : Iterable<UserBill>
    fun findAllByUserId(userId: Long) : Iterable<UserBill>
}