package com.example.splitwise.splitwise.module

import com.example.splitwise.splitwise.enum.PaymentStatus
import com.example.splitwise.splitwise.enum.PaymentType
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime
import javax.persistence.*

@Entity
data class Payment(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "payment_id")
        var paymentId: Long,

        @Column(name = "bill_id", nullable = false)
        var billId: Long,

        @Column(name = "payer_id", nullable = false)
        var payerId: Long,

        @Column(name = "receiver_id", nullable = false)
        var receiverId: Long,

        @Column(name = "amount", nullable = false)
        var amount: Double,

        @Column(name = "payment_type", nullable = false)
        var paymentType: PaymentType,

        @Column(name = "payment_due")
        var paymentDue: Double? = null,

        @Column(name = "date")
        var date:LocalDateTime?=null,

        @Column(name = "payment_status")
        @Enumerated(value = EnumType.STRING)
        var paymentStatus: PaymentStatus = PaymentStatus.PENDING
)