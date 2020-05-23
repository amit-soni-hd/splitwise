package com.example.splitwise.splitwise.module

import com.example.splitwise.splitwise.enum.PaymentStatus
import javax.persistence.*

@Entity
@Table(name = "payment")
data class Payment(

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "bill_id",nullable = false)
        val billId: Long,

        @Column(name = "payer_id",nullable = false)
        val payerId: Long,

        @Column(name = "receiver_id",nullable = false)
        val receiverId: Long,

        @Column(name = "amount",nullable = false)
        val amount: Double,

        @Column(name = "payment_status")
        var paymentStatus: PaymentStatus = PaymentStatus.PENDING
)