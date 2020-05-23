package com.example.splitwise.splitwise.module

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "bill_table")
data class Bill(

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "bill_id")
        val billId: Long,

        @Column(name = "owner_id")
        val ownerId:Long,

        @Column(name = "bill_name")
        var billName:String,

        @Column(name = "bill_description")
        var description: String,

        @Column(name = "bill_amount")
        var amount: Double,

        @Column(name = "date_time")
        var date: LocalDateTime,

        @ManyToMany(mappedBy = "bills")
        var involvedUser: MutableList<User> = mutableListOf(),

        @ManyToOne(fetch = FetchType.LAZY, optional = false)
        @JoinColumn(name = "group_id")
        var group:Group? = null
)

