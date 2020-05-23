package com.example.splitwise.splitwise.module

import com.example.splitwise.splitwise.enum.PaymentStatus
import com.example.splitwise.splitwise.enum.PaymentType
import com.fasterxml.jackson.annotation.JsonProperty
import javax.persistence.*

@Entity
@Table(name = "payment_table")
data class Payment(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "payment_id")
        val paymentId: Long,

        @Column(name = "bill_id", nullable = false)
        val billId: Long,

        @Column(name = "payer_id", nullable = false)
        val payerId: Long,

        @Column(name = "receiver_id", nullable = false)
        val receiverId: Long,

        @Column(name = "amount", nullable = false)
        val amount: Double,

        @Column(name = "payment_type", nullable = false)
        val paymentType: PaymentType,

        @Column(name = "payment_status")
        var paymentStatus: PaymentStatus = PaymentStatus.PENDING
)