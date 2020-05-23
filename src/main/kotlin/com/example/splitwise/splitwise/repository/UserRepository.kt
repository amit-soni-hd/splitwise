package com.example.splitwise.splitwise.repository

import com.example.splitwise.splitwise.module.User
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
@EnableJpaRepositories
interface UserRepository : CrudRepository<User, Long> {
    fun findByEmailIdOrContact(email: String?, contact:String?): Optional<User>
    fun findByEmailId(email: String) : Optional<User>
    fun findByContact(contact: String) : Optional<User>
}