package com.example.splitwise.splitwise.module

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "bill")
data class Bill(

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "bill_id")
        var billId: Long,

        @Column(name = "owner_id")
        var ownerId:Long,

        @Column(name = "bill_name")
        var billName:String,

        @Column(name = "bill_description")
        var description: String,

        @Column(name = "bill_amount")
        var amount: Double,

        @Column(name = "date_time")
        var date: LocalDateTime,

        @Column(name = "no_of_user")
        var noOfUser:Long = 0

)

