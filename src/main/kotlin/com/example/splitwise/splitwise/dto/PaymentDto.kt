package com.example.splitwise.splitwise.dto

import com.example.splitwise.splitwise.enum.PaymentStatus
import com.example.splitwise.splitwise.enum.PaymentType
import com.fasterxml.jackson.annotation.JsonProperty

data class PaymentDto(

        @JsonProperty("bill_id", required = true)
        val billId: Long,

        @JsonProperty("payer_id", required = true)
        val payerId: Long,

        @JsonProperty("receiver_id", required = true)
        val receiverId: Long,

        @JsonProperty("amount", required = true)
        val amount: Double,

        @JsonProperty("payment_type", required = true)
        val paymentType: PaymentType,

        @JsonProperty("payment_status")
        var paymentStatus: PaymentStatus = PaymentStatus.PENDING
) 