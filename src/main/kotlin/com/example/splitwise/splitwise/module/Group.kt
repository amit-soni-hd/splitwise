package com.example.splitwise.splitwise.module

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

        @ManyToMany(mappedBy = "groups")
        val involvedUser: MutableList<User> = mutableListOf(),

        @OneToMany(cascade = [(CascadeType.ALL)], fetch = FetchType.LAZY, mappedBy = "group")
        val bills: MutableList<Bill> = mutableListOf()
)