package com.example.splitwise.splitwise.repository

import com.example.splitwise.splitwise.module.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.stereotype.Repository
import java.util.*

@Repository
@EnableJpaRepositories
interface UserRepository : JpaRepository<User, Long> {
    fun findByEmailId(email: String) : Optional<User>
    fun findByContact(contact: String) : Optional<User>
}