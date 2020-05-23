package com.example.splitwise.splitwise.repository

import com.example.splitwise.splitwise.module.Bill
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.stereotype.Repository

@Repository
@EnableJpaRepositories
interface BillRepository : JpaRepository<Bill, Long> {
}