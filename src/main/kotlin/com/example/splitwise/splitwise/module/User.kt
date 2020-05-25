package com.example.splitwise.splitwise.module

import com.fasterxml.jackson.annotation.JsonIdentityInfo
import com.fasterxml.jackson.annotation.ObjectIdGenerators
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import org.hibernate.annotations.Fetch
import org.hibernate.annotations.FetchMode
import javax.persistence.*

@Entity
@Table(name = "user_table")
data class User(

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "user_id")
        var userId: Long,

        @Column(name = "user_email", nullable = false, unique = true)
        var emailId: String,

        @Column(name = "user_name", nullable = false)
        var name: String,

        @Column(name = "user_contact", nullable = false, unique = true)
        var contact: String

)
