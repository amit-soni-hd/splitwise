package com.example.splitwise.splitwise.module

import com.fasterxml.jackson.databind.annotation.JsonSerialize
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "group_table")
data class Group(

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "group_id")
        val groupId: Long,

        @Column(name = "group_name")
        var groupName: String,

        @Column(name = "created_date_time")
        var date: LocalDateTime,

        @ManyToMany(mappedBy = "groups", fetch = FetchType.EAGER)
        val involvedUser: MutableList<User> = mutableListOf(),

        @OneToMany(cascade = [(CascadeType.ALL)], fetch = FetchType.LAZY, mappedBy = "group")
        val bills: MutableList<Bill> = mutableListOf()
)