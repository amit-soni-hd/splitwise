package com.example.splitwise.splitwise.repository

import com.example.splitwise.splitwise.module.Payment
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
@EnableJpaRepositories
interface PaymentRepository : CrudRepository<Payment, Long> {
}