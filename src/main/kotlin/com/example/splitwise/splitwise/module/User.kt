package com.example.splitwise.splitwise.module

import javax.persistence.*

@Entity
@Table(name = "user_table")
data class User(

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "user_id")
        val userId: Long,

        @Column(name = "user_email", nullable = false, unique = true)
        var emailId: String,

        @Column(name = "user_name", nullable = false)
        var name: String,

        @Column(name = "user_contact", nullable = false, unique = true)
        var contact: String,

        @ManyToMany(cascade = arrayOf(CascadeType.ALL))
        @JoinTable(name = "user_bills",
                joinColumns = arrayOf(JoinColumn(name = "user_id", referencedColumnName = "user_id")),
                inverseJoinColumns = arrayOf(JoinColumn(name = "bill_id", referencedColumnName = "bill_id")))
        val bills: MutableList<Bill> = mutableListOf(),

        @ManyToMany(cascade = arrayOf(CascadeType.ALL))
        @JoinTable(name = "user_groups",
                joinColumns = arrayOf(JoinColumn(name = "user_id", referencedColumnName = "user_id")),
                inverseJoinColumns = arrayOf(JoinColumn(name = "group_id", referencedColumnName = "group_id")))
        val groups: MutableList<Group> = mutableListOf()
)
