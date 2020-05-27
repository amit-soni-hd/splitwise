package com.example.splitwise.splitwise.module

import com.fasterxml.jackson.annotation.JsonIdentityInfo
import com.fasterxml.jackson.annotation.ObjectIdGenerators
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "group_table")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator::class,
        property = "groupId")
data class Group(

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "group_id")
        var groupId: Long = 0,

        @Column(name = "group_name")
        var groupName: String,

        @Column(name = "created_date_time")
        var date: LocalDateTime

)