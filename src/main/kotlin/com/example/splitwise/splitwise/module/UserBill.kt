package com.example.splitwise.splitwise.module

import javax.persistence.*

@Entity
class UserBill(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id")
        var id: Long = 0,

        @Column(name = "user_Id")
        var userId:Long,
        @Column(name = "bill_Id")
        var billId:Long
)