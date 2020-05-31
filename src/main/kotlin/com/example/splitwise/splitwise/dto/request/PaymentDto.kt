package com.example.splitwise.splitwise.dto.request

import com.example.splitwise.splitwise.enum.PaymentStatus
import com.example.splitwise.splitwise.enum.PaymentType
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.EnumType
import javax.persistence.Enumerated

data class PaymentDto(

        @JsonProperty("payment_id", required = false)
        var paymentId:Long = 0,

        @JsonProperty("bill_id", required = true)
        var billId: Long,

        @JsonProperty("payer_id", required = true)
        var payerId: Long,

        @JsonProperty("receiver_id", required = true)
        var receiverId: Long,

        @JsonProperty("amount", required = true)
        var amount: Double,

        @JsonProperty("payment_type", required = true)
        var paymentType: PaymentType,

        @JsonProperty("payment_due",required = false)
        var paymentDue: Double? = null,

        @JsonProperty("date",required = false)
        var date:LocalDateTime?=null,

        @JsonProperty("payment_status",required = false)
        @Enumerated(value = EnumType.STRING)
        var paymentStatus: PaymentStatus = PaymentStatus.PENDING
) 