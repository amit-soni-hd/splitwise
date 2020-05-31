package com.example.splitwise.splitwise.module

import com.example.splitwise.splitwise.enum.BillStatus
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
        var ownerId: Long,

        @Column(name = "bill_name")
        var billName: String,

        @Column(name = "bill_description")
        var description: String,

        @Column(name = "bill_amount")
        var amount: Double,

        @Column(name = "date_time")
        var date: LocalDateTime,

        @Column(name = "no_of_user")
        var noOfUser: Long = 0,

        @Column(name = "bill_status")
        @Enumerated(value = EnumType.STRING)
        var billStatus: BillStatus = BillStatus.PRESENT,

        @ManyToOne(cascade = arrayOf(CascadeType.ALL), fetch = FetchType.EAGER)
        @JoinColumn(name = "group_id")
        var group: Group? = null

)

